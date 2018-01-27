import com.xinyunlian.jinfu.channel.dto.ChannelUserAreaDto;
import com.xinyunlian.jinfu.channel.dto.ChannelUserRelationDto;
import com.xinyunlian.jinfu.channel.dto.ChannelUserSearchDto;
import com.xinyunlian.jinfu.channel.service.ChannelUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * ChannelUserServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre> 8, 2017</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class) // 整合
@ContextConfiguration(locations = "classpath:standard-code-dao-test.xml") // 加载配置
public class ChannelUserServiceImplTest {
    @Autowired
    private ChannelUserService channelUserService;

    /**
     * Method: saveUserRelation(List<ChannelUserRelationDto> list)
     */
    @Test
    public void testSaveUserRelation() throws Exception {
        List<ChannelUserRelationDto> list = new ArrayList<>();
        ChannelUserRelationDto channelUserRelationDto = new ChannelUserRelationDto();
        channelUserRelationDto.setParentUserId("1");
        channelUserRelationDto.setUserId("2");
        list.add(channelUserRelationDto);

        channelUserRelationDto = new ChannelUserRelationDto();
        channelUserRelationDto.setParentUserId("1");
        channelUserRelationDto.setUserId("3");
        list.add(channelUserRelationDto);

        channelUserService.saveUserRelation(list);
    }

    /**
     * Method: listUserRelation(String parentUserId)
     */
    @Test
    public void testListUserRelation() throws Exception {
        channelUserService.listUserRelation("1");
    }

    /**
     * Method: saveUserArea(List<ChannelUserAreaDto> list)
     */
    @Test
    public void testSaveUserArea() throws Exception {
        List<ChannelUserAreaDto> channelUserAreaDtos = new ArrayList<>();
        ChannelUserAreaDto channelUserAreaDto = new ChannelUserAreaDto();
        channelUserAreaDto.setUserId("1");
        channelUserAreaDto.setAreaId(1L);
        channelUserAreaDto.setAreaTreePath("1,");
        channelUserAreaDtos.add(channelUserAreaDto);
        channelUserService.saveUserArea(channelUserAreaDtos);
    }

    /**
     * Method: listUserArea(String userId)
     */
    @Test
    public void testListUserArea() throws Exception {
        channelUserService.listUserArea("1");
    }

    /**
     * Method: getChannelUserPage(ChannelUserSearchDto searchDto)
     */
    @Test
    public void testGetChannelUserPage() throws Exception {
        ChannelUserSearchDto channelUserSearchDto = new ChannelUserSearchDto();
        channelUserSearchDto.setDuty("REGIONAL_MGT");
        channelUserSearchDto.setParentUserId("1");
        channelUserService.getChannelUserPage(channelUserSearchDto);
    }


} 
