package com.xinyunlian.jinfu.qrcode.dao;

import com.xinyunlian.jinfu.qrcode.entity.ProdCodePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 商品码DAO接口
 *
 * @author menglei
 */
public interface ProdCodeDao extends JpaRepository<ProdCodePo, Long>, JpaSpecificationExecutor<ProdCodePo> {

    @Query(nativeQuery = true, value = "select * from prod_code where to_days(CREATE_TS) = to_days(?1) order by PROD_CODE_ID desc limit 1")
    ProdCodePo findLastByDateTime(String dateTime);

    @Query(nativeQuery = true, value = "select * from prod_code where ORDER_Id=?1 order by PROD_CODE_ID desc")
    List<ProdCodePo> findByOrderId(Long orderId);

    ProdCodePo findByQrCodeUrl(String qrCodeUrl);

    /**
     * 根据订单列表查订单已绑码数
     *
     * @return
     */
    @Query(nativeQuery = true, value = "select ORDER_ID,count(PROD_CODE_ID) as BIND_COUNT from prod_code where STATUS != 'UNUSED' and ORDER_ID in ?1 group by ORDER_ID")
    List<Object[]> findCountByOrderId(List<Long> orderIds);

}
