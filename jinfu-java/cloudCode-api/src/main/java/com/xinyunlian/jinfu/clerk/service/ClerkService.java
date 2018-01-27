package com.xinyunlian.jinfu.clerk.service;

import com.xinyunlian.jinfu.clerk.dto.ClerkInfDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;

import java.util.List;

/**
 * Created by menglei on 2016-12-06.
 */
public interface ClerkService {

    /**
     * 新增店员
     * @param dto
     * @throws BizServiceException
     */
    ClerkInfDto addClerk(ClerkInfDto dto) throws BizServiceException;

    ClerkInfDto findByOpenId(String openId);

    List<ClerkInfDto> findByClerkIds(List<String> clerkIds);

    ClerkInfDto findByMobile(String mobile);

    void deleteClerk(String clerkId);

}
