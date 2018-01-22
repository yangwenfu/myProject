package com.xinyunlian.jinfu.user.controller;

import com.xinyunlian.jinfu.aio.controller.AioHomeController;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.authc.ESourceType;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.olduser.dto.OldUserDto;
import com.xinyunlian.jinfu.olduser.service.OldUserService;
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

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jll on 2016/11/3.
 */
@Controller
@RequestMapping(value = "user/active")
public class ActiveController {
    private final static String ACTIVE_USER_REDEIS_SUFFIX = "ActiveUser_";
    private final static String STORE_INFO_REDEIS_SUFFIX = "_OauthStoreInfo";
    @Autowired
    private StoreService storeService;
    @Autowired
    private UserService userService;
    @Autowired
    private BankService bankService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private OldUserService oldUserService;
    @Autowired
    private OperationLogService logService;
    @Autowired
    private RedisCacheManager redisCacheManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveController.class);

    /**
     * 查询烟草证号
     * @param tobaccoCertificateNo
     * @return
     */
    @RequestMapping(value = "/storeQuery", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> storeQuery(@RequestParam String tobaccoCertificateNo) {
        StoreInfDto storeDto = storeService.findByTobaccoCertificateNo(tobaccoCertificateNo);
        //店铺不存在
        if(storeDto == null){
            return ResultDtoFactory.toAck("1","烟草证不存在,请重新注册");
        }

        //用户已激活
        if(StringUtils.isNotEmpty(storeDto.getUserId())){
            return ResultDtoFactory.toAck("2","该用户已注册，请登录");
        }

        //提示400
        OldUserDto oldUserDto = oldUserService.findByTobaccoCertificateNo(storeDto.getTobaccoCertificateNo());
        if(oldUserDto == null ||  StringUtils.isEmpty(oldUserDto.getMobile())){
            return ResultDtoFactory.toAck("4","请拨打400");
        }
        return ResultDtoFactory.toAck("3",oldUserDto.getMobile());
    }

    /**
     * 验证手机号
     * @param verifyCode
     * @param tobaccoCertificateNo
     * @return
     */
    @RequestMapping(value = "/confirmMobile", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> confirmMobile(@RequestParam String verifyCode,@RequestParam String tobaccoCertificateNo) {
        OldUserDto oldUserDto = oldUserService.findByTobaccoCertificateNo(tobaccoCertificateNo);
        StoreInfDto storeInfDto = storeService.findByTobaccoCertificateNo(tobaccoCertificateNo);
        oldUserDto.setStoreInfDto(storeInfDto);

        boolean flag = smsService.confirmVerifyCode(oldUserDto.getMobile(), verifyCode, ESmsSendType.ACTIVE);
        if (!flag) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("sms.code.error"));
        }
        smsService.clearVerifyCode(oldUserDto.getMobile(), ESmsSendType.ACTIVE);
        String access = UUID.randomUUID().toString();
        redisCacheManager.getCache(CacheType.DEFAULT).put(ACTIVE_USER_REDEIS_SUFFIX + access,oldUserDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("sms.code.right"),access);
    }

    @RequestMapping(value = "/confirmStore", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> confirmStore(@RequestParam String access) {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),
                redisCacheManager.getCache(CacheType.DEFAULT).get(ACTIVE_USER_REDEIS_SUFFIX + access,OldUserDto.class));
    }



    /**
     * 激活用户
     *
     * @param userActiveDto
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> resetPassword(@RequestBody UserActiveDto userActiveDto) {
        OldUserDto oldUserDto = redisCacheManager.getCache(CacheType.DEFAULT).get(ACTIVE_USER_REDEIS_SUFFIX + userActiveDto.getAccess(),OldUserDto.class);
        if (oldUserDto == null) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("激活用户已过期"));
        }

        UserInfoDto userInfoDto = userService.findUserByMobile(userActiveDto.getMobile());

        //如果手机号更改了则验证手机号
        if(!StringUtils.equals(userActiveDto.getMobile(),oldUserDto.getMobile())){
            boolean flag = smsService.confirmVerifyCode(userActiveDto.getMobile(), userActiveDto.getVerifyCode(), ESmsSendType.ACTIVE);
            if (!flag) {
                return ResultDtoFactory.toNack(MessageUtil.getMessage("sms.code.error"));
            }
        }

        //用户不存在，验证短信验证码，生成用户
        if (userInfoDto == null) {
            //创建用户信息
            userInfoDto = new UserInfoDto();
            userInfoDto.setMobile(userActiveDto.getMobile());
            userInfoDto.setLoginPassword(userActiveDto.getPassword());
            userInfoDto.setSource(ESource.IMPORT);
            userInfoDto.setIdCardNo(oldUserDto.getIdCardNo());
            userInfoDto.setUserName(oldUserDto.getUserName());
            userInfoDto = userService.saveUser(userInfoDto);

            if(oldUserDto.getIdentityAuth() != null && oldUserDto.getIdentityAuth()){
                BankCardDto bankCardDto = new BankCardDto();
                bankCardDto.setBankCardNo(oldUserDto.getBankCardNo());
                bankCardDto.setBankCardName(oldUserDto.getBankCardName());
                bankCardDto.setMobileNo(oldUserDto.getMobileNo());
                bankCardDto.setBankCnapsCode(oldUserDto.getBankCnapsCode());
                bankCardDto.setBankCode(oldUserDto.getBankCode());
                bankCardDto.setBankName(oldUserDto.getBankName());
                bankCardDto.setIdCardNo(oldUserDto.getIdCardNo());
                bankCardDto.setUserId(userInfoDto.getUserId());
                bankService.save(bankCardDto);
            }

            //记录注册日志
            PasswordDto passwordDto = new PasswordDto();
            passwordDto.setUserId(userInfoDto.getUserId());
            logService.saveLog(passwordDto, EOperationType.REGISTER);
            smsService.clearVerifyCode(userActiveDto.getMobile(), ESmsSendType.ACTIVE);
        }

        //合并店铺
        StoreInfDto storeInfDto = storeService.findByStoreId(oldUserDto.getStoreInfDto().getStoreId());
        storeInfDto.setUserId(userInfoDto.getUserId());
        storeService.updateUserId(storeInfDto);
        userService.updateStoreAuth(userInfoDto.getUserId());
        SecurityContext.login(userInfoDto.getUserId(), ESourceType.JINFU_WEB);

        redisCacheManager.getCache(CacheType.DEFAULT).evict(ACTIVE_USER_REDEIS_SUFFIX + userActiveDto.getAccess());
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 验证B2B转跳用户店铺数据是否可以正常转换
     * @param userActiveDto
     * @return
     */
    @RequestMapping(value = "/b2bCheck", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<StoreInfDto> b2bCheck(@RequestBody UserActiveDto userActiveDto) {
        StoreInfDto storeInfDtoB2B = redisCacheManager.getCache(CacheType.DEFAULT).get(userActiveDto.getAccess() + STORE_INFO_REDEIS_SUFFIX, StoreInfDto.class);
        if (storeInfDtoB2B == null) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("激活用户已过期"));
        }
        if(!StringUtils.isEmpty(userActiveDto.getProvinceId())) {
            storeInfDtoB2B.setProvinceId(userActiveDto.getProvinceId());
            storeInfDtoB2B.setCityId(userActiveDto.getCityId());
            storeInfDtoB2B.setAreaId(userActiveDto.getAreaId());
            storeInfDtoB2B.setStreetId(userActiveDto.getStreetId());
            storeInfDtoB2B.setDistrictId(userActiveDto.getStreetId());
            storeInfDtoB2B.setProvince(userActiveDto.getProvince());
            storeInfDtoB2B.setCity(userActiveDto.getCity());
            storeInfDtoB2B.setArea(userActiveDto.getArea());
            storeInfDtoB2B.setStreet(userActiveDto.getStreet());
        }
        if(checkStore(storeInfDtoB2B)){
            redisCacheManager.getCache(CacheType.DEFAULT)
                    .put(userActiveDto.getAccess() + STORE_INFO_REDEIS_SUFFIX, storeInfDtoB2B);
            return ResultDtoFactory.toAck("店铺信息完整",storeInfDtoB2B);
        }else{
            return ResultDtoFactory.toNack("店铺信息数据不完整",storeInfDtoB2B);
        }
    }

    /**
     * B2B转跳激活
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

        //是否需要自动登入
       /* if(!StringUtils.isEmpty(userInfoDto.getUserId())){
            SecurityContext.login();
        }*/

        //用户不存在，验证短信验证码，生成用户
        if (userInfoDto == null) {
            boolean flag = smsService.confirmVerifyCode(userActiveDto.getMobile(), userActiveDto.getVerifyCode(), ESmsSendType.ACTIVE);
            if (!flag) {
                return ResultDtoFactory.toNack(MessageUtil.getMessage("sms.code.error"));
            }
            //创建用户信息
            userInfoDto = new UserInfoDto();
            userInfoDto.setMobile(userActiveDto.getMobile());
            userInfoDto.setLoginPassword(userActiveDto.getPassword());
            userInfoDto.setSource(ESource.THIRD_PARTY);
            userInfoDto.setIdentityAuth(false);
            userInfoDto = userService.saveUser(userInfoDto);

            //记录注册日志
            PasswordDto passwordDto = new PasswordDto();
            passwordDto.setUserId(userInfoDto.getUserId());
            logService.saveLog(passwordDto, EOperationType.REGISTER);
            smsService.clearVerifyCode(userActiveDto.getMobile(), ESmsSendType.ACTIVE);
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
             storeInfDtoB2B.setSource(ESource.THIRD_PARTY);
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
        SecurityContext.login(userInfoDto.getUserId(), ESourceType.JINFU_WEB);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    private boolean checkStore(StoreInfDto storeInfDtoB2B ){
        if(StringUtils.isEmpty(storeInfDtoB2B.getProvinceId())) {
            //先判断店铺地址是否能够转换
            List<SysAreaInfDto> sysAreaInfDtos = sysAreaInfService.getSysAreaByName(storeInfDtoB2B.getProvince());
            if (sysAreaInfDtos != null && sysAreaInfDtos.size() > 0) {
                storeInfDtoB2B.setProvinceId(sysAreaInfDtos.get(0).getId().toString());
            }
            sysAreaInfDtos = sysAreaInfService.getSysAreaByName(storeInfDtoB2B.getCity());
            if (sysAreaInfDtos != null && sysAreaInfDtos.size() > 0) {
                storeInfDtoB2B.setCityId(sysAreaInfDtos.get(0).getId().toString());
            }
            sysAreaInfDtos = sysAreaInfService.getSysAreaByName(storeInfDtoB2B.getArea());
            if (sysAreaInfDtos != null && sysAreaInfDtos.size() > 0) {
                sysAreaInfDtos.forEach(sysAreaInfDto -> {
                    if (sysAreaInfDto.getTreePath().
                            contains(storeInfDtoB2B.getProvinceId() + "," + storeInfDtoB2B.getCityId())) {
                        storeInfDtoB2B.setAreaId(sysAreaInfDto.getId().toString());
                    }
                });
            }
        }

        StoreActiveDto storeActiveDto = ConverterService.convert(storeInfDtoB2B, StoreActiveDto.class);
        if(!isValid(storeActiveDto)){
            return false;
        }
        return true;
    }

    private boolean isValid(Object obj){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set constraintViolations = validator.validate(obj);
        if (!constraintViolations.isEmpty()) {
            return false;
        }

        return true;
    }
}
