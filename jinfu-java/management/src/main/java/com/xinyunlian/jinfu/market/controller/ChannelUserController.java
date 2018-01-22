package com.xinyunlian.jinfu.market.controller;

import com.xinyunlian.jinfu.channel.dto.ChannelUserAreaDto;
import com.xinyunlian.jinfu.channel.dto.ChannelUserRelationDto;
import com.xinyunlian.jinfu.channel.dto.ChannelUserSearchDto;
import com.xinyunlian.jinfu.channel.service.ChannelUserService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.user.dto.MgtUserDto;
import com.xinyunlian.jinfu.user.service.MgtUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by King on 2017/5/5.
 */
@RestController
@RequestMapping("market/channel")
@RequiresPermissions({"CHAN_MEM_MGT"})
public class ChannelUserController {
    @Autowired
    private MgtUserService mgtUserService;
    @Autowired
    private ChannelUserService channelUserService;

    @ApiOperation(value = "渠道人员列表")
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResultDto<ChannelUserSearchDto> getChannelUserPage(ChannelUserSearchDto searchDto){
        ChannelUserSearchDto dto = channelUserService.getChannelUserPage(searchDto);
        return ResultDtoFactory.toAck("获取成功", dto);
    }

    @RequiresPermissions({"CHAN_MEM_SAVE_AREA"})
    @ApiOperation(value = "保存渠道人员地区信息")
    @ResponseBody
    @RequestMapping(value = "saveUserArea", method = RequestMethod.POST)
    public ResultDto<ChannelUserSearchDto> saveUserArea(@RequestBody List<ChannelUserAreaDto> list){
        channelUserService.saveUserArea(list);
        return ResultDtoFactory.toAck("操作成功");
    }

    @ApiOperation(value = "渠道人员地区列表")
    @ResponseBody
    @RequestMapping(value = "listUserArea", method = RequestMethod.GET)
    public ResultDto<Object> listUserArea(String userId){
        List<ChannelUserAreaDto> channelUserAreaDtos = channelUserService.listUserArea(userId);
        return ResultDtoFactory.toAck("获取成功", channelUserAreaDtos);
    }

    @RequiresPermissions({"CHAN_MEM_SAVE_USER"})
    @ApiOperation(value = "保存渠道人员上下级")
    @ResponseBody
    @RequestMapping(value = "saveUserRelation", method = RequestMethod.POST)
    public ResultDto<ChannelUserSearchDto> saveUserRelation(@RequestBody List<ChannelUserRelationDto> list){
        channelUserService.saveUserRelation(list);
        return ResultDtoFactory.toAck("操作成功");
    }

    @ApiOperation(value = "获取未分配城市经理")
    @ResponseBody
    @RequestMapping(value = "unallocatedUsers", method = RequestMethod.GET)
    public ResultDto<Object> unallocatedUsers(String userId){
        List<MgtUserDto> unallocatedUsers = mgtUserService.findNotInChannel("CITY_MGT");
        return ResultDtoFactory.toAck("获取成功", unallocatedUsers);
    }

    @ApiOperation(value = "获取渠道人员下级城市经理")
    @ResponseBody
    @RequestMapping(value = "allocatedUsers", method = RequestMethod.GET)
    public ResultDto<Object> allocatedUsers(String userId){
        List<ChannelUserRelationDto> channelUserAreaDtos = channelUserService.listUserRelation(userId);
        List<String> userIds = new ArrayList<>();
        if(!CollectionUtils.isEmpty(channelUserAreaDtos)){
            channelUserAreaDtos.forEach(channelUserRelationDto -> {
                userIds.add(channelUserRelationDto.getUserId());
            });
        }
        List<MgtUserDto> allocatedUsers = mgtUserService.findByMgtUserIds(userIds);

        return ResultDtoFactory.toAck("获取成功", allocatedUsers);
    }
}
