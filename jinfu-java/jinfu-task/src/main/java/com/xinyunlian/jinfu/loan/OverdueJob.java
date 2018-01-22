package com.xinyunlian.jinfu.loan;

import com.xinyunlian.jinfu.repay.service.RepayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author willwang
 */
@Component
public class OverdueJob {

    @Autowired
    private RepayService repayService;

    /**
     * execute at 00:05
     */
    @Scheduled(cron = "0 5 0 * * ?")
    public void autoOverdue() {
        repayService.overdueJob();
    }

}
