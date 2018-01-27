package com.xinyunlian.jinfu.push.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by apple on 2017/1/3.
 */
public class PushMessageDto implements Serializable {

    private static final long serialVersionUID = -7516793482876883041L;

    private Long messageId;

    private String title;

    private String imagePath;

    private String description;

    private String pushTime;

    private Date createTs;

    private Integer pushObject;

    private String pushObjectName;

    private Integer platform;

    private String platformName;

    private Integer type;

    private String typeName;

    private String url;

    private String content;

    private Integer pushStates;

    private String pushStatesName;

    private Integer areaCount;

    private String areaCountName;

    private List<PushMessageAreaDto> areas;

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

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public Integer getPushObject() {
        return pushObject;
    }

    public void setPushObject(Integer pushObject) {
        this.pushObject = pushObject;
    }

    public String getPushObjectName() {
        return pushObjectName;
    }

    public void setPushObjectName(String pushObjectName) {
        this.pushObjectName = pushObjectName;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getPushStatesName() {
        return pushStatesName;
    }

    public void setPushStatesName(String pushStatesName) {
        this.pushStatesName = pushStatesName;
    }

    public Integer getAreaCount() {
        return areaCount;
    }

    public void setAreaCount(Integer areaCount) {
        this.areaCount = areaCount;
    }

    public String getAreaCountName() {
        return areaCountName;
    }

    public void setAreaCountName(String areaCountName) {
        this.areaCountName = areaCountName;
    }

    public List<PushMessageAreaDto> getAreas() {
        return areas;
    }

    public void setAreas(List<PushMessageAreaDto> areas) {
        this.areas = areas;
    }
}
