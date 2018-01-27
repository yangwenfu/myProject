package com.xinyunlian.jinfu.dealer.dealer.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.dealer.dto.DealerSendDto;
import com.xinyunlian.jinfu.dealer.service.DealerSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by menglei on 2016年09月19日.
 */
@Controller
@RequestMapping(value = "dealer/dealerSend")
public class DealerSendController {

    @Autowired
    private DealerSendService dealerSendService;

    /**
     * 派单
     *
     * @param dealerSendDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> add(DealerSendDto dealerSendDto) {
        dealerSendService.createNote(dealerSendDto);
        return ResultDtoFactory.toAck("添加成功");
    }

}
