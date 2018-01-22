package com.xinyunlian.jinfu.cms.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by dongfangchao on 2017/1/24/0024.
 */
public class NoticeInfDto implements Serializable{

    private static final long serialVersionUID = -5575684718195097900L;

    private Long id;

    private String noticeContent;

    private String linkUrl;

    private Date startDate;

    private Date endDate;

    private ECmsValidStatus cmsValidStatus;

    private String createOpId;

    private Date createTs;

    private String lastMntOpId;

    private Date lastMntTs;

    private Long versionCt;

    private List<NoticePlatformDto> noticePlatformList;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<NoticePlatformDto> getNoticePlatformList() {
        return noticePlatformList;
    }

    public void setNoticePlatformList(List<NoticePlatformDto> noticePlatformList) {
        this.noticePlatformList = noticePlatformList;
    }

    public String getCreateOpId() {
        return createOpId;
    }

    public void setCreateOpId(String createOpId) {
        this.createOpId = createOpId;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public String getLastMntOpId() {
        return lastMntOpId;
    }

    public void setLastMntOpId(String lastMntOpId) {
        this.lastMntOpId = lastMntOpId;
    }

    public Date getLastMntTs() {
        return lastMntTs;
    }

    public void setLastMntTs(Date lastMntTs) {
        this.lastMntTs = lastMntTs;
    }

    public Long getVersionCt() {
        return versionCt;
    }

    public void setVersionCt(Long versionCt) {
        this.versionCt = versionCt;
    }

    public ECmsValidStatus getCmsValidStatus() {
        return cmsValidStatus;
    }

    public void setCmsValidStatus(ECmsValidStatus cmsValidStatus) {
        this.cmsValidStatus = cmsValidStatus;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
