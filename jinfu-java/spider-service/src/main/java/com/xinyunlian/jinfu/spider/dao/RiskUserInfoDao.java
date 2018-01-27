package com.xinyunlian.jinfu.spider.dao;

import com.xinyunlian.jinfu.spider.entity.RiskUserInfoPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by menglei on 2016年11月07日.
 */
public interface RiskUserInfoDao extends JpaRepository<RiskUserInfoPo, Long>, JpaSpecificationExecutor<RiskUserInfoPo> {

    RiskUserInfoPo findByUserId(String userId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from risk_user_info where USER_ID = ?1")
    void deleteByUserId(String userId);

}
