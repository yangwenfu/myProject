package com.xinyunlian.jinfu.balance.dao;

import com.xinyunlian.jinfu.balance.entity.BalanceOutlinePo;
import com.xinyunlian.jinfu.balance.enums.EBalanceOutlineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author Willwang
 */
public interface BalanceOutlineDao extends JpaRepository<BalanceOutlinePo, Long>, JpaSpecificationExecutor<BalanceOutlinePo> {

    @Modifying
    @Query(nativeQuery = true, value = "update  BALANCE_OUTLINE a set a.BALANCE_STATUS = ?1,a.balance_Date = ?2,a.BALANCE_USER_ID = ?3  where id = ?4")
    void finish(String balanceStatusCode, Date balanceDate, String mgtUserId, Long id);


    @Query(value = "from BalanceOutlinePo a where DATE_FORMAT(a.generateDate,'%Y%m%d') = DATE_FORMAT(?1,'%Y%m%d')")
    BalanceOutlinePo findByGenerateDate(Date generateDate);


    @Query(value = "from BalanceOutlinePo i where i.generateDate >= ?1 and i.generateDate <= ?2 order by i.id desc")
    List<BalanceOutlinePo> listByDate(Date start, Date end);

    @Modifying
    @Query("update BalanceOutlinePo a set a.balanceOutlineStatus = ?1, a.balanceDate = ?2 where a.id = ?3")
    void updateBalanceOutlineStatusAndBalanceDateById(EBalanceOutlineStatus balanceOutlineStatus, Date date, Long id);

    @Modifying
    @Query("update BalanceOutlinePo a set a.autoed = ?1 where a.id = ?2")
    void updateAutoedById(boolean autoed, Long id);

}
