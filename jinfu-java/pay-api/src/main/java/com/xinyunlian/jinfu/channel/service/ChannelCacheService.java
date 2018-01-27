package com.xinyunlian.jinfu.channel.service;

import com.xinyunlian.jinfu.channel.dto.ChannelDto;
import com.xinyunlian.jinfu.channel.enums.EChannelType;

import java.util.List;

/**
 * Created by bright on 2016/11/17.
 */
public interface ChannelCacheService {
    List<ChannelDto> getChannelsByType(EChannelType channelType);

    void addChannel(ChannelDto cachedChannelDto);

    void putAll(EChannelType channelType, List<ChannelDto> cachedChannelDtos);

    void deleteChannel(ChannelDto cachedChannelDto);
}
