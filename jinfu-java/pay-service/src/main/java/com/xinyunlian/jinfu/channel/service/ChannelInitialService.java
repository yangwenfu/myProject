package com.xinyunlian.jinfu.channel.service;

import com.xinyunlian.jinfu.channel.dto.ChannelDto;
import com.xinyunlian.jinfu.channel.enums.EChannelType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bright on 2016/11/18.
 */
@Service
public class ChannelInitialService implements ApplicationListener<ContextRefreshedEvent>{


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ChannelService channelService = contextRefreshedEvent.getApplicationContext().getBean("channelServiceImpl", ChannelService.class);
        ChannelCacheService cacheService = contextRefreshedEvent.getApplicationContext().getBean("channelCacheServiceImpl", ChannelCacheService.class);
        List<ChannelDto> channelDtos = channelService.getChannelsByType(EChannelType.REAL_AUTH);
        cacheService.putAll(EChannelType.REAL_AUTH, channelDtos);
    }
}
