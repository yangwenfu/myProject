package com.xinyunlian.jinfu.loan.dao;

import com.xinyunlian.jinfu.loan.entity.LoanApplExtPo;
import com.xinyunlian.jinfu.loan.entity.LoanApplPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author willwang
 */
public interface LoanApplExtDao extends JpaRepository<LoanApplExtPo, String>, JpaSpecificationExecutor<LoanApplExtPo> {

    LoanApplExtPo findByApplId(String applId);

}
