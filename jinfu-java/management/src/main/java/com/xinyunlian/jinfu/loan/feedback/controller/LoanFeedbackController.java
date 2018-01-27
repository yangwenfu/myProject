package com.xinyunlian.jinfu.loan.feedback.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.feedback.dto.FeedbackSearchDto;
import com.xinyunlian.jinfu.feedback.service.LoanFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Willwang
 */
@Controller
@RequestMapping(value = "loan/feedback")
public class LoanFeedbackController {

    @Autowired
    private LoanFeedbackService loanFeedbackService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultDto<FeedbackSearchDto> getFeedbackPage(@RequestBody  FeedbackSearchDto searchDto) {
        FeedbackSearchDto list = loanFeedbackService.list(searchDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), list);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> delete(@RequestParam Long id) {
        loanFeedbackService.delete(id);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> read(@RequestParam Long id,@RequestParam Boolean status) {
        loanFeedbackService.hasRead(id,status);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }
}
