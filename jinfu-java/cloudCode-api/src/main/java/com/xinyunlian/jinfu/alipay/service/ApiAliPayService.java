package com.xinyunlian.jinfu.alipay.service;

/**
 * Created by menglei on 2016年11月20日.
 */
public interface ApiAliPayService {

    String getAuthCodeUrl(String uri, String state);

    String getAuthUserid(String code);

}
