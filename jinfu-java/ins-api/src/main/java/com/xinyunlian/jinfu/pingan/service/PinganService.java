package com.xinyunlian.jinfu.pingan.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.pingan.dto.PolicyDto;

/**
 * Created by DongFC on 2016-09-01.
 */
public interface PinganService {

    /**
     * 加密用户信息
     * @param policyDto
     * @return 加密后的用户信息
     */
    String aesEncrypt(PolicyDto policyDto) throws BizServiceException;

}
