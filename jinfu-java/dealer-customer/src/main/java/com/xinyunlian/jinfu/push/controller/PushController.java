package com.xinyunlian.jinfu.push.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.push.dto.*;
import com.xinyunlian.jinfu.push.enums.PushObject;
import com.xinyunlian.jinfu.push.service.PushService;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by apple on 2016/12/30.
 */
@Controller
@RequestMapping(value = "push")
public class PushController {

    @Autowired
    private PushService pushService;

    @Autowired
    private DealerUserService dealerUserService;

    @RequestMapping(value = "/getMessageList", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getMessageList(@Valid PushRequestDto pushRequestDto) {
        String userId = SecurityContext.getCurrentUserId();
        pushRequestDto.setUserId(userId);
        PushMessagePageListDto messageDtos= pushService.getPushlistByUserId(pushRequestDto, PushObject.XHB);
        return ResultDtoFactory.toAck("成功",messageDtos);
    }

    @RequestMapping(value = "/getUnreadMessageCount", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getMessageList() {
        Long count =  pushService.getunreadMessageCountByUserId(SecurityContext.getCurrentUserId(),PushObject.XHB);
        PushUnReadDto dto = new PushUnReadDto();
        dto.setUnreadCount(count);
        return ResultDtoFactory.toAck("成功",dto);
    }

    @RequestMapping(value = "/updateReadState", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> updateReadState(@RequestBody @Valid PushMessagePageDto PushMsgDto) {
        if (PushMsgDto.getMessageId() == null){
            return ResultDtoFactory.toNack("参数错误");
        }
        pushService.readMessage(PushMsgDto.getMessageId(),SecurityContext.getCurrentUserId());
        return ResultDtoFactory.toAck("成功");
    }

    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> bind(@RequestBody @Valid PushDeviceDto device) {
        device.setUserId(SecurityContext.getCurrentUserId());
        pushService.updateRegistrationId(device,PushObject.XHB);
        return ResultDtoFactory.toAck("成功");
    }

    @RequestMapping(value = "/unbind", method = RequestMethod.POST)
    public ResultDto<Object> unbind(@RequestBody @Valid PushDeviceDto device) {
        pushService.unBindRegistrationId(SecurityContext.getCurrentUserId(),PushObject.XHB);
        return ResultDtoFactory.toAck("成功");
    }
}
