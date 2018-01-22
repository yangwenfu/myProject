package jinfu.promo.service;

import com.xinyunlian.jinfu.promo.dto.OrderDto;
import com.xinyunlian.jinfu.promo.dto.PromoRuleDto;
import com.xinyunlian.jinfu.promo.dto.StoreDto;
import com.xinyunlian.jinfu.promo.dto.UserDto;
import com.xinyunlian.jinfu.promo.service.PromoApplyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * PromoApplyServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十一月 23, 2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jinfu.spring.xml"})
@TransactionConfiguration(defaultRollback = false)
public class PromoApplyServiceImplTest {
    @Autowired
    private PromoApplyService promoApplyService;

    /**
     * Method: gain(UserDto user, StoreDto store, OrderDto orderDto, String prodId, String platform)
     */
    @Test
    public void testGain() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId("1");
        userDto.setIdCardNo("330204198605231039");
        userDto.setMobile("13586847425");
        userDto.setUserName("韩志斌");
        List<StoreDto> storeDtos = new ArrayList<>();
        StoreDto storeDto = new StoreDto();
        storeDto.setTobaccoCertificateNo("410401105547");
        storeDto.setProvinceId("1");
        storeDto.setCityId("2");
        storeDto.setAreaId("3341");
        storeDto.setStreetId("4");
        storeDtos.add(storeDto);
        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(new BigDecimal("45900"));
        orderDto.setOrderTotal(1);
        orderDto.setPlatform("loan");
        orderDto.setProdId("L01001");
        PromoRuleDto promoRuleDto = promoApplyService.gain(userDto,storeDtos,orderDto);
        promoRuleDto.getPromoId();

    }


    /**
     * Method: checkBlackList(Long promoId, UserDto user, StoreDto store)
     */
    @Test
    public void testCheckBlackList() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = PromoApplyServiceImpl.getClass().getMethod("checkBlackList", Long.class, UserDto.class, StoreDto.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: checkWhiteList(Long promoId, UserDto user, StoreDto store)
     */
    @Test
    public void testCheckWhiteList() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = PromoApplyServiceImpl.getClass().getMethod("checkWhiteList", Long.class, UserDto.class, StoreDto.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: checkArea(Long promoId, StoreDto store)
     */
    @Test
    public void testCheckArea() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = PromoApplyServiceImpl.getClass().getMethod("checkArea", Long.class, StoreDto.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
