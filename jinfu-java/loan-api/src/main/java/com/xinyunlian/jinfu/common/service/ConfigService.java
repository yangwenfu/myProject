package com.xinyunlian.jinfu.common.service;

import com.xinyunlian.jinfu.common.dto.ConfigDto;

public interface ConfigService {

    void update(ConfigDto configDto);

    ConfigDto get(String key);

}
