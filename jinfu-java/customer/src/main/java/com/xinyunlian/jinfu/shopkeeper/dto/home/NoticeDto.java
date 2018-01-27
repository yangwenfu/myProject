package com.xinyunlian.jinfu.shopkeeper.dto.home;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/24/0024.
 */
public class NoticeDto implements Serializable{

    private static final long serialVersionUID = -5575684718195097900L;

    private Long id;

    private String noticeContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }
}
