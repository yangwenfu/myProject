package com.xinyunlian.jinfu.ad.dao;

import com.xinyunlian.jinfu.ad.entity.AdPositionPo;
import com.xinyunlian.jinfu.ad.enums.EPosPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by DongFC on 2016-08-19.
 */
public interface AdPositionDao extends JpaRepository<AdPositionPo, Long>, JpaSpecificationExecutor<AdPositionPo> {

    @Query(nativeQuery = true, value = "select * from ad_position where POS_STATUS = 'NORMAL'")
    List<AdPositionPo> findAdPosList();

    List<AdPositionPo> findByPosPlatform(EPosPlatform posPlatform);

}
