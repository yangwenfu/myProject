package com.xinyunlian.jinfu.dealer.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserNoteStatus;
import com.xinyunlian.jinfu.dealer.enums.converter.EDealerUserNoteStatusConverter;
import com.xinyunlian.jinfu.user.entity.DealerUserPo;

import javax.persistence.*;

/**
 * Created by menglei on 2016年08月26日.
 */
@Entity
@Table(name = "dealer_user_note")
public class DealerUserNotePo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTE_ID")
    private Long noteId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "DEALER_ID")
    private String dealerId;

    @Column(name = "STORE_ID")
    private String storeId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "STORE_NAME")
    private String storeName;

    @Column(name = "STORE_USERNAME")
    private String storeUserName;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "STATUS")
    @Convert(converter = EDealerUserNoteStatusConverter.class)
    private EDealerUserNoteStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEALER_ID", insertable = false, updatable = false)
    private DealerPo dealerPo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private DealerUserPo dealerUserPo;

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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public DealerPo getDealerPo() {
        return dealerPo;
    }

    public void setDealerPo(DealerPo dealerPo) {
        this.dealerPo = dealerPo;
    }

    public DealerUserPo getDealerUserPo() {
        return dealerUserPo;
    }

    public void setDealerUserPo(DealerUserPo dealerUserPo) {
        this.dealerUserPo = dealerUserPo;
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
