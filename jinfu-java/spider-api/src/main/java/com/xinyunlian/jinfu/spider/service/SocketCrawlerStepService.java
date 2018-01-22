package com.xinyunlian.jinfu.spider.service;

import com.xinyunlian.jinfu.spider.dto.SocketCrawlerStepDto;

/**
 * Created by carrot on 2017/8/7.
 */
public interface SocketCrawlerStepService {
    SocketCrawlerStepDto findStepByArea(Long provinceId, Long cityId, Long areaId);
}
