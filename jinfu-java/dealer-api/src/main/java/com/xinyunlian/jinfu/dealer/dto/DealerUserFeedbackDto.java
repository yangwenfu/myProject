package com.xinyunlian.jinfu.dealer.dto;

import com.xinyunlian.jinfu.user.dto.DealerUserDto;

import java.io.Serializable;

/**
 * Created by menglei on 2016年09月02日.
 */
public class DealerUserFeedbackDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long feedbackId;

    private String userId;

    private String title;

    private String content;

    private String createTime;

    private DealerUserDto dealerUserDto;

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public DealerUserDto getDealerUserDto() {
        return dealerUserDto;
    }

    public void setDealerUserDto(DealerUserDto dealerUserDto) {
        this.dealerUserDto = dealerUserDto;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
