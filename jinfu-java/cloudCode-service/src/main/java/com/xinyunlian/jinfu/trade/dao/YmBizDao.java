package com.xinyunlian.jinfu.trade.dao;

import com.xinyunlian.jinfu.trade.enums.EBizCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xinyunlian.jinfu.trade.entity.YmBizPo;

/**
 * 云码业务配置DAO接口
 * @author jll
 * @version 
 */
public interface YmBizDao extends JpaRepository<YmBizPo, Long>, JpaSpecificationExecutor<YmBizPo> {

    YmBizPo findByCode(EBizCode code);
	
}
