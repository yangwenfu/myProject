import com.xinyunlian.jinfu.common.util.ApplicationContextUtil;
import com.xinyunlian.jinfu.dto.UserRealAuthDto;
import com.xinyunlian.jinfu.service.UserRealAuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class PayTest {

    @Test
    public void RealAuth() {
        UserRealAuthService dealerService = ApplicationContextUtil.getBean("userRealAuthServiceImpl", UserRealAuthService.class);
        UserRealAuthDto authDto = new UserRealAuthDto();
        authDto.setIdCardNo("341126197709218366");
        authDto.setBankCardNo("6216261000000000018");
        authDto.setName("全渠道");
        authDto.setPhone("13552535506");
        dealerService.realAuth(authDto);

    }


}
