package com.xinyunlian.jinfu.cms.entity;

import com.xinyunlian.jinfu.cms.enums.ENoticePlatform;
import com.xinyunlian.jinfu.cms.enums.ENoticePosition;
import com.xinyunlian.jinfu.cms.enums.converter.ENoticePlatformConverter;
import com.xinyunlian.jinfu.cms.enums.converter.ENoticePositionConverter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/24/0024.
 */
@Entity
@Table(name = "notice_platform")
public class NoticePlatformPo implements Serializable{
    private static final long serialVersionUID = -5739373803656605004L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name = "NOTICE_ID")
    private Long noticeId;

    @Column(name = "NOTICE_PLATFORM")
    @Convert(converter = ENoticePlatformConverter.class)
    private ENoticePlatform noticePlatform;

    @Column(name = "NOTICE_POSITION")
    @Convert(converter = ENoticePositionConverter.class)
    private ENoticePosition noticePosition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public ENoticePlatform getNoticePlatform() {
        return noticePlatform;
    }

    public void setNoticePlatform(ENoticePlatform noticePlatform) {
        this.noticePlatform = noticePlatform;
    }

    public ENoticePosition getNoticePosition() {
        return noticePosition;
    }

    public void setNoticePosition(ENoticePosition noticePosition) {
        this.noticePosition = noticePosition;
    }
}
