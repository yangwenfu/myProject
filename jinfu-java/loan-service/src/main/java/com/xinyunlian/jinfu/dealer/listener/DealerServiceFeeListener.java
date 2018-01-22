package com.xinyunlian.jinfu.dealer.listener;

import com.xinyunlian.jinfu.cashier.dto.LoanPayDto;
import com.xinyunlian.jinfu.cashier.service.LoanPayService;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.dealer.dao.DealerServiceFeeDao;
import com.xinyunlian.jinfu.dealer.entity.DealerServiceFeePo;
import com.xinyunlian.jinfu.dealer.enums.EDealerServiceFeeStatus;
import com.xinyunlian.jinfu.loan.dao.LoanDtlDao;
import com.xinyunlian.jinfu.loan.entity.LoanDtlPo;
import com.xinyunlian.jinfu.loan.enums.ETransferStat;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DealerServiceFeeListener {

    private static final Logger logger = LoggerFactory.getLogger(DealerServiceFeeListener.class);

    @Autowired
    private LoanDtlDao loanDtlDao;

    @Autowired
    private LoanPayService loanPayService;

    @Autowired
    private DealerServiceFeeDao dealerServiceFeeDao;

    @JmsListener(destination = DestinationDefine.DEALER_SERVICE_FEE_DEDUCTION)
    public void dealerServiceFee(String loanId) {
        LoanDtlPo loanDtlPo = loanDtlDao.findOne(loanId);
        if (loanDtlPo != null && loanDtlPo.getTransferStat() == ETransferStat.SUCCESS) {
            BigDecimal serviceFeeRt = loanDtlPo.getServiceFeeRt();
            if (serviceFeeRt != null && serviceFeeRt.compareTo(BigDecimal.ZERO) != 0) {
                List<DealerServiceFeePo> dealerServiceFeePos = dealerServiceFeeDao.findByLoanId(loanId);
                if (dealerServiceFeePos.isEmpty()) {
                    logger.info("贷款单{}开始扣除手续费。", loanId);
                    BigDecimal serviceFee = loanDtlPo.getLoanAmt().multiply(serviceFeeRt);
                    DealerServiceFeePo dealerServiceFeePo = new DealerServiceFeePo();
                    dealerServiceFeePo.setLoanId(loanId);
                    dealerServiceFeePo.setLoanAmt(loanDtlPo.getLoanAmt());
                    dealerServiceFeePo.setServiceFeeRt(loanDtlPo.getServiceFeeRt());
                    dealerServiceFeePo.setServiceFee(serviceFee);
                    dealerServiceFeePo.setStatus(EDealerServiceFeeStatus.PROCESS);
                    dealerServiceFeePo.setBankCardId(loanDtlPo.getBankCardId().toString());
                    dealerServiceFeeDao.save(dealerServiceFeePo);
                    //发起代扣
                    String appId = AppConfigUtil.getConfig("cashier.pay.appId");
                    String sellerId = AppConfigUtil.getConfig("cashier.pay.sellerId");
                    String partnerId = AppConfigUtil.getConfig("cashier.pay.partnerId");
                    String callback = AppConfigUtil.getConfig("cashier.withhold.callback.url");
                    LoanPayDto withholdRequest = new LoanPayDto();
                    withholdRequest.setAppId(appId);
                    withholdRequest.setSellerId(sellerId);
                    withholdRequest.setPartnerId(partnerId);
                    withholdRequest.setBuyerId(loanDtlPo.getUserId());
                    withholdRequest.setBankCardId(loanDtlPo.getBankCardId().toString());
                    withholdRequest.setOutTradeNo(dealerServiceFeePo.getId());
                    withholdRequest.setSrcAmt(NumberUtil.roundTwo(serviceFee).toString());
                    withholdRequest.setNotifyUrl(callback);
                    loanPayService.withhold(withholdRequest);
                }
            }
        } else {
            logger.error("贷款单{}不存在或尚未转账成功。", loanId);
        }
    }
}
