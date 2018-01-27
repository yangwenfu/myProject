package com.xinyunlian.jinfu.store.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.industry.dto.IndustryDto;
import com.xinyunlian.jinfu.industry.service.IndustryService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2017年05月18日.
 */
@Controller
@RequestMapping(value = "industry")
public class IndustryController {

    @Autowired
    private IndustryService industryService;
    @Autowired
    private StoreService storeService;

    /**
     * 所有行业列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "所有行业列表")
    public ResultDto<List<IndustryDto>> getList() {
        List<IndustryDto> list = industryService.getAllIndustry();
        List<IndustryDto> result = new ArrayList<>();
        for (IndustryDto industryDto : list) {
            if (!"5227".equals(industryDto.getMcc())) {//过滤烟草店
                result.add(industryDto);
            }
        }
        return ResultDtoFactory.toAck("获取成功", result);
    }

    /**
     * 根据商户id获取商户所属行业
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getByUserId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据商户id获取商户所属行业 null=没有店铺")
    public ResultDto<IndustryDto> getByUserId(String userId) {
        List<StoreInfDto> storeList = storeService.findByUserId(userId);
        if (CollectionUtils.isEmpty(storeList)) {
            return ResultDtoFactory.toAck("获取成功", null);
        }
        IndustryDto industry = industryService.getByMcc(storeList.get(0).getIndustryMcc());
        return ResultDtoFactory.toAck("获取成功", industry);
    }

}
