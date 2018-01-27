package com.xinyunlian.jinfu.pingan.dao;

import com.xinyunlian.jinfu.pingan.entity.PinganTyphoonControlRegionPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public interface PinganTyphoonControlRegionDao extends JpaSpecificationExecutor<PinganTyphoonControlRegionPo>, JpaRepository<PinganTyphoonControlRegionPo, Long>{

    PinganTyphoonControlRegionPo findByRegionId(Long regionId);

    List<PinganTyphoonControlRegionPo> findByTreePathLike(String treePath);

}
