package com.xinyunlian.jinfu.risk.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.risk.dto.req.*;
import com.xinyunlian.jinfu.risk.dto.resp.UserCreditInfoDto;

/**
 * Created by bright on 2016/11/15.
 */
public interface UserCreditService {
    public UserCreditInfoDto getUserCreditInfo(String userId) throws BizServiceException;

    public UserCreditInfoDto retrieveUserCreditInfo(UserCreditInfoReqDto userCreditInfoReqDto) throws BizServiceException;

    public void saveUserCreditInfo(UserCreditInfoDto userCreditInfoDto);
}
