package com.xinyunlian.jinfu.zhongan.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.RC4Util;
import com.xinyunlian.jinfu.zhongan.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by dongfangchao on 2017/3/13/0013.
 */
@Service
public class ZhonganOpenPlatformServiceImpl implements ZhonganOpenPlatformService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZhonganOpenPlatformServiceImpl.class);

    @Value("${zhongan.rc4.key}")
    private String rc4Key;

    @Value("${yljf.zhongan.private.key}")
    private String zhognanYljfPrivateKey;

    @Override
    public String rc4Encrypt(ZhongAnRequestDto req) throws BizServiceException {
        try {
            String json = JsonUtil.toJson(req);
            LOGGER.debug("RC4需要加密的内容：" + json);
            return RC4Util.encryRC4String(json, rc4Key);
        } catch (Exception e) {
            LOGGER.error("众安bizContent rc4加密失败", e);
        }
        return null;
    }

    @Override
    public VehInsNotifyDto translateToVehNotify(String json) throws BizServiceException {
        if (StringUtils.isEmpty(json)){
            VehicleInsNotifyStringDto dto = JsonUtil.toObject(VehicleInsNotifyStringDto.class, json);
            VehInsNotifyDto retDto = ConverterService.convert(dto, VehInsNotifyDto.class);
            return retDto;
        }
        return null;
    }

    @Override
    public VehInsDetailNotifyDto translateToVehDetailNofity(String json) throws BizServiceException {
        if (StringUtils.isEmpty(json)){
            VehicleInsDetailNotifyStringDto dto = JsonUtil.toObject(VehicleInsDetailNotifyStringDto.class, json);
            VehInsDetailNotifyDto retDto = ConverterService.convert(dto, VehInsDetailNotifyDto.class);
            return retDto;
        }
        return null;
    }


}
