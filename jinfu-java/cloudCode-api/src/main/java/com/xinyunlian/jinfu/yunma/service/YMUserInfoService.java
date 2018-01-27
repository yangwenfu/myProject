package com.xinyunlian.jinfu.yunma.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.yunma.dto.YMUserInfoDto;

/**
 * Created by menglei on 2017-01-04.
 */
public interface YMUserInfoService {

    YMUserInfoDto findByOpenId(String openId);

    YMUserInfoDto findByUserId(String userId);

    YMUserInfoDto findByYmUserId(String ymUserId);

    YMUserInfoDto addUserInfo(YMUserInfoDto dto) throws BizServiceException;

    void updateUserInfo(YMUserInfoDto dto) throws BizServiceException;

    void deleteByUserId(String userId);

}
