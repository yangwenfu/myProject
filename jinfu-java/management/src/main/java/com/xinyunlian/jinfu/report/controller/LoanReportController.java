package com.xinyunlian.jinfu.report.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.report.loan.dto.*;
import com.xinyunlian.jinfu.report.loan.enums.EReportType;
import com.xinyunlian.jinfu.report.loan.service.LoanReportService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/11/8.
 */
@RestController
@RequestMapping("loanReport")
public class LoanReportController {
    @Autowired
    private LoanReportService loanReportService;

    @RequiresPermissions({"LOAN_HISTORY"})
    @RequestMapping(value = "loanDtl", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<LoanDtlSearchDto> getLoanDtl(LoanDtlSearchDto searchDto){
        searchDto = loanReportService.getLoanDtlReport(searchDto, EReportType.HTML);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), searchDto);
    }

    @RequiresPermissions({"REPAYMENT_PLAN"})
    @RequestMapping(value = "repaySchd", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<RepaySchdSearchDto> getRepaySchd(RepaySchdSearchDto searchDto){
        searchDto = loanReportService.getRepaySchdReport(searchDto, EReportType.HTML);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), searchDto);
    }

    @RequiresPermissions({"REPAYMENT_HISTORY"})
    @RequestMapping(value = "repayDtl", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<RepayDtlSearchDto> getRepayDtl(RepayDtlSearchDto searchDto){
        searchDto = loanReportService.getRepayDtlReport(searchDto, EReportType.HTML);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), searchDto);
    }

    @RequiresPermissions({"LOAN_HISTORY"})
    @RequestMapping(value = "loanDtl/export", method = RequestMethod.GET)
    public ModelAndView exportLoanDtl( LoanDtlSearchDto searchDto){
        searchDto = loanReportService.getLoanDtlReport(searchDto, EReportType.EXCEL);
        List<LoanDtlDto> data = searchDto.getList();
        Map<String, Object> model = new HashedMap();
        model.put("data", data);
        model.put("fileName","贷款记录.xls");
        model.put("tempPath","/templates/贷款记录.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }

    @RequiresPermissions({"REPAYMENT_PLAN"})
    @RequestMapping(value = "repaySchd/export", method = RequestMethod.GET)
    public ModelAndView exportRepaySchd( RepaySchdSearchDto searchDto){
        searchDto = loanReportService.getRepaySchdReport(searchDto, EReportType.EXCEL);
        List<RepayScheduleDto> data = searchDto.getList();
        Map<String, Object> model = new HashedMap();
        model.put("data", data);
        model.put("fileName","还款计划.xls");
        model.put("tempPath","/templates/还款计划.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }

    @RequiresPermissions({"REPAYMENT_HISTORY"})
    @RequestMapping(value = "repayDtl/export", method = RequestMethod.GET)
    public ModelAndView exportRepayDtl(RepayDtlSearchDto searchDto){
        searchDto = loanReportService.getRepayDtlReport(searchDto, EReportType.EXCEL);
        List<RepayDtlDto> data = searchDto.getList();
        Map<String, Object> model = new HashedMap();
        model.put("data", data);
        model.put("fileName","还款记录.xls");
        model.put("tempPath","/templates/还款记录.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }
}
