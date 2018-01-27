package com.xinyunlian.jinfu.report.virtual.dao;

import com.xinyunlian.jinfu.report.virtual.entity.VirtualTboSearchPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 虚拟烟草证DAO接口
 * @author jll
 * @version 
 */
public interface VirtualTobaccoDao extends JpaRepository<VirtualTboSearchPo, Long>, JpaSpecificationExecutor<VirtualTboSearchPo> {

}
