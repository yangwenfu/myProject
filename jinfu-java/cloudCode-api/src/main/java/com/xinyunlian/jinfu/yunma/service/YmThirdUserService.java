package com.xinyunlian.jinfu.yunma.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.yunma.dto.YmThirdUserDto;

/**
 * Created by menglei on 2017-05-31.
 */
public interface YmThirdUserService {

    YmThirdUserDto findByUserId(String userId);

    YmThirdUserDto findByMemberIdAndThirdConfigId(Long memberId, Long thirdConfigId);

    YmThirdUserDto findByMemberId(Long memberId);

    void save(YmThirdUserDto ymThirdUserDto) throws BizServiceException;

    void updateMemberId(YmThirdUserDto ymThirdUserDto) throws BizServiceException;

}
