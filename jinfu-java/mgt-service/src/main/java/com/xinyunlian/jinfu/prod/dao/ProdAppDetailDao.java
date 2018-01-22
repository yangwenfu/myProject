package com.xinyunlian.jinfu.prod.dao;

import com.xinyunlian.jinfu.prod.entity.ProdAppDetailPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dongfangchao on 2017/1/22/0022.
 */
public interface ProdAppDetailDao extends JpaRepository<ProdAppDetailPo, Long>, JpaSpecificationExecutor<ProdAppDetailPo> {

    ProdAppDetailPo findByProdId(String prodId);

}
