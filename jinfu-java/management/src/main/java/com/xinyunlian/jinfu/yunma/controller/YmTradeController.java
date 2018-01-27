package com.xinyunlian.jinfu.yunma.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.trade.dto.TradeTotal;
import com.xinyunlian.jinfu.trade.dto.YmTradeDto;
import com.xinyunlian.jinfu.trade.dto.YmTradeSearchDto;
import com.xinyunlian.jinfu.trade.service.YmTradeService;
import com.xinyunlian.jinfu.yunma.dto.YMMemberReportDto;
import com.xinyunlian.jinfu.yunma.dto.YMMemberSearchDto;
import com.xinyunlian.jinfu.yunma.dto.YMTradeReportDto;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by King on 2017/1/6.
 */
@Controller
@RequestMapping(value = "yunma/trade")
public class YmTradeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(YmTradeController.class);

    @Autowired
    private YmTradeService tradeService;


    /**
     * 云码交易流水列表查询
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> getList(@RequestBody YmTradeSearchDto searchDto) {
        searchDto = tradeService.getTradePage(searchDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), searchDto);
    }

    /**
     * 云码交易流水合计
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "tradeTotal", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> getTradeTotal(@RequestBody YmTradeSearchDto searchDto) {
        TradeTotal tradeTotal = tradeService.getTradeTotal(searchDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), tradeTotal);

    }

    @RequestMapping(value = "getByMemberNo", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getByMemberNo(@RequestParam String memberNo) {
        List<YmTradeDto> ymTradeDtos = tradeService.findByMemberNo(memberNo);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), ymTradeDtos);

    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ModelAndView export(YmTradeSearchDto searchDto) {
        List<YmTradeDto> list = tradeService.getTradeExportList(searchDto);
        List<YMTradeReportDto> data = new ArrayList<>();

        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(ymTradePo -> {
                YMTradeReportDto ymTradeReportDto = ConverterService.convert(ymTradePo, YMTradeReportDto.class);
                data.add(ymTradeReportDto);
            });
        }

        Map<String, Object> model = new HashedMap();
        model.put("data", data);
        model.put("fileName", "云码流水记录.xls");
        model.put("tempPath", "/templates/云码流水记录.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }

}
