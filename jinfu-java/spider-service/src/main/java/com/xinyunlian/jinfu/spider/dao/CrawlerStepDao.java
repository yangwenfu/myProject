package com.xinyunlian.jinfu.spider.dao;

import com.xinyunlian.jinfu.spider.entity.CrawlerStepPo;
import com.xinyunlian.jinfu.spider.entity.RiskUserInfoPo;
import com.xinyunlian.jinfu.spider.enums.EDataType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2016年11月07日.
 */
public interface CrawlerStepDao extends JpaRepository<CrawlerStepPo, Long>, JpaSpecificationExecutor<CrawlerStepPo> {
    List<CrawlerStepPo> getByProvinceIdAndDataTypeOrderByOrderAsc(Long provinceId, EDataType dataType);
    List<CrawlerStepPo> getByCityIdAndDataTypeOrderByOrderAsc(Long cityId, EDataType dataType);
    List<CrawlerStepPo> getByAreaIdAndDataTypeOrderByOrderAsc(Long areaId, EDataType dataType);

}
