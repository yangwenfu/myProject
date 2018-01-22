import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.config.dto.ConfigDto;
import com.xinyunlian.jinfu.config.enums.ECategory;
import com.xinyunlian.jinfu.config.service.ConfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by bright on 2017/1/6.
 */
@RunWith(SpringJUnit4ClassRunner.class) // 整合
@ContextConfiguration(locations = "classpath:jinfu.spring.xml") // 加载配置
public class ConfigServiceTest {

    @Autowired
    private ConfigService configService;

    @Test
    public void testDelete(){
        configService.delete(2L);
    }

    @Test
    public void testSave(){
        ConfigDto config = new ConfigDto();
        config.setId(1L);
        config.setCategory(ECategory.SPIDER);
        config.setKey("PROXY_IP");
        config.setValue("121.196.223.199");
        config.setCached(Boolean.TRUE);
        configService.save(config);
    }

    @Test
    public void testGetByCategory(){
        List<ConfigDto> configDtos = configService.getByCategory(ECategory.SPIDER);
        System.out.println(JsonUtil.toJson(configDtos));
    }

    @Test
    public void testGetByCategoryAndKey(){
        ConfigDto configDto = configService.getByCategoryAndKey(ECategory.SPIDER, "PROXY_IP");
        System.out.println(JsonUtil.toJson(configDto));
    }
}
