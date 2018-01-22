package com.xinyunlian.jinfu.log.dao;

import com.xinyunlian.jinfu.log.entity.LoanAuditLogPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by jl062 on 2017/2/20.
 */
public interface LoanAuditLogDao extends JpaRepository<LoanAuditLogPo, String>, JpaSpecificationExecutor<LoanAuditLogPo> {

    @Query("from LoanAuditLogPo a where a.applId = ?1 order by a.logId desc")
    List<LoanAuditLogPo> findByApplId(String applId);
}
