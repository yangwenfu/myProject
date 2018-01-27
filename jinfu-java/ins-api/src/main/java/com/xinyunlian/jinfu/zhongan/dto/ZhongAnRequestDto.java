package com.xinyunlian.jinfu.zhongan.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2016/12/2/0002.
 */
public class ZhongAnRequestDto implements Serializable {

    private static final long serialVersionUID = -5436061478561834905L;

    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
