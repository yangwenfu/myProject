package com.xinyunlian.jinfu.yunma.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.yunma.dao.YmThirdConfigDao;
import com.xinyunlian.jinfu.yunma.dto.YmThirdConfigDto;
import com.xinyunlian.jinfu.yunma.entity.YmThirdConfigPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by menglei on 2017-01-04.
 */
@Service
public class YmThirdConfigServiceImpl implements YmThirdConfigService {

    @Autowired
    private YmThirdConfigDao ymThirdConfigDao;

    @Override
    @Transactional
    public YmThirdConfigDto findByAppId(String appId) {
        YmThirdConfigPo po = ymThirdConfigDao.findByAppId(appId);
        if (po == null) {
            return null;
        }
        YmThirdConfigDto dto = ConverterService.convert(po, YmThirdConfigDto.class);
        return dto;
    }

    @Override
    @Transactional
    public YmThirdConfigDto findById(Long id) {
        YmThirdConfigPo po = ymThirdConfigDao.findOne(id);
        if (po == null) {
            return null;
        }
        YmThirdConfigDto dto = ConverterService.convert(po, YmThirdConfigDto.class);
        return dto;
    }

}
