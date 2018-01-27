package com.xinyunlian.jinfu.audit.dao;

import com.xinyunlian.jinfu.audit.entity.LoanAuditPo;
import com.xinyunlian.jinfu.audit.enums.EAuditType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

/**
 * @author willwang
 */
public interface LoanAuditDao extends JpaRepository<LoanAuditPo, String>, JpaSpecificationExecutor<LoanAuditPo> {

    @Query("FROM LoanAuditPo a WHERE a.applId = ?1 order by a.auditId asc")
    List<LoanAuditPo> findByApplId(String applId);

    List<LoanAuditPo> findByApplIdIn(Collection<String> applIds);

    LoanAuditPo findByApplIdAndAuditType(String applId, EAuditType auditType);

    void deleteByApplId(String applId);

}
