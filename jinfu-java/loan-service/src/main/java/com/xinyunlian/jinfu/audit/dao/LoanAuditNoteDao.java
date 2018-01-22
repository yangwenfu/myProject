package com.xinyunlian.jinfu.audit.dao;

import com.xinyunlian.jinfu.audit.entity.LoanAuditNotePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author willwang
 */
public interface LoanAuditNoteDao extends JpaRepository<LoanAuditNotePo, String>, JpaSpecificationExecutor<LoanAuditNotePo> {
}
