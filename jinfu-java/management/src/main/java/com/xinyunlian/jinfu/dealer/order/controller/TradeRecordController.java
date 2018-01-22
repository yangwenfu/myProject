package com.xinyunlian.jinfu.dealer.order.controller;

import com.xinyunlian.jinfu.api.dto.TradeCountSearchDto;
import com.xinyunlian.jinfu.api.dto.TradeSearchDto;
import com.xinyunlian.jinfu.api.service.ApiYunMaService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by menglei on 2016年09月23日.
 */
@Controller
@RequestMapping(value = "dealer/trade")
public class TradeRecordController {

    @Autowired
    private ApiYunMaService apiYunMaService;

    /**
     * 根据storeId分页查询流水统计
     *
     * @param tradeCountSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listByStoreId", method = RequestMethod.GET)
    public ResultDto<TradeCountSearchDto> getTradeCountPage(TradeCountSearchDto tradeCountSearchDto) {
        TradeCountSearchDto page = apiYunMaService.getTradeCountByStoreId(tradeCountSearchDto);
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 根据storeId分页查询流水
     *
     * @param tradeSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/tradeListByStoreId", method = RequestMethod.GET)
    public ResultDto<TradeSearchDto> getTradePage(TradeSearchDto tradeSearchDto) {
        TradeSearchDto page = apiYunMaService.getTradeByStoreId(tradeSearchDto);
        return ResultDtoFactory.toAck("获取成功", page);
    }

}
