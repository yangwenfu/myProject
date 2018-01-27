package com.xinyunlian.jinfu.cloudcode;

import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.dto.PayRecvReqDto;
import com.xinyunlian.jinfu.enums.PayRecvResult;
import com.xinyunlian.jinfu.order.dto.CmccTradeRecordDto;
import com.xinyunlian.jinfu.order.enums.ECmccOrderTradeStatus;
import com.xinyunlian.jinfu.order.service.CmccOrderService;
import com.xinyunlian.jinfu.order.service.CmccTradeService;
import com.xinyunlian.jinfu.service.PayRecvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2016年11月20日.
 */
@Component
public class CmccPayTurnoverJob {

    @Autowired
    private CmccTradeService cmccTradeService;
    @Autowired
    private PayRecvService payRecvService;
    @Autowired
    private CmccOrderService cmccOrderService;
    @Autowired
    private BankService bankService;

    private final static Logger LOGGER = LoggerFactory.getLogger(CmccPayTurnoverJob.class);

    /**
     * 打款，按成交额>500
     * execute at 17:00
     */
    @Scheduled(cron = "0 0 17 * * ?")
    public void payTurnover() {
        List<Object[]> storeList = cmccOrderService.getOrderList("turnover");
        List<Long> storeIds = new ArrayList<>();
        for (Object[] object : storeList) {
            storeIds.add(Long.valueOf(object[0].toString()));
        }
        List<Object[]> bankList = new ArrayList<>();
        if (storeIds.size() > 0) {
            bankList = bankService.findByStoreIds(storeIds);
        }
        List<CmccTradeRecordDto> trades = cmccTradeService.startTrade(storeList, bankList);

        LOGGER.info(String.format("%s process pay should be pay", trades.size()));

        trades.forEach(trade -> {
            try {
                PayRecvReqDto req = new PayRecvReqDto();
                req.setIdCardNo(trade.getIdCardNo());
                req.setBankCardNo(trade.getBankCardNo());
                req.setBankCardName(trade.getBankCardName());
                req.setBankCode(trade.getBankCode());
                req.setTranNo(trade.getCmccTradeNo());
                req.setTrxMemo("cmcc_pay");
                req.setTrxAmt(trade.getAmount());
                req.setToPrivate(true);
                PayRecvResult result = payRecvService.pay(req);

                //状态更新回去
                cmccTradeService.updateTradeStatus(trade, convertOrdStatus(result));

                LOGGER.info(String.format("pay result:cmccTradeNo:%s,rs:%s",
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
