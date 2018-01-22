package com.xinyunlian.jinfu;

import com.xinyunlian.jinfu.log.dto.LoanAuditLogDto;
import com.xinyunlian.jinfu.log.enums.ELoanAuditLogType;
import com.xinyunlian.jinfu.log.service.LoanAuditLogService;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jl062 on 2017/2/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class LoanAuditLogTest {

    @Autowired
    private LoanAuditLogService loanAuditLogService;


    @Test
    public void saveTest() {
        LoanAuditLogDto logDto = new LoanAuditLogDto("李嘉亮", "1232131", ELoanAuditLogType.APPL, "李嘉亮", "1232131");
        loanAuditLogService.save(logDto);
    }

    @Test
    public void getTest() {
        List<LoanAuditLogDto> dtos = loanAuditLogService.getByApplNo("1232131");
        dtos.forEach(dto -> System.out.println(dto.getOperateDate()));
    }

    @Test
    public void addAllTest(){
        String[] a = new String[]{"ccccc", "dddddddd"};
        String[] b = new String[]{"aaaaaaa", "bbbbbb"};

        System.out.println(Arrays.toString(ArrayUtils.addAll(a, b)));
    }

}
