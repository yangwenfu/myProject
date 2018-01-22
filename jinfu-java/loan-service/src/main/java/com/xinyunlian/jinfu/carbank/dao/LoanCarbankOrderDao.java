package com.xinyunlian.jinfu.carbank.dao;

import com.xinyunlian.jinfu.carbank.entity.LoanCarbankOrderPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
public interface LoanCarbankOrderDao extends JpaRepository<LoanCarbankOrderPo, String>, JpaSpecificationExecutor<LoanCarbankOrderPo>{

    LoanCarbankOrderPo findByOutTradeNo(String outTradeNo);

    List<LoanCarbankOrderPo> findByUserId(String userId);

    @Query(nativeQuery = true, value = "SELECT * FROM loan_carbank_order WHERE (ORDER_STATUS ='3' OR OVERDUE = 1) AND USER_ID = ?")
    List<LoanCarbankOrderPo> findSuccessOrder(String userId);

}
