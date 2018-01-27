package com.xinyunlian.jinfu.fintask.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.finaccbankcard.dto.FinAccBankCardDto;
import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;
import com.xinyunlian.jinfu.finaccbankcard.service.FinAccBankCardService;
import com.xinyunlian.jinfu.finaccfundprofit.dto.FinAccFundProfitDto;
import com.xinyunlian.jinfu.finaccfundprofit.service.FinAccFundProfitService;
import com.xinyunlian.jinfu.finfunddetail.dto.FinFundDetailDto;
import com.xinyunlian.jinfu.finfunddetail.service.FinFundDetailService;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistoryDto;
import com.xinyunlian.jinfu.finprofithistory.service.FinProfitHistoryService;
import com.xinyunlian.jinfu.fintxhistory.dto.FinTxHistoryDto;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxStatus;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxType;
import com.xinyunlian.jinfu.fintxhistory.service.FinTxHistoryService;
import com.xinyunlian.jinfu.zrfundstx.dto.SuperCashQueryShareReq;
import com.xinyunlian.jinfu.zrfundstx.dto.SuperCashQueryShareResp;
import com.xinyunlian.jinfu.zrfundstx.dto.SuperCashQueryShareSubResp;
import com.xinyunlian.jinfu.zrfundstx.service.ZrFundsHttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dongfangchao on 2017/6/5/0005.
 */
@Service
public class ShareJobServiceImpl implements ShareJobService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShareJobServiceImpl.class);

    @Value("${zrfunds.salt}")
    private String salt;
    @Autowired
    private ZrFundsHttpService zrFundsHttpService;
    @Autowired
    private FinProfitHistoryService finProfitHistoryService;
    @Autowired
    private FinTxHistoryService finTxHistoryService;
    @Autowired
    private FinAccFundProfitService finAccFundProfitService;
    @Autowired
    private FinAccBankCardService finAccBankCardService;
    @Autowired
    private FinFundDetailService finFundDetailService;

    @Override
    @Transactional
    public void updateShare() throws BizServiceException {
        List<FinAccBankCardDto> accList = finAccBankCardService.getFinAccBankCardList(null);
        List<FinFundDetailDto> fundDetailList = finFundDetailService.getFinFundDetailList(null);
        if (!CollectionUtils.isEmpty(accList) && !CollectionUtils.isEmpty(fundDetailList)){

            Map<String, FinFundDetailDto> fundDetailMap = new HashMap<>();
            fundDetailList.forEach(fundDetail -> {
                fundDetailMap.put(fundDetail.getFinFundCode(), fundDetail);
            });

            //一个账号查一次
            accList.forEach(acc -> {
                //插入交易历史表
                FinTxHistoryDto finTxHistoryDto = new FinTxHistoryDto();
                finTxHistoryDto.setUserId(acc.getUserId());
                finTxHistoryDto.setOrderDate(new Date());
                finTxHistoryDto.setTxType(ETxType.QUERY);
                finTxHistoryDto.setTxStatus(ETxStatus.INPROCESS);

                finTxHistoryDto = finTxHistoryService.addFinTxHistory(finTxHistoryDto);
                String applicationNo = finTxHistoryDto.getFinTxId();

                boolean isSuccess =
                        updateProfit(acc.getUserId(), applicationNo, acc.getExtTxAccId(), fundDetailMap);

                //更新历史交易表
                FinTxHistoryDto txHistoryDto = new FinTxHistoryDto();
                txHistoryDto.setFinTxId(applicationNo);
                if (isSuccess){
                    txHistoryDto.setTxStatus(ETxStatus.SUCCESS);
                }else {
                    txHistoryDto.setTxStatus(ETxStatus.FAILURE);
                }
                finTxHistoryService.updateFinTxHistoryStatus(txHistoryDto);
            });

        }
    }

    private boolean updateProfit(String userId, String applicationNo, String extTxAccId, Map<String, FinFundDetailDto> fundDetailMap){
        //查询超级现金宝份额
        SuperCashQueryShareReq scShareReq = new SuperCashQueryShareReq();
        scShareReq.setApplicationNo(applicationNo);
        scShareReq.setTransactionAccountID(extTxAccId);

        SuperCashQueryShareResp superCashQueryShareResp = zrFundsHttpService.querySuperCashShare(scShareReq, salt);
        if (superCashQueryShareResp == null){
            updateTxHistoryStauts(applicationNo, ETxStatus.FAILURE);
            return false;
        }

        updateTxHistoryStauts(applicationNo, ETxStatus.SUCCESS);

        if (!CollectionUtils.isEmpty(superCashQueryShareResp.getList())){

            for (SuperCashQueryShareSubResp subResp : superCashQueryShareResp.getList()) {
                if (fundDetailMap.containsKey(subResp.getFundCode())){

                    FinFundDetailDto fundDetail = fundDetailMap.get(subResp.getFundCode());
                    Date profitDate = DateHelper.getStartDate(new Date());
                    //更新收益记录
                    FinProfitHistoryDto finProfitHistoryDto = new FinProfitHistoryDto();
                    finProfitHistoryDto.setUserId(userId);
                    finProfitHistoryDto.setFinOrg(EFinOrg.ZRFUNDS);
                    finProfitHistoryDto.setExtTxAccId(extTxAccId);
                    finProfitHistoryDto.setFinFundId(fundDetail.getFinFundId());
                    finProfitHistoryDto.setProfitDate(profitDate);
                    finProfitHistoryDto.setAssetAmt(subResp.getTotalFundVol());
                    finProfitHistoryDto.setTotalProfit(subResp.getTotalIncome());
                    finProfitHistoryDto.setProfitAmt(subResp.getFundDayIncome());

                    finProfitHistoryService.updateProfitHistory(finProfitHistoryDto);
                    finProfitHistoryService.updateProfitHistorySummary(userId, profitDate);

                    FinAccFundProfitDto profitDto = new FinAccFundProfitDto();
                    profitDto.setUserId(userId);
                    profitDto.setFinOrg(EFinOrg.ZRFUNDS);
                    profitDto.setExtTxAccId(extTxAccId);
                    profitDto.setFinFundId(fundDetail.getFinFundId());
                    finAccFundProfitService.updateFinAccProdProfit(profitDto);
                }
            }
        }
        return true;
    }

    private void updateTxHistoryStauts(String applicationNo, ETxStatus txStatus){
        FinTxHistoryDto failureDto = new FinTxHistoryDto();
        failureDto.setFinTxId(applicationNo);
        failureDto.setTxStatus(txStatus);
        finTxHistoryService.updateFinTxHistoryStatus(failureDto);
    }
}
