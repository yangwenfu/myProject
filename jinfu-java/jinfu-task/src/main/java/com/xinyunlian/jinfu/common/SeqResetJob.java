package com.xinyunlian.jinfu.common;

import com.xinyunlian.jinfu.common.constant.ApplicationConstant;
import com.xinyunlian.jinfu.common.entity.id.seq.service.SeqResetService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by JL on 2016/9/23.
 */
@Component
public class SeqResetJob {

    private final static Logger LOGGER = LoggerFactory.getLogger(SeqResetJob.class);

    @Autowired
    @Qualifier("loanSeqResetService")
    private SeqResetService loanSeqResetService;

    @Autowired
    @Qualifier("userSeqResetService")
    private SeqResetService userSeqResetService;

    @Autowired
    @Qualifier("mgtSeqResetService")
    private SeqResetService mgtSeqResetService;

    @Autowired
    @Qualifier("dealerSeqResetService")
    private SeqResetService dealerSeqResetService;

    @Autowired
    @Qualifier("paySeqResetService")
    private SeqResetService paySeqResetService;

    @Autowired
    @Qualifier("cloudCodeSeqResetService")
    private SeqResetService cloudCodeSeqResetService;

    /**
     * execute at 00:00
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void reset() {
        String workDate = DateFormatUtils.format(new Date(), ApplicationConstant.DATE_FORMAT);
        try {
            LOGGER.info("pay-service-app resetSeq start");
            paySeqResetService.reset(workDate);
        } catch (Exception e) {
            LOGGER.error("pay-service-app resetSeq failed", e);
        }
        try {
            LOGGER.info("loan-service-app resetSeq start");
            loanSeqResetService.reset(workDate);
        } catch (Exception e) {
            LOGGER.error("loan-service-app resetSeq failed", e);
        }
        try {
            LOGGER.info("user-service-app resetSeq start");
            userSeqResetService.reset(workDate);
        } catch (Exception e) {
            LOGGER.error("user-service-app resetSeq failed", e);
        }
        try {
            LOGGER.info("mgt-service-app resetSeq start");
            mgtSeqResetService.reset(workDate);
        } catch (Exception e) {
            LOGGER.error("mgt-service-app resetSeq failed", e);
        }
        try {
            LOGGER.info("dealer-service-app resetSeq start");
            dealerSeqResetService.reset(workDate);
        } catch (Exception e) {
            LOGGER.error("dealer-service-app resetSeq failed", e);
        }
        try {
            LOGGER.info("cloudCode-service-app resetSeq start");
            cloudCodeSeqResetService.reset(workDate);
        } catch (Exception e) {
            LOGGER.error("cloudCode-service-app resetSeq failed", e);
        }
    }

}
