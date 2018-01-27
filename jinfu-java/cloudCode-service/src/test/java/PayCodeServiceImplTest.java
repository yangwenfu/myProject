import com.alibaba.fastjson.JSONObject;
import com.xinyunlian.jinfu.payCode.dto.PayCodeBalanceLogSearchDto;
import com.xinyunlian.jinfu.payCode.dto.PayCodeDto;
import com.xinyunlian.jinfu.payCode.dto.PayCodeSearchDto;
import com.xinyunlian.jinfu.payCode.enums.PayCodeBalanceType;
import com.xinyunlian.jinfu.payCode.enums.PayCodeStatus;
import com.xinyunlian.jinfu.payCode.service.PayCodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.math.BigDecimal;

/**
 * Created by carrot on 2017/8/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/jinfu.spring-collect.xml"})
@TransactionConfiguration(defaultRollback = false)
public class PayCodeServiceImplTest {

    @Autowired
    private PayCodeService payCodeService;

    @Test
    public void save() throws Exception {
        PayCodeDto payCodeDto = new PayCodeDto();
        payCodeDto.setMobile("1501101");
        payCodeDto.setPayCodeNo("003");
        payCodeDto = payCodeService.save(payCodeDto);
        System.out.println(JSONObject.toJSON(payCodeDto));
    }

    @Test
    public void get(){
        System.out.println(JSONObject.toJSON(payCodeService.get("001")));
    }

    @Test
    public void balance() throws Exception {
        payCodeService.balance("001",new BigDecimal(10.25), PayCodeBalanceType.PAY,"11111111");
    }

    @Test
    public void updateStatus(){
        payCodeService.updateStatus("002",PayCodeStatus.DISABLE);
    }

    @Test
    public void list(){
        PayCodeSearchDto searchDto = new PayCodeSearchDto();
        searchDto.setPayCodeNo("00");
        searchDto = payCodeService.list(searchDto);
        System.out.println(JSONObject.toJSON(searchDto));
    }

    @Test
    public void logList(){
        PayCodeBalanceLogSearchDto searchDto = new PayCodeBalanceLogSearchDto();
        searchDto.setPayCodeNo("001");
        searchDto.setType(PayCodeBalanceType.PAY);
        searchDto = payCodeService.logList(searchDto);
        System.out.println(JSONObject.toJSON(searchDto));
    }
}
