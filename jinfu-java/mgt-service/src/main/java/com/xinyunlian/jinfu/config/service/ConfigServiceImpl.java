package com.xinyunlian.jinfu.config.service;

import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.config.dao.ConfigDao;
import com.xinyunlian.jinfu.config.dto.ConfigDto;
import com.xinyunlian.jinfu.config.entity.ConfigPo;
import com.xinyunlian.jinfu.config.enums.ECategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by bright on 2017/1/6.
 */
@Service
public class ConfigServiceImpl implements ConfigService, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConfigDao configDao;

    @Autowired
    private RedisCacheManager redisCacheManager;

    private void putCache(ConfigPo configPo){
        if(configPo.getCached()) {
            redisCacheManager.getCache(CacheType.APPLICATIONAL).put(
                    configPo.getCategory().getCode() + "." + configPo.getKey(),
                    configPo.getValue());
        }
    }

    private void deleteCache(ConfigPo configPo){
        redisCacheManager.getCache(CacheType.APPLICATIONAL).evict(
                configPo.getCategory().getCode() + "." + configPo.getKey()
        );
    }

    @Override
    public void save(ConfigDto configDto) {
        ConfigPo configPo = null;
        if (Objects.nonNull(configDto.getId())) {
            configPo = configDao.findOne(configDto.getId());
        }
        if (Objects.isNull(configPo)) {
            configPo = new ConfigPo();
        }
        ConverterService.convert(configDto, configPo);
        configDao.save(configPo);
        putCache(configPo);
    }

    @Override
    public List<ConfigDto> getByCategory(ECategory category) {
        List<ConfigPo> configPos = configDao.getByCategory(category);
        List<ConfigDto> configDtos = new ArrayList<>();
        configPos.forEach(configPo -> {
            configDtos.add(ConverterService.convert(configPo, ConfigDto.class));
        });
        return configDtos;
    }

    @Override
    public ConfigDto getByCategoryAndKey(ECategory category, String key) {
        ConfigPo configPo = configDao.getByCategoryAndKey(category, key);
        return ConverterService.convert(configPo, ConfigDto.class);
    }

    @Override
    public void delete(Long id) {
        ConfigPo configPo = configDao.findOne(id);
        configDao.delete(configPo);
        deleteCache(configPo);
    }

    @Override
    public void refreshCache() {
        List<ConfigPo> configPos = configDao.getByCached(Boolean.TRUE);
        configPos.forEach(configPo -> {
            putCache(configPo);
        });
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            refreshCache();
        }
    }
}
