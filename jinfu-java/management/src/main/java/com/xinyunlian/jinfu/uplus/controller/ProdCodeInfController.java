package com.xinyunlian.jinfu.uplus.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.qrcode.dto.*;
import com.xinyunlian.jinfu.qrcode.enums.EProdCodeStatus;
import com.xinyunlian.jinfu.qrcode.service.ProdCodeService;
import com.xinyunlian.jinfu.qrcode.service.ScanCodeRecordService;
import com.xinyunlian.jinfu.uplus.dto.ProdCodeDetailDto;
import com.xinyunlian.jinfu.uplus.dto.ProdCodeReportDto;
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
 * Created by menglei on 2017/3/16.
 */
@Controller
@RequestMapping(value = "uplus/prodCode")
public class ProdCodeInfController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProdCodeInfController.class);

    @Autowired
    private ProdCodeService prodCodeService;
    @Autowired
    private ScanCodeRecordService scanCodeRecordService;

    /**
     * 分页查询商品码(视图)
     *
     * @param prodCodeViewSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/viewList", method = RequestMethod.GET)
    public ResultDto<ProdCodeViewSearchDto> getViewListPage(ProdCodeViewSearchDto prodCodeViewSearchDto) {
        if (EProdCodeStatus.UNUSED.equals(prodCodeViewSearchDto.getStatus())) {
            return ResultDtoFactory.toNack("状态不正确");
        }
        ProdCodeViewSearchDto page = prodCodeService.getProdCodeViewPage(prodCodeViewSearchDto);
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 分页查询新商品码
     *
     * @param prodCodeSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<ProdCodeSearchDto> getListPage(ProdCodeSearchDto prodCodeSearchDto) {
        ProdCodeSearchDto page = prodCodeService.getProdCodePage(prodCodeSearchDto);
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 商品码详情(视图)
     *
     * @param prodCodeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/viewDetail", method = RequestMethod.GET)
    public ResultDto<Object> getViewDetail(@RequestParam Long prodCodeId) {
        ProdCodeViewDto dto = prodCodeService.getViewOne(prodCodeId);
        //每次扫码记录
        List<ScanCodeRecordDto> list = scanCodeRecordService.findByProdCodeId(prodCodeId);
        ProdCodeDetailDto prodCodeDetailDto = ConverterService.convert(dto, ProdCodeDetailDto.class);
        prodCodeDetailDto.setScanCodeRecordList(list);
        return ResultDtoFactory.toAck("获取成功", prodCodeDetailDto);
    }

    /**
     * 冻结解冻
     *
     * @param prodCodeId
     * @return
     */
    @RequestMapping(value = "updateFrozen", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> updateFrozen(@RequestParam Long prodCodeId) {
        prodCodeService.frozenProdCode(prodCodeId);
        return ResultDtoFactory.toAck("操作成功");
    }

    /**
     * 删除商品码
     *
     * @param prodCodeId
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> delete(@RequestParam Long prodCodeId) {
        ProdCodeDto prodCodeDto = prodCodeService.getOne(prodCodeId);
        if (prodCodeDto == null) {
            return ResultDtoFactory.toNack("商品码不存在");
        } else if (!EProdCodeStatus.UNUSED.equals(prodCodeDto.getStatus())) {
            return ResultDtoFactory.toNack("商品码无法删除");
        }
        prodCodeService.deleteProdCode(prodCodeId);
        return ResultDtoFactory.toAck("删除成功");
    }

    /**
     * 批量生成商品码
     *
     * @param count
     * @return
     */
    @RequestMapping(value = "/bathAdd", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> bathAdd(@RequestParam Integer count) {
        if (count == null || count > 1000 || count < 1) {
            return ResultDtoFactory.toNack("数量必须在1-1000范围之内");
        }
        Boolean flag = prodCodeService.addBathProdCode(count);
        if (!flag) {
            return ResultDtoFactory.toNack("历史商品码格式有误");
        }
        return ResultDtoFactory.toAck("生成成功");
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ModelAndView export(ProdCodeSearchDto prodCodeSearchDto) {
        List<ProdCodeDto> list = prodCodeService.getProdCodeExportList(prodCodeSearchDto);
        List<ProdCodeReportDto> data = new ArrayList<>();

        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(prodCodeDto -> {
                ProdCodeReportDto prodCodeReportDto = ConverterService.convert(prodCodeDto, ProdCodeReportDto.class);
                prodCodeReportDto.setCreateTime(DateHelper.formatDate(prodCodeDto.getCreateTs(), "yyyy-MM-dd HH:mm:ss"));
                prodCodeReportDto.setBindTime(DateHelper.formatDate(prodCodeDto.getBindTime(), "yyyy-MM-dd HH:mm:ss"));
                data.add(prodCodeReportDto);
            });
        }

        Map<String, Object> model = new HashedMap();
        model.put("data", data);
        model.put("fileName", "商品码记录.xls");
        model.put("tempPath", "/templates/商品码记录.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }
}
