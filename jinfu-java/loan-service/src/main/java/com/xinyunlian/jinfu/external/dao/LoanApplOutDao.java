package com.xinyunlian.jinfu.external.dao;

import com.xinyunlian.jinfu.external.entity.LoanApplOutPo;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author willwang
 */
public interface LoanApplOutDao extends JpaRepository<LoanApplOutPo, String>, JpaSpecificationExecutor<LoanApplOutPo> {

    List<LoanApplOutPo> findByApplId(String applId);

    List<LoanApplOutPo> findByApplIdAndType(String applId, EApplOutType type);

    List<LoanApplOutPo> findByOutTradeNoAndType(String outTradeNo, EApplOutType type);

}
