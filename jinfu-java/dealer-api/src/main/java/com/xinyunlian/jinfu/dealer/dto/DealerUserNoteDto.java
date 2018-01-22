package com.xinyunlian.jinfu.dealer.dto;

import com.xinyunlian.jinfu.dealer.enums.EDealerUserNoteStatus;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;

import java.io.Serializable;

/**
 * Created by menglei on 2016年08月31日.
 */
public class DealerUserNoteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long noteId;

    private String userId;

    private String dealerId;

    private String storeId;

    private String title;

    private String content;

    private String storeName;

    private String storeUserName;

    private String beginTime;

    private String endTime;

    private EDealerUserNoteStatus status;

    private String createTime;

    private DealerUserDto dealerUserDto;

    private DealerDto dealerDto;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EDealerUserNoteStatus getStatus() {
        return status;
    }

    public void setStatus(EDealerUserNoteStatus status) {
        this.status = status;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public DealerUserDto getDealerUserDto() {
        return dealerUserDto;
    }

    public void setDealerUserDto(DealerUserDto dealerUserDto) {
        this.dealerUserDto = dealerUserDto;
    }

    public DealerDto getDealerDto() {
        return dealerDto;
    }

    public void setDealerDto(DealerDto dealerDto) {
        this.dealerDto = dealerDto;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getStoreUserName() {
        return storeUserName;
    }

    public void setStoreUserName(String storeUserName) {
        this.storeUserName = storeUserName;
    }
}
