package com.xinyunlian.jinfu.trade.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.trade.dto.TradeTotal;
import com.xinyunlian.jinfu.trade.dto.YmTradeDayDto;
import com.xinyunlian.jinfu.trade.dto.YmTradeDto;
import com.xinyunlian.jinfu.trade.dto.YmTradeSearchDto;

import java.util.List;

/**
 * 云码流水表Service
 * @author jll
 * @version 
 */

public interface YmTradeService {

    TradeTotal getTradeTotal(YmTradeSearchDto searchDto);

    /**
     * 分页获取云码流水
     * @param searchDto
     * @return
     */
    YmTradeSearchDto getTradePage(YmTradeSearchDto searchDto);

    /**
     * 导数据
     * @param searchDto
     * @return
     */
    List<YmTradeDto> getTradeExportList(YmTradeSearchDto searchDto);

    /**
     * 添加流水
     * @param ymTradeDto
     * @return
     * @throws BizServiceException
     */
    YmTradeDto saveTrade(YmTradeDto ymTradeDto) throws BizServiceException;

    /**
     * 按日统计查询流水列表
     *
     * @param memberNo
     * @return
     */
    List<YmTradeDayDto> findDayByMemberNo(String memberNo);

    /**
     * 按日期和商户号查询流水列表
     *
     * @param memberNo
     * @param dates
     * @return
     */
    List<YmTradeDto> findByMemberNoAndDates(String memberNo, String dates);

    /**
     * 通过商户号查询流水
     * @param memberNo
     * @return
     */
    List<YmTradeDto> findByMemberNo(String memberNo);

    YmTradeDto updateTrade(YmTradeDto ymTradeDto) throws BizServiceException;

    YmTradeDto findByTradeNo(String tradeNo);

    YmTradeDto updateOutTradeNo(YmTradeDto ymTradeDto) throws BizServiceException;

    /**
     * 生成订单
     * @return
     */
    String getOrderNo();

}
