package com.xinyunlian.jinfu.loan.dto.feedback;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * Created by Willwang on 2016/11/10.
 */
public class FeedbackAddReqDto implements Serializable {
    @Length(min = 1, max = 32, message = "标题长度超过限制")
    private String title;

    @Length(min = 1, max = 250, message = "内容长度超过限制")
    private String content;

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
