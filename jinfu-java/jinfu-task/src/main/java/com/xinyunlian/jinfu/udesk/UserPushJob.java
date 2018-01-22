package com.xinyunlian.jinfu.udesk;

import com.xinyunlian.jinfu.udesk.service.UserPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by King on 2017/4/11.
 */
@Component
public class UserPushJob {
    @Autowired
    private UserPushService userPushService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void autoUserPush(){
        userPushService.autoUserImport();
        userPushService.autoUserUpdate();
    }
}
