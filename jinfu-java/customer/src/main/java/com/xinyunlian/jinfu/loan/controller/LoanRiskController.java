package com.xinyunlian.jinfu.loan.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.contract.dto.UserContractDto;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.loan.dto.risk.AuthorizeDto;
import com.xinyunlian.jinfu.spider.dto.req.AuthLoginDto;
import com.xinyunlian.jinfu.spider.dto.resp.RiskUserInfoDto;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author willwang
 */

@Controller
@RequestMapping(value = "loan/risk")
public class LoanRiskController {
    @Autowired
    private RiskUserInfoService riskUserInfoService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private UserService userService;

    @Autowired
    private StoreService storeService;

    @RequestMapping(value = "get/user", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto getUser() {
        String userId = SecurityContext.getCurrentUserId();
        RiskUserInfoDto riskUserInfoDto = riskUserInfoService.getUserInfo(userId);
        AuthorizeDto authorizeDto = ConverterService.convert(riskUserInfoDto, AuthorizeDto.class);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), authorizeDto);
    }

    /**
     * 授权认证
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "authorize/user", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto authorizeUser(@RequestBody AuthorizeDto user) {
        String userId = SecurityContext.getCurrentUserId();
        List<StoreInfDto> storeList = storeService.findByUserId(userId);
        if(CollectionUtils.isEmpty(storeList)){
            return ResultDtoFactory.toNack("请先添加店铺");
        }

        boolean rs = this.getAuthLoginResult(user);
        //合同签订
        this.saveContractPCSQ(userId);
        if(rs) {
            return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), rs);
        } else {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("tobacco.authorization.failed"), rs);
        }
    }

    /**
     * 验证登录有效性，申请时调用
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "validate/user", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto validateUser(@RequestBody AuthorizeDto user) {

        String userId = SecurityContext.getCurrentUserId();

        boolean rs = this.getAuthLoginResult(user);

        //异步抓一把
        riskUserInfoService.spiderUserInfo(userId);


        if(rs) {
            return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), rs);
        } else {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("tobacco.authorization.failed"), rs);
        }
    }

    private boolean getAuthLoginResult(AuthorizeDto user) {
        String userId = SecurityContext.getCurrentUserId();
        AuthLoginDto authLoginDto = ConverterService.convert(user, AuthLoginDto.class);
        authLoginDto.setUserId(userId);
        List<StoreInfDto> storeList = storeService.findByUserId(userId);
        StoreInfDto store = storeList.get(0);
        for(StoreInfDto storeInfDto : storeList) {
            if(user.getUsername().equals(storeInfDto.getTobaccoCertificateNo())){
                store = storeInfDto;
                break;
            }
        };
        if(StringUtils.isNotEmpty(store.getProvinceId())){
            authLoginDto.setProvinceId(Long.parseLong(store.getProvinceId()));
        }
        if(StringUtils.isNotEmpty(store.getCityId())) {
            authLoginDto.setCityId(Long.parseLong(store.getCityId()));
        }
        if(StringUtils.isNotEmpty(store.getCityId())) {
            authLoginDto.setAreaId(Long.parseLong(store.getAreaId()));
        }
        return riskUserInfoService.authLogin(authLoginDto);
    }

    /**
     * 爬虫授权
     *
     * @return
     */
    @RequestMapping(value = "contract/PCSQ", method = RequestMethod.GET)
    @ResponseBody
    public String getContractPCSQ() {
        UserContractDto userContractDto = new UserContractDto();
        userContractDto.setTemplateType(ECntrctTmpltType.XSMSQ);
        return contractService.getContract(userContractDto).getContent();
    }

    private void saveContractPCSQ(String userId) {
        UserContractDto userContractDto = new UserContractDto();
        userContractDto.setUserId(userId);
        userContractDto.setTemplateType(ECntrctTmpltType.XSMSQ);
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUserId());
        UserContractDto existsContract = contractService.getUserContract(userId, ECntrctTmpltType.XSMSQ);

        if(existsContract == null){
            contractService.saveContract(userContractDto, userInfoDto);
        }else{
            contractService.updateContract(userContractDto, userInfoDto);
        }
    }

}
