import com.xinyunlian.jinfu.cms.dto.NoticeInfDto;
import com.xinyunlian.jinfu.cms.enums.ENoticePlatform;
import com.xinyunlian.jinfu.cms.service.NoticeInfService;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by dongfangchao on 2017/5/24/0024.
 */
@RunWith(SpringJUnit4ClassRunner.class) // 整合
@ContextConfiguration(locations="classpath:standard-code-dao-test.xml") // 加载配置
public class NoticeServiceTest {

    @Autowired
    private NoticeInfService noticeInfService;

    @Test
    public void getNoticeByPlatform(){
        List<NoticeInfDto> list = noticeInfService.getNoticeByPlatform(ENoticePlatform.JINFU_MALL);
        System.out.println(JsonUtil.toJson(list));
    }

}
