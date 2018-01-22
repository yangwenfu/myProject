package com.xinyunlian.jinfu.push.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by apple on 2017/1/3.
 */
@Entity
@Table(name = "PUSH_MESSAGE")
public class PushMessagePo  extends BaseMaintainablePo {

    @Id
    @Column(name = "MESSAGE_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long messageId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "IMAGEPATH")
    private String imagePath;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PUSH_TIME")
    private Date pushTime;

    @Column(name = "PUSH_OBJECT")
    private Integer pushObject;

    @Column(name = "PLATFORM")
    private Integer platform;

    @Column(name = "TYPE")
    private Integer type;

    @Column(name = "URL")
    private String url;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "PUSH_STATES")
    private Integer pushStates;

    @Column(name = "IS_DELETE")
    private Integer isDelete;

    @Column(name = "IS_ALL_PLACE")
    private Integer isAllPlace;

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

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getIsAllPlace() {
        return isAllPlace;
    }

    public void setIsAllPlace(Integer isAllPlace) {
        this.isAllPlace = isAllPlace;
    }
}
