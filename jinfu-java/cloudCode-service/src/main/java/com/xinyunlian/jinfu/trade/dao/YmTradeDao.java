package com.xinyunlian.jinfu.trade.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xinyunlian.jinfu.trade.entity.YmTradePo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 云码流水表DAO接口
 *
 * @author jll
 */
public interface YmTradeDao extends JpaRepository<YmTradePo, Long>, JpaSpecificationExecutor<YmTradePo> {

    /**
     * 按日统计查询流水列表
     *
     * @return
     */
    @Query(nativeQuery = true, value = "select DATE_FORMAT(CREATE_TS,'%Y-%m-%d') dates,DATE_FORMAT(CREATE_TS,'%m-%d') days, " +
            "case when DAYOFWEEK(CREATE_TS)=2 then '周一' when DAYOFWEEK(CREATE_TS)=3 then '周二' when DAYOFWEEK(CREATE_TS)=4 then '周三' " +
            "when DAYOFWEEK(CREATE_TS)=5 then '周四' when DAYOFWEEK(CREATE_TS)=6 then '周五' when DAYOFWEEK(CREATE_TS)=7 then '周六' " +
            "when DAYOFWEEK(CREATE_TS)=1 then '周日' else '' END weeks,BIZ_CODE,SUM(TRANS_AMT) amount,SUM(TRANS_FEE) fee,count(ID) as count " +
            "from ym_trade where MEMBER_NO=?1 and STATUS=1 group by BIZ_CODE,days order by ID desc")
    List<Object[]> findDayByMemberNo(String memberNo);

    /**
     * 按日期和商户号查询流水列表
     *
     * @param memberNo
     * @param dates
     * @return
     */
    @Query(nativeQuery = true, value = "select * from ym_trade where MEMBER_NO=?1 and STATUS=1 and DATE_FORMAT(CREATE_TS,'%Y-%m-%d')=?2 order by ID desc")
    List<YmTradePo> findByMemberNoAndDates(String memberNo, String dates);

    List<YmTradePo> findByMemberNo(String memberNo);

    YmTradePo findByTradeNo(String tradeNo);

    /**
     * 更新订单memberNo(老商户进件用)
     * @param newMemberNo
     * @param oldMemberNo
     */
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE ym_trade SET MEMBER_NO = ?1 where MEMBER_NO = ?2")
    void updateByMemberNo(String newMemberNo, String oldMemberNo);

}
