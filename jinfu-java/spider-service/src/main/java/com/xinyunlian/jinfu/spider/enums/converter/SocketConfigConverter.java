package com.xinyunlian.jinfu.spider.enums.converter;

import com.alibaba.fastjson.JSONObject;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.spider.dto.SocketConfigDto;
import org.apache.commons.lang.StringUtils;

import javax.persistence.AttributeConverter;

/**
 * Created by carrot on 2017/8/7.
 */
public class SocketConfigConverter implements AttributeConverter<SocketConfigDto, String> {

    @Override
    public String convertToDatabaseColumn(SocketConfigDto socketConfigDto) {
        if (socketConfigDto != null) {
            JSONObject json = (JSONObject) JSONObject.toJSON(socketConfigDto);
            return json.toJSONString();
        } else
            return null;
    }

    @Override
    public SocketConfigDto convertToEntityAttribute(String s) {
        SocketConfigDto defaultConfig = new SocketConfigDto();
        if (StringUtils.isNotEmpty(s)) {
            SocketConfigDto socketConfigDto = JSONObject.parseObject(s, SocketConfigDto.class);
            if (socketConfigDto != null)
                ConverterService.convert(socketConfigDto, defaultConfig);
        }
        return defaultConfig;
    }
}
