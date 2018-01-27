package com.xinyunlian.jinfu.wechat.service;

import com.xinyunlian.jinfu.wechat.dto.JsApiDto;
import com.xinyunlian.jinfu.wechat.enums.EWeChatPushType;

import java.util.Map;

/**
 * Created by menglei on 2016年11月20日.
 */
public interface ApiWeChatService {

    void sendPush(Map<String, String> paramsMap, EWeChatPushType type);

    String getAuthCodeUrl(String uri, String state);

    String getAuthOpenid(String code);

    JsApiDto getJsApi(String url);

}
