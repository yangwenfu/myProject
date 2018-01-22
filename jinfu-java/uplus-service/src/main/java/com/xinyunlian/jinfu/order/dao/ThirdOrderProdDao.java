package com.xinyunlian.jinfu.order.dao;

import com.xinyunlian.jinfu.order.entity.ThirdOrderProdPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 第三方订单商品DAO接口
 *
 * @author menglei
 */
public interface ThirdOrderProdDao extends JpaRepository<ThirdOrderProdPo, Long>, JpaSpecificationExecutor<ThirdOrderProdPo> {

    @Query(nativeQuery = true, value = "select * from third_order_prod where ORDER_ID=?1 order by ORDER_PROD_ID desc")
    List<ThirdOrderProdPo> findByOrderId(Long orderId);

    /**
     * 根据订单列表查订单可绑码数
     *
     * @return
     */
    @Query(nativeQuery = true, value = "select ORDER_ID,sum(BIND_COUNT) as BIND_COUNT from third_order_prod where PROD_ID > 0 and ORDER_ID in ?1 group by ORDER_ID")
    List<Object[]> findCountByOrderId(List<Long> orderIds);

}
