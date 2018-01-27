import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.user.dto.MgtPermissionDto;
import com.xinyunlian.jinfu.user.service.MgtAuthorityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by dongfangchao on 2016/12/8/0008.
 */
@RunWith(SpringJUnit4ClassRunner.class) // 整合
@ContextConfiguration(locations="classpath:jinfu.spring.xml") // 加载配置
public class MgtAuthorityServiceTest {

    @Autowired
    private MgtAuthorityService mgtAuthorityService;

    @Test
    public void getPermTree(){
        MgtPermissionDto permissionDto = mgtAuthorityService.getPermTree();
        System.out.println(JsonUtil.toJson(permissionDto));
    }


}
