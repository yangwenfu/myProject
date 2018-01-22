package com.xinyunlian.jinfu.channel.service;

import com.xinyunlian.jinfu.channel.dao.ChannelDao;
import com.xinyunlian.jinfu.channel.dto.ChannelDto;
import com.xinyunlian.jinfu.channel.entity.ChannelPo;
import com.xinyunlian.jinfu.channel.enums.EChannelStatus;
import com.xinyunlian.jinfu.channel.enums.EChannelType;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bright on 2016/11/18.
 */
@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    private ChannelDao channelDao;

    @Override
    public void saveChannel(ChannelDto channelDto) {
        ChannelPo channelPo = ConverterService.convert(channelDto, ChannelPo.class);
        channelDao.save(channelPo);
    }

    @Override
    public List<ChannelDto> getChannelsByType(EChannelType type) {
        List<ChannelPo> channels = channelDao.getByChnlTypeOrderByChnlPriorityAsc(type);
        List<ChannelDto> dtos = new ArrayList<>(channels.size());
        channels.forEach(channelPo -> {
            dtos.add(ConverterService.convert(channelPo, ChannelDto.class));
        });
        return dtos;
    }

    @Override
    public void prioritizeChannel(Integer id) {
        ChannelPo channelPo = channelDao.findOne(id);

        //如果已禁用，直接返回
        if(channelPo.getChnlStatus() == EChannelStatus.DISABLED){
            return;
        }

        List<ChannelPo> channels = channelDao.getByChnlTypeAndChnlStatusOrderByChnlPriorityAsc(channelPo.getChnlType(), EChannelStatus.ENABLED);
        for(int i=0;i<channels.size();i++){
            ChannelPo channel = channels.get(i);
            if(channel.getId().equals(channelPo.getId())){
                if(i == 0){
                    // 如果已经是第一个，直接返回
                    return;
                }
                // 和上一个交换优先级
                Integer priority = channels.get(i-1).getChnlPriority();
                channels.get(i-1).setChnlPriority(channel.getChnlPriority());
                channel.setChnlPriority(priority);
                channelDao.save(channels);
                return;
            }
        }
    }

    @Override
    public void deprioritizeChannel(Integer id) {
        ChannelPo channelPo = channelDao.findOne(id);

        //如果已禁用，直接返回
        if(channelPo.getChnlStatus() == EChannelStatus.DISABLED){
            return;
        }

        List<ChannelPo> channels = channelDao.getByChnlTypeAndChnlStatusOrderByChnlPriorityAsc(channelPo.getChnlType(), EChannelStatus.ENABLED);
        for(int i=0;i<channels.size();i++){
            ChannelPo channel = channels.get(i);
            if(channel.getId().equals(channelPo.getId())){
                if(i == channels.size() - 1){
                    // 如果已经是最后一个，直接返回
                    return;
                }
                // 和下一个交换优先级
                Integer priority = channels.get(i+1).getChnlPriority();
                channels.get(i+1).setChnlPriority(channel.getChnlPriority());
                channel.setChnlPriority(priority);
                channelDao.save(channels);
                return;
            }
        }

    }

    @Override
    public void disableChannel(Integer id) {
        ChannelPo channel = channelDao.findOne(id);
        if(channel.getChnlStatus() == EChannelStatus.DISABLED)
            return;
        channel.setChnlStatus(EChannelStatus.DISABLED);
        List<ChannelPo> channels = channelDao.getByChnlTypeOrderByChnlPriorityAsc(channel.getChnlType());
        if (channels.size()>0){
            //设置禁用的优先级为最大的+1，即置于尾部
            channel.setChnlPriority(channels.get(channels.size()-1).getChnlPriority()+1);
        }
        channelDao.save(channel);
    }

    @Override
    public void enableChannel(Integer id) {
        ChannelPo channel = channelDao.findOne(id);
        if(channel.getChnlStatus() == EChannelStatus.ENABLED)
            return;
        channel.setChnlStatus(EChannelStatus.ENABLED);
        List<ChannelPo> enabledChannels = channelDao.getByChnlTypeAndChnlStatusOrderByChnlPriorityAsc(channel.getChnlType(), EChannelStatus.ENABLED);
        List<ChannelPo> disabledChannels = channelDao.getByChnlTypeAndChnlStatusOrderByChnlPriorityAsc(channel.getChnlType(), EChannelStatus.DISABLED);
        channel.setChnlPriority(0);
        if(enabledChannels.size()>0){
            channel.setChnlPriority(enabledChannels.get(enabledChannels.size()-1).getChnlPriority()+1);
        }
        disabledChannels.forEach(channelPo -> {
            if(channelPo.getId() != channel.getId()){
                channelPo.setChnlPriority(channelPo.getChnlPriority()+1);
            }
        });
        channelDao.save(enabledChannels);
        channelDao.save(disabledChannels);
        channelDao.save(channel);
    }
}
