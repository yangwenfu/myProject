package com.xinyunlian.jinfu.channel.controller;

import com.xinyunlian.jinfu.channel.dto.ChannelDto;
import com.xinyunlian.jinfu.channel.enums.EChannelType;
import com.xinyunlian.jinfu.channel.service.ChannelCacheService;
import com.xinyunlian.jinfu.channel.service.ChannelService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by bright on 2016/11/18.
 */
@RestController
@RequestMapping("channel")
@RequiresPermissions({"AUTHC_CHAN_SETUP"})
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    @Autowired
    private ChannelCacheService channelCacheService;

    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResultDto<List<ChannelDto>> saveChannel(@RequestBody ChannelDto channelDto){
        channelService.saveChannel(channelDto);
        List<ChannelDto> channels = channelService.getChannelsByType(channelDto.getChnlType());
        channelCacheService.putAll(channelDto.getChnlType(), channels);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), channels);
    }

    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResultDto<List<ChannelDto>> getChannelList(@RequestParam  String channelType){
        List<ChannelDto> channels = channelService.getChannelsByType(EnumHelper.translate(EChannelType.class, channelType));
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), channels);
    }

    @ResponseBody
    @RequestMapping(value = "prioritize", method = RequestMethod.POST)
    public ResultDto<List<ChannelDto>> prioritizeChannel(@RequestParam Integer id, @RequestParam  String channelType){
        channelService.prioritizeChannel(id);
        EChannelType type = EnumHelper.translate(EChannelType.class, channelType);
        List<ChannelDto> channels = channelService.getChannelsByType(type);
        channelCacheService.putAll(type, channels);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), channels);
    }


    @ResponseBody
    @RequestMapping(value = "deprioritize", method = RequestMethod.POST)
    public ResultDto<List<ChannelDto>> deprioritizeChannel(@RequestParam Integer id, @RequestParam  String channelType){
        channelService.deprioritizeChannel(id);
        EChannelType type = EnumHelper.translate(EChannelType.class, channelType);
        List<ChannelDto> channels = channelService.getChannelsByType(type);
        channelCacheService.putAll(type, channels);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), channels);
    }

    @ResponseBody
    @RequestMapping(value = "enable", method = RequestMethod.POST)
    public ResultDto<List<ChannelDto>> enableChannel(@RequestParam Integer id, @RequestParam  String channelType){
        channelService.enableChannel(id);
        EChannelType type = EnumHelper.translate(EChannelType.class, channelType);
        List<ChannelDto> channels = channelService.getChannelsByType(type);
        channelCacheService.putAll(type, channels);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), channels);
    }

    @ResponseBody
    @RequestMapping(value = "disable", method = RequestMethod.POST)
    public ResultDto<List<ChannelDto>> disableChannel(@RequestParam Integer id, @RequestParam  String channelType){
        channelService.disableChannel(id);
        EChannelType type = EnumHelper.translate(EChannelType.class, channelType);
        List<ChannelDto> channels = channelService.getChannelsByType(type);
        channelCacheService.putAll(type, channels);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), channels);
    }
}
