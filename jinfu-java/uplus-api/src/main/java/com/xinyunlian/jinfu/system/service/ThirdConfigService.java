package com.xinyunlian.jinfu.system.service;

import com.xinyunlian.jinfu.system.dto.ThirdConfigDto;

/**
 * Created by menglei on 2017年03月08日.
 */
public interface ThirdConfigService {

    ThirdConfigDto findByAppId(String appId);

}
