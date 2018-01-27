package com.xinyunlian.jinfu.spider.service;

import com.xinyunlian.jinfu.spider.dto.CrawlerStepDto;
import com.xinyunlian.jinfu.spider.enums.EDataType;

import java.util.List;

/**
 * Created by bright on 2016/12/8.
 */
public interface CrawlerStepService {
    List<CrawlerStepDto> getStepByAreaAndDataType(Long provinceId, Long cityId, Long areaId, EDataType dataType);
}
