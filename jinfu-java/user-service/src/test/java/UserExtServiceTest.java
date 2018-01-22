
import com.xinyunlian.jinfu.user.dto.ext.UserExtAllDto;
import com.xinyunlian.jinfu.user.dto.ext.UserExtIdDto;
import com.xinyunlian.jinfu.user.service.UserExtService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class UserExtServiceTest {

    @Autowired
    private UserExtService userExtService;

    @Test
    public void test1(){
        UserExtAllDto userExtAllDto = new UserExtAllDto();
        userExtAllDto.setUserId("UC0000000908");

        userExtAllDto = (UserExtAllDto) userExtService.getUserExtPart(userExtAllDto);

        System.exit(1);
    }


}
