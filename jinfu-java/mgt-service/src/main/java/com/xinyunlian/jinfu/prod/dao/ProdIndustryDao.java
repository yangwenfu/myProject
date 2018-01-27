package com.xinyunlian.jinfu.prod.dao;

import com.xinyunlian.jinfu.prod.entity.ProdIndustryPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by dongfangchao on 2017/5/17/0017.
 */
public interface ProdIndustryDao extends JpaRepository<ProdIndustryPo, Long>, JpaSpecificationExecutor<ProdIndustryPo> {

    @Modifying
    @Query(nativeQuery = true, value = "delete from prod_industry where PROD_ID = ?1")
    void deleteByProdId(String prodId);

    List<ProdIndustryPo> findByProdId(String prodId);

    ProdIndustryPo findByProdIdAndIndMcc(String prodId, String indMcc);

}
