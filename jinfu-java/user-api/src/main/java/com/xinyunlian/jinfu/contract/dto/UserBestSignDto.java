package com.xinyunlian.jinfu.contract.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/2/8/0008.
 */
public class UserBestSignDto implements Serializable {

    private static final long serialVersionUID = 809737642862662519L;

    private Long id;

    private String userId;

    private String bestSignUid;

    private Boolean bestSignCa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBestSignUid() {
        return bestSignUid;
    }

    public void setBestSignUid(String bestSignUid) {
        this.bestSignUid = bestSignUid;
    }

    public Boolean getBestSignCa() {
        return bestSignCa;
    }

    public void setBestSignCa(Boolean bestSignCa) {
        this.bestSignCa = bestSignCa;
    }
}
