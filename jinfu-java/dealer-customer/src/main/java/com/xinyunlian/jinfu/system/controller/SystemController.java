package com.xinyunlian.jinfu.system.controller;

import com.xinyunlian.jinfu.ad.dto.AdFrontDto;
import com.xinyunlian.jinfu.ad.service.AdService;
import com.xinyunlian.jinfu.area.dto.*;
import com.xinyunlian.jinfu.bank.dto.BankInfDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by menglei on 2016年08月30日.
 */
@Controller
@RequestMapping(value = "system")
public class SystemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private BankService bankService;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private AdService adService;

    /**
     * 查询地区列表
     *
     * @param sysAreaInfSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/area/list", method = RequestMethod.GET)
    public ResultDto<List<SysAreaInfDto>> getSysAreaList(SysAreaInfSearchDto sysAreaInfSearchDto) {
        List<SysAreaInfDto> list = sysAreaInfService.getSysAreaInfList(sysAreaInfSearchDto);
        return ResultDtoFactory.toAck("获取成功", list);
    }

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
     * 广告列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/ad/list", method = RequestMethod.GET)
    public ResultDto<List<AdFrontDto>> getAdList(AdFrontDto adFrontDto) {
        List<AdFrontDto> list = adService.getAdFront(adFrontDto.getAdPosId(), adFrontDto.getAdPosWidth(), adFrontDto.getAdPosHeight());
        if (CollectionUtils.isEmpty(list)){
           return ResultDtoFactory.toNack("没有找到广告");
        }
        return ResultDtoFactory.toAck("获取成功", list);
    }

}
