package com.xinyunlian.jinfu.pingan.dao;

import com.xinyunlian.jinfu.pingan.entity.PinganRegionPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public interface PinganRegionDao extends JpaRepository<PinganRegionPo, Long>, JpaSpecificationExecutor<PinganRegionPo>{

    @Query(nativeQuery = true, value = "SELECT * FROM `pingan_region` WHERE GB_CODE = ?1 AND (LENGTH(TREE_PATH) - LENGTH(REPLACE(TREE_PATH,',',''))) = ?2")
    PinganRegionPo findByGbCodeAndLevel(String gbCode, Integer level);

}
