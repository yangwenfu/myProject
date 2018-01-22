package com.xinyunlian.jinfu.note.dto.req;

import com.xinyunlian.jinfu.dealer.enums.EDealerUserNoteStatus;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * Created by menglei on 2016年09月18日.
 */
public class NoteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String noteId;

    private String userId;

    private String dealerId;

    private String storeId;

    @Length(min = 1, max = 50, message = "标题长度超过限制")
    private String title;

    @Length(min = 1, max = 255, message = "内容长度超过限制")
    private String content;

    private String storeName;

    private String stroeUserName;

    private EDealerUserNoteStatus status;

    private String logLng;

    private String logLat;

    private String logAddress;

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStroeUserName() {
        return stroeUserName;
    }

    public void setStroeUserName(String stroeUserName) {
        this.stroeUserName = stroeUserName;
    }

    public EDealerUserNoteStatus getStatus() {
        return status;
    }

    public void setStatus(EDealerUserNoteStatus status) {
        this.status = status;
    }

    public String getLogLng() {
        return logLng;
    }

    public void setLogLng(String logLng) {
        this.logLng = logLng;
    }

    public String getLogLat() {
        return logLat;
    }

    public void setLogLat(String logLat) {
        this.logLat = logLat;
    }

    public String getLogAddress() {
        return logAddress;
    }

    public void setLogAddress(String logAddress) {
        this.logAddress = logAddress;
    }
}
