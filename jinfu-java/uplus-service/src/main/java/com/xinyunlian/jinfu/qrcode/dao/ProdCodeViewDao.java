package com.xinyunlian.jinfu.qrcode.dao;

import com.xinyunlian.jinfu.qrcode.entity.ProdCodeViewPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 云码商铺DAO接口
 * @author menglei
 * @version 
 */
public interface ProdCodeViewDao extends JpaRepository<ProdCodeViewPo, Long>, JpaSpecificationExecutor<ProdCodeViewPo> {

}

