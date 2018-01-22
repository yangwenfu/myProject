package com.xinyunlian.jinfu.feedback.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.dealer.dto.DealerUserFeedbackDto;
import com.xinyunlian.jinfu.dealer.service.DealerUserFeedbackService;
import com.xinyunlian.jinfu.feedback.dto.FeedbackDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Created by menglei on 2016年09月18日.
 */
@Controller
@RequestMapping(value = "feedback")
public class FeedbackController {

    @Autowired
    private DealerUserFeedbackService dealerUserFeedbackService;

    /**
     * 添加反馈
     *
     * @param feedbackDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> save(@RequestBody @Valid FeedbackDto feedbackDto, BindingResult result) {
        if (result.hasErrors()){
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        DealerUserFeedbackDto dealerUserFeedbackDto = ConverterService.convert(feedbackDto, DealerUserFeedbackDto.class);
        dealerUserFeedbackDto.setUserId(SecurityContext.getCurrentUserId());
        dealerUserFeedbackService.createDealerUserFeedback(dealerUserFeedbackDto);
        return ResultDtoFactory.toAck("反馈成功");
    }

}
