package com.xinyunlian.jinfu.loan.audit.controller;

import com.alibaba.dubbo.rpc.RpcContext;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;

import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.loan.user.service.PrivateLoanUserService;
import com.xinyunlian.jinfu.risk.dto.resp.UserCreditInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.xinyunlian.jinfu.spider.dto.req.AuthLoginDto;
import com.xinyunlian.jinfu.spider.dto.resp.RiskUserInfoDto;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by dell on 2016/11/10.
 */
@Controller
@RequestMapping("loan/risk")
public class RiskController {

    @Autowired
    private RiskUserInfoService riskUserInfoService;

    @Autowired
    private SysAreaInfService sysAreaInfService;

    @Autowired
    private PrivateLoanUserService privateLoanUserService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RiskController.class);

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<RiskUserInfoDto> getRiskUserInfoDto(@RequestParam String userId, @RequestParam String applId) {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),
                privateLoanUserService.getTobaccoSpider(userId, applId));
    }

    @RequestMapping(value = "/userCredit/get", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<UserCreditInfoDto> getUserCreditInfo(@RequestParam String userId, @RequestParam(required = false, defaultValue = "false") Boolean forceRetrieve, String applId) {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),
                privateLoanUserService.getCreditSpider(userId, applId, forceRetrieve));
    }

    @RequestMapping(value = "/user/failed", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto getFailedUsers() {
        List<AuthLoginDto> authLoginDtos = riskUserInfoService.getFailedUserList();
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), authLoginDtos);
    }

    @RequestMapping(value = "/retrieve", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto retrieveRiskUserInfoDto(@RequestParam String userId) {
        //获取一把保存的用户名密码
        AuthLoginDto authLoginDto = riskUserInfoService.getAuthLoginData(userId);

        if (null == authLoginDto || StringUtils.isEmpty(authLoginDto.getUsername()) || StringUtils.isEmpty(authLoginDto.getPassword())) {
            return ResultDtoFactory.toNack("用户名或密码不存在");
        }

        Boolean authed = riskUserInfoService.authLogin(authLoginDto);

        if (!authed) {
            return ResultDtoFactory.toNack("用户名或密码错误");
        }

        //异步抓一把
        riskUserInfoService.spiderUserInfo(userId);

        Future<Void> future = RpcContext.getContext().getFuture();

        try {
            future.get();
        } catch (Exception e) {
            return ResultDtoFactory.toBizError("抓取异常", e);
        }

        RiskUserInfoDto userInfo = riskUserInfoService.getUserInfo(userId);


        SysAreaInfDto province = sysAreaInfService.getSysAreaInfById(userInfo.getProvinceId());
        if (province != null) {
            userInfo.setProvince(province.getName());
        }
        SysAreaInfDto city = sysAreaInfService.getSysAreaInfById(userInfo.getCityId());
        if (city != null) {
            userInfo.setCity(city.getName());
        }
        SysAreaInfDto area = sysAreaInfService.getSysAreaInfById(userInfo.getAreaId());
        if (area != null) {
            userInfo.setArea(area.getName());
        }

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), userInfo);
    }

}
