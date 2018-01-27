package com.xinyunlian.jinfu.order.dao;

import com.xinyunlian.jinfu.order.entity.ThirdOrderInfPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 第三方订单DAO接口
 *
 * @author menglei
 */
public interface ThirdOrderInfDao extends JpaRepository<ThirdOrderInfPo, Long>, JpaSpecificationExecutor<ThirdOrderInfPo> {

    ThirdOrderInfPo findByOrderNo(String orderNo);

}
