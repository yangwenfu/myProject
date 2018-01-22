package com.xinyunlian.jinfu.feedback.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * @author Willwang
 */
public class FeedbackSearchDto extends PagingDto<FeedbackDto> {

    private static final long serialVersionUID = 1L;

    private String userName;

    private String mobile;

    private String beginTime;

    private String endTime;

    private Boolean read;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
}
