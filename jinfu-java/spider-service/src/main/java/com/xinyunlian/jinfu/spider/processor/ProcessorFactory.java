package com.xinyunlian.jinfu.spider.processor;

import com.xinyunlian.jinfu.spider.enums.EProcessor;

/**
 * Created by bright on 2016/12/30.
 */
public class ProcessorFactory {
    public static Processor getProcessor(EProcessor processor) {
        switch (processor){
            case HTML:
                return new HtmlProcessor();
            case XML:
                return new XMLProcessor();
            case CAPTCHA:
                return new CaptchaProcessor();
            case ANHUI_ORDER_JSON:
                return  new AnhuiOrderProcessor();
            case HTML_ELEMENT_EXIST:
                return new HtmlElementExistProcessor();
            default:
                return new DummyProcessor();
        }
    }
}
