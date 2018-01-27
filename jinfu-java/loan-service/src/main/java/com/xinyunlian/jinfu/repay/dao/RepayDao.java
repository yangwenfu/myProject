package com.xinyunlian.jinfu.repay.dao;

import com.xinyunlian.jinfu.loan.entity.RepayDtlPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface RepayDao extends JpaRepository<RepayDtlPo, String>, JpaSpecificationExecutor<RepayDtlPo> {


    @Query("from RepayDtlPo i where i.loanId = ?1 order by i.repayId desc")
    List<RepayDtlPo> getRepayedList(String loanId);

    @Query("from RepayDtlPo i where i.loanId in ?1 order by i.repayId desc")
    List<RepayDtlPo> findByLoanIdIn(Collection<String> loanIds);

    @Query("from RepayDtlPo i where i.repayDateTime >= ?1 and i.repayDateTime <= ?2 order by i.repayId desc")
    List<RepayDtlPo> findByRepayDate(Date start, Date end);

    List<RepayDtlPo> findByRepayIdIn(Collection<String> repayIds);

}
