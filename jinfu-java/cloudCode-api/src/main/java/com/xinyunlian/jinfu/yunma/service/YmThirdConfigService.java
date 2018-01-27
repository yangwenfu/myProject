package com.xinyunlian.jinfu.yunma.service;

import com.xinyunlian.jinfu.yunma.dto.YmThirdConfigDto;

/**
 * Created by menglei on 2017-01-04.
 */
public interface YmThirdConfigService {

    YmThirdConfigDto findByAppId(String appId);

    YmThirdConfigDto findById(Long id);

}
