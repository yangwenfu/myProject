package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by KimLL on 2016/8/29.
 */
@Controller
@RequestMapping(value = "open-api/store")
public class ApiStoreController {
    @Autowired
    private StoreService storeService;
    @Autowired
    private UserService userService;

//    /**
//     * 获取该用户店铺信息
//     *
//     * @return
//     */
//    @OpenApi
//    @RequestMapping(value = "/getByMobile", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "获取用户店铺信息")
//    public ResultDto<Object> get(@RequestBody MobileOpenApiDto mobileOpenApiDto) {
//        UserInfoDto userInfoDto = userService.findUserByMobile(mobileOpenApiDto.getMobile());
//        if (userInfoDto == null) {
//            return ResultDtoFactory.toNack(MessageUtil.getMessage("user.not.exist"));
//        }
//        List<StoreInfDto> storeInfDtoList = storeService.findByUserId(userInfoDto.getUserId());
//        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), storeInfDtoList);
//    }
//
//    /**
//     * 云码上传
//     *
//     * @param storeOpenApiDto
//     * @return
//     */
//    @OpenApi
//    @RequestMapping(value = "/qrcode/upload", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "云码上传")
//    public ResultDto<Object> get(@RequestBody StoreOpenApiDto storeOpenApiDto) {
//        StoreInfDto storeInfDto = storeService.findByStoreId(storeOpenApiDto.getStoreId());
//        if (storeInfDto == null) {
//            return ResultDtoFactory.toNack(MessageUtil.getMessage("store.not.exist"));
//        }
//        storeInfDto.setQrCodeUrl(storeOpenApiDto.getQrCodeUrl());
//        storeInfDto.setBankCardNo(storeOpenApiDto.getBankCardNo());
//        storeService.updateStore(storeInfDto);
//        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
//    }
}
