package com.xinyunlian.jinfu.spider.processor;

import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.spider.util.Crawler;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bright on 2016/12/29.
 */
public class HtmlProcessor implements Processor {
    public static final Logger LOGGER = LoggerFactory.getLogger(HtmlProcessor.class);

    @Override
    public String process(byte[] data, Charset charset, String config, Map<String, String> context, Object crawler) {
        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("正在解析{}, 解析脚本为{}", new String(data), config);
        }
        String result = "";
        try {
            Map<String, String> configMap = JsonUtil.toMap(config);
            String container = configMap.get("container");
            String script = configMap.get("script");
            Document doc = Jsoup.parse(new String(data, charset));
            if(StringUtils.isNotEmpty(container)){
                Elements elements = doc.select(container);
                List<String> results = new ArrayList<>();
                elements.forEach(element -> {
                    Pattern pattern = Pattern.compile("\\|.*?\\|");
                    Matcher matcher = pattern.matcher(script);
                    StringBuffer sb = new StringBuffer();
                    while(matcher.find()){
                        String group = matcher.group();
                        String selector = group.substring(1, group.length() - 1);
                        Elements target = element.select(selector);
                        String value = target.text().trim();
                        if(target.hasAttr("value")){
                            value = target.val();
                        }
                        matcher.appendReplacement(sb, value);
                    }
                    matcher.appendTail(sb);
                    results.add(sb.toString());
                });
                result = "["+String.join(",", results.toArray(new String[0]))+"]";
            } else {
                Pattern pattern = Pattern.compile("\\|.*?\\|");
                Matcher matcher = pattern.matcher(script);
                StringBuffer sb = new StringBuffer();
                while(matcher.find()){
                    String group = matcher.group();
                    String selector = group.substring(1, group.length() - 1);
                    String value = "";
                    Elements es = doc.select(selector);
                    if(!es.isEmpty()){
                        if(es.hasAttr("value")){
                            value = es.val();
                        }
                        if(StringUtils.isEmpty(value)){
                            value = es.get(0).ownText().trim();
                        }
                    }
                    matcher.appendReplacement(sb, value);
                }
                matcher.appendTail(sb);
                result = sb.toString();
                try {
                    context.putAll(JsonUtil.toMap(result));
                } catch (Exception ex){
                    LOGGER.info(ex.getMessage());
                }

            }
        } catch (IOException e) {
            LOGGER.error("内容解析失败, 内容为{}, 解析脚本为{}", new String(data), config, e);
            throw new BizServiceException(EErrorCode.CRAWLER_CLIMB_FAILURE, "内容解析失败", e);
        }
        return result;
    }
}
