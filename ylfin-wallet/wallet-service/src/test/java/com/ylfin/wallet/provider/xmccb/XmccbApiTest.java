package com.ylfin.wallet.provider.xmccb;

import com.ylfin.wallet.config.XmccbConfiguration;
import com.ylfin.wallet.provider.xmccb.req.OpenAccountReq;
import com.ylfin.wallet.provider.xmccb.req.UserQueryReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class XmccbApiTest {

    @Autowired
    private XmccbApi xmccbApi;

    @Test
    public void openAccount() throws Exception {
        OpenAccountReq req = new OpenAccountReq();
        req.setRequestNo("20171227170500");
        req.setRealName("陈立之");
        req.setIdCardType("PRC_ID");
        req.setIdCardNo("330203198502160614");
        req.setUserRole("供应商");
        req.setMobile("18058557118");
        req.setBankcardNo("6216911900927935");
        req.setCheckType("LIMITED");
        req.setUserLimitType("ID_CARD_NO_UNIQUE");
        req.setAuthList("WITHDRAW,RECHARGE,REPAYMENT,TENDER");
        xmccbApi.openAccount(req);
    }

    @Test
    public void queryUser() throws Exception {
        UserQueryReq req = new UserQueryReq();
        req.setPlatformUserNo("1234567");
        xmccbApi.queryUser(req);
    }

    @Configuration
    @Import(XmccbConfiguration.class)
    public static class TestConfig {

    }
}