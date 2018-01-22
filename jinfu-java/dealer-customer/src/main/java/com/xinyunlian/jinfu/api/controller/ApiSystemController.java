package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.ad.dto.AdFrontDto;
import com.xinyunlian.jinfu.ad.service.AdService;
import com.xinyunlian.jinfu.app.dto.AppVersionControlDto;
import com.xinyunlian.jinfu.app.dto.DataVersionControlDto;
import com.xinyunlian.jinfu.app.enums.EAppSource;
import com.xinyunlian.jinfu.app.enums.EDataType;
import com.xinyunlian.jinfu.app.enums.EOperatingSystem;
import com.xinyunlian.jinfu.app.service.AppVersionControlService;
import com.xinyunlian.jinfu.app.service.DataVersionControlService;
import com.xinyunlian.jinfu.bank.dto.BankInfDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by menglei on 2016年09月22日.
 */
@Controller
@RequestMapping(value = "open-api/system")
public class ApiSystemController {

    @Autowired
    private BankService bankService;
    @Autowired
    private AppVersionControlService appVersionControlService;
    @Autowired
    private DataVersionControlService dataVersionControlService;
    @Autowired
    private AdService adService;

    /**
     * 查询银行列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bank/list", method = RequestMethod.GET)
    public ResultDto<List<BankInfDto>> getBankList() {
        List<BankInfDto> list = bankService.findBankInfAll();
        return ResultDtoFactory.toAck("获取成功", list);
    }

    /**
     * 获取版本号
     *
     * @param sourceType
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/appVersion", method = RequestMethod.GET)
    public ResultDto<AppVersionControlDto> getAppVersion(@RequestHeader String sourceType) {
        EOperatingSystem operatingSystem = null;
        if (sourceType.equals("ANDROID")) {
            operatingSystem = EOperatingSystem.ANDROID;
        } else if (sourceType.equals("IOS")) {
            operatingSystem = EOperatingSystem.IOS;
        }
        if (operatingSystem == null) {
            return ResultDtoFactory.toNack("获取失败");
        }
        AppVersionControlDto appVersionControlDto = appVersionControlService.getLatestAppInfo(EAppSource.BUDDY, operatingSystem);
        return ResultDtoFactory.toAck("获取成功", appVersionControlDto);
    }

    /**
     * 获取数据版本号
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/areaVersion", method = RequestMethod.GET)
    public ResultDto<DataVersionControlDto> getAreaVersion(@RequestHeader String sourceType) {
        EOperatingSystem operatingSystem = null;
        if (sourceType.equals("ANDROID")) {
            operatingSystem = EOperatingSystem.ANDROID;
        } else if (sourceType.equals("IOS")) {
            operatingSystem = EOperatingSystem.IOS;
        }
        if (operatingSystem == null) {
            return ResultDtoFactory.toNack("获取失败");
        }
        DataVersionControlDto dataVersionControlDto = dataVersionControlService.getLatestDataInfo(EAppSource.BUDDY, operatingSystem, EDataType.AREA);
        return ResultDtoFactory.toAck("获取成功", dataVersionControlDto);
    }

    /**
     * 广告列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/ad/list", method = RequestMethod.GET)
    public ResultDto<List<AdFrontDto>> getAdList(AdFrontDto adFrontDto) {
        List<AdFrontDto> list = adService.getAdFront(adFrontDto.getAdPosId(), adFrontDto.getAdPosWidth(), adFrontDto.getAdPosHeight());
        return ResultDtoFactory.toAck("获取成功", list);
    }

}
