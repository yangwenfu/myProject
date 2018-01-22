package com.xinyunlian.jinfu.spider.processor;

import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by bright on 2016/12/29.
 */
public class HtmlElementExistProcessor implements Processor {
    public static final Logger LOGGER = LoggerFactory.getLogger(HtmlElementExistProcessor.class);

    @Override
    public String process(byte[] data, Charset charset, String config, Map<String, String> context, Object cra) {
        try {
            Document doc = Jsoup.parse(new String(data,charset));
            Element e = doc.getElementById(config);
            if(null == e){
                return Boolean.FALSE.toString();
            }

        } catch (Exception e){
            LOGGER.error("内容解析失败, 内容为{}, 解析脚本为{}", new String(data), config, e);
            throw new BizServiceException(EErrorCode.CRAWLER_CLIMB_FAILURE, "内容解析失败", e);
        }
        return Boolean.TRUE.toString();
    }

}
