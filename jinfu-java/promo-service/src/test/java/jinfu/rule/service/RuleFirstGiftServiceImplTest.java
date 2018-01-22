package jinfu.rule.service;

import com.xinyunlian.jinfu.rule.dto.RuleFirstGiftDto;
import com.xinyunlian.jinfu.rule.service.RuleFirstGiftService;
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
* RuleFirstGiftServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>ʮһ�� 22, 2016</pre> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:jinfu.spring.xml"})
@TransactionConfiguration(defaultRollback = false)
public class RuleFirstGiftServiceImplTest {
    @Autowired
    private RuleFirstGiftService ruleFirstGiftService;


/** 
* 
* Method: save(List<RuleFirstGiftDto> ruleFirstGiftDtos) 
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
    List<RuleFirstGiftDto> ruleFirstGiftDtos = new ArrayList<>();
    RuleFirstGiftDto ruleFirstGiftDto = new RuleFirstGiftDto();
    ruleFirstGiftDto.setPromoId(1l);
    ruleFirstGiftDto.setDescribe("describe1");
    ruleFirstGiftDto.setGiftName("name");
    ruleFirstGiftDto.setPerNum(1);
    ruleFirstGiftDto.setPrice(new BigDecimal(1));
    ruleFirstGiftDto.setTotal(1);
    ruleFirstGiftDto.setId(1l);
    ruleFirstGiftDtos.add(ruleFirstGiftDto);
    ruleFirstGiftService.save(ruleFirstGiftDtos);
    ruleFirstGiftService.findByPromoId(1l);
}


} 
