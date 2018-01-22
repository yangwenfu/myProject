package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.api.dto.BsSignInDto;
import com.xinyunlian.jinfu.api.validate.OpenApi;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.dto.bscontract.CerApplWrap;
import com.xinyunlian.jinfu.common.dto.bscontract.RegUserWrap;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.RandomUtil;
import com.xinyunlian.jinfu.contract.dto.BsCertApplReq;
import com.xinyunlian.jinfu.contract.dto.BsRegUserReq;
import com.xinyunlian.jinfu.contract.dto.UserBestSignDto;
import com.xinyunlian.jinfu.contract.service.BestSignService;
import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserExtService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by dongfangchao on 2017/3/29/0029.
 */
@RestController
@RequestMapping("open-api/contract")
public class ApiContractController {

    @Autowired
    private BestSignService bestSignService;

    @Autowired
    private UserExtService userExtService;

    @Autowired
    private UserService userService;

    private static final String RET_CODE = "100000";

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiContractController.class);

    @OpenApi
    @RequestMapping(value = "bsSignIn", method = RequestMethod.POST)
    public ResultDto signInAndCheck(@RequestBody @Valid BsSignInDto req, BindingResult result){
        try {
            if (result.hasErrors()){
                return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
            }

            //用户地址信息检测
            UserBestSignDto userBestSignDto = bestSignService.getUserBestSignByUserId(req.getJinfuUserId());
            UserInfoDto userInfoDto = userService.findUserByUserId(req.getJinfuUserId());
            UserExtDto userExtDto = userExtService.findUserByUserId(req.getJinfuUserId());
            String province = AppConfigUtil.getConfig("best.sign.province");
            String city = AppConfigUtil.getConfig("best.sign.city");
            String area = AppConfigUtil.getConfig("best.sign.area");
            String address = AppConfigUtil.getConfig("best.sign.address");
            if (userExtDto == null && StringUtils.isEmpty(userExtDto.getProvince()) && StringUtils.isEmpty(userExtDto.getCity())
                    && StringUtils.isEmpty(userExtDto.getArea()) && StringUtils.isEmpty(userExtDto.getAddress())){
                province = userExtDto.getProvince();
                city = userExtDto.getCity();
                area = userExtDto.getArea();
                address = userExtDto.getAddress();
            }

            if (userBestSignDto == null){

                //上上签注册用户
                BsRegUserReq bsRegUserReq = new BsRegUserReq();
                bsRegUserReq.setEmail(userInfoDto.getEmail());
                bsRegUserReq.setMobile(userInfoDto.getMobile());
                bsRegUserReq.setName(userInfoDto.getUserName());
                String regUserJson = bestSignService.regUser(bsRegUserReq);

                RegUserWrap regUserWrap = JsonUtil.toObject(RegUserWrap.class, regUserJson);
                if (!RET_CODE.equals(regUserWrap.getResp().getInfo().getCode())){
                    return ResultDtoFactory.toNack("上上签用户注册失败");
                }

                UserBestSignDto addUserBestSignDto = new UserBestSignDto();
                addUserBestSignDto.setBestSignUid(regUserWrap.getResp().getContent().getUid());
                addUserBestSignDto.setUserId(req.getJinfuUserId());
                addUserBestSignDto.setBestSignCa(false);
                bestSignService.addUserBestSign(addUserBestSignDto);

            }

            if (userBestSignDto == null || !userBestSignDto.getBestSignCa()){
                UserBestSignDto dbUserBestSign = bestSignService.getUserBestSignByUserId(req.getJinfuUserId());

                //上上签申请个人CA证书
                BsCertApplReq bsCertApplReq = new BsCertApplReq();
                bsCertApplReq.setName(userInfoDto.getUserName());
                bsCertApplReq.setPassword(RandomUtil.getNumberStr(10));
                bsCertApplReq.setMobile(userInfoDto.getMobile());
                bsCertApplReq.setIdentity(userInfoDto.getIdCardNo());

                StringBuilder addressBuilder = new StringBuilder();
                addressBuilder.append(province)
                        .append(city)
                        .append(area)
                        .append(address);
                bsCertApplReq.setAddress(addressBuilder.toString());
                bsCertApplReq.setProvince(province);
                bsCertApplReq.setCity(city);

                String cerApplJson = bestSignService.certificateApply(bsCertApplReq);
                CerApplWrap cerApplWrap = JsonUtil.toObject(CerApplWrap.class, cerApplJson);
                if (!cerApplWrap.getResult()){
                    return ResultDtoFactory.toNack("上上签用户申请CA证书失败");
                }

                dbUserBestSign.setBestSignCa(true);

                bestSignService.updateUserBestSign(dbUserBestSign);
            }

            return ResultDtoFactory.toAck("成功");
        } catch (Exception e) {
            LOGGER.error("上上签注册异常", e);
            return ResultDtoFactory.toNack("上上签注册异常");
        }
    }

}
