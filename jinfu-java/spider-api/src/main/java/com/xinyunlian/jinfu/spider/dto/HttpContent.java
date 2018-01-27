package com.xinyunlian.jinfu.spider.dto;

import java.nio.charset.Charset;

/**
 * Created by bright on 2016/12/29.
 */
public class HttpContent {
    private Charset charset;
    private byte[] data;

    public HttpContent(Charset charset, byte[] data) {
        this.charset = charset;
        this.data = data;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
