package com.xinyunlian.jinfu;

import com.xinyunlian.jinfu.balance.entity.BalanceCashierPo;
import com.xinyunlian.jinfu.balance.enums.EBalanceStatus;
import com.xinyunlian.jinfu.balance.service.BalanceCashierService;
import com.xinyunlian.jinfu.balance.service.BalanceOutlineService;
import com.xinyunlian.jinfu.common.constant.ApplicationConstant;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.loan.dto.RepayDtlDto;
import com.xinyunlian.jinfu.loan.entity.RepayDtlPo;
import com.xinyunlian.jinfu.loan.enums.ERepayStatus;
import com.xinyunlian.jinfu.loan.enums.ETransMode;
import com.xinyunlian.jinfu.repay.service.RepayService;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by jl062 on 2017/2/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class BalanceTest {
    @Autowired
    private BalanceOutlineService balanceOutlineService;

    @Autowired
    private BalanceCashierService balanceCashierService;

    @Autowired
    private RepayService repayService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BalanceTest.class);

    @Test
    public void testGenerate() {
        balanceOutlineService.generate();
    }

    @Test
    public void testRefresh() {
        balanceCashierService.refresh(5L);
    }

    @Test
    public void dataImport() {


        File file = new File("D:/data.csv");

        boolean skip = true;
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String str;
            int counter = 1;
            while ((str = br.readLine()) != null) {
                if (skip) {
                    skip = false;
                    continue;
                }
                String[] args = str.split(",");

                for (int begin = 8;begin <= 94; begin = begin + 2){
                    String amt = args[begin];

                    if(StringUtils.isEmpty(amt)){
                        continue;
                    }

                    String repayDate = args[begin + 1];
                    Date repayDateTime = DateHelper.getDate(repayDate, DateHelper.SIMPLE_DATE_YMD);

                    String loanId = args[97];

                    RepayDtlDto repayDtlDto = new RepayDtlDto();

                    repayDtlDto.setAcctNo("0");
                    repayDtlDto.setLoanId(loanId);
                    repayDtlDto.setLoanName("云随贷");
                    repayDtlDto.setRepayDate(DateHelper.formatDate(repayDateTime, ApplicationConstant.DATE_FORMAT));
                    repayDtlDto.setRepayDateTime(repayDateTime);
                    repayDtlDto.setRepayPrinAmt(new BigDecimal(amt));
                    repayDtlDto.setRepayIntr(BigDecimal.ZERO);
                    repayDtlDto.setRepayFine(BigDecimal.ZERO);
                    repayDtlDto.setRepayFee(BigDecimal.ZERO);
                    repayDtlDto.setStatus(ERepayStatus.SUCCESS);
                    repayDtlDto.setTransMode(ETransMode.AUTO);

                    repayDtlDto = repayService.save(repayDtlDto);

                    LOGGER.info("repay data import success,counter:{},index:{},loan_id:{},repay_id:{},date:{},amt:{}", counter, begin, loanId, repayDtlDto.getRepayId(), repayDate, amt);
                }

                counter++;
            }

            br.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("finish");

    }
}