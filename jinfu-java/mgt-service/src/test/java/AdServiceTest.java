import com.xinyunlian.jinfu.ad.dto.AdFrontDto;
import com.xinyunlian.jinfu.ad.service.AdService;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by dongfangchao on 2017/1/9/0009.
 */
@RunWith(SpringJUnit4ClassRunner.class) // 整合
@ContextConfiguration(locations="classpath:jinfu.spring.xml") // 加载配置
public class AdServiceTest {

    @Autowired
    private AdService adService;

    @Test
    public void getAdFront(){
        List<AdFrontDto> list = adService.getAdFront(12l, 1080, 0);
        System.out.println(JsonUtil.toJson(list));
    }


}
