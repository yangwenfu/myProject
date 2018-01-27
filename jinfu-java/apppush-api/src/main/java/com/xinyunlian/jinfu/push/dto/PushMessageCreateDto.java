package com.xinyunlian.jinfu.push.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/1/10.
 */
public class PushMessageCreateDto implements Serializable {

    private static final long serialVersionUID = 922649370372406340L;

    private Long messageId;

    private String title;

    private String imagePath;

    private String description;

    private String pushTime;

    private Integer pushObject;

    private Integer platform;

    private Integer type;

    private List<PushMessageAreaDto> areas;

    private String url;

    private String content;

    private Integer pushStates;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public Integer getPushObject() {
        return pushObject;
    }

    public void setPushObject(Integer pushObject) {
        this.pushObject = pushObject;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<PushMessageAreaDto> getAreas() {
        return areas;
    }

    public void setAreas(List<PushMessageAreaDto> areas) {
        this.areas = areas;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPushStates() {
        return pushStates;
    }

    public void setPushStates(Integer pushStates) {
        this.pushStates = pushStates;
    }
}
