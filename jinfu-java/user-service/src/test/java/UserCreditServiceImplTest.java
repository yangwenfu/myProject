import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.risk.dto.req.UserCreditInfoReqDto;
import com.xinyunlian.jinfu.risk.dto.resp.UserCreditInfoDto;
import com.xinyunlian.jinfu.risk.service.UserCreditService;
import com.xinyunlian.jinfu.risk.service.UserCreditServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.Date;

/**
 * Created by bright on 2016/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:jinfu.spring.xml"})
@TransactionConfiguration(defaultRollback = false)
public class UserCreditServiceImplTest {
    @Autowired
    private UserCreditService userCreditService;

    @Test
    public void testGetUserCreditInfo(){
        String userId = "AAA";
        UserCreditInfoDto userCreditInfoDto = userCreditService.getUserCreditInfo(userId);
        Assert.assertEquals(userId, userCreditInfoDto.getUserId());
        System.out.println(JsonUtil.toJson(userCreditInfoDto));
    }

    @Test
    public void testRetieveUserCreditInfo(){
        UserCreditInfoReqDto reqDto = new UserCreditInfoReqDto();
        reqDto.setUserId("UC0000000401");
        reqDto.setQueryDate(DateHelper.formatDate(new Date()));
        reqDto.setName("样本");
        reqDto.setPhone("18655556180");
        reqDto.setIdCard("340521198405315611");
        UserCreditInfoDto userCreditInfoDto = userCreditService.retrieveUserCreditInfo(reqDto);
        System.out.println(JsonUtil.toJson(userCreditInfoDto));
    }
}
