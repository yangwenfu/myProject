import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistoryDto;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistorySearchDto;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistorySumDto;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistorySumSearchDto;
import com.xinyunlian.jinfu.finprofithistory.service.FinProfitHistoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * Created by dongfangchao on 2016/11/23.
 */

@RunWith(SpringJUnit4ClassRunner.class) // 整合
@ContextConfiguration(locations="classpath:standard-code-dao-test.xml") // 加载配置
public class FinProfitHistoryServiceTest {

    @Autowired
    private FinProfitHistoryService finProfitHistoryService;

    @Test
    public void getProfitHistory(){
        FinProfitHistorySearchDto searchDto = new FinProfitHistorySearchDto();
        searchDto.setUserId("UC0000002485");
        Calendar ytd = Calendar.getInstance();
        searchDto.setProfitDateFrom(DateHelper.getStartDate(ytd.getTime()));
        searchDto.setProfitDateTo(DateHelper.getEndDate(ytd.getTime()));

        List<FinProfitHistoryDto> list = finProfitHistoryService.getProfitHistory(searchDto);
        System.out.println(JsonUtil.toJson(list));
    }

    @Test
    public void getProfitHistorySum(){
        FinProfitHistorySumSearchDto searchDto2 = new FinProfitHistorySumSearchDto();
        searchDto2.setUserId("UC0000002485");
        Calendar ytd = Calendar.getInstance();
        searchDto2.setProfitDateFrom(DateHelper.getStartDate(ytd.getTime()));
        searchDto2.setProfitDateTo(DateHelper.getEndDate(ytd.getTime()));
        List<FinProfitHistorySumDto> ytdProfitList = finProfitHistoryService.getProfitHistorySum(searchDto2);
        BigDecimal ytdProfit = new BigDecimal("0");
        if (!CollectionUtils.isEmpty(ytdProfitList)){
            ytdProfit = ytdProfitList.get(0).getProfitAmt();
        }

        System.out.println(ytdProfit);
    }

}
