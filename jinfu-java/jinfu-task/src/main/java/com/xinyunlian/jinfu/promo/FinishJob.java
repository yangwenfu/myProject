package com.xinyunlian.jinfu.promo;

import com.xinyunlian.jinfu.promo.service.PromoInfService;
import com.xinyunlian.jinfu.promo.service.PromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by bright on 2016/11/21.
 */
@Component
public class FinishJob {
    private final static Logger LOGGER = LoggerFactory.getLogger(FinishJob.class);

    @Autowired
    private PromotionService promotionService;

    @Scheduled(cron = "0 0 * * * ?")
    public void autoFinish(){
        promotionService.finishJob();
    }

}
