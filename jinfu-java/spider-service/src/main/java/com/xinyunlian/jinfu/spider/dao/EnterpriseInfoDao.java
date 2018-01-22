package com.xinyunlian.jinfu.spider.dao;

import com.xinyunlian.jinfu.spider.entity.EnterpriseInfoPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


/**
 * Created by menglei on 2016年11月07日.
 */
public interface EnterpriseInfoDao extends JpaRepository<EnterpriseInfoPo, Long>, JpaSpecificationExecutor<EnterpriseInfoPo> {

    @Query(nativeQuery = true, value = "select * from enterprise_inf where org_code = ?1")
    EnterpriseInfoPo findByOrgCode(String orgId);

    EnterpriseInfoPo findByUserIdAndOrgCode(String userId, String orgCode);

    EnterpriseInfoPo findByUserId(String userId);
}
