package com.xinyunlian.jinfu.dealer.dao;

import com.xinyunlian.jinfu.dealer.entity.DealerUserOrderPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by menglei on 2016年08月26日.
 */
public interface DealerUserOrderDao extends JpaRepository<DealerUserOrderPo, Long>, JpaSpecificationExecutor<DealerUserOrderPo> {

    DealerUserOrderPo findByOrderNo(String orderNo);

    @Query(nativeQuery = true, value = "UPDATE dealer_user_order SET STATUS = 'ERROR' " +
            "WHERE STATUS = 'PROCESS' AND SOURCE = 'DEALER' AND (CASE WHEN DAYOFWEEK(CREATE_TS) in (7, 1) THEN TIMESTAMPDIFF(DAY, CREATE_TS, NOW()) >= 9 ELSE TIMESTAMPDIFF(DAY, CREATE_TS, NOW()) >= 10 END)")
    @Modifying
    void updateExpireOrder();

}
