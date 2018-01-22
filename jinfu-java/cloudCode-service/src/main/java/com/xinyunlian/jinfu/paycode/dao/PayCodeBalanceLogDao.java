package com.xinyunlian.jinfu.paycode.dao;

import com.xinyunlian.jinfu.paycode.entity.PayCodeBalanceLogPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

/**
 * Created by carrot on 2017/8/28.
 */
public interface PayCodeBalanceLogDao extends JpaRepository<PayCodeBalanceLogPo, Long>, JpaSpecificationExecutor<PayCodeBalanceLogPo> {

    @Query(nativeQuery = true, value = "SELECT IFNULL(SUM(amount),0) FROM pay_code_balance_log where PAY_CODE_NO = ?1 AND type = 'PAY'")
    BigDecimal sumByPayCodeNo(String payCodeNo);

}
