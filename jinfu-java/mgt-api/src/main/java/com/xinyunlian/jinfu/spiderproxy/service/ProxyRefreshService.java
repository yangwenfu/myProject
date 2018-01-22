package com.xinyunlian.jinfu.spiderproxy.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;

/**
 * Created by bright on 2017/1/8.
 */
public interface ProxyRefreshService {
    String getNewProxyIp() throws BizServiceException;
}
