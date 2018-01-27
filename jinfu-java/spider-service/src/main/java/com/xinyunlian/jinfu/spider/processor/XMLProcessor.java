package com.xinyunlian.jinfu.spider.processor;

import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by bright on 2016/12/29.
 */
public class XMLProcessor implements Processor {
    public static final Logger LOGGER = LoggerFactory.getLogger(XMLProcessor.class);

    public static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();

    @Override
    public String process(byte[] data, Charset charset, String config, Map<String, String> context, Object crawler) {
        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("正在解析{}, 解析脚本为{}", new String(data, charset), config);
        }
        StringWriter sw = new StringWriter();
        try {
            Transformer transformer = TRANSFORMER_FACTORY.newTransformer(new StreamSource(new StringReader(config)));
            transformer.transform(new StreamSource(new ByteArrayInputStream(data)), new StreamResult(sw));
        } catch (Exception e) {
            LOGGER.error("内容解析失败, 内容为{}, 解析脚本为{}", new String(data), config, e);
            throw new BizServiceException(EErrorCode.CRAWLER_CLIMB_FAILURE, "内容解析失败", e);
        }
        String content = sw.toString();
        String[] values = content.split("\\|");
        if(values.length > 1){
            String result = values[0];
            for( int i=1; i< values.length; i++){
                String[] pair = values[i].split(":");
                if(pair.length > 1) {
                    context.put(pair[0], pair[1]);
                }
            }
            return result;
        } else {
            return content;
        }
    }
}
