package com.xinyunlian.jinfu.spider.processor;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by bright on 2016/12/30.
 */
public class DummyProcessor implements Processor {
    @Override
    public String process(byte[] data, Charset charset, String config, Map<String, String> context, Object crawler) {
        return new String(data, charset);
    }
}
