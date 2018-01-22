package com.xinyunlian.jinfu.finprofithistory.dao;

import com.xinyunlian.jinfu.finprofithistory.entity.FinProfitHistoryPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by dongfangchao on 2016/11/21.
 */
public interface FinProfitHistoryDao extends JpaRepository<FinProfitHistoryPo, Long>, JpaSpecificationExecutor<FinProfitHistoryPo> {

    /**
     * 查询收益记录，根据用户id、产品id和收益日期
     * @param userId
     * @param finFundId
     * @param profitDate
     * @return
     */
    FinProfitHistoryPo findByUserIdAndFinFundIdAndProfitDate(String userId, Long finFundId, Date profitDate);

    /**
     * 查询收益记录，根据用户id
     * @param userId
     * @return
     */
    List<FinProfitHistoryPo> findByUserId(String userId);

    /**
     * 查询收益记录，根据用户id和收益日期
     * @param userId
     * @return
     */
    List<FinProfitHistoryPo> findByUserIdAndProfitDate(String userId, Date profitDate);

    /**
     * 查询最新的收益记录
     * @param userId
     * @param finOrg
     * @param extTxAccId
     * @param finFundId
     * @return
     */
    @Query(nativeQuery = true, value = "select * from fin_profit_history where USER_ID = ?1 AND FIN_ORG = ?2 AND EXT_TX_ACC_ID = ?3 AND FIN_FUND_ID = ?4 order by PROFIT_DATE DESC LIMIT 1")
    FinProfitHistoryPo findByLatest(String userId, String finOrg, String extTxAccId, Long finFundId);

    /**
     * 查询唯一的一条记录
     * @param userId
     * @param extTxAccId
     * @param finFundId
     * @param profitDate
     * @return
     */
    @Query(nativeQuery = true, value = "select * from fin_profit_history where USER_ID = ?1 AND EXT_TX_ACC_ID = ?2 AND FIN_FUND_ID = ?3 AND PROFIT_DATE = ?4")
    FinProfitHistoryPo findSpecOne(String userId, String extTxAccId, Long finFundId, Date profitDate);

}
