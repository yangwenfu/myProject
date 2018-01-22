package com.xinyunlian.jinfu.api.dto;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * Created by DongFC on 2016-11-07.
 */
public class InsureAcquireResp implements Serializable{

    private static final long serialVersionUID = 372223828866497625L;
    private String http_url;
    private String user_id;
    private String result_code;
    private String result_msg;
    private String sign_type;
    private String sign_msg;

    public String signSrc() {
        return "result_code=" + StringUtils.trimToEmpty(result_code)
                + "&result_msg=" + StringUtils.trimToEmpty(result_msg)
                +"&sign_type="+StringUtils.trimToEmpty(sign_type) 
                + "&http_url=" + StringUtils.trimToEmpty(http_url)
                +"&user_id=" + StringUtils.trimToEmpty(user_id);
    }
    
    public String getHttp_url() {
        return http_url;
    }

    public void setHttp_url(String http_url) {
        this.http_url = http_url;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getResult_msg() {
        return result_msg;
    }

    public void setResult_msg(String result_msg) {
        this.result_msg = result_msg;
    }

    public String getSign_msg() {
        return sign_msg;
    }

    public void setSign_msg(String sign_msg) {
        this.sign_msg = sign_msg;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
