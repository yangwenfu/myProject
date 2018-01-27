package com.xinyunlian.jinfu.crm.dao;

import com.xinyunlian.jinfu.crm.entity.CrmCallTypePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 客户通话类型DAO接口
 * @author jll
 * @version 
 */
public interface CrmCallTypeDao extends JpaRepository<CrmCallTypePo, Long>, JpaSpecificationExecutor<CrmCallTypePo> {
    @Modifying
    @Query(nativeQuery = true, value = "delete from crm_call_type where parent = ?1")
    void deleteByParent(Long parent);
}
