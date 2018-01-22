import com.xinyunlian.jinfu.channel.dto.ChannelDto;
import com.xinyunlian.jinfu.channel.enums.EChannelStatus;
import com.xinyunlian.jinfu.channel.enums.EChannelType;
import com.xinyunlian.jinfu.channel.service.ChannelCacheService;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bright on 2016/11/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class ChannelCacheTest {

    @Autowired
    private ChannelCacheService channelCacheService;

    @Test
    public void testPutAll(){
        ChannelDto cachedChannelDto = new ChannelDto();
        cachedChannelDto.setId(0);
        cachedChannelDto.setBeanName("A");
        cachedChannelDto.setChnlDelay(10);
        cachedChannelDto.setChnlName("A");
        cachedChannelDto.setChnlPriority(1);
        cachedChannelDto.setChnlStatus(EChannelStatus.ENABLED);
        cachedChannelDto.setChnlType(EChannelType.REAL_AUTH);
        List<ChannelDto> dtos = new ArrayList<>();
        dtos.add(cachedChannelDto);
        channelCacheService.putAll(EChannelType.REAL_AUTH, dtos);
    }

    @Test
    public void testGet(){
        long curMils = System.currentTimeMillis();
        List<ChannelDto> dtos = channelCacheService.getChannelsByType(EChannelType.REAL_AUTH);
        for(ChannelDto dto: dtos){
            System.out.println(JsonUtil.toJson(dto));
        }
    }

    @Test
    public void testAdd(){
        ChannelDto cachedChannelDto = new ChannelDto();
        cachedChannelDto.setId(1);
        cachedChannelDto.setBeanName("B");
        cachedChannelDto.setChnlDelay(10);
        cachedChannelDto.setChnlName("B");
        cachedChannelDto.setChnlPriority(2);
        cachedChannelDto.setChnlStatus(EChannelStatus.ENABLED);
        cachedChannelDto.setChnlType(EChannelType.REAL_AUTH);
        channelCacheService.addChannel(cachedChannelDto);
    }


    @Test
    public void testGet2(){
        long curMils = System.currentTimeMillis();
        List<ChannelDto> dtos = channelCacheService.getChannelsByType(EChannelType.REAL_AUTH);
        for(ChannelDto dto: dtos){
            System.out.println(JsonUtil.toJson(dto));
        }
    }

    @Test
    public void testDelete(){
        ChannelDto cachedChannelDto = new ChannelDto();
        cachedChannelDto.setId(0);
        cachedChannelDto.setBeanName("A");
        cachedChannelDto.setChnlDelay(10);
        cachedChannelDto.setChnlName("A");
        cachedChannelDto.setChnlPriority(1);
        cachedChannelDto.setChnlStatus(EChannelStatus.ENABLED);
        cachedChannelDto.setChnlType(EChannelType.REAL_AUTH);
        channelCacheService.deleteChannel(cachedChannelDto);
    }


    @Test
    public void testGet3(){
        long curMils = System.currentTimeMillis();
        List<ChannelDto> dtos = channelCacheService.getChannelsByType(EChannelType.REAL_AUTH);
        for(ChannelDto dto: dtos){
            System.out.println(JsonUtil.toJson(dto));
        }
    }
}
