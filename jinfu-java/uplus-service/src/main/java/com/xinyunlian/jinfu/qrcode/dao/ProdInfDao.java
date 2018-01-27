package com.xinyunlian.jinfu.qrcode.dao;

import com.xinyunlian.jinfu.qrcode.entity.ProdInfPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 商品DAO接口
 *
 * @author menglei
 */
public interface ProdInfDao extends JpaRepository<ProdInfPo, Long>, JpaSpecificationExecutor<ProdInfPo> {

    ProdInfPo findBySku(String sku);

}
