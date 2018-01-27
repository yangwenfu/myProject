package com.xinyunlian.jinfu.loan.dao;

import com.xinyunlian.jinfu.loan.entity.LoanDtlPo;
import com.xinyunlian.jinfu.loan.enums.ETransferStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

/**
 * @author willwang
 */
public interface LoanDtlDao extends JpaRepository<LoanDtlPo, String>, JpaSpecificationExecutor<LoanDtlPo> {
    LoanDtlPo findByApplId(String applId);

    List<LoanDtlPo> findByApplIdIn(Collection<String> applIds);

    Long countByUserIdAndTransferStat(String userId, ETransferStat transferStat);

    @Query(nativeQuery = true, value="select * from fp_loan_dtl limit 1")
    LoanDtlPo getFirst();

    Long countByUserIdAndProdId(String userId, String prodId);

    List<LoanDtlPo> findByBankCardId(Long bankCardId);

    @Query(nativeQuery = true, value = "SELECT * from fp_loan_dtl where USER_ID = ?1 order by create_ts desc")
    List<LoanDtlPo> findByUserId(String userId);
}
