package com.xinyunlian.jinfu.spider.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.spider.dao.CrawlerStepDao;
import com.xinyunlian.jinfu.spider.dto.CrawlerStepDto;
import com.xinyunlian.jinfu.spider.entity.CrawlerStepPo;
import com.xinyunlian.jinfu.spider.enums.EDataType;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bright on 2016/12/8.
 */
@Service
public class CrawlerStepServiceImpl implements CrawlerStepService {
    @Autowired
    private CrawlerStepDao crawlerStepDao;

    @Override
    public List<CrawlerStepDto> getStepByAreaAndDataType(Long provinceId, Long cityId, Long areaId, EDataType dataType) {
        List<CrawlerStepPo> steps = new ArrayList<>();
        List<CrawlerStepDto> dtos = new ArrayList<>();

        if(areaId != null){
            steps = crawlerStepDao.getByAreaIdAndDataTypeOrderByOrderAsc(areaId, dataType);
        }

        if(cityId != null && CollectionUtils.isEmpty(steps)){
            steps = crawlerStepDao.getByCityIdAndDataTypeOrderByOrderAsc(cityId, dataType);
        }

        if(provinceId != null && CollectionUtils.isEmpty(steps)){
            steps = crawlerStepDao.getByProvinceIdAndDataTypeOrderByOrderAsc(provinceId, dataType);
        }

        steps.forEach(crawlerStepPo -> {
            dtos.add(ConverterService.convert(crawlerStepPo, CrawlerStepDto.class));
        });

        return dtos;
    }
}
