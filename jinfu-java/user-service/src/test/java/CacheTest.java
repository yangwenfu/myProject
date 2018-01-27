/**
 * Created by Admin on 2016/8/10.
 */

import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xyl on 2016/7/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class CacheTest {

    @Autowired
    private UserService userService;


    @Test
    public void test() {
        UserInfoDto passwordDto = new UserInfoDto();
        passwordDto.setMobile("33635821");
        passwordDto.setLoginPassword("23213");
        userService.saveUser(passwordDto);
    }


}
