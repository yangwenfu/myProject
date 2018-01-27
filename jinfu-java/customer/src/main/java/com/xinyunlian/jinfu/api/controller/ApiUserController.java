package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.api.dto.MobileOpenApi;
import com.xinyunlian.jinfu.api.dto.UserBriefOpenApiDto;
import com.xinyunlian.jinfu.api.dto.UserOpenApi;
import com.xinyunlian.jinfu.api.dto.resp.StoreOpenApiResp;
import com.xinyunlian.jinfu.api.dto.resp.UserOpenApiResp;
import com.xinyunlian.jinfu.api.service.ApiBaiduService;
import com.xinyunlian.jinfu.api.validate.OpenApi;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.controller.UserController;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.PasswordDto;
import com.xinyunlian.jinfu.user.enums.EOperationType;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.service.OperationLogService;
import com.xinyunlian.jinfu.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Created by jll on 2016/8/19.
 */
@Controller
@RequestMapping(value = "open-api/user")
public class ApiUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private OperationLogService logService;
    @Autowired
    private ApiBaiduService apiBaiduService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

     /** 通过手机号查询用户详细信息
     * @param mobileOpenApiDto
     * @return
     */
    @OpenApi
    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询用户信息")
    public ResultDto<Object> getUserInfo(@RequestBody @Valid MobileOpenApi mobileOpenApiDto,BindingResult result) {
        if (result.hasErrors()){
            return ResultDtoFactory.toNack("input.param.invalid",result.getFieldError().getDefaultMessage());
        }

        UserInfoDto userInfoDto = userService.findUserByMobile(mobileOpenApiDto.getMobile());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("user.not.exist");
        }

        UserOpenApiResp userOpenApi = ConverterService.convert(userInfoDto, UserOpenApiResp.class);
        List<StoreInfDto> stores = storeService.findByUserId(userInfoDto.getUserId());

        List<StoreOpenApiResp> storeResp = ConverterService.convertToList(stores,StoreOpenApiResp.class);
        userOpenApi.setStores(storeResp);

        return ResultDtoFactory.toAck("common.operate.success", userOpenApi);
    }

    @OpenApi
    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户注册")
    public ResultDto<Object> registerUser(@RequestBody @Valid UserOpenApi userOpenApiDto,BindingResult result) {
        if (result.hasErrors()){
            return ResultDtoFactory.toNack("input.param.invalid",result.getFieldError().getDefaultMessage());
        }

        UserInfoDto userInfoDto = userService.findUserByMobile(userOpenApiDto.getMobile());
        StoreInfDto storeInfDto = storeService.findByTobaccoCertificateNo(userOpenApiDto.getTobaccoCertificateNo());

        if(storeInfDto != null){
            return ResultDtoFactory.toNack("tobacco.certificate.exist");
        }

        storeInfDto = ConverterService.convert(userOpenApiDto,StoreInfDto.class);

        try {
            //先判断店铺地址是否能够转换
            List<SysAreaInfDto> sysAreaInfDtos = sysAreaInfService.getSysAreaByName(storeInfDto.getProvince());
            storeInfDto.setProvinceId(sysAreaInfDtos.get(0).getId().toString());

            sysAreaInfDtos = sysAreaInfService.getSysAreaByName(storeInfDto.getCity());
            for (SysAreaInfDto sysAreaInfDto : sysAreaInfDtos) {
                if (null != sysAreaInfDto.getParent() &&
                        sysAreaInfDto.getParent().toString().equals(storeInfDto.getProvinceId())) {
                    storeInfDto.setCityId(sysAreaInfDto.getId().toString());
                }
            }

            sysAreaInfDtos = sysAreaInfService.getSysAreaByName(storeInfDto.getArea());
            for (SysAreaInfDto sysAreaInfDto : sysAreaInfDtos) {
                if (sysAreaInfDto.getTreePath().
                        contains(storeInfDto.getProvinceId() + "," + storeInfDto.getCityId())) {
                    storeInfDto.setAreaId(sysAreaInfDto.getId().toString());
                }
            }

            if(!StringUtils.isEmpty(storeInfDto.getStreet())) {
                sysAreaInfDtos = sysAreaInfService.getSysAreaByName(storeInfDto.getStreet());
                if(!CollectionUtils.isEmpty(sysAreaInfDtos)) {
                    for (SysAreaInfDto sysAreaInfDto : sysAreaInfDtos) {
                        if (sysAreaInfDto.getTreePath().
                                contains(storeInfDto.getProvinceId() + ","
                                        + storeInfDto.getCityId() + ","
                                        + storeInfDto.getArea())) {
                            storeInfDto.setStreetId(sysAreaInfDto.getId().toString());
                        }
                    }
                }
            }
        }catch (Exception e){
            LOGGER.debug("地址转化出错");
            return ResultDtoFactory.toNack("user.address.error");
        }

        if(StringUtils.isEmpty(storeInfDto.getProvinceId()) ||
                StringUtils.isEmpty(storeInfDto.getCityId()) ||
                StringUtils.isEmpty(storeInfDto.getAreaId())){
            return ResultDtoFactory.toNack("user.address.error");
        }

        //用户不存在，生成用户
        if (userInfoDto == null) {
            //创建用户信息
            userInfoDto = new UserInfoDto();
            userInfoDto.setMobile(userOpenApiDto.getMobile());
            userInfoDto.setLoginPassword(UUID.randomUUID().toString());
            userInfoDto.setSource(ESource.THIRD_PARTY);
            userInfoDto = userService.saveUser(userInfoDto);

            //记录注册日志
            PasswordDto passwordDto = new PasswordDto();
            passwordDto.setUserId(userInfoDto.getUserId());
            logService.saveLog(passwordDto, EOperationType.REGISTER);
        }

        storeInfDto.setUserId(userInfoDto.getUserId());
        storeInfDto.setSource(ESource.THIRD_PARTY);
        if(StringUtils.isEmpty(storeInfDto.getStreetId())) {
            storeInfDto.setDistrictId(storeInfDto.getAreaId());
        }else{
            storeInfDto.setDistrictId(storeInfDto.getStreetId());
        }
        storeService.saveStore(storeInfDto);
        userService.updateStoreAuth(userInfoDto.getUserId());
        apiBaiduService.updatePoint(storeInfDto);

        UserOpenApi userResult = ConverterService.convert(userInfoDto,UserOpenApi.class);

        return ResultDtoFactory.toAck("common.operate.success",userResult);
    }

    /**
     * 通过手机号查询用户简明信息
     *
     * @param mobile
     * @return
     */
    @OpenApi
    @RequestMapping(value = "/briefByMobile", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询用户简明信息")
    public ResultDto<Object> briefByMobile(@RequestParam(required = true) String mobile) {
        if (!org.springframework.util.StringUtils.isEmpty(mobile)){
            UserInfoDto userInfoDto = userService.findUserByMobile(mobile);

            if (userInfoDto != null){
                UserBriefOpenApiDto result = ConverterService.convert(userInfoDto, UserBriefOpenApiDto.class);
                return ResultDtoFactory.toAck("查询成功", result);
            }else {
                return ResultDtoFactory.toNack("店主手机号不存在");
            }

        }else {
            return ResultDtoFactory.toNack("手机号不能为空");
        }
    }

}
