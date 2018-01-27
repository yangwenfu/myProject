package com.xinyunlian.jinfu.system.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.system.dao.ThirdConfigDao;
import com.xinyunlian.jinfu.system.dto.ThirdConfigDto;
import com.xinyunlian.jinfu.system.entity.ThirdConfigPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by menglei on 2017年03月08日.
 */
@Service
public class ThirdConfigServiceImpl implements ThirdConfigService {

    @Autowired
    private ThirdConfigDao thirdConfigDao;

    @Override
    @Transactional
    public ThirdConfigDto findByAppId(String appId) {
        ThirdConfigPo po = thirdConfigDao.findByAppId(appId);
        if (po == null) {
            return null;
        }
        ThirdConfigDto dto = ConverterService.convert(po, ThirdConfigDto.class);
        return dto;
    }

}
