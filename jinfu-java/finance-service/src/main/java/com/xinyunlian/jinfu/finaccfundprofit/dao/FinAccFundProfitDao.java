package com.xinyunlian.jinfu.finaccfundprofit.dao;

import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;
import com.xinyunlian.jinfu.finaccfundprofit.entity.FinAccFundProfitPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by dongfangchao on 2016/11/23.
 */
public interface FinAccFundProfitDao extends JpaRepository<FinAccFundProfitPo, Long>, JpaSpecificationExecutor<FinAccFundProfitPo> {

    @Query(nativeQuery = true, value = "select * from fin_acc_fund_profit where USER_ID = ?1 AND EXT_TX_ACC_ID = ?2 AND FIN_FUND_ID = ?3 AND FIN_ORG = ?4")
    FinAccFundProfitPo findByUindex(String userId, String extUserId, Long finFundId, String finOrg);

    FinAccFundProfitPo findByFinOrgAndExtTxAccId(EFinOrg finOrg, String extTxAccId);

}
