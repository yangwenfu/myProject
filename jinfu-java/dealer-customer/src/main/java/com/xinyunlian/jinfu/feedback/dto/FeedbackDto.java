package com.xinyunlian.jinfu.feedback.dto;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * Created by menglei on 2016年09月18日.
 */
public class FeedbackDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long feedbackId;

    private String userId;

    @Length(min = 1, max = 32, message = "标题长度超过限制")
    private String title;

    @Length(min = 1, max = 250, message = "内容长度超过限制")
    private String content;

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
}
