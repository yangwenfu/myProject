package com.xinyunlian.jinfu.report.allloan.service;

import com.xinyunlian.jinfu.report.allloan.dto.LoanDtlSearchDto;
import com.xinyunlian.jinfu.report.allloan.dto.RepayDtlSearchDto;
import com.xinyunlian.jinfu.report.allloan.dto.RepaySchdSearchDto;
import com.xinyunlian.jinfu.report.allloan.enums.EReportType;

/**
 * Created by dell on 2016/11/7.
 */
public interface AllLoanReportService {
    LoanDtlSearchDto getLoanDtlReport(LoanDtlSearchDto searchDto, EReportType reportType);

    RepaySchdSearchDto getRepaySchdReport(RepaySchdSearchDto searchDto, EReportType reportType);

    RepayDtlSearchDto getRepayDtlReport(RepayDtlSearchDto searchDto, EReportType reportType);
}
