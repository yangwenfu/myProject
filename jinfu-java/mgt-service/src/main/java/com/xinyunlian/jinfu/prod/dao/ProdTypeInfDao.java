package com.xinyunlian.jinfu.prod.dao;

import com.xinyunlian.jinfu.prod.entity.ProdTypeInfPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by DongFC on 2016-09-18.
 */
public interface ProdTypeInfDao extends JpaRepository<ProdTypeInfPo, Long>, JpaSpecificationExecutor<ProdTypeInfPo> {

    @Query(nativeQuery = true, value = "SELECT * FROM prod_type_inf where PROD_TYPE_PATH LIKE CONCAT(',',?1,',%') ORDER BY length(PROD_TYPE_PATH) ASC")
    List<ProdTypeInfPo> findByTopProdTypeId(String topProdTypeId);

    ProdTypeInfPo findByProdTypeCode(String prodTypeCode);

}
