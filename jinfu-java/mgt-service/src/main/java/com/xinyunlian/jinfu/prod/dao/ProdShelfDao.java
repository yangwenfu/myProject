package com.xinyunlian.jinfu.prod.dao;

import com.xinyunlian.jinfu.prod.entity.ProdShelfPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by dongfangchao on 2017/1/22/0022.
 */
public interface ProdShelfDao extends JpaRepository<ProdShelfPo, Long>, JpaSpecificationExecutor<ProdShelfPo> {

    List<ProdShelfPo> findByProdId(String prodId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from prod_shelf where PROD_ID = ?1")
    void deleteByProdId(String prodId);

}