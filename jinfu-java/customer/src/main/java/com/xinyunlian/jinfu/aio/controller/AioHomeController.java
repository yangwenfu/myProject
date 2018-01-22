package com.xinyunlian.jinfu.aio.controller;

import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.authc.dto.UserInfo;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.prod.enums.EShelfPlatform;
import com.xinyunlian.jinfu.shopkeeper.dto.home.HomePageDto;
import com.xinyunlian.jinfu.shopkeeper.service.PrivateHomeService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.StoreActiveDto;
import com.xinyunlian.jinfu.user.dto.UserActiveDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.PasswordDto;
import com.xinyunlian.jinfu.user.enums.EOperationType;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.service.OperationLogService;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.xinyunlian.jinfu.common.util.BeanValidators.isValid;

/**
 * Created by menglei on 2016年08月30日.
 */
@Controller
@RequestMapping(value = "aio/home")
public class AioHomeController {
    private final static String STORE_INFO_REDEIS_SUFFIX = "_OauthStoreInfo";
    @Autowired
    private StoreService storeService;
    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private OperationLogService logService;
    @Autowired
    private RedisCacheManager redisCacheManager;

    @Autowired
    private PrivateHomeService privateHomeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AioHomeController.class);

    @ResponseBody
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ResultDto<Object> main(@RequestParam Integer width,@RequestParam Integer height) {
        SysAreaInfDto sysAreaInfDto = new SysAreaInfDto();
        sysAreaInfDto.setId(941L);
        HomePageDto homePageDto = new HomePageDto();
        //配置产品分类
        privateHomeService.setProductFloor(homePageDto,EShelfPlatform.AIO,sysAreaInfDto);

        return ResultDtoFactory.toAck("获取成功", homePageDto);
    }

    /**
     * 聚合app转跳激活
     * @param userActiveDto
     * @return
     */
    @RequestMapping(value = "/b2bActive", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> b2bActive(@RequestBody UserActiveDto userActiveDto) {
        LOGGER.debug("UserActiveDto info{}", userActiveDto.toString());
        StoreInfDto storeInfDtoB2B = redisCacheManager.getCache(CacheType.DEFAULT).get(userActiveDto.getAccess() + STORE_INFO_REDEIS_SUFFIX, StoreInfDto.class);
        if (storeInfDtoB2B == null) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("激活用户已过期"));
        }

        StoreActiveDto storeActiveDto = ConverterService.convert(storeInfDtoB2B, StoreActiveDto.class);
        if(!isValid(storeActiveDto)){
            return ResultDtoFactory.toNack("-1","店铺信息不完整，请重新激活。");
        }

        UserInfoDto userInfoDto = userService.findUserByMobile(userActiveDto.getMobile());
        StoreInfDto storeInfDto = storeService.findByTobaccoCertificateNo(storeInfDtoB2B.getTobaccoCertificateNo());

        //用户不存在，验证短信验证码，生成用户
        if (userInfoDto == null) {
            if(StringUtils.isEmpty(storeInfDtoB2B.getMobile())) {
                boolean flag = smsService.confirmVerifyCode(userActiveDto.getMobile(), userActiveDto.getVerifyCode(), ESmsSendType.ACTIVE);
                if (!flag) {
                    return ResultDtoFactory.toNack(MessageUtil.getMessage("sms.code.error"));
                }

                smsService.clearVerifyCode(userActiveDto.getMobile(), ESmsSendType.ACTIVE);
            }
            //创建用户信息
            userInfoDto = new UserInfoDto();
            userInfoDto.setMobile(userActiveDto.getMobile());
            userInfoDto.setLoginPassword(userActiveDto.getPassword());
            userInfoDto.setSource(ESource.AIO);
            userInfoDto.setIdentityAuth(false);
            userInfoDto = userService.saveUser(userInfoDto);

            //记录注册日志
            PasswordDto passwordDto = new PasswordDto();
            passwordDto.setUserId(userInfoDto.getUserId());
            logService.saveLog(passwordDto, EOperationType.REGISTER);
        }



        //合并店铺
        if(storeInfDto != null) {
            storeInfDto.setUserId(userInfoDto.getUserId());
            storeInfDto.setProvinceId(userActiveDto.getProvinceId());
            storeInfDto.setCityId(userActiveDto.getCityId());
            storeInfDto.setAreaId(userActiveDto.getAreaId());
            storeInfDto.setStreetId(userActiveDto.getStreetId());
            storeInfDto.setDistrictId(userActiveDto.getStreetId());
            storeInfDto.setProvince(userActiveDto.getProvince());
            storeInfDto.setCity(userActiveDto.getCity());
            storeInfDto.setArea(userActiveDto.getArea());
            storeInfDto.setStreet(userActiveDto.getStreet());

            if(StringUtils.isEmpty(storeInfDto.getStreetId())) {
                storeInfDto.setDistrictId(storeInfDto.getAreaId());
            }else{
                storeInfDto.setDistrictId(storeInfDto.getStreetId());
            }
            storeService.updateUserId(storeInfDto);
        }else{
            storeInfDtoB2B.setUserId(userInfoDto.getUserId());
            storeInfDtoB2B.setSource(ESource.AIO);
            storeInfDtoB2B.setProvinceId(userActiveDto.getProvinceId());
            storeInfDtoB2B.setCityId(userActiveDto.getCityId());
            storeInfDtoB2B.setAreaId(userActiveDto.getAreaId());
            storeInfDtoB2B.setStreetId(userActiveDto.getStreetId());
            storeInfDtoB2B.setDistrictId(userActiveDto.getStreetId());
            storeInfDtoB2B.setProvince(userActiveDto.getProvince());
            storeInfDtoB2B.setCity(userActiveDto.getCity());
            storeInfDtoB2B.setArea(userActiveDto.getArea());
            storeInfDtoB2B.setStreet(userActiveDto.getStreet());

            if(StringUtils.isEmpty(storeInfDtoB2B.getStreetId())) {
                storeInfDtoB2B.setDistrictId(storeInfDtoB2B.getAreaId());
            }else{
                storeInfDtoB2B.setDistrictId(storeInfDtoB2B.getStreetId());
            }

            storeService.saveStore(storeInfDtoB2B);
        }

        userService.updateStoreAuth(userInfoDto.getUserId());
        UserInfo userInfo= SecurityContext.login(userInfoDto.getUserId(), userActiveDto.getSourceType());
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),userInfo.getToken());
    }

}
