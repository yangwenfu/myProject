package com.xinyunlian.jinfu.channel.service;

import com.xinyunlian.jinfu.channel.dto.ChannelUserAreaDto;
import com.xinyunlian.jinfu.channel.dto.ChannelUserRelationDto;
import com.xinyunlian.jinfu.channel.dto.ChannelUserSearchDto;

import java.util.List;

/**
 * 渠道用户Service
 *
 * @author jll
 */

public interface ChannelUserService {
    void saveUserRelation(List<ChannelUserRelationDto> list);

    List<ChannelUserRelationDto> listUserRelation(String parentUserId);

    void saveUserArea(List<ChannelUserAreaDto> list);

    List<ChannelUserAreaDto> listUserArea(String userId);

    ChannelUserSearchDto getChannelUserPage(ChannelUserSearchDto searchDto);
}
