package com.xinyunlian.jinfu.wechat.service;

import java.util.Map;

/**
 * Created by menglei on 2017年08月01日.
 */
public interface WeChatService {

    Map<String, String> createQrcode(String userId);

    Map<String, String> getQrcodeInfo(String userId);
}
