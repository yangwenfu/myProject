package com.xinyunlian.jinfu.push.dto;

import java.io.Serializable;

/**
 * Created by apple on 2017/1/4.
 */
public class PushReadStateDto implements Serializable {

    private static final long serialVersionUID = 2785941015341934734L;

    private Long id;

    private Long messageId;

    private String userId;

    private String readState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReadState() {
        return readState;
    }

    public void setReadState(String readState) {
        this.readState = readState;
    }
}
