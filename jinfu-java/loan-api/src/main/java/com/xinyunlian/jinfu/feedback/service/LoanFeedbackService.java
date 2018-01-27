package com.xinyunlian.jinfu.feedback.service;

import com.xinyunlian.jinfu.feedback.dto.FeedbackDto;
import com.xinyunlian.jinfu.feedback.dto.FeedbackSearchDto;

/**
 * @author Willwang
 */
public interface LoanFeedbackService {

    void add(FeedbackDto feedbackAddDto);

    void delete(Long id);

    void hasRead(Long id,boolean status);

    FeedbackSearchDto list(FeedbackSearchDto feedbackSearchDto);
}
