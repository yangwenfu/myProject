import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.trans.dto.TransHistoryDto;
import com.xinyunlian.jinfu.trans.enums.EInsTransType;
import com.xinyunlian.jinfu.trans.service.TransHistoryInnerHistoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:standard-code-dao-test.xml"})
public class TransHistoryInnerTest {

    @Autowired
    private TransHistoryInnerHistoryService transHistoryInnerHistoryService;

    @Test
    public void addTransHistory(){
        TransHistoryDto transHistoryDto = new TransHistoryDto();
        transHistoryDto.setTransType(EInsTransType.PINGAN_QUOTE_PRICE);
        TransHistoryDto dbDto = transHistoryInnerHistoryService.addTransHistory(transHistoryDto);
        System.out.println(JsonUtil.toJson(dbDto));
    }

    @Test
    public void updateTransHistory(){
        TransHistoryDto transHistoryDto = new TransHistoryDto();
        transHistoryDto.setTransSerialNo("INSTRANS2061258784117001");
        transHistoryDto.setTransType(EInsTransType.PINGAN_QUOTE_PRICE);
        transHistoryDto.setTransResponse("{response:\"ok\"}");
        transHistoryDto.setTransRequest("{request:\"123\"}");
        transHistoryDto.setRequestUrl("http://localhost");
        transHistoryInnerHistoryService.updateTransHistory(transHistoryDto);
    }

}
