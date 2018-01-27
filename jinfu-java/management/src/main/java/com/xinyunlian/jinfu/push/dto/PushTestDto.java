package com.xinyunlian.jinfu.push.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/1/16.
 */
public class PushTestDto implements Serializable {

    private String content;

    private List<String> pushToken;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPushToken() {
        return pushToken;
    }

    public void setPushToken(List<String> pushToken) {
        this.pushToken = pushToken;
    }
}
