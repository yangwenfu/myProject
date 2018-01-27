package com.xinyunlian.jinfu.user.dto;


import java.io.Serializable;

/**
 * Created by JL on 2016/9/5.
 */
public class OperationLogDto<T> implements Serializable {

    private String userId;

    private T content;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
