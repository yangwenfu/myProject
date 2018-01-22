package com.xinyunlian.jinfu.external.dao;

import com.xinyunlian.jinfu.external.entity.LoanApplOutPo;
import com.xinyunlian.jinfu.external.entity.LoanApplOutRefundsAdvancePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by godslhand on 2017/7/14.
 */
public interface LoanApplOutRefundsAdvanceDao extends
        JpaRepository<LoanApplOutRefundsAdvancePo, String>, JpaSpecificationExecutor<LoanApplOutRefundsAdvancePo> {

    List<LoanApplOutRefundsAdvancePo> findByApplyIdAndDisabledOrderByIdDesc(String applyId, boolean disabled);

    @Query("from LoanApplOutRefundsAdvancePo l where l.applyId = ?1 and l.outRefundsAdvanceId=?2 and l.disabled =false")
    List<LoanApplOutRefundsAdvancePo> findByApplyIdAndOutId(String applyId,String refoundsId);


    @Modifying
    @Query(nativeQuery = true, value = "UPDATE fp_loan_appl_out_refundsadvance SET DISABLED = true where apply_id = ?1 and OUT_REFUNDS_ADVANCE_ID = ?2")
    void updateDisableTag(String applyId, String outRefundsId);
}
