import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.*;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.xinyunlian.jinfu.spiderproxy.service.ProxyRefreshService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by bright on 2017/1/8.
 */
@RunWith(SpringJUnit4ClassRunner.class) // 整合
@ContextConfiguration(locations = "classpath:jinfu.spring.xml") // 加载配置
public class ProxyRefreshTest {
    @Autowired
    private ProxyRefreshService proxyRefreshService;

    @Test
    public void testRefresh(){
        System.out.println(proxyRefreshService.getNewProxyIp());
    }
}
