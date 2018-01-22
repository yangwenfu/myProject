package jinfu.share.service;

import com.xinyunlian.jinfu.share.dto.RecommendDto;
import com.xinyunlian.jinfu.share.service.RecommendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:standard-code-dao-test.xml"})
public class RecommendServiceImplTest {
    @Autowired
    private RecommendService recommendService;

    /**
     * Method: add(RecommendDto recommendDto)
     */
    @Test
    public void testAdd() throws Exception {
        RecommendDto recommendDto = new RecommendDto();
        recommendDto.setUserId("UC1001");
        recommendDto.setRefereeUserId("UC1002");
        recommendService.add(recommendDto);
    }

    /**
     * Method: giveRefereeCoupon(String userId)
     */
    @Test
    public void testGiveRefereeCoupon() throws Exception {
//TODO: Test goes here... 
    }


} 
