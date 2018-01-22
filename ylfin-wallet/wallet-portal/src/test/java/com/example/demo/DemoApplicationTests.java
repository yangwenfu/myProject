package com.example.demo;

import com.ylfin.wallet.portal.WalletPortalApplication;
import com.ylfin.wallet.portal.controller.RegisterController;
import com.ylfin.wallet.portal.controller.req.UserRegisterReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WalletPortalApplication.class)
public class DemoApplicationTests {
    @Autowired
    RegisterController registerController;

    @Test
    public void mobileExists() {
        registerController.mobileExists("15558127163");
    }
    @Test
    public void sendVerifyCode() {
        registerController.sendVerifyCode("15558127163");
    }
    @Test
    public void registerUser() {
        UserRegisterReq userRegisterReq = new UserRegisterReq();
        userRegisterReq.setMobile("15558127163");
        userRegisterReq.setPassword("123456");
        userRegisterReq.setVerifyCode("000000");
        registerController.registerUser(userRegisterReq);
    }
    @Test
    public void asg(){

    }

}
