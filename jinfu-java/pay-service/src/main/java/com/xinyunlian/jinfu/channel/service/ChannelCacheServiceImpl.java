package com.xinyunlian.jinfu.channel.service;

import com.xinyunlian.jinfu.channel.dto.ChannelDto;
import com.xinyunlian.jinfu.channel.enums.EChannelType;
import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bright on 2016/11/17.
 */
@Service
public class ChannelCacheServiceImpl implements ChannelCacheService {

    @Autowired
    private RedisCacheManager cacheManager;

    @Override
    public List<ChannelDto> getChannelsByType(EChannelType channelType) {
        RedisCache cache = getCache();
        List<ChannelDto> cachedChannelDtos = JsonUtil.toObject(List.class, ChannelDto.class, cache.get(getCacheKey(channelType), String.class));
        return cachedChannelDtos;
    }

    @Override
    public void addChannel(ChannelDto cachedChannelDto){
        RedisCache cache = getCache();
        List<ChannelDto> cachedChannelDtos = getChannelsByType(cachedChannelDto.getChnlType());
        cachedChannelDtos.add(cachedChannelDto);
        putAll(cachedChannelDto.getChnlType(), cachedChannelDtos);
    }

    @Override
    public void putAll(EChannelType channelType, List<ChannelDto> cachedChannelDtos){
        RedisCache cache = getCache();
        cache.put(getCacheKey(channelType), JsonUtil.toJson(cachedChannelDtos));
    }

    @Override
    public void deleteChannel(ChannelDto cachedChannelDto){
        RedisCache cache = getCache();
        List<ChannelDto> cachedChannelDtos = getChannelsByType(cachedChannelDto.getChnlType());
        for (ChannelDto dto : cachedChannelDtos){
            if(dto.getChnlName().equals(cachedChannelDto.getChnlName())){
                cachedChannelDtos.remove(dto);
            }
        }
        putAll(cachedChannelDto.getChnlType(), cachedChannelDtos);
    }

    private String getCacheKey(EChannelType channelType){
        return "chnl_"+channelType.getCode();
    }

    private RedisCache getCache(){
        return (RedisCache)cacheManager.getCache(CacheType.APPLICATIONAL);
    }
}
