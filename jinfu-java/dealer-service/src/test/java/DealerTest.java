import com.xinyunlian.jinfu.common.util.ApplicationContextUtil;
import com.xinyunlian.jinfu.dealer.dto.DealerDto;
import com.xinyunlian.jinfu.dealer.dto.DealerExtraDto;
import com.xinyunlian.jinfu.dealer.dto.DealerSearchDto;
import com.xinyunlian.jinfu.dealer.enums.EDealerStatus;
import com.xinyunlian.jinfu.dealer.enums.EDealerType;
import com.xinyunlian.jinfu.dealer.service.DealerService;
import com.xinyunlian.jinfu.dealer.service.DealerUserOrderService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.service.DealerUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by menglei on 2016年08月26日.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class DealerTest {

    @Test
    public void dealerPageTest() {
        DealerService dealerService = ApplicationContextUtil.getBean("dealerServiceImpl", DealerService.class);
        DealerSearchDto dealerSearchDto = new DealerSearchDto();
        DealerSearchDto dealer = dealerService.getDealerPage(dealerSearchDto);
        System.out.println(dealer.getList().size());
    }

    @Test
    public void updateExpireOrderTest() {
        DealerUserOrderService dealerUserOrderService = ApplicationContextUtil.getBean("dealerUserOrderServiceImpl", DealerUserOrderService.class);
        dealerUserOrderService.updateExpireOrder();
    }

    @Test
    public void createDealerTest() {
        DealerService dealerService = ApplicationContextUtil.getBean("dealerServiceImpl", DealerService.class);
        DealerDto dealerDto = new DealerDto();
//        dealerDto.setDealerId("16082900000007");
        dealerDto.setDealerName("test1");
        dealerDto.setIndustryId("test");
        dealerDto.setLevelId(1l);
        dealerDto.setDistrictId("test");
        dealerDto.setProvinceId("test");
        dealerDto.setCityId("test");
        dealerDto.setAreaId("test");
        dealerDto.setStreetId("test");
        dealerDto.setProvince("test");
        dealerDto.setCity("test");
        dealerDto.setArea("test");
        dealerDto.setStreet("test");
        dealerDto.setAddress("test");
        dealerDto.setType(EDealerType.SUBSIDIARY);
        dealerDto.setBeginTime("2016-08-29 00:00:00");
        dealerDto.setEndTime("2016-08-29 00:00:00");
        dealerDto.setStatus(EDealerStatus.NORMAL);

        DealerExtraDto dealerExtraDto = new DealerExtraDto();
        dealerExtraDto.setBizLicence("test");
        dealerExtraDto.setBizLicencePic("test");
        dealerExtraDto.setContact("test");
        dealerExtraDto.setPhone("test");
        dealerExtraDto.setIdCardNo("test");
        dealerExtraDto.setIdCardNoPic1("test");
        dealerExtraDto.setIdCardNoPic2("test");
        dealerExtraDto.setFinanContact("test");
        dealerExtraDto.setFinanPhone("test");
        dealerExtraDto.setBankName("test");
        dealerExtraDto.setBankCode("test");
        dealerExtraDto.setBankCardName("test");
        dealerExtraDto.setBankCardNo("test");

        dealerDto.setDealerExtraDto(dealerExtraDto);

        dealerService.createDealer(dealerDto, null, null);
    }

    @Test
    public void updateDealerTest() {
        DealerService dealerService = ApplicationContextUtil.getBean("dealerServiceImpl", DealerService.class);
        DealerDto dealerDto = new DealerDto();
        dealerDto.setDealerId("16082900000006");
        dealerDto.setDealerName("test1");
        dealerDto.setIndustryId("test");
        dealerDto.setLevelId(1l);
        dealerDto.setDistrictId("test");
        dealerDto.setProvinceId("test");
        dealerDto.setCityId("test");
        dealerDto.setAreaId("test");
        dealerDto.setStreetId("test");
        dealerDto.setProvince("test");
        dealerDto.setCity("test");
        dealerDto.setArea("test");
        dealerDto.setStreet("test");
        dealerDto.setAddress("test");
        dealerDto.setType(EDealerType.SUBSIDIARY);
        dealerDto.setBeginTime("2016-08-29 00:00:00");
        dealerDto.setEndTime("2016-08-29 00:00:00");
        dealerDto.setStatus(EDealerStatus.NORMAL);

        DealerExtraDto dealerExtraDto = new DealerExtraDto();
        dealerExtraDto.setBizLicence("test");
        dealerExtraDto.setBizLicencePic("test");
        dealerExtraDto.setContact("test");
        dealerExtraDto.setPhone("test");
        dealerExtraDto.setIdCardNo("test");
        dealerExtraDto.setIdCardNoPic1("test");
        dealerExtraDto.setIdCardNoPic2("test");
        dealerExtraDto.setFinanContact("test");
        dealerExtraDto.setFinanPhone("test");
        dealerExtraDto.setBankName("test");
        dealerExtraDto.setBankCode("test");
        dealerExtraDto.setBankCardName("test");
        dealerExtraDto.setBankCardNo("test");

        dealerDto.setDealerExtraDto(dealerExtraDto);

        dealerService.createDealer(dealerDto, null, null);
    }

    @Test
    public void idAuthDealerTest() {
        DealerUserService dealerUserService = ApplicationContextUtil.getBean("DealerUserServiceImpl", DealerUserService.class);
        DealerUserDto dealerUserDto = new DealerUserDto();
        dealerUserDto.setUserId("UDC0000000817");
        dealerUserService.idAuth(dealerUserDto);
    }

}
