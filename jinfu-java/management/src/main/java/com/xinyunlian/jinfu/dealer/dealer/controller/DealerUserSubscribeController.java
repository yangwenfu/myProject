package com.xinyunlian.jinfu.dealer.dealer.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.dealer.dealer.dto.resp.DealerUserSubscribeReportDto;
import com.xinyunlian.jinfu.dealer.dto.*;
import com.xinyunlian.jinfu.dealer.service.DealerUserSubscribeService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by menglei on 2016年09月05日.
 */
@Controller
@RequestMapping(value = "dealer/subscribe")
@RequiresPermissions({"DT_MGT"})
public class DealerUserSubscribeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DealerUserSubscribeController.class);

    @Autowired
    private DealerUserSubscribeService dealerUserSubscribeService;

    /**
     * 分页查询关注公众号列表
     *
     * @param dealerUserSubscribeSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<DealerUserSubscribeSearchDto> getPage(DealerUserSubscribeSearchDto dealerUserSubscribeSearchDto) {
        DealerUserSubscribeSearchDto page = dealerUserSubscribeService.getPage(dealerUserSubscribeSearchDto);
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 关注导出
     *
     * @param dealerUserSubscribeSearchDto
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ApiOperation(value = "关注导出")
    public ModelAndView exportDealer(DealerUserSubscribeSearchDto dealerUserSubscribeSearchDto) {
        List<DealerUserSubscribeDto> list = dealerUserSubscribeService.getReport(dealerUserSubscribeSearchDto);
        List<DealerUserSubscribeReportDto> data = new ArrayList<>();
        for (DealerUserSubscribeDto dealerUserSubscribeDto : list) {
            DealerUserSubscribeReportDto dealerUserSubscribeReportDto = ConverterService.convert(dealerUserSubscribeDto, DealerUserSubscribeReportDto.class);
            data.add(dealerUserSubscribeReportDto);
        }
        Map<String, Object> model = new HashedMap();
        model.put("data", data);
        model.put("fileName", "微信公众号关注记录.xls");
        model.put("tempPath", "/templates/微信公众号关注记录.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }

}
