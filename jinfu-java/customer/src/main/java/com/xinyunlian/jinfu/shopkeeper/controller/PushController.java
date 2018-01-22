package com.xinyunlian.jinfu.shopkeeper.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.push.dto.*;
import com.xinyunlian.jinfu.push.enums.PushObject;
import com.xinyunlian.jinfu.push.service.PushService;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Created by apple on 2017/3/8.
 */
@Controller
@RequestMapping(value = "shopkeeper/push")
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
        PushMessagePageListDto messageDtos= pushService.getPushlistByUserId(pushRequestDto, PushObject.ZG);
        return ResultDtoFactory.toAck("成功",messageDtos);
    }

    @RequestMapping(value = "/getUnreadMessageCount", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getMessageList() {
        Long count =  pushService.getunreadMessageCountByUserId(SecurityContext.getCurrentUserId(),PushObject.ZG);
        PushUnreadDto dto = new PushUnreadDto();
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
        pushService.updateRegistrationId(device,PushObject.ZG);
        return ResultDtoFactory.toAck("成功");
    }

    @RequestMapping(value = "/unbind", method = RequestMethod.POST)
    public ResultDto<Object> unbind(@RequestBody @Valid PushDeviceDto device) {
        PushDeviceDto deviceDto = new PushDeviceDto();
        deviceDto.setPushToken("");
        deviceDto.setUserId(SecurityContext.getCurrentUserId());
        pushService.updateRegistrationId(deviceDto,PushObject.ZG);
        return ResultDtoFactory.toAck("成功");
    }
}
