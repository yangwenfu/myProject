package com.xinyunlian.jinfu.user.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017年06月05日.
 */
public class HomeInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String logLng;

    private String logLat;

    public String getLogLng() {
        return logLng;
    }

    public void setLogLng(String logLng) {
        this.logLng = logLng;
    }

    public String getLogLat() {
        return logLat;
    }

    public void setLogLat(String logLat) {
        this.logLat = logLat;
    }

}
