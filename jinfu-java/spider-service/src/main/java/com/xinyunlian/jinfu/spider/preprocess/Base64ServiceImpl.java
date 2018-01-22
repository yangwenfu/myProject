package com.xinyunlian.jinfu.spider.preprocess;

import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

/**
 * Created by bright on 2016/12/26.
 */
@Component(value = "base64")
public class Base64ServiceImpl implements ParamProcessService {
    @Override
    public String process(String[] texts) {
        byte[] b = null;
        String s = null;
        try {
            b = texts[0].getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

}
