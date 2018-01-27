package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.external.dto.RefundsAdvanceDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * Created by godslhand on 2017/7/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class LoanApplOutRefundsAdvanceServiceTest {

    @Autowired
    LoanApplOutRefundsAdvanceService loanApplOutRefundsAdvanceService;


    @Test
    public void findByApplId() throws Exception {
    }

    @Test
    public void updateDisableTag() throws Exception {
    }

    @Test
    public void save() throws Exception {

        RefundsAdvanceDto dto = new RefundsAdvanceDto();
        dto.setLoanRefuandInAdvanceId("1111112");
        dto.setAmount(new BigDecimal("10000.00"));
        dto.setCapital(new BigDecimal("10000.00"));
        dto.setInterest(new BigDecimal("10000.00"));
        dto.setBalance(new BigDecimal("10000.00"));
        dto.setApplyId("2");
        loanApplOutRefundsAdvanceService.save(dto);

        Assert.assertNotNull(loanApplOutRefundsAdvanceService.findByApplId("2"));
//        loanApplOutRefundsAdvanceService.updateDisableTag("2","111111");

    }



}
