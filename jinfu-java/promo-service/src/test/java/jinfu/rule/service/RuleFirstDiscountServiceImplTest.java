package jinfu.rule.service;

import com.xinyunlian.jinfu.rule.dto.RuleFirstDiscountDto;
import com.xinyunlian.jinfu.rule.enums.EDiscountType;
import com.xinyunlian.jinfu.rule.service.RuleFirstDiscountService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.math.BigDecimal;

/** 
* RuleFirstDiscountServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>ʮһ�� 22, 2016</pre> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:jinfu.spring.xml"})
@TransactionConfiguration(defaultRollback = false)
public class RuleFirstDiscountServiceImplTest {
    @Autowired
    private RuleFirstDiscountService ruleFirstDiscountService;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: findByPromoId(Long promoId) 
* 
*/ 
@Test
public void testFindByPromoId() throws Exception {
    RuleFirstDiscountDto ruleFirstDiscountDto = new RuleFirstDiscountDto();
    ruleFirstDiscountDto.setDiscount(new BigDecimal((0.8)));
    ruleFirstDiscountDto.setDiscountType(EDiscountType.AVE_CAP_PLUS_INTR);
    ruleFirstDiscountDto.setPromoId(1l);
    ruleFirstDiscountDto.setTerm(1);
    ruleFirstDiscountService.save(ruleFirstDiscountDto);
    RuleFirstDiscountDto ruleFirstDiscountDto1 = ruleFirstDiscountService.findByPromoId(1l);
    System.out.println(ruleFirstDiscountDto.getDiscount());
} 


} 
