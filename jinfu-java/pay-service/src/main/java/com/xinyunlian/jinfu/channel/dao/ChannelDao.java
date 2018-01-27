package com.xinyunlian.jinfu.channel.dao;

import com.xinyunlian.jinfu.channel.entity.ChannelPo;
import com.xinyunlian.jinfu.channel.enums.EChannelStatus;
import com.xinyunlian.jinfu.channel.enums.EChannelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by bright on 2016/11/18.
 */
public interface ChannelDao extends JpaRepository<ChannelPo, Integer>, JpaSpecificationExecutor<ChannelPo> {
    public List<ChannelPo> getByChnlTypeOrderByChnlPriorityAsc(EChannelType channelType);

    public List<ChannelPo> getByChnlTypeAndChnlStatusOrderByChnlPriorityAsc(EChannelType channelType, EChannelStatus channelStatus);
}
