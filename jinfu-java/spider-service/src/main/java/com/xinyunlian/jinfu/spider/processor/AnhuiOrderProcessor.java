package com.xinyunlian.jinfu.spider.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinyunlian.jinfu.spider.dto.OrderInfo;
import net.sf.json.util.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bright on 2016/12/29.
 */
public class AnhuiOrderProcessor implements Processor {
    public static final Logger LOGGER = LoggerFactory.getLogger(AnhuiOrderProcessor.class);

    @Override
    public String process(byte[] data, Charset charset, String config, Map<String, String> context, Object cra) {
        OrderInfo orderInfo = new OrderInfo();
        List<String> los = new ArrayList<>();
        String content1 = new String(data,charset);
        JSONObject jsonObject = JSON.parseObject(content1);
        JSONArray rows1 = jsonObject.getJSONArray("rows");
        String result = null;
        rows1.forEach(o -> {
            JSONObject row = (JSONObject)o;
            JSONArray cells = row.getJSONArray("cell");
            orderInfo.setOrderNo(cells.getString(0));
            orderInfo.setOrderTime(cells.getString(1));
            orderInfo.setAmount(cells.getString(2));
            orderInfo.setTotalPrice(cells.getString(3));
            los.add(JSONObject.toJSONString(orderInfo));
        });
        result = "["+String.join(",", los.toArray(new String[0]))+"]";
        return result;
    }
}
