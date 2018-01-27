package com.xinyunlian.jinfu.report.dealer.service;

import com.xinyunlian.jinfu.report.dealer.dto.DealerStatsMonthOrderDto;
import com.xinyunlian.jinfu.report.dealer.dto.DealerStatsOrderSearchDto;

import java.util.List;

/**
 * Created by menglei on 2016/12/07.
 */
public interface DealerStatsOrderService {

    DealerStatsOrderSearchDto getStatsOrderPage(DealerStatsOrderSearchDto dealerStatsOrderSearchDto);

    List<DealerStatsMonthOrderDto> getMonthByUserId(String userId, List<String> months);

    List<DealerStatsMonthOrderDto> getMonthByDealerId(String dealerId, List<String> months);

}
