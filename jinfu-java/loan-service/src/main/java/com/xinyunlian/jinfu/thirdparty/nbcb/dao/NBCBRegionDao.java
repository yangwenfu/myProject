package com.xinyunlian.jinfu.thirdparty.nbcb.dao;

import com.xinyunlian.jinfu.thirdparty.nbcb.entity.NBCBRegionPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by bright on 2017/7/10.
 */
public interface NBCBRegionDao extends JpaRepository<NBCBRegionPo, Long>, JpaSpecificationExecutor<NBCBRegionPo> {
    List<NBCBRegionPo> findAllByEnabledIsTrue();
}
