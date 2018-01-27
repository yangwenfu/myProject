package com.xinyunlian.jinfu.push.service;

import com.xinyunlian.jinfu.push.dto.PushDeviceDto;
import com.xinyunlian.jinfu.push.enums.EPushObject;
import com.xinyunlian.jinfu.push.enums.EPushPlatform;
import com.xinyunlian.jinfu.push.enums.PushObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Sephy
 * @since: 2017-01-17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:standard-code-dao-test.xml"})
public class PushServiceTest {

    @Autowired
    private PushService pushService;

    @Test
    public void pushJob() throws Exception {
        pushService.pushJob();
    }

    @Test
    public void updateRegistrationId() throws Exception {
        PushDeviceDto dto =  new PushDeviceDto();
        dto.setUserId("UC0000001844");
        dto.setPushToken("");
        pushService.updateRegistrationId(dto, PushObject.ZG);
    }

    @Test
    public void unRegistrationId() throws Exception {
        pushService.unBindRegistrationId("UC000000612", PushObject.XHB);
    }

    @Test
    public void compensatingPushJob() throws Exception {
        pushService.compensatingPushJob(1);
    }

    @Test
    public void pushAPP() throws Exception {
        pushService.pushMessageToApp("UC0000001455","测试测试", EPushObject.ZG, EPushPlatform.ALL);
    }

    @Test
    public void createPushMessage() throws Exception {
//        PushMessageCreateDto dto = new PushMessageCreateDto();
//        dto.setTitle("推送自测添加1");
//        dto.setContent("推送推送");
//        dto.setType(1);
//        dto.setPlatform(0);
//        dto.setPushStates(0);
//        dto.setPushObject(1);
//        dto.setImagePath("/Push/pig");
//        dto.setDescription("你好");
//        dto.setUrl("http://www.baidu.com");
//        dto.setPushTime("2017-01-17 00:00:00");
//
//        List<PushMessageAreaDto> areas = new ArrayList<>();
//        PushMessageAreaDto areaDto = new PushMessageAreaDto();
//        areaDto.setProvince("浙江省");
//        areaDto.setProvinceId("926");
//        areas.add(areaDto);
//        dto.setAreas(areas);
//        pushService.createPushMessage(dto);
    }
}
