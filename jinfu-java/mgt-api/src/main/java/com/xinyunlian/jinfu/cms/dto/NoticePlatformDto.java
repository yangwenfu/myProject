package com.xinyunlian.jinfu.cms.dto;

import com.xinyunlian.jinfu.cms.enums.ENoticePlatform;
import com.xinyunlian.jinfu.cms.enums.ENoticePosition;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/24/0024.
 */
public class NoticePlatformDto implements Serializable{

    private static final long serialVersionUID = 1939980023244339571L;

    private Long id;

    private Long noticeId;

    private ENoticePlatform noticePlatform;

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
