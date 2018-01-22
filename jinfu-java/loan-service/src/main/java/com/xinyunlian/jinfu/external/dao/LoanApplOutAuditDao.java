package com.xinyunlian.jinfu.external.dao;

import com.xinyunlian.jinfu.external.entity.LoanApplOutAuditPo;
import com.xinyunlian.jinfu.external.entity.LoanApplOutPo;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

/**
 * @author willwang
 */
public interface LoanApplOutAuditDao extends JpaRepository<LoanApplOutAuditPo, String>, JpaSpecificationExecutor<LoanApplOutAuditPo> {


    @Query(nativeQuery = true, value= "select * from FP_LOAN_APPL_OUT_AUDIT where APPL_ID = ?1 order by id asc limit 0,1")
    LoanApplOutAuditPo findByApplId(String applId);

    @Query(nativeQuery = true, value= "select * from FP_LOAN_APPL_OUT_AUDIT where APPL_ID = ?1 limit 0,1")
    LoanApplOutAuditPo findByApplIdLatest(String applId);

    List<LoanApplOutAuditPo> findByApplIdIn(Collection<String> applIds);
}
