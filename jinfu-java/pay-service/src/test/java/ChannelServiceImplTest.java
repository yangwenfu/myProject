import com.xinyunlian.jinfu.channel.dto.ChannelDto;
import com.xinyunlian.jinfu.channel.enums.EChannelStatus;
import com.xinyunlian.jinfu.channel.enums.EChannelType;
import com.xinyunlian.jinfu.channel.service.ChannelService;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by bright on 2016/11/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class ChannelServiceImplTest {
    @Autowired
    private ChannelService channelService;

    @Test
    public void addData(){
        ChannelDto xyl4eleChannel = new ChannelDto();
        xyl4eleChannel.setChnlPriority(0);
        xyl4eleChannel.setBeanName("XylUserRealAuthWith4ElsServiceImpl");
        xyl4eleChannel.setChnlDelay(0);
        xyl4eleChannel.setId(1);
        xyl4eleChannel.setChnlName("捷汇通四要素验证");
        xyl4eleChannel.setChnlType(EChannelType.REAL_AUTH);
        xyl4eleChannel.setChnlStatus(EChannelStatus.ENABLED);
        ChannelDto xyl3eleChannel = new ChannelDto();
        xyl3eleChannel.setChnlPriority(1);
        xyl3eleChannel.setBeanName("XylUserRealAuthWith3ElsServiceImpl");
        xyl3eleChannel.setChnlDelay(0);
        xyl3eleChannel.setId(2);
        xyl3eleChannel.setChnlName("捷汇通三要素验证");
        xyl3eleChannel.setChnlType(EChannelType.REAL_AUTH);
        xyl3eleChannel.setChnlStatus(EChannelStatus.ENABLED);
        ChannelDto ny4eleChannel = new ChannelDto();
        ny4eleChannel.setChnlPriority(2);
        ny4eleChannel.setBeanName("NyUserRealAuthWith4ElsServiceImpl");
        ny4eleChannel.setChnlDelay(0);
        ny4eleChannel.setId(3);
        ny4eleChannel.setChnlName("楠云四要素验证");
        ny4eleChannel.setChnlType(EChannelType.REAL_AUTH);
        ny4eleChannel.setChnlStatus(EChannelStatus.ENABLED);
        ChannelDto ny3eleChannel = new ChannelDto();
        ny3eleChannel.setChnlPriority(3);
        ny3eleChannel.setBeanName("NyUserRealAuthWith3ElsServiceImpl");
        ny3eleChannel.setChnlDelay(3);
        ny3eleChannel.setId(4);
        ny3eleChannel.setChnlName("楠云三要素验证");
        ny3eleChannel.setChnlType(EChannelType.REAL_AUTH);
        ny3eleChannel.setChnlStatus(EChannelStatus.ENABLED);

        channelService.saveChannel(xyl4eleChannel);
        channelService.saveChannel(xyl3eleChannel);
        channelService.saveChannel(ny4eleChannel);
        channelService.saveChannel(ny3eleChannel);
    }

    @Test
    public void testGet(){
        List<ChannelDto> channels = channelService.getChannelsByType(EChannelType.REAL_AUTH);
        channels.forEach(channelDto -> {
            System.out.println(JsonUtil.toJson(channelDto));
        });
    }

    @Test
    public void testPrioritizeChannel(){
        List<ChannelDto> channels = channelService.getChannelsByType(EChannelType.REAL_AUTH);
        channels.forEach(channelDto -> {
            if(channelDto.getChnlName().equals("ny4")){
                channelService.prioritizeChannel(2);
            }
        });
        channels = channelService.getChannelsByType(EChannelType.REAL_AUTH);
        channels.forEach(channelDto -> {
            System.out.println(JsonUtil.toJson(channelDto));
        });
    }


    @Test
    public void testDeprioritizeChannel(){
        List<ChannelDto> channels = channelService.getChannelsByType(EChannelType.REAL_AUTH);
        channels.forEach(channelDto -> {
            if(channelDto.getChnlName().equals("ny4")){
                channelService.deprioritizeChannel(2);
            }
        });
        channels = channelService.getChannelsByType(EChannelType.REAL_AUTH);
        channels.forEach(channelDto -> {
            System.out.println(JsonUtil.toJson(channelDto));
        });
    }

    @Test
    public void testEnable(){
        List<ChannelDto> channels = channelService.getChannelsByType(EChannelType.REAL_AUTH);
        channels.forEach(channelDto -> {
            if(channelDto.getChnlName().equals("ny3")){
                channelService.enableChannel(3);
            }
        });
        channels = channelService.getChannelsByType(EChannelType.REAL_AUTH);
        channels.forEach(channelDto -> {
            System.out.println(JsonUtil.toJson(channelDto));
        });
    }


    @Test
    public void testDisable(){
        List<ChannelDto> channels = channelService.getChannelsByType(EChannelType.REAL_AUTH);
        channels.forEach(channelDto -> {
            if(channelDto.getChnlName().equals("ny4")){
                channelService.disableChannel(2);
            }
        });
        channels = channelService.getChannelsByType(EChannelType.REAL_AUTH);
        channels.forEach(channelDto -> {
            System.out.println(JsonUtil.toJson(channelDto));
        });
    }
}
