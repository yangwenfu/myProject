package jinfu.promo.service;

import com.xinyunlian.jinfu.promo.dto.UserCouponDto;
import com.xinyunlian.jinfu.promo.dto.UserDto;
import com.xinyunlian.jinfu.promo.enums.EUserCouponStatus;
import com.xinyunlian.jinfu.promo.service.UserCouponService;
import com.xinyunlian.jinfu.rule.dto.RuleCouponDto;
import com.xinyunlian.jinfu.rule.enums.ECouponType;
import com.xinyunlian.jinfu.rule.service.RuleCouponService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jinfu.spring.xml"})
@TransactionConfiguration(defaultRollback = false)
public class UserCouponServiceImplTest {
    @Autowired
    private RuleCouponService ruleCouponService;
    @Autowired
    private UserCouponService userCouponService;

    /**
     * Method: activeCoupon(String userId, String couponCode)
     */
    @Test
    public void testActiveCoupon() throws Exception {
        userCouponService.activeCoupon("UC0001","abc123","loan,");
    }

    /**
     * Method: findByUserId(String userId, EUserCouponStatus status)
     */
    @Test
    public void testFindByUserId() throws Exception {
        List<UserCouponDto> list = userCouponService.findByUserId("UC0001", EUserCouponStatus.UNUSED);
        System.out.println(list.size());
    }

    /**
     * Method: getUsable(String userId, String prodId)
     */
    @Test
    public void testGetUsable() throws Exception {
        //List<UserCouponDto> list = userCouponService.getUsable("UC0001", 3);
       // System.out.println(list.size());
    }

    /**
     * Method: useCoupon(UserDto user, Long couponId)
     */
    @Test
    public void testUseCoupon() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId("UC0001");
        userDto.setIdCardNo("330204198605231039");
        userDto.setMobile("13586847425");
        userDto.setUserName("韩志斌");
        userCouponService.useCoupon(userDto,11L);
    }

    @Test
    public void testSaveCoupon() throws Exception {
        RuleCouponDto ruleCouponDto = new RuleCouponDto();
        ruleCouponDto.setPromoId(21L);
        ruleCouponDto.setValidBeginDate(new Date());
        ruleCouponDto.setCouponType(ECouponType.INTEREST);
        ruleCouponDto.setCouponCode("ABCD");
        ruleCouponDto.setCouponName("优惠券");
        ruleCouponDto.setAdded(true);
        ruleCouponDto.setPrice(BigDecimal.valueOf(100));
        ruleCouponDto.setValidDays(4);
        ruleCouponDto.setValidEndDate(new Date());
        ruleCouponService.save(ruleCouponDto);
    }

    @Test
    public void testFindByPromoId() throws Exception {
        ruleCouponService.findByPromoId(21L);
    }

    @Test
    public void testDeletePromoId() throws Exception {
        ruleCouponService.delete(40L);
    }

    @Test
    public void testDeleteByPromoId() throws Exception {
        ruleCouponService.deleteByPromoId(21L);
    }

    @Test
    public void testOverdueJob() throws Exception {
        userCouponService.overdueJob();
    }


} 
