package com.xinyunlian.jinfu.yunma.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.yunma.dto.YMRouterAgentDto;
import com.xinyunlian.jinfu.yunma.dto.YMRouterSearchDto;

import java.util.List;

/**
 * Created by menglei on 2017-01-04.
 */
public interface YMRouterAgentService {

    void addRouterAgent(YMRouterAgentDto dto) throws BizServiceException;

    void updateRouterAgent(YMRouterAgentDto dto) throws BizServiceException;

    YMRouterAgentDto findByUserAgent(String userAgent);

    List<YMRouterAgentDto> findAll();

    void delete(Long id);

    YMRouterSearchDto getRouterPage(YMRouterSearchDto searchDto);

    YMRouterAgentDto findLocalByUserAgent(String userAgent);

}
