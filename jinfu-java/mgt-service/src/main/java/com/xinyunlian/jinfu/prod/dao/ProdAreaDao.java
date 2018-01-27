package com.xinyunlian.jinfu.prod.dao;

import com.xinyunlian.jinfu.prod.entity.ProdAreaPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by DongFC on 2016-08-19.
 */
public interface ProdAreaDao extends JpaRepository<ProdAreaPo, Long>, JpaSpecificationExecutor<ProdAreaPo> {

    @Modifying
    @Query(nativeQuery = true, value = "delete from prod_area where AREA_ID = ?1")
    void deleteByAreaId(Long areaId);

    ProdAreaPo findByProdIdAndAreaId(String prodId, Long areaId);

    List<ProdAreaPo> findByProdIdAndAreaTreePathLike(String prodId, String areaTreePath);

    List<ProdAreaPo> findByProdId(String prodId);

}
