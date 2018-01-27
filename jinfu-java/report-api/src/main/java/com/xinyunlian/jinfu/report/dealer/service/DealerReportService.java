package com.xinyunlian.jinfu.report.dealer.service;

import com.xinyunlian.jinfu.report.dealer.dto.*;

import java.util.List;

/**
 * Created by bright on 2016/11/29.
 */
public interface DealerReportService {
    List<DealerStoreInfDto> getStoreReport(DealerStoreSearchDto searchDto);

    List<DealerOrderDto> getOrderReport(DealerOrderSearchDto searchDto);

    List<DealerLogDto> getLogReport(DealerLogSearchDto dealerLogSearchDto);

    List<DealerInfDto> getDealerReport(DealerReportSearchDto searchDto);

    List<InsuranceDto> getInsuranceReport(InsuranceSearchDto searchDto);

    List<UserReportDto> getUserReport(UserReportSearchDto searchDto);

    List<DealerUserReportDto> getDealerUserReport(DealerReportSearchDto searchDto);
}
