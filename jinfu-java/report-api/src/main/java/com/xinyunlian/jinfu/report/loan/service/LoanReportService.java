package com.xinyunlian.jinfu.report.loan.service;

import com.xinyunlian.jinfu.report.loan.dto.LoanDtlSearchDto;
import com.xinyunlian.jinfu.report.loan.dto.RepayDtlSearchDto;
import com.xinyunlian.jinfu.report.loan.dto.RepaySchdSearchDto;
import com.xinyunlian.jinfu.report.loan.enums.EReportType;

/**
 * Created by dell on 2016/11/7.
 */
public interface LoanReportService {
    LoanDtlSearchDto getLoanDtlReport(LoanDtlSearchDto searchDto, EReportType reportType);

    RepaySchdSearchDto getRepaySchdReport(RepaySchdSearchDto searchDto, EReportType reportType);

    RepayDtlSearchDto getRepayDtlReport(RepayDtlSearchDto searchDto, EReportType reportType);
}
