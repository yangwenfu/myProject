package com.xinyunlian.jinfu.spider.preprocess;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bright on 2016/12/30.
 */
@Component(value = "date_process")
public class DateProcessImpl implements ParamProcessService {
    @Override
    public String process(String[] texts) {
        String days = texts[0];
        String format = texts[1];
        SimpleDateFormat df = new SimpleDateFormat(format);
        Long back = Long.parseLong(days);
        Date now = new Date();
        Date date = new Date(now.getTime() - (back * 1000 * 60 * 60 * 24));
        return df.format(date);
    }
}
