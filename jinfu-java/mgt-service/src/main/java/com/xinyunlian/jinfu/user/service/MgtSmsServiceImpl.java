package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.sms.SmsUtil;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.RandomUtil;
import com.xinyunlian.jinfu.user.enums.EMgtSmsType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dongfangchao on 2016/12/26/0026.
 */
@Service
public class MgtSmsServiceImpl implements MgtSmsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MgtSmsServiceImpl.class);
    @Autowired
    private RedisCacheManager redisCacheManager;

    private String prefix = "MGT_";

    @Value("${send.sms}")
    private Boolean sendSms;
    @Value("${default.verify.code}")
    private String defaultVerifyCode;

    @Override
    public String getVerifyCode(String mobile, EMgtSmsType type) {
        String verifyCode = defaultVerifyCode;
        if (sendSms){
            verifyCode = RandomUtil.getNumberStr(6);
            redisCacheManager.getCache(CacheType.REDIS_FOR_SMS).put(prefix + type.getCode() + mobile, verifyCode);
            Map<String, String> params = new HashMap<>();
            params.put("code",verifyCode);
            SmsUtil.send(AppConfigUtil.getConfig("sms.template.certifycode"), params, mobile);
        }else {
            LOGGER.debug("不发送短信验证码");
            redisCacheManager.getCache(CacheType.REDIS_FOR_SMS).put(prefix + type.getCode() + mobile, verifyCode);
        }
        return verifyCode;
    }

    @Override
    public Boolean checkVerifyCode(String mobile, String verifyCode, EMgtSmsType type) {
        Boolean result = false;
        String code  = redisCacheManager.getCache(CacheType.REDIS_FOR_SMS).get(prefix + type.getCode() + mobile,String.class);
        LOGGER.debug("SMS code in redis : " + code);
        if(StringUtils.equals(code, verifyCode)){
            result = true;
        }
        return result;
    }

    @Override
    public void clearVerifyCode(String mobile, EMgtSmsType type) {
        redisCacheManager.getCache(CacheType.REDIS_FOR_SMS).evict(prefix + type.getCode() + mobile);
    }
}
