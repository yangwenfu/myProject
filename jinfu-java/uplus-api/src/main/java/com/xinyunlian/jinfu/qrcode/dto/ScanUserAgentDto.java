package com.xinyunlian.jinfu.qrcode.dto;

import java.io.Serializable;

/**
 * 扫码记录
 *
 * @author menglei
 */
public class ScanUserAgentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String userAgent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}


