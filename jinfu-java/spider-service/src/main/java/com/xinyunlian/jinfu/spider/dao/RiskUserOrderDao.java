package com.xinyunlian.jinfu.spider.dao;

import com.xinyunlian.jinfu.spider.entity.RiskUserOrderPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2016年11月07日.
 */
public interface RiskUserOrderDao extends JpaRepository<RiskUserOrderPo, Long>, JpaSpecificationExecutor<RiskUserOrderPo> {

    @Modifying
    @Query(nativeQuery = true, value = "delete from risk_user_order where RISK_USER_ID = ?1")
    void deleteByRiskUserId(Long riskUserId);

    @Query(nativeQuery = true, value = "select * from risk_user_order where RISK_USER_ID = ?1 order by ORDER_TIME desc")
    List<RiskUserOrderPo> findByRiskUserId(Long riskUserId);

}
