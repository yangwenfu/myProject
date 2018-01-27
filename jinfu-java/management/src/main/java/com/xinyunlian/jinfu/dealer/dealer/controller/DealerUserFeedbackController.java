package com.xinyunlian.jinfu.dealer.dealer.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.dealer.dto.DealerUserFeedbackSearchDto;
import com.xinyunlian.jinfu.dealer.service.DealerUserFeedbackService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by menglei on 2016年09月26日.
 */
@Controller
@RequestMapping(value = "dealer/feedback")
@RequiresPermissions({"CHAN_QA"})
public class DealerUserFeedbackController {

    @Autowired
    private DealerUserFeedbackService dealerUserFeedbackService;

    /**
     * 分页查询反馈
     *
     * @param dealerUserFeedbackSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<DealerUserFeedbackSearchDto> getFeedbackPage(DealerUserFeedbackSearchDto dealerUserFeedbackSearchDto) {
        DealerUserFeedbackSearchDto page = dealerUserFeedbackService.getFeedbackPage(dealerUserFeedbackSearchDto);
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 删除反馈
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> delete(@RequestParam Long id) {
        dealerUserFeedbackService.deleteFeedback(id);
        return ResultDtoFactory.toAck("删除成功");
    }
}
