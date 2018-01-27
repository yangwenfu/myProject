package com.xinyunlian.jinfu.push;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xinyunlian.jinfu.push.service.PushService;

/**
 * Created by apple on 2017/1/12.
 */
@Component
public class PushJob {
    private final static Logger LOGGER = LoggerFactory.getLogger(com.xinyunlian.jinfu.push.PushJob.class);

    @Autowired
    private PushService pushService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void autoFinish(){
        pushService.pushJob();
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void autoFinishV2_XHB(){
        pushService.compensatingPushJob(1);
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void autoFinishV2_ZG(){
        pushService.compensatingPushJob(2);
    }
}