import com.xinyunlian.jinfu.spider.dto.req.AuthLoginDto;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by carrot on 2017/8/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class RiskUserInfoServiceTest {

    @Autowired
    RiskUserInfoService riskUserInfoService;

    @Test
    public void authLogin() {
        AuthLoginDto loginDto = new AuthLoginDto();
        loginDto.setCityId(1204l);
        loginDto.setProvinceId(1105l);
        loginDto.setUsername("350623107426");
        loginDto.setPassword("a123456");
        System.out.println(riskUserInfoService.authLogin(loginDto));
    }

}
