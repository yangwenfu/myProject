package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.sms.SmsUtil;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.RandomUtil;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell1 on 2016/8/25.
 */
@Service
public class SmsServiceImpl implements SmsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsServiceImpl.class);
    @Autowired
    private RedisCacheManager redisCacheManager;

    @Override
    public String getVerifyCode(String mobile,ESmsSendType type) {
        String verifyCode = RandomUtil.getNumberStr(6);


        if((AppConfigUtil.isDevEnv() || AppConfigUtil.isQAEnv()) && !"13552535506".equals(mobile)) {
            verifyCode = "000000";
            LOGGER.info("dev or qa code:000000");
        }else {
            Map<String, String> params = new HashMap<>();
            params.put("code", verifyCode);
            SmsUtil.send(AppConfigUtil.getConfig("sms.template.certifycode"), params, mobile);
        }
        redisCacheManager.getCache(CacheType.REDIS_FOR_SMS).put(type.getCode() + mobile, verifyCode);

        return verifyCode;
    }

    @Override
    public Boolean confirmVerifyCode(String mobile, String verifyCode,ESmsSendType type) {
        Boolean result = false;
        String code  = redisCacheManager.getCache(CacheType.REDIS_FOR_SMS).get(type.getCode() + mobile,String.class);
        LOGGER.debug("SMS code in redis : " + code);
        if(!StringUtils.isEmpty(code) && StringUtils.equals(code, verifyCode)){
            result = true;
        }
        return result;
    }

    @Override
    public void clearVerifyCode(String mobile,ESmsSendType type){
        redisCacheManager.getCache(CacheType.REDIS_FOR_SMS).evict(type.getCode() + mobile);
    }
}
