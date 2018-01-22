package com.xinyunlian.jinfu.balance.dao;

import com.xinyunlian.jinfu.balance.entity.BalanceCashierPo;
import com.xinyunlian.jinfu.balance.enums.EBalanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author Willwang
 */
public interface BalanceCashierDao extends JpaRepository<BalanceCashierPo, Long>, JpaSpecificationExecutor<BalanceCashierPo> {

    @Modifying
    @Query(nativeQuery = true, value = "update  BALANCE_CASHIER a set a.BALANCE_STATUS = ?1,a.balance_Date = ?2 where DATE_FORMAT(PAY_DATE,'%Y-%m-%d') = ?3")
    void updateStatusByDate(String balanceStatus, Date balanceDate, String date);

    List<BalanceCashierPo> findByOutlineId(Long outlineId);

    /**
     * repayDate: yyyy-mm-dd
     */
    @Query(nativeQuery = true, value = "update BALANCE_CASHIER a set a.BALANCE_STATUS = ?1,a.balance_date = ?2 where id = ?3")
    @Modifying
    void updateStatusAndBalanceDateById(String balanceStatus, Date balanceDate, Long id);
}
