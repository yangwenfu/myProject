package com.xinyunlian.jinfu.shopkeeper.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.industry.dto.IndustryDto;
import com.xinyunlian.jinfu.industry.service.IndustryService;
import com.xinyunlian.jinfu.shopkeeper.dto.card.StoreBaseDto;
import com.xinyunlian.jinfu.shopkeeper.dto.card.StoreExtDto;
import com.xinyunlian.jinfu.shopkeeper.dto.my.StoreEachDto;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by King on 2017/2/15.
 */
@Controller
@RequestMapping(value = "shopkeeper/shop")
public class StoreInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreInfoController.class);

    @Autowired
    private StoreService storeService;
    @Autowired
    private YMMemberService ymMemberService;
    @Autowired
    private IndustryService industryService;


    /**
     * 获取该用户店铺信息
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> list() {
        List<StoreEachDto> storeEachDtos = new ArrayList<>();
        List<StoreInfDto> storeInfDtoList = storeService.findByUserId(SecurityContext.getCurrentUserId());
        if(!CollectionUtils.isEmpty(storeInfDtoList)) {
            storeInfDtoList.forEach(storeInfDto -> {
                StoreEachDto storeListEachDto = ConverterService.convert(storeInfDto,StoreEachDto.class);

                IndustryDto industryDto = industryService.getByMcc(storeInfDto.getIndustryMcc());
                storeListEachDto.setMcc(industryDto.getMcc());
                storeListEachDto.setLicenceName(industryDto.getLicenceName());
                storeListEachDto.setStoreLicence(industryDto.getStoreLicence());
                storeListEachDto.setIndName(industryDto.getIndName());

                YMMemberDto ymMemberDto = ymMemberService.getMemberByStoreId(storeInfDto.getStoreId());
                if(null != ymMemberDto && !StringUtils.isEmpty(ymMemberDto.getQrcodeUrl())){
                    storeListEachDto.setQrcodeUrl(ymMemberDto.getQrcodeUrl());
                    storeListEachDto.setQrStatus(true);
                }
                storeEachDtos.add(storeListEachDto);
            });
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), storeEachDtos);
    }

    @RequestMapping(value = "/getByID", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getById(@RequestParam Long storeId) {
        StoreInfDto storeInfDto = storeService.findByStoreId(storeId);
        StoreBaseDto storeBaseDto = ConverterService.convert(storeInfDto,StoreBaseDto.class);

        IndustryDto industryDto = industryService.getByMcc(storeInfDto.getIndustryMcc());
        storeBaseDto.setLicenceName(industryDto.getLicenceName());
        storeBaseDto.setStoreLicence(industryDto.getStoreLicence());
        storeBaseDto.setIndName(industryDto.getIndName());

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), storeBaseDto);
    }

    @RequestMapping(value = "/ext/getByID", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getExtById(@RequestParam Long storeId) {
        StoreInfDto storeInfDto = storeService.findByStoreId(storeId);
        StoreExtDto storeExtDto = ConverterService.convert(storeInfDto,StoreExtDto.class);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), storeExtDto);
    }
}
