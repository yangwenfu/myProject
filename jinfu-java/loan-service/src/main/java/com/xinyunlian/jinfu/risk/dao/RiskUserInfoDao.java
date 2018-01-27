package com.xinyunlian.jinfu.risk.dao;

import com.xinyunlian.jinfu.loan.entity.LoanDtlPo;
import com.xinyunlian.jinfu.risk.entity.RiskUserInfoPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author willwang
 */
public interface RiskUserInfoDao extends JpaRepository<RiskUserInfoPo, String>, JpaSpecificationExecutor<LoanDtlPo> {

    RiskUserInfoPo findByUserId(String userId);

    @Modifying
    @Transactional
    @Query(value="delete from risk_user_info where user_id = ?1",nativeQuery=true)
    void deleteByUserId(String userId);

    int countByUserId(String userId);
}
