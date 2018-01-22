package com.xinyunlian.jinfu.user.dto;

import com.xinyunlian.jinfu.oauth.dto.AioUserInfoDto;

/**
 * Created by jl062 on 2017/2/27.
 */
public class OuterRsaDto extends AioUserInfoDto {

    private String sendtime, sign , accessCode;

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
