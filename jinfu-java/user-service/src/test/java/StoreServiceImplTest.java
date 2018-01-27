import com.xinyunlian.jinfu.center.dto.CenterStoreDto;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.dto.req.StoreSearchDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.enums.ESource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** 
* StoreServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 23, 2016</pre> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:standard-code-dao-test.xml"})
@TransactionConfiguration(defaultRollback = false)
public class StoreServiceImplTest {
    @Autowired
    private StoreService storeService;

/**
* 
* Method: createStore(StoreInfDto storeInfDto) 
* 
*/ 
@Test
public void testCreateStore() throws Exception {
    StoreInfDto storeInfDto = new StoreInfDto();
    storeInfDto.setUserId("UC0000000647");
    storeInfDto.setCityId("2");
    storeInfDto.setProvinceId("3");
    storeInfDto.setDistrictId("3");
    storeInfDto.setAreaId("44064");
    storeInfDto.setAddress("宁波市海曙区老外滩");
    storeInfDto.setArea("海曙区");
    storeInfDto.setBizLicence("1231");
    storeInfDto.setCity("宁波市");
    storeInfDto.setProvince("浙江省");
    storeInfDto.setStoreName("烟酒店");
    storeInfDto.setStreet("杨山路");
    storeInfDto.setTobaccoCertificateNo("2331423");
    storeInfDto.setBizEndDate(new Date());
    storeInfDto.setTobaccoEndDate(new Date());
    storeInfDto.setSource(ESource.REGISTER);
    storeInfDto.setIndustryMcc("5227");
    storeService.saveSupportAll(storeInfDto);
}

/** 
* 
* Method: deleteStore(String storeId) 
* 
*/ 
@Test
public void testDeleteStore() throws Exception {
    storeService.deleteStore(1L);
} 

/** 
* 
* Method: findByUserId(String userId) 
* 
*/
@Test
public void testFindByUserId() throws Exception { 
    storeService.findByUserId("16082300000004");
}

    @Test
    public void testFindByDistrictIds() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("14299");
        List<StoreInfDto> storeInfDtos = storeService.findStoreIdByDistrictIds(list);
    }

    @Test
    public void testGetStoreInfPage() throws Exception {
        StoreSearchDto userSearchDto = new StoreSearchDto();
       // userSearchDto.setMobile("13805844950");
       // userSearchDto.setProvince("浙江省");
       // userSearchDto.setUserName("测试人员");
        //userSearchDto.setArea("海曙区");
       // userSearchDto.setCity("宁波市");
       // userSearchDto.setDistrictIds("14315,14314");
        userSearchDto = storeService.getStorePage(userSearchDto);
        System.out.print(userSearchDto.getList().get(0).toString());
        System.out.print(userSearchDto.getList().get(0).getTotalPages());
    }

    @Test
    public void testSaveFromUserCenter() throws Exception {
        CenterStoreDto centerStoreDto = new CenterStoreDto();
        centerStoreDto.setStoreName("店铺");
        centerStoreDto.setProvince("省");
        centerStoreDto.setCity("市");
        centerStoreDto.setArea("区");
        centerStoreDto.setStreet("街道");
        centerStoreDto.setProvinceId(3L);
        centerStoreDto.setCityId(4L);
        centerStoreDto.setAreaId(1L);
        centerStoreDto.setStreetId(2L);
        centerStoreDto.setStoreAddress("地址");
        centerStoreDto.setTobaccoLicence("123");

        centerStoreDto.setUserUUID("123");
        centerStoreDto.setUuid("f9664818-dc15-4241-b0f2-50056b43dbdc");
        centerStoreDto.setStorePhone("138888");
        centerStoreDto.setBusinessLicence("123");
        centerStoreDto.setBusinessArea(34D);

        centerStoreDto.setBusinessEndDate(DateHelper.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        centerStoreDto.setTobaccoEndDate(DateHelper.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        centerStoreDto.setEstate(1);
        centerStoreDto.setRelationship("PARENT");
        centerStoreDto.setEmployeeNum(1);
        System.out.print(storeService.saveFromUserCenter(centerStoreDto));
    }


} 
