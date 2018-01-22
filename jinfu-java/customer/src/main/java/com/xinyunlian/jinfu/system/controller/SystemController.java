package com.xinyunlian.jinfu.system.controller;

import com.xinyunlian.jinfu.ad.dto.AdFrontDto;
import com.xinyunlian.jinfu.ad.service.AdService;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.dto.SysAreaInfSearchDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.bank.dto.BankInfDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.dict.dto.DictionaryItemDto;
import com.xinyunlian.jinfu.dict.service.DictService;
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

    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private BankService bankService;
    @Autowired
    private AdService adService;
    @Autowired
    private DictService dictService;

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
     * 获取银行卡信息
     * @return
     */
    @RequestMapping(value = "/bank/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<BankInfDto>> getBankList() {
        List<BankInfDto> bankInfDtoList = bankService.findBankInfAll();
        return ResultDtoFactory.toAck("获取成功", bankInfDtoList);
    }

    /**
     * 获取支持代收付银行
     * @return
     */
    @RequestMapping(value = "/bank/support", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<BankInfDto>> getSupportBankList() {
        List<BankInfDto> bankInfDtoList = bankService.findBySupport();
        return ResultDtoFactory.toAck("获取成功", bankInfDtoList);
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

    /**
     * 根据字典类型code查询字典数据
     * @param dictTypeCode
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getDictItemsByDictType", method = RequestMethod.GET)
    public ResultDto<Object> getDictItemsByDictType(String dictTypeCode){
        List<DictionaryItemDto> list = dictService.getDictItemsByDictType(dictTypeCode);
        return ResultDtoFactory.toAck("查询成功", list);
    }

}
