package com.xinyunlian.jinfu.signInfo.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.ImageUtils;
import com.xinyunlian.jinfu.dealer.dto.SignInfoDto;
import com.xinyunlian.jinfu.dealer.service.SignInfoService;
import com.xinyunlian.jinfu.signInfo.dto.SignInDto;
import com.xinyunlian.jinfu.signInfo.dto.SignOutDto;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by menglei on 2017年05月02日.
 */
@Controller
@RequestMapping(value = "sign")
public class SignInfoController {

    @Autowired
    private SignInfoService signInfoService;
    @Autowired
    private FileStoreService fileStoreService;

    /**
     * 签到
     *
     * @param signInDto
     * @return
     */
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "签到")
    public ResultDto<Object> signIn(@RequestBody SignInDto signInDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
//        //判断今日该店铺是否已签到
//        SignInfoDto signInfoDto = signInfoService.getByUserIdAndStoreId(SecurityContext.getCurrentUserId(), signInDto.getStoreId());
//        if (signInfoDto != null) {
//            return ResultDtoFactory.toNack("该店铺已签到");
//        }
//        String signInStoreHeader;
//        String signInStoreInner;
//        try {
//            File signInStoreHeaderFile = ImageUtils.GenerateImageByBase64(signInDto.getSignInStoreHeaderBase64());
//            signInStoreHeader = fileStoreService.upload(EFileType.DEALER_USER_IMG, signInStoreHeaderFile, signInStoreHeaderFile.getName());
//            File signInStoreInnerFile = ImageUtils.GenerateImageByBase64(signInDto.getSignInStoreInnerBase64());
//            signInStoreInner = fileStoreService.upload(EFileType.DEALER_USER_IMG, signInStoreInnerFile, signInStoreInnerFile.getName());
//        } catch (IOException e) {
//            return ResultDtoFactory.toNack("签到失败", e);
//        }
//        SignInfoDto signInfo = ConverterService.convert(signInDto, SignInfoDto.class);
//        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
//        signInfo.setDealerId(dealerUserDto.getDealerId());
//        signInfo.setUserId(dealerUserDto.getUserId());
//        signInfo.setSignInStoreHeader(signInStoreHeader);
//        signInfo.setSignInStoreInner(signInStoreInner);
//        signInfo.setSignInTime(new Date());
//        signInfoService.createSignIn(signInfo);
        return ResultDtoFactory.toAck("签到成功");
    }

    /**
     * 签退
     *
     * @param signOutDto
     * @return
     */
    @RequestMapping(value = "/signOut", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "签退")
    public ResultDto<Object> signOut(@RequestBody SignOutDto signOutDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
//        //判断今日该店铺是否已签到
//        SignInfoDto signInfoDto = signInfoService.getByUserIdAndStoreId(SecurityContext.getCurrentUserId(), signOutDto.getStoreId());
//        if (signInfoDto == null) {
//            return ResultDtoFactory.toNack("该店铺未签到");
//        } else if (signInfoDto.getSignOutTime() != null) {
//            return ResultDtoFactory.toNack("该店铺已签退");
//        }
//        String signOutStoreHeader;
//        try {
//            File signOutStoreHeaderFile = ImageUtils.GenerateImageByBase64(signOutDto.getSignOutStoreHeaderBase64());
//            signOutStoreHeader = fileStoreService.upload(EFileType.DEALER_USER_IMG, signOutStoreHeaderFile, signOutStoreHeaderFile.getName());
//        } catch (IOException e) {
//            return ResultDtoFactory.toNack("签退失败", e);
//        }
//        Date date = new Date();
//        SignInfoDto signInfo = ConverterService.convert(signOutDto, SignInfoDto.class);
//        signInfo.setId(signInfoDto.getId());
//        signInfo.setSignOutStoreHeader(signOutStoreHeader);
//        signInfo.setSignOutTime(date);
//        signInfo.setDistanceTime(getDistanceTime(signInfoDto.getSignInTime(), date));
//        signInfoService.updateSignOut(signInfo);
        return ResultDtoFactory.toAck("签退成功");
    }

    /**
     * 判断该店铺是否已签到
     *
     * @param storeId
     * @return
     */
    @RequestMapping(value = "/checkSignIn", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "判断该店铺是否已签到 true已签到 false未签到")
    public ResultDto<Object> checkSignIn(@RequestParam Long storeId) {
//        SignInfoDto signInfoDto = signInfoService.getByUserIdAndStoreId(SecurityContext.getCurrentUserId(), storeId);
//        Boolean flag = false;
//        if (signInfoDto != null) {
//            flag = true;
//        }
        Map<String, Boolean> resultMap = new HashMap<>();
        //resultMap.put("flag", flag);
        resultMap.put("flag", true);
        return ResultDtoFactory.toAck("获取成功", resultMap);
    }

    /**
     * 判断该店铺是否已签退
     *
     * @param storeId
     * @return
     */
    @RequestMapping(value = "/checkSignOut", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "判断该店铺是否已签退 true已签退 false未签退")
    public ResultDto<Object> checkSignOut(@RequestParam Long storeId) {
//        SignInfoDto signInfoDto = signInfoService.getByUserIdAndStoreId(SecurityContext.getCurrentUserId(), storeId);
//        if (signInfoDto == null) {
//            return ResultDtoFactory.toNack("该店铺未签到");
//        }
//        Boolean flag = false;
//        if (signInfoDto.getSignOutTime() != null) {
//            flag = true;
//        }
        Map<String, Boolean> resultMap = new HashMap<>();
        //resultMap.put("flag", flag);
        resultMap.put("flag", true);
        return ResultDtoFactory.toAck("获取成功", resultMap);
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(Date str1, Date str2) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long time1 = str1.getTime();
        long time2 = str2.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        //return day + "天" + hour + "小时" + min + "分" + sec + "秒";
        String distanceTime = day + "天" + hour + "小时" + min + "分" + sec + "秒";
        if (day == 0) {
            distanceTime = hour + "小时" + min + "分" + sec + "秒";
        }
        return distanceTime;
    }

}
