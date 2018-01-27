package com.xinyunlian.jinfu.order.dao;

import com.xinyunlian.jinfu.order.entity.CmccOrderInfoPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2016年11月20日.
 */
public interface CmccOrderInfoDao extends JpaRepository<CmccOrderInfoPo, String>, JpaSpecificationExecutor<CmccOrderInfoPo> {

    /**
     * 按成交额大于500查店铺
     * @return
     */
    @Query(nativeQuery = true, value = "select STORE_ID,SUM(COUPON_AMOUNT) as COUPON from cmcc_order_info " +
            "where PAY_STATUS='SUCCESS' and TRADE_STATUS='NOPAYMENT' and CREATE_TS < curdate() group by STORE_ID having SUM(COUPON_AMOUNT) >= 500")
    List<Object[]> findByTurnover();

    /**
     * 按所有成交额查店铺
     * @return
     */
    @Query(nativeQuery = true, value = "select STORE_ID,SUM(COUPON_AMOUNT) as COUPON from cmcc_order_info " +
            "where PAY_STATUS='SUCCESS' and TRADE_STATUS='NOPAYMENT' and CREATE_TS < curdate() group by STORE_ID")
    List<Object[]> findByMonth();

    /**
     * 根据storeId查询需打款的订单
     * @return
     */
    @Query(nativeQuery = true, value = "select * from cmcc_order_info " +
            "where  STORE_ID= ?1 and PAY_STATUS='SUCCESS' and TRADE_STATUS='NOPAYMENT' and CREATE_TS < curdate()")
    List<CmccOrderInfoPo> findByStoreId(Long storeId);

    /**
     * 根据订单号更新状态
     * @param tradeStatus
     * @param cmccTradeNo
     */
    @Modifying
    @Query(nativeQuery = true, value = "update cmcc_order_info set TRADE_STATUS=?1 where CMCC_TRADE_NO=?2")
    void updateTradeStatus(String tradeStatus, String cmccTradeNo);

}
