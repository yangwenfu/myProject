package com.xinyunlian.jinfu.dealer.order.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.dealer.order.dto.DealerOrderReportDto;
import com.xinyunlian.jinfu.report.dealer.dto.DealerOrderDto;
import com.xinyunlian.jinfu.report.dealer.dto.DealerOrderSearchDto;
import com.xinyunlian.jinfu.dealer.order.dto.OrderSearchDto;
import com.xinyunlian.jinfu.dealer.order.service.OrderService;
import com.xinyunlian.jinfu.report.dealer.service.DealerReportService;
import com.xinyunlian.jinfu.prod.enums.EProd;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by menglei on 2016年10月08日.
 */
@Controller
@RequestMapping(value = "dealer/order")
@RequiresPermissions({"DA_ORDER"})
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DealerReportService dealerReportService;

    /**
     * 分页查询订单列表
     *
     * @param orderSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<OrderSearchDto> getOrderPage(OrderSearchDto orderSearchDto) {
        OrderSearchDto page = new OrderSearchDto();
        if (orderSearchDto.getProdId().equals(EProd.S01001.getCode()) || orderSearchDto.getProdId().equals(EProd.S01002.getCode())) {//店铺宝，车险
            page = orderService.getInsPage(orderSearchDto);
        } else if (orderSearchDto.getProdId().equals(EProd.L01001.getCode()) || orderSearchDto.getProdId().equals(EProd.L01002.getCode())) {//云速贷，云随贷
            page = orderService.getLoanPage(orderSearchDto);
        }
        return ResultDtoFactory.toAck("获取成功", page);
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ModelAndView exportOrder(DealerOrderSearchDto searchDto) {
        List<DealerOrderDto> list = dealerReportService.getOrderReport(searchDto);

        List<DealerOrderReportDto> data = new ArrayList<>();
        for (DealerOrderDto dealerOrderDto : list) {
            DealerOrderReportDto dealerOrderReportDto = ConverterService.convert(dealerOrderDto, DealerOrderReportDto.class);
            data.add(dealerOrderReportDto);
        }
        Map<String, Object> model = new HashedMap();
        model.put("data", data);
        model.put("fileName", "订单记录.xls");
        model.put("tempPath", "/templates/订单记录.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }

}
