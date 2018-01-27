package com.xinyunlian.jinfu.loan.dao;

import com.xinyunlian.jinfu.loan.entity.LoanApplPo;
import com.xinyunlian.jinfu.loan.enums.EApplStatus;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author willwang
 */
public interface LoanApplDao extends JpaRepository<LoanApplPo, String>, JpaSpecificationExecutor<LoanApplPo> {

    List<LoanApplPo> findByUserId(String userId);

    List<LoanApplPo> findByUserIdAndFinanceSourceId(String userId, Integer financeSourceId);

    List<LoanApplPo> findByApplStatus(EApplStatus applStatus);

    /**
     * 根据申请表和贷款表、审核表关联找出审核通过1个月仍然未使用的申请单
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT a.appl_id FROM fp_loan_audit a LEFT JOIN fp_loan_appl b ON a.APPL_ID = b.APPL_ID LEFT JOIN fp_loan_dtl c ON a.APPL_ID = c.APPL_ID WHERE 1 = 1 AND a.AUDIT_TYPE = '02' AND a.`STATUS` = '01' AND c.LOAN_ID IS NULL AND b.APPL_STATUS = '02' AND a.AUDIT_DATE <= DATE_SUB(now(), INTERVAL 1 MONTH)")
    List<String> findLongtimeNoused();


    /**
     * 根据申请表和贷款表、审核表关联找出审核通过10天仍然未使用的小烟贷申请单
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT a.appl_id FROM fp_loan_audit a LEFT JOIN fp_loan_appl b ON a.APPL_ID = b.APPL_ID LEFT JOIN fp_loan_dtl c ON a.APPL_ID = c.APPL_ID WHERE b.REPAY_MODE = '08' AND a.AUDIT_TYPE = '02' AND a.`STATUS` = '01' AND c.LOAN_ID IS NULL AND b.APPL_STATUS = '02' AND a.AUDIT_DATE <= DATE_SUB(now(), INTERVAL 10 DAY)")
    List<String> findAvgCapAvgIntrTenDaysNoUsed();

}
