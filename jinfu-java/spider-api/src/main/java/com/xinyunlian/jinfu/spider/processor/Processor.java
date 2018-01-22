package com.xinyunlian.jinfu.spider.processor;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by bright on 2016/12/29.
 */
public interface Processor {
    String process(byte[] data, Charset charset, String config, Map<String, String> context, Object obj);
}
