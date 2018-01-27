package com.xinyunlian.jinfu.udesk.service;

import com.xinyunlian.jinfu.udesk.dto.CustomerDto;
import com.xinyunlian.jinfu.udesk.dto.UserImportDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class UserPushServiceTest {
    @Autowired UserPushService userPushService;


    /**
     * Method: gerAuthToken()
     */
    @Test
    public void testGerAuthToken() throws Exception {
        UserPushService userPushService = new UserPushService();
        String result = userPushService.gerAuthToken();
        System.out.printf(result);
    }

    @Test
    public void testGerAgents() throws Exception {
        UserPushService userPushService = new UserPushService();
        String token = userPushService.gerAuthToken();
        String result = userPushService.getAgents(token);

        UserImportDto userPushDto = new UserImportDto();

        CustomerDto customer = new CustomerDto();
        customer.setNick_name("测试");
        customer.getCellphones().add("13805844954");
        List<CustomerDto> customers = new ArrayList<>();
        customers.add(customer);
        userPushDto.setCustomers(customers);

        userPushService.batchImport(token,userPushDto);
        System.out.printf(result);
    }

    @Test
    public void testAutoUserPush() throws Exception {
        userPushService.autoUserImport();
    }

    @Test
    public void testAutoUserUpdate() throws Exception {
        userPushService.autoUserUpdate();
    }

} 
