package com.xinyunlian.jinfu.trade.service;

import com.xinyunlian.jinfu.trade.dto.YmBizDto;

import java.util.List;

/**
 * 云码业务配置Service
 * @author jll
 * @version 
 */

public interface YmBizService {
    List<YmBizDto> findAll();

    void update(List<YmBizDto> ymBizDtos);
}
