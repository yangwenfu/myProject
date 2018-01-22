package com.xinyunlian.jinfu.balance.dao;

import com.xinyunlian.jinfu.balance.entity.BalanceDetailPo;
import com.xinyunlian.jinfu.balance.entity.BalanceOutlinePo;
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
public interface BalanceDetailDao extends JpaRepository<BalanceDetailPo, Long>, JpaSpecificationExecutor<BalanceDetailPo> {

    List<BalanceDetailPo> findByOutlineId(Long outlineId);

    BalanceDetailPo findByRepayId(String repayId);

    @Query(nativeQuery = true, value = "update BALANCE_DETAIL a set a.BALANCE_STATUS = ?1,a.balance_date = ?2 where id = ?3")
    @Modifying
    void updateStatusAndBalanceDateById(String balanceStatus, Date balanceDate, Long id);

}
