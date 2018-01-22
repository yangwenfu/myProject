package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.dealer.dto.DealerUserFeedbackDto;
import com.xinyunlian.jinfu.dealer.dto.DealerUserFeedbackSearchDto;

/**
 * Created by menglei on 2016年09月02日.
 */
public interface DealerUserFeedbackService {

    DealerUserFeedbackSearchDto getFeedbackPage(DealerUserFeedbackSearchDto dealerUserFeedbackSearchDto);

    void createDealerUserFeedback(DealerUserFeedbackDto dealerUserFeedbackDto);

    void deleteFeedback(Long id);

}
