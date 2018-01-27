package com.xinyunlian.jinfu;

import com.xinyunlian.jinfu.center.dto.CenterStoreDto;
import com.xinyunlian.jinfu.center.dto.CenterUserDto;
import com.xinyunlian.jinfu.center.service.CenterStoreService;
import com.xinyunlian.jinfu.center.service.CenterUserService;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:standard-code-dao-test.xml"})
public class CenterUserServiceImplTest {

    @Autowired
    private CenterUserService centerUserService;
    @Autowired
    private CenterStoreService centerStoreService;

    @Test
    public void testFindByUuid() throws Exception {
//TODO: Test goes here... 
    }

    @Test
    public void testAddUserFromMQ() throws Exception {
        CenterUserDto centerUserDto = new CenterUserDto();
        centerUserDto.setUserId("UC0000000644");
        centerUserDto.setMobile("18815289033");
        centerUserDto.setUsername(centerUserDto.getMobile());
       /* centerUserDto.setUsername("无名氏");
        centerUserDto.setGender(1);
        centerUserDto.setProvince("省");
        centerUserDto.setCity("市");
        centerUserDto.setArea("区");
        centerUserDto.setAreaGbCode(130502L);
        centerUserDto.setIdentityAuth("1");*/
        centerUserService.addUserFromMQ(JsonUtil.toJson(centerUserDto));
    }

    /**
     * Method: updateUserFromMQ(String centerUser)
     */
    @Test
    public void testUpdateUserFromMQ() throws Exception {
        CenterUserDto centerUserDto = new CenterUserDto();
        centerUserDto.setUuid("bc5c9e3f-53e5-4d68-a30f-e3b4f7ab9185");
        centerUserDto.setUsername("无名氏");
        centerUserDto.setGender(1);
        centerUserDto.setProvince("省");
        centerUserDto.setCity("市");
        centerUserDto.setArea("区");
        centerUserDto.setAreaGbCode("130502");
        centerUserDto.setIdentityAuth(1);
        centerUserService.addUserFromMQ(JsonUtil.toJson(centerUserDto));
    }

    @Test
    public void testAddStoreFromMQ() throws Exception {
        CenterStoreDto centerStoreDto = new CenterStoreDto();
        centerStoreDto.setStoreId(1L);
        centerStoreDto.setUserUUID("bc5c9e3f-53e5-4d68-a30f-e3b4f7ab9185");
        centerStoreDto.setStoreName("测试小店");
        centerStoreDto.setProvince("省");
        centerStoreDto.setCity("市");
        centerStoreDto.setArea("区");
        centerStoreDto.setAreaGbCode("130502");
        centerStoreDto.setStoreAddress("测试地址");
        centerStoreDto.setTobaccoLicence("12343");

        centerStoreService.addStoreFromMQ(JsonUtil.toJson(centerStoreDto));
    }

    @Test
    public void testUpdateStoreFromMQ() throws Exception {
        CenterStoreDto centerStoreDto = new CenterStoreDto();
        centerStoreDto.setUuid("9bafba32-4fe0-42be-90b4-52fe0329a2b7");
        centerStoreDto.setUserUUID("bc5c9e3f-53e5-4d68-a30f-e3b4f7ab9185");
        centerStoreDto.setStoreName("测试小店");
        centerStoreDto.setProvince("省");
        centerStoreDto.setCity("市");
        centerStoreDto.setArea("区");
        centerStoreDto.setAreaGbCode("130502");
        centerStoreDto.setStoreAddress("测试地址");
        //centerStoreDto.setTobaccoLicence("12343");

        centerStoreService.addStoreFromMQ(JsonUtil.toJson(centerStoreDto));
    }

} 
