package com.xinyunlian.jinfu.sign.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dongfangchao on 2017/5/19/0019.
 */
public class UserSignInLogDto implements Serializable {

    private Long id;

    private String userId;

    private Date signInDate;

    private Long conDays;

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

    public Date getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(Date signInDate) {
        this.signInDate = signInDate;
    }

    public Long getConDays() {
        return conDays;
    }

    public void setConDays(Long conDays) {
        this.conDays = conDays;
    }
}
