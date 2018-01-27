package com.xinyunlian.jinfu.center.service;

/**
 * Created by King on 2017/5/11.
 */
public interface CenterClientService {
    String get(String url);

    String post(String url, String data);

    String  put(String url, String data);

}
