package com.xinyunlian.jinfu.report.dealer.service;

import com.xinyunlian.jinfu.report.dealer.dto.DealerStatsMonthObjectDto;
import com.xinyunlian.jinfu.report.dealer.dto.DealerStatsMonthSearchDto;

import java.util.List;

/**
 * Created by menglei on 2016/12/07.
 */
public interface DealerStatsMonthService {

    DealerStatsMonthSearchDto getStatsMonthPageByUserId(DealerStatsMonthSearchDto dealerStatsMonthSearchDto);

    DealerStatsMonthSearchDto getStatsMonthPageByDealerId(DealerStatsMonthSearchDto dealerStatsMonthSearchDto);

    List<DealerStatsMonthObjectDto> getMonthMemberByUserId(String userId, List<String> months);

    List<DealerStatsMonthObjectDto> getMonthMemberByDealerId(String dealerId, List<String> months);

    List<DealerStatsMonthObjectDto> getMonthSignInfoByUserId(String userId, List<String> months);

    List<DealerStatsMonthObjectDto> getMonthSignInfoByDealerId(String dealerId, List<String> months);

}
