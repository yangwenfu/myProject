package com.xinyunlian.jinfu.push.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by apple on 2016/12/30.
 */
public class PushRequestDto extends PagingDto<PushReadStateDto> {

    private String userId;

    private String lastId;

    private String pushState;

    private int pushObject;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getPushState() {
        return pushState;
    }

    public void setPushState(String pushState) {
        this.pushState = pushState;
    }

    public int getPushObject() {
        return pushObject;
    }

    public void setPushObject(int pushObject) {
        this.pushObject = pushObject;
    }
}
