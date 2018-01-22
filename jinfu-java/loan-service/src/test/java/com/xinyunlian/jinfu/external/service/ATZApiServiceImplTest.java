package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.AbstractServiceTest;
import com.xinyunlian.jinfu.external.dto.req.LoanApplyReq;
import com.xinyunlian.jinfu.external.dto.req.RegisterReq;
import com.xinyunlian.jinfu.external.enums.LoanTermType;
import com.xinyunlian.jinfu.external.enums.PaymentOptionType;
import com.xinyunlian.jinfu.external.util.Base64Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by godslhand on 2017/6/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class ATZApiServiceImplTest {

    @Autowired
    ATZApiService atzApiService;
    private String idNo ="330881198712082120"; //e99464be-3e6d-427f-a3f8-9a6e81abb1e4
    private String tel ="17757416901";

    @Test
    public void register() throws Exception {
        regist();
    }

    @Test
    public void loanApply() throws Exception {
//        regist();
        loanApply();
    }



    private void regist() throws IOException {
        RegisterReq registerReq = new RegisterReq() ;
        registerReq.setIdNo(idNo);
        registerReq.setUserName("钱城");
        registerReq.setContactTel(tel);
        registerReq.setUserIdResource("11");
        registerReq.setRegisterName("烟草小店");
        registerReq.setLegalRepId("330683199303285017");
        registerReq.setLegalRepMobile("113");
        registerReq.setLegalRepAdd("宁波江北");
        registerReq.setActualAdd("宁波江北");
        String bas64 = Base64Util.byteToBase64Encoding(Base64Util.getFileByte(new File("D:/timg.jpg")));
        System.out.println(bas64);
        registerReq.setBussinesLicencePhoto(bas64);
        registerReq.setTaxRegistrationPhoto(bas64);
        registerReq.setOrganizationCodePhoto(bas64);
        registerReq.setIdPhotoFront(bas64);
        registerReq.setOutsidePhoto(bas64);
        registerReq.setInsidePhoto(bas64);
        atzApiService.register(registerReq);
    }
    private  void  apply(){
        LoanApplyReq loanApply=  new LoanApplyReq();
        loanApply.setIdNo(idNo);
        loanApply.setAmount(new Double("10000.00"));
        loanApply.setLoanTerm(Integer.valueOf(LoanTermType._90.getCode()));
        loanApply.setPaymentOption(Integer.valueOf(PaymentOptionType.thirty_period.getCode()));
        loanApply.setLoanPurpose("测试23");

        String applyId =atzApiService.loanApply(loanApply);
        System.out.println(applyId);
    }

    @Autowired
    ExternalService externalService;
    @Test
    public void testFefounds(){
        externalService.loanRefundInAdvance("1d5e6a72-e9b8-453e-80d6-940586f038a5");
    }

}