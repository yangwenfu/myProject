package com.xinyunlian.jinfu.audit.dao;

import com.xinyunlian.jinfu.audit.entity.LoanAttPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by dongfangchao on 2017/2/20/0020.
 */
public interface LoanAttDao extends JpaRepository<LoanAttPo, Long>, JpaSpecificationExecutor<LoanAttPo> {

    List<LoanAttPo> findByApplyId(String applyId);

}
