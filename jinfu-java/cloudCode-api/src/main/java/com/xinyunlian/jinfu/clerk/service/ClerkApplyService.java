package com.xinyunlian.jinfu.clerk.service;

import com.xinyunlian.jinfu.clerk.dto.ClerkApplyDto;
import com.xinyunlian.jinfu.clerk.dto.ClerkInfDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;

import java.util.List;

/**
 * Created by menglei on 2016-12-06.
 */
public interface ClerkApplyService {

    void addApply(ClerkApplyDto dto) throws BizServiceException;

    List<ClerkApplyDto> findByUserId(String userId);

    List<ClerkApplyDto> findByClerkId(String clerkId);

    ClerkInfDto applyRefuse(String userId, String clerkId) throws BizServiceException;

    ClerkInfDto applyPass(String userId, String clerkId, String storeIds) throws BizServiceException;

}
