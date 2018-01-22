package com.xinyunlian.jinfu.spider.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.spider.dao.SocketCrawlerStepDao;
import com.xinyunlian.jinfu.spider.dto.SocketCrawlerStepDto;
import com.xinyunlian.jinfu.spider.entity.SocketCrawlerStepPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by carrot on 2017/8/7.
 */
@Service
public class SocketCrawlerStepServiceImpl implements SocketCrawlerStepService {

    @Autowired
    private SocketCrawlerStepDao socketCrawlerStepDao;

    @Override
    @Transactional(readOnly = true)
    public SocketCrawlerStepDto findStepByArea(Long provinceId, Long cityId, Long areaId) {
        SocketCrawlerStepPo step = null;
        SocketCrawlerStepDto dto = null;

        if (areaId != null) {
            step = socketCrawlerStepDao.getByAreaId(areaId);
        }

        if (cityId != null && step == null) {
            step = socketCrawlerStepDao.getByCityId(cityId);
        }

        if (provinceId != null && step == null) {
            step = socketCrawlerStepDao.getByProvinceId(provinceId);
        }

        if (step != null)
            dto = ConverterService.convert(step, SocketCrawlerStepDto.class);


        return dto;
    }
}
