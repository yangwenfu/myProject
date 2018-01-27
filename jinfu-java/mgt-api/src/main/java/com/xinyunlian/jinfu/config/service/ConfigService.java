package com.xinyunlian.jinfu.config.service;

import com.xinyunlian.jinfu.config.dto.ConfigDto;
import com.xinyunlian.jinfu.config.enums.ECategory;

import java.util.List;

/**
 * Created by bright on 2017/1/6.
 */
public interface ConfigService {
    void save(ConfigDto configDto);

    List<ConfigDto> getByCategory(ECategory category);

    ConfigDto getByCategoryAndKey(ECategory category, String key);

    void delete(Long id);

    void refreshCache();
}
