package com.xinyunlian.jinfu.risk.dto.req;

import java.io.Serializable;

/**
 * Created by bright on 2016/11/17.
 */
abstract public class CreditReqBaseDto implements Serializable{
    /* 调用方唯一ID */
    private String pname;

    /* 提交请求时的时间戳 System.currentTimeMillis() */
    private String ptime;

    /* 安全验证码 md5(pkey+"_"+ptime+"_"+pkey)*/
    private String vkey;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getVkey() {
        return vkey;
    }

    public void setVkey(String vkey) {
        this.vkey = vkey;
    }
}
