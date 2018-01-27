package com.xinyunlian.jinfu.promo;

import com.xinyunlian.jinfu.promo.service.PromotionService;
import com.xinyunlian.jinfu.promo.service.UserCouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by bright on 2016/11/21.
 */
@Component
public class CouponOverdueJob {
    private final static Logger LOGGER = LoggerFactory.getLogger(CouponOverdueJob.class);

    @Autowired
    private UserCouponService userCouponService;

    @Scheduled(cron = "0 0 * * * ?")
    public void autoFinish(){
        userCouponService.overdueJob();
    }

}
