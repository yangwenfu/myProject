package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.external.dto.LoanOutRiskDto;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.mq.sender.QueueSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by godslhand on 2017/7/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class LoanOutRiskServiceTest {
    @Autowired
    LoanOutRiskService loanOutRiskService;

    @Autowired
    QueueSender queueSender;

    @Test
    public  void testSave(){
        LoanOutRiskDto riskDto = new LoanOutRiskDto();
        riskDto.setType(EApplOutType.AITOUZI);
        riskDto.setActiveDegree(1D);
        riskDto.setCustomerType(1);
        riskDto.setAverageMonthlyAmount(10000.00D);
        riskDto.setUserId("0530e2a8-e840-4193-b6cc-e866b14f91e1");
        riskDto.setApplyId("302020671568879096");
        loanOutRiskService.save(riskDto);
    }


    @Test
    public void testMQ(){
        queueSender.send(DestinationDefine.LOAN_APPLY_OUT_RESULT,"test");
    }
}
