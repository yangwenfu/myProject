package com.xinyunlian.jinfu.yunma.service;

import com.xinyunlian.jinfu.yunma.dto.YmPayChannelDto;

import java.util.List;

/**
 * Created by menglei on 2017-07-19.
 */
public interface YmPayChannelService {

    /**
     * 查云码店铺区域配置的通道
     * @param areaTreePath 传',0,'为默认通道
     * @param channel
     * @return
     */
    List<YmPayChannelDto> findByChannelAndAreaTreePath(String areaTreePath, List<String> channel);

    /**
     * 查云码店铺区域支持的通道
     * @param areaTreePath
     * @param channel
     * @param areaId
     * @return
     */
    List<YmPayChannelDto> findByChannel(Long areaId, String areaTreePath, List<String> channel);

}
