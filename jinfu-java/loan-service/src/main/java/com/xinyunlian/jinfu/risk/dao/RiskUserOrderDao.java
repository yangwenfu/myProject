package com.xinyunlian.jinfu.risk.dao;

import com.xinyunlian.jinfu.loan.entity.LoanDtlPo;
import com.xinyunlian.jinfu.risk.entity.RiskUserInfoPo;
import com.xinyunlian.jinfu.risk.entity.RiskUserOrderPo;
import com.xinyunlian.jinfu.risk.enums.ERiskOrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author willwang
 */
public interface RiskUserOrderDao extends JpaRepository<RiskUserOrderPo, String>, JpaSpecificationExecutor<LoanDtlPo> {

    List<RiskUserOrderPo> findByUserIdAndRiskOrderType(String userId, ERiskOrderType riskOrderType);

    @Modifying
    @Transactional
    @Query(value="delete from risk_user_order where user_id = ?1",nativeQuery=true)
    void deleteByUserId(String userId);

    int countByUserId(String userId);
}
