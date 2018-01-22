package jinfu.rule.service;

import com.xinyunlian.jinfu.rule.dto.RuleFullOffDto;
import com.xinyunlian.jinfu.rule.dto.RuleFullOffGradDto;
import com.xinyunlian.jinfu.rule.enums.EOffType;
import com.xinyunlian.jinfu.rule.service.RuleFullOffService;
import org.junit.After;
import org.junit.Before;
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
* RuleFullOffServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>ʮһ�� 22, 2016</pre> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:jinfu.spring.xml"})
@TransactionConfiguration(defaultRollback = false)
public class RuleFullOffServiceImplTest {
    @Autowired
    private RuleFullOffService ruleFullOffService;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: save(RuleFullOffDto ruleFullOffDto) 
* 
*/ 
@Test
public void testSave() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: findByPromoId(Long promoId) 
* 
*/ 
@Test
public void testFindByPromoId() throws Exception {
    RuleFullOffDto ruleFullOffDto = new RuleFullOffDto();
    ruleFullOffDto.setPromoId(1l);
    ruleFullOffDto.setTerm(1);
    ruleFullOffDto.setCap(true);
    ruleFullOffDto.setOffId(1l);
    ruleFullOffDto.setOffType(EOffType.MONEY);
    RuleFullOffGradDto ruleFullOffGradDto = new RuleFullOffGradDto();
    ruleFullOffGradDto.setAmount(new BigDecimal(2));
    ruleFullOffGradDto.setDiscount(new BigDecimal(3));
    ruleFullOffGradDto.setOffId(1l);
    ruleFullOffGradDto.setId(19l);
    List<RuleFullOffGradDto> ruleFullOffGradDtos = new ArrayList<>();
    ruleFullOffGradDtos.add(ruleFullOffGradDto);
    ruleFullOffGradDtos.add(ruleFullOffGradDto);
    ruleFullOffDto.setRuleFullOffGradDtos(ruleFullOffGradDtos);
    ruleFullOffService.save(ruleFullOffDto);
    ruleFullOffService.findByPromoId(1l);

} 


} 
