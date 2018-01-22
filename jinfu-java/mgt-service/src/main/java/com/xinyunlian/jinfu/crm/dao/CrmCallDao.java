package com.xinyunlian.jinfu.crm.dao;

import com.xinyunlian.jinfu.crm.entity.CrmCallPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 客户通话记录DAO接口
 * @author jll
 * @version 
 */
public interface CrmCallDao extends JpaRepository<CrmCallPo, Long>, JpaSpecificationExecutor<CrmCallPo> {

    Long countByCallTypeIdIn(List<Long> callTypeIds);
}
