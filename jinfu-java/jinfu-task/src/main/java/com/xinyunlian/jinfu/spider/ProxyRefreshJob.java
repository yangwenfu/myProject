package com.xinyunlian.jinfu.spider;

import com.xinyunlian.jinfu.config.dto.ConfigDto;
import com.xinyunlian.jinfu.config.enums.ECategory;
import com.xinyunlian.jinfu.config.service.ConfigService;
import com.xinyunlian.jinfu.spiderproxy.service.ProxyRefreshService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Created by bright on 2017/1/8.
 */
@Component
public class ProxyRefreshJob {
    public static final Logger LOGGER = LoggerFactory.getLogger(ProxyRefreshJob.class);

    @Autowired
    private ConfigService configService;

    @Autowired
    private ProxyRefreshService proxyRefreshService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void refresh(){
        LOGGER.info("Proxy Refresh Job begin");
        String ip = proxyRefreshService.getNewProxyIp();
        ConfigDto proxyIpConfig = configService.getByCategoryAndKey(ECategory.SPIDER, "ProxyIp");
        if(Objects.isNull(proxyIpConfig)){
            proxyIpConfig = new ConfigDto();
            proxyIpConfig.setKey("ProxyIp");
            proxyIpConfig.setCached(true);
            proxyIpConfig.setCategory(ECategory.SPIDER);
        }
        proxyIpConfig.setValue(ip);
        configService.save(proxyIpConfig);
        LOGGER.info("Proxy Refresh Job end");
    }
}
