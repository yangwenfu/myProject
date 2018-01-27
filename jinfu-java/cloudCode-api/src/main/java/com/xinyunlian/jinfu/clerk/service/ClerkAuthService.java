package com.xinyunlian.jinfu.clerk.service;

import com.xinyunlian.jinfu.clerk.dto.ClerkAuthDto;
import com.xinyunlian.jinfu.clerk.dto.ClerkInfDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;

import java.util.List;

/**
 * Created by menglei on 2016-12-06.
 */
public interface ClerkAuthService {

    ClerkInfDto deleteAuth(String userId, String clerkId) throws BizServiceException;

    List<ClerkAuthDto> findByClerkId(String clerkId);

    ClerkAuthDto findByClerkIdAndStoreId(String clerkId, String storeId);

    List<ClerkAuthDto> findByStoreId(String storeId);

    List<ClerkAuthDto> findByUserId(String userId);

}
