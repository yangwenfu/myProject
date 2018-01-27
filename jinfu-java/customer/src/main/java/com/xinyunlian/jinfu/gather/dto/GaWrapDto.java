package com.xinyunlian.jinfu.gather.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongfangchao on 2017/3/15/0015.
 */
public class GaWrapDto implements Serializable {
    private static final long serialVersionUID = -2597929333426723689L;

    private String deviceId;

    private List<Object> list = new ArrayList<>();

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }
}
