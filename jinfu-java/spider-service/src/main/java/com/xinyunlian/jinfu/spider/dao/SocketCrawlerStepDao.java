package com.xinyunlian.jinfu.spider.dao;

import com.xinyunlian.jinfu.spider.entity.SocketCrawlerStepPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by carrot on 2017/8/7.
 */
public interface SocketCrawlerStepDao extends JpaRepository<SocketCrawlerStepPo, Long>, JpaSpecificationExecutor<SocketCrawlerStepPo> {
    SocketCrawlerStepPo getByProvinceId(Long provinceId);
    SocketCrawlerStepPo getByCityId(Long cityId);
    SocketCrawlerStepPo getByAreaId(Long areaId);
}
