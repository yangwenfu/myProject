package com.xinyunlian.jinfu.yunma.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.yunma.dao.YmPayChannelDao;
import com.xinyunlian.jinfu.yunma.dto.YmPayChannelDto;
import com.xinyunlian.jinfu.yunma.entity.YmPayChannelPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by menglei on 2017-07-17.
 */
@Service
public class YmPayChannelServiceImpl implements YmPayChannelService {

    @Autowired
    private YmPayChannelDao ymPayChannelDao;

    @Override
    @Transactional
    public List<YmPayChannelDto> findByChannelAndAreaTreePath(String areaTreePath, List<String> channel) {
        List<YmPayChannelDto> rs = new ArrayList<>();
        List<YmPayChannelPo> list = ymPayChannelDao.findByChannelAndAreaTreePath(areaTreePath, channel);
        for (YmPayChannelPo ymPayChannelPo : list) {
            rs.add(ConverterService.convert(ymPayChannelPo, YmPayChannelDto.class));
        }
        return rs;
    }

    @Override
    @Transactional
    public List<YmPayChannelDto> findByChannel(Long areaId, String areaTreePath, List<String> channel) {
        List<YmPayChannelDto> rs = new ArrayList<>();
        List<YmPayChannelPo> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(channel) && areaId != null && areaTreePath != null) {
            list = ymPayChannelDao.findByChannelAndAreaId(areaId, channel);//先根据市查找
            if(CollectionUtils.isEmpty(list)){//根据省匹配
                list = ymPayChannelDao.findByChannelAndAreaTreePath(areaTreePath, channel);
            }
            if(CollectionUtils.isEmpty(list)){//默认通道
                list = ymPayChannelDao.findByChannelAndAreaId(0L, channel);
            }
        }
        for (YmPayChannelPo ymPayChannelPo : list) {
            rs.add(ConverterService.convert(ymPayChannelPo, YmPayChannelDto.class));
        }
        return rs;
    }

}
