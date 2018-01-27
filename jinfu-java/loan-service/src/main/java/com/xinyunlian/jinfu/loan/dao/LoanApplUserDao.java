package com.xinyunlian.jinfu.loan.dao;

import com.xinyunlian.jinfu.loan.entity.LoanApplUserPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author willwang
 */
public interface LoanApplUserDao extends JpaRepository<LoanApplUserPo, String>, JpaSpecificationExecutor<LoanApplUserPo> {

    LoanApplUserPo findByApplId(String applId);

}
