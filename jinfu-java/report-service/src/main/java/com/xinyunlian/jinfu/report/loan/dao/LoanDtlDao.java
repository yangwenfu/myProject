package com.xinyunlian.jinfu.report.loan.dao;

import com.xinyunlian.jinfu.report.loan.entity.LoanDtlPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dell on 2016/11/7.
 */
public interface LoanDtlDao extends JpaRepository<LoanDtlPo, String>, JpaSpecificationExecutor<LoanDtlPo> {
}
