package com.xinyunlian.jinfu.cloudcode;

import com.xinyunlian.jinfu.enums.PayRecvResult;
import com.xinyunlian.jinfu.order.dto.CmccTradeRecordDto;
import com.xinyunlian.jinfu.order.enums.ECmccOrderTradeStatus;
import com.xinyunlian.jinfu.order.service.CmccTradeService;
import com.xinyunlian.jinfu.service.PayRecvQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by menglei on 2016年11月20日.
 */
@Component
public class CmccPayQueryJob {

    @Autowired
    private CmccTradeService cmccTradeService;
    @Autowired
    private PayRecvQueryService payRecvQueryService;

    private final static Logger LOGGER = LoggerFactory.getLogger(CmccPayQueryJob.class);

    /**
     * 每15分钟执行一次
     */
    @Scheduled(cron = "0 0/15 * * * ?")
    public void payQuery() {
        List<CmccTradeRecordDto> trades = cmccTradeService.findTradeList(ECmccOrderTradeStatus.PROCESS);

        LOGGER.info(String.format("%s process pay should be query", trades.size()));

        trades.forEach(trade -> {
            try {
                PayRecvResult result = payRecvQueryService.payQuery(trade.getCmccTradeNo(), trade.getCreateTime());

                //状态更新回去
                cmccTradeService.updateTradeStatus(trade, convertOrdStatus(result));

                LOGGER.info(String.format("pay query result:cmccTradeNo:%s,rs:%s",
                        trade.getCmccTradeNo(), result));

            } catch (Exception e) {
                LOGGER.warn("process order:{} failed", trade.getCmccTradeNo(), e);
            }
        });
    }

    private ECmccOrderTradeStatus convertOrdStatus(PayRecvResult result) {
        if (result == PayRecvResult.SUCCESS) {
            return ECmccOrderTradeStatus.SUCCESS;
        } else if (result == PayRecvResult.FAILED) {
            return ECmccOrderTradeStatus.FAILED;
        }
        return ECmccOrderTradeStatus.PROCESS;
    }

}
