package com.xinyunlian.jinfu.service;

import com.xinyunlian.jinfu.channel.dto.ChannelDto;
import com.xinyunlian.jinfu.channel.enums.EChannelStatus;
import com.xinyunlian.jinfu.channel.enums.EChannelType;
import com.xinyunlian.jinfu.channel.service.ChannelCacheService;
import com.xinyunlian.jinfu.common.util.ApplicationContextUtil;
import com.xinyunlian.jinfu.domain.req.RealAuthRequest;
import com.xinyunlian.jinfu.domain.resp.RealAuthResponse;
import com.xinyunlian.jinfu.dto.RealAuthResponseDto;
import com.xinyunlian.jinfu.dto.UserRealAuthDto;
import com.xinyunlian.jinfu.enums.PayRecvResult;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JL on 2016/9/12.
 */
@Service
public class UserRealAuthServiceImpl implements UserRealAuthService{

    private static final List<ChannelDto> DEFAULT_CHANNELS = new ArrayList<>();

    static{
        ChannelDto xyl4eleChannel = new ChannelDto();
        xyl4eleChannel.setChnlPriority(0);
        xyl4eleChannel.setBeanName("XylUserRealAuthWith4ElsServiceImpl");
        xyl4eleChannel.setChnlDelay(0);
        ChannelDto xyl3eleChannel = new ChannelDto();
        xyl3eleChannel.setChnlPriority(1);
        xyl3eleChannel.setBeanName("XylUserRealAuthWith3ElsServiceImpl");
        xyl3eleChannel.setChnlDelay(0);
        ChannelDto ny4eleChannel = new ChannelDto();
        ny4eleChannel.setChnlPriority(2);
        ny4eleChannel.setBeanName("NyUserRealAuthWith4ElsServiceImpl");
        ny4eleChannel.setChnlDelay(0);
        ChannelDto ny3eleChannel = new ChannelDto();
        ny3eleChannel.setChnlPriority(3);
        ny3eleChannel.setBeanName("NyUserRealAuthWith3ElsServiceImpl");
        ny3eleChannel.setChnlDelay(3);
        DEFAULT_CHANNELS.add(xyl4eleChannel);
        DEFAULT_CHANNELS.add(xyl3eleChannel);
        DEFAULT_CHANNELS.add(ny4eleChannel);
        DEFAULT_CHANNELS.add(ny3eleChannel);
    }

    @Resource(name = "channelCacheServiceImpl")
    private ChannelCacheService channelCacheService;

    @Override
    public boolean realAuth(UserRealAuthDto realAuthDto) {
        List<ChannelDto> channels = channelCacheService.getChannelsByType(EChannelType.REAL_AUTH);

        if(channels == null || channels.size() == 0){
            channels = DEFAULT_CHANNELS;
        }

        for(ChannelDto channelDto: channels) {
            if(channelDto.getChnlStatus() == EChannelStatus.DISABLED)
                continue;
            UserRealAuthService realAuthService = ApplicationContextUtil.getBean(channelDto.getBeanName(), UserRealAuthService.class);
            try{
                Thread.sleep(channelDto.getChnlDelay() * 1000L);
            }catch (InterruptedException ie){
                // Ignore
            }
            if(realAuthService.realAuth(realAuthDto)){
                return true;
            }
        }
        
        return false;
    }

    @Override
    public RealAuthResponseDto realAuthWithResponse(UserRealAuthDto realAuthDto) {
        UserRealAuthService realAuthService = ApplicationContextUtil.getBean("NyUserRealAuthWith4ElsServiceImpl", UserRealAuthService.class);

        if(realAuthService == null){
            throw new UnsupportedOperationException();
        }

        return realAuthService.realAuthWithResponse(realAuthDto);
    }
}
