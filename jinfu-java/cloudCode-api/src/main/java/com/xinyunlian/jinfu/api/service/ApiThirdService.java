package com.xinyunlian.jinfu.api.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;

import java.util.Map;

/**
 * Created by menglei on 2017年06月02日.
 */
public interface ApiThirdService {

    void thirdNotify(Map<String, String> params, String notifyUrl) throws BizServiceException;

}
