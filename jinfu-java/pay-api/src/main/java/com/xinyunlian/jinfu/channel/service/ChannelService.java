package com.xinyunlian.jinfu.channel.service;

import com.xinyunlian.jinfu.channel.dto.ChannelDto;
import com.xinyunlian.jinfu.channel.enums.EChannelType;

import java.util.List;

/**
 * Created by bright on 2016/11/17.
 */
public interface ChannelService {
    public void saveChannel(ChannelDto channelDto);

    public List<ChannelDto> getChannelsByType(EChannelType type);

    public void prioritizeChannel(Integer id);

    public void deprioritizeChannel(Integer id);

    public void disableChannel(Integer id);

    public void enableChannel(Integer id);

}
