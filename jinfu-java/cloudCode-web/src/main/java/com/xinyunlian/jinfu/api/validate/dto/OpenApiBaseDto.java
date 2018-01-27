package com.xinyunlian.jinfu.api.validate.dto;

import com.xinyunlian.jinfu.api.validate.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by JL on 2016/5/25.
 */
public class OpenApiBaseDto implements Serializable {

    private static Logger LOGGER = LoggerFactory.getLogger(OpenApiBaseDto.class);

    private String appId;

    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public boolean validate(String key) {
        try {
            return SignUtil.createSign(this, key).equals(this.sign);
        } catch (Exception e) {
            LOGGER.info("签名认证失败", e);
            return false;
        }
    }

}
