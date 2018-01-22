package com.xinyunlian.jinfu.yunma.dao;

import com.xinyunlian.jinfu.yunma.entity.YMMemberViewPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 云码商铺DAO接口
 * @author jll
 * @version 
 */
public interface YMMemberViewDao extends JpaRepository<YMMemberViewPo, Long>, JpaSpecificationExecutor<YMMemberViewPo> {

}

