import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.report.dealer.dto.DealerOrderSearchDto;
import com.xinyunlian.jinfu.report.dealer.service.DealerReportService;
import com.xinyunlian.jinfu.report.loan.dto.LoanDtlSearchDto;
import com.xinyunlian.jinfu.report.loan.dto.RepayDtlSearchDto;
import com.xinyunlian.jinfu.report.loan.dto.RepaySchdSearchDto;
import com.xinyunlian.jinfu.report.loan.enums.EReportType;
import com.xinyunlian.jinfu.report.loan.service.LoanReportService;
import com.xinyunlian.jinfu.report.virtual.dto.VirtualTboSearchDto;
import com.xinyunlian.jinfu.report.virtual.service.VirtualTboReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by dell on 2016/11/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class ReportTest {
    @Autowired @Qualifier("loanReportService")
    LoanReportService reportService;

    @Autowired @Qualifier("dealerReportService")
    DealerReportService dealerReportService;

    @Autowired @Qualifier("virtualTboReportService")
    VirtualTboReportService virtualTboReportService;

    @Test
    public void LoanDtlReport(){
        LoanDtlSearchDto searchDto = new LoanDtlSearchDto();
        searchDto = reportService.getLoanDtlReport(searchDto, EReportType.EXCEL);
        System.out.println(searchDto.getList().size());
    }

    @Test
    public void RepaySchdReport(){
        RepaySchdSearchDto searchDto = new RepaySchdSearchDto();
        searchDto = reportService.getRepaySchdReport(searchDto, EReportType.EXCEL);
        System.out.println(searchDto.getList().size());
    }

    @Test
    public void RepayDtlReport(){
        RepayDtlSearchDto searchDto = new RepayDtlSearchDto();
        searchDto = reportService.getRepayDtlReport(searchDto, EReportType.EXCEL);
        System.out.println(searchDto.getList().size());
    }

    @Test
    public void getPage(){
        VirtualTboSearchDto virtualTboSearchDto = new VirtualTboSearchDto();
        virtualTboSearchDto.setStreet("泾渭街道办事处");
        virtualTboSearchDto = virtualTboReportService.getPage(virtualTboSearchDto);
        System.out.println(virtualTboSearchDto.getList().size());
    }

    @Test
    public void DealerOrderReport(){
        DealerOrderSearchDto searchDto = new DealerOrderSearchDto();
        searchDto.setProdId("L01002");
        List data = dealerReportService.getOrderReport(searchDto);
        System.out.println(JsonUtil.toJson(data));
    }

    @Test
    public void DealerLogReport(){
        dealerReportService.getLogReport(null);
    }

    @Test
    public void DealerReport(){
        dealerReportService.getDealerReport(null);
    }

    @Test
    public void InsuranceReport(){
        dealerReportService.getInsuranceReport(null);
    }

    @Test
    public void UserReport(){
        dealerReportService.getUserReport(null);
    }

    @Test
    public void DealerUserReport(){
        dealerReportService.getDealerUserReport(null);
    }
}
