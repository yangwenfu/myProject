package com.xinyunlian.jinfu.external.dao;

import com.xinyunlian.jinfu.external.entity.LoanApplOutBankCardPo;
import com.xinyunlian.jinfu.external.entity.LoanApplOutPo;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * @author willwang
 */
public interface LoanApplOutBankCardDao extends JpaRepository<LoanApplOutBankCardPo, String>, JpaSpecificationExecutor<LoanApplOutBankCardPo> {

    @Query(nativeQuery = true, value = "select * from FP_LOAN_APPL_OUT_BANKCARD where user_id = ?1 order by id desc limit 0,1")
    LoanApplOutBankCardPo findByUserLatest(String userId);

    LoanApplOutBankCardPo findByApplId(String applId);

    List<LoanApplOutBankCardPo> findByApplIdIn(Set<String> applIds);


}
