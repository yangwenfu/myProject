package com.xinyunlian.jinfu.store.controller;

import com.xinyunlian.jinfu.api.dto.ApiBaiduDto;
import com.xinyunlian.jinfu.api.service.ApiBaiduService;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.olduser.dto.OldUserDto;
import com.xinyunlian.jinfu.olduser.service.OldUserService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by KimLL on 2016/8/29.
 */
@Controller
@RequestMapping(value = "user/store")
public class StoreController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreController.class);
    @Autowired
    private StoreService storeService;
    @Autowired
    private ApiBaiduService apiBaiduService;
    @Autowired
    private OldUserService oldUserService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private UserService userService;
    @Autowired
    private SysAreaInfService sysAreaInfService;

    /**
     * 添加店铺 不含图片
     *
     * @param storeInfDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> save(@RequestBody StoreInfDto storeInfDto) {
        storeInfDto.setUserId(SecurityContext.getCurrentUserId());
        storeInfDto.setSource(ESource.REGISTER);
        StoreInfDto storeInf = storeService.saveStore(storeInfDto);
        apiBaiduService.updatePoint(storeInf);
        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 删除店铺
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> delete(@RequestParam Long storeId) {
        storeService.deleteStore(storeId);
        return ResultDtoFactory.toAck("删除成功");
    }

    /**
     * 修改店铺
     *
     * @param storeInfDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> update(@RequestBody StoreInfDto storeInfDto) {
        storeInfDto.setUserId(SecurityContext.getCurrentUserId());
        storeService.updateStore(storeInfDto);
        apiBaiduService.updatePoint(storeInfDto);
        return ResultDtoFactory.toAck("修改成功");
    }

    /**
     * 获取该用户店铺信息
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> get() {
        List<StoreInfDto> storeInfDtoList = storeService.findByUserId(SecurityContext.getCurrentUserId());
       /* storeInfDtoList.forEach(storeInfDto -> {
            storeInfDto.setTobaccoCertificateNo(MaskUtil.maskMiddleValue(3, 2, storeInfDto.getTobaccoCertificateNo()));
            storeInfDto.setBizLicence(MaskUtil.maskMiddleValue(3, 3, storeInfDto.getBizLicence()));
        });*/
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), storeInfDtoList);
    }

    @RequestMapping(value = "/getByID", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getById(@RequestParam Long storeId) {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),  storeService.findByStoreId(storeId));
    }

    /**
     * 云码上传
     *
     * @param storeId
     * @param qrCodeUrl
     * @return
     */
    @RequestMapping(value = "/qrcode/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> get(@RequestParam Long storeId, String qrCodeUrl) {
        StoreInfDto storeInfDto = storeService.findByStoreId(storeId);
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        storeInfDto.setQrCodeUrl(qrCodeUrl);
        storeService.updateStore(storeInfDto);
        return ResultDtoFactory.toAck("上传成功");
    }

    /**
     * 添加店铺 包括图片
     *
     * @param storeInfDto
     * @return
     */
    @RequestMapping(value = "/saveAndUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> saveStore(@RequestBody StoreInfDto storeInfDto) {
        //System.out.println("store input :" + storeInfDto.toString());
        storeInfDto.setUserId(SecurityContext.getCurrentUserId());
        if (storeInfDto.getStoreId() == null) {
            storeInfDto.setSource(ESource.REGISTER);
        }
        storeInfDto = storeService.saveSupportAll(storeInfDto);
        apiBaiduService.updatePoint(storeInfDto);
        return ResultDtoFactory.toAck("添加成功",storeInfDto.getStoreId());
    }

    /**
     * 店铺详细地址转经纬度
     *
     * @return
     */
    @RequestMapping(value = "/updateAllStorePoint", method = RequestMethod.POST)
    @ResponseBody
    public void updateAllStorePoint() {
        List<StoreInfDto> list = storeService.findByNotPoint();
        for (StoreInfDto storeInfDto : list) {
            if (StringUtils.isNotEmpty(storeInfDto.getCity()) && StringUtils.isNotEmpty(storeInfDto.getAddress())) {
                String address = storeInfDto.getCity();
                if (address.equals("市辖区") || address.equals("市辖县")) {
                    address = storeInfDto.getProvince();
                }
                String point = apiBaiduService.getPoint(address + storeInfDto.getArea() + storeInfDto.getAddress());
                if (StringUtils.isNotEmpty(point)) {
                    storeInfDto.setLng(point.split(",")[0]);
                    storeInfDto.setLat(point.split(",")[1]);
                    storeService.updateStore(storeInfDto);
                }
            }

        }
    }

    /**
     * 老商城店铺绑定
     * @param verifyCode
     * @param tobaccoCertificateNo
     * @return
     */
    @RequestMapping(value = "/active", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> activeStore(@RequestParam String verifyCode,@RequestParam String tobaccoCertificateNo) {
        OldUserDto oldUserDto = oldUserService.findByTobaccoCertificateNo(tobaccoCertificateNo);
        StoreInfDto storeInfDto = storeService.findByTobaccoCertificateNo(tobaccoCertificateNo);

        boolean flag = smsService.confirmVerifyCode(oldUserDto.getMobile(), verifyCode, ESmsSendType.ACTIVE);
        if (!flag) {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("sms.code.error"));
        }
        smsService.clearVerifyCode(oldUserDto.getMobile(), ESmsSendType.ACTIVE);
        //绑定店铺
        storeInfDto.setUserId(SecurityContext.getCurrentUserId());
        storeService.updateUserId(storeInfDto);
        userService.updateStoreAuth(SecurityContext.getCurrentUserId());
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    @RequestMapping(value = "/getLocationArea", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getLocationArea(@RequestParam String location) {
        ApiBaiduDto apiBaiduDto = apiBaiduService.getArea(location);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),apiBaiduDto);
    }
}
