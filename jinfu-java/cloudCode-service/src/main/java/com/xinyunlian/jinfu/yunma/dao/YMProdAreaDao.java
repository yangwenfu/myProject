package com.xinyunlian.jinfu.yunma.dao;

import com.xinyunlian.jinfu.yunma.entity.YMProdAreaPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by menglei on 2017-01-06.
 */
public interface YMProdAreaDao extends JpaRepository<YMProdAreaPo, Long>, JpaSpecificationExecutor<YMProdAreaPo> {

    YMProdAreaPo findByProdIdAndAreaId(String prodId, Long areaId);

    List<YMProdAreaPo> findByProdIdAndAreaTreePathLike(String prodId, String areaTreePath);

    List<YMProdAreaPo> findByProdId(String prodId);

}
