package com.xinyunlian.jinfu.loan;

import com.xinyunlian.jinfu.cloudcode.CmccPayMonthJob;
import com.xinyunlian.jinfu.cloudcode.CmccPayQueryJob;
import com.xinyunlian.jinfu.cloudcode.CmccPayTurnoverJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class JobTest {

    @Autowired
    private RepayJob repayJob;

    @Autowired
    private CmccPayMonthJob cmccPayMonthJob;

    @Autowired
    private CmccPayTurnoverJob cmccPayTurnoverJob;

    @Autowired
    private CmccPayQueryJob cmccPayQueryJob;

    @Test
    public void repayTest(){
//        repayJob.autoRepay();
    }

    @Test
    public void repayQueryTest(){
//        repayQueryJob.repayQuery();
    }

    @Test
    public void cmccPayMonthJob(){
        //cmccPayMonthJob.payMonth();
    }

    @Test
    public void repayJob(){
        repayJob.autoRepay();
    }

}
