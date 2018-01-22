package com.xinyunlian.jinfu.qrcode.service;

import com.xinyunlian.jinfu.qrcode.dto.ScanUserAgentDto;

/**
 * Created by menglei on 2017年03月08日.
 */
public interface ScanUserAgentService {

    ScanUserAgentDto getByUserAgent(String userAgent);

}
