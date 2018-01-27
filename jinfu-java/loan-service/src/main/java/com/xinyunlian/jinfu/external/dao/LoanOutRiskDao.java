package com.xinyunlian.jinfu.external.dao;

import com.xinyunlian.jinfu.external.entity.LoanApplOutPo;
import com.xinyunlian.jinfu.external.entity.LoanApplOutRiskPo;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author willwang
 */
public interface LoanOutRiskDao extends JpaRepository<LoanApplOutRiskPo, String>, JpaSpecificationExecutor<LoanApplOutRiskPo> {

    LoanApplOutRiskPo findByUserIdAndApplyId(String outUserId,String outLoanId);



}
