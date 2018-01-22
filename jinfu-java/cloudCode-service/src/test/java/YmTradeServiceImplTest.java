import com.xinyunlian.jinfu.trade.dto.TradeTotal;
import com.xinyunlian.jinfu.trade.dto.YmTradeSearchDto;
import com.xinyunlian.jinfu.trade.service.YmTradeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:jinfu.spring.xml"})
@TransactionConfiguration(defaultRollback = false)
public class YmTradeServiceImplTest {
    @Autowired
    private YmTradeService ymTradeService;

    /**
     * Method: sumTrade(YmTradeSearchDto searchDto)
     */
    @Test
    public void testSumTrade() {
        YmTradeSearchDto searchDto = new YmTradeSearchDto();
        //searchDto.setStatus(ETradeStatus.SUCCESS);
        TradeTotal tradeTotal = ymTradeService.getTradeTotal(searchDto);
        System.out.println(tradeTotal.getTransAmtSum());
    }

    /**
     * Method: getTradePage(YmTradeSearchDto searchDto)
     */
    @Test
    public void testGetTradePage() {
    }


} 
