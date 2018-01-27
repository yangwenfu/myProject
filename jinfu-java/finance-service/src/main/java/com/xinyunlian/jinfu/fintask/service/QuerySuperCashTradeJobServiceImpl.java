package com.xinyunlian.jinfu.fintask.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;
import com.xinyunlian.jinfu.finaccfundprofit.dto.FinAccFundProfitDto;
import com.xinyunlian.jinfu.finaccfundprofit.service.FinAccFundProfitService;
import com.xinyunlian.jinfu.finfunddetail.dto.FinFundDetailDto;
import com.xinyunlian.jinfu.finfunddetail.service.FinFundDetailService;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistoryDto;
import com.xinyunlian.jinfu.finprofithistory.service.FinProfitHistoryService;
import com.xinyunlian.jinfu.fintxhistory.dto.FinTxHistoryDto;
import com.xinyunlian.jinfu.fintxhistory.dto.FinTxHistorySearchDto;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxStatus;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxType;
import com.xinyunlian.jinfu.fintxhistory.service.FinTxHistoryService;
import com.xinyunlian.jinfu.zrfundstx.dto.*;
import com.xinyunlian.jinfu.zrfundstx.enums.EConfirmFlag;
import com.xinyunlian.jinfu.zrfundstx.enums.EPayFlag;
import com.xinyunlian.jinfu.zrfundstx.enums.EReturnCode;
import com.xinyunlian.jinfu.zrfundstx.service.ZrFundsHttpService;
import com.ylfin.redis.lock.RedisLock;
import com.ylfin.redis.lock.RedisLockFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by dongfangchao on 2017/6/19/0019.
 */
@Service
public class QuerySuperCashTradeJobServiceImpl implements QuerySuperCashTradeJobService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuotationJobServiceImpl.class);

    @Value("${zrfunds.salt}")
    private String salt;

    @Autowired
    private ZrFundsHttpService zrFundsHttpService;

    @Autowired
    private FinTxHistoryService finTxHistoryService;

    @Autowired
    private FinFundDetailService finFundDetailService;

    @Autowired
    private FinProfitHistoryService finProfitHistoryService;

    @Autowired
    private FinAccFundProfitService finAccFundProfitService;

    @Autowired
    private RedisLockFactory redisLockFactory;

    @Override
    @Transactional
    public void queryApplyPurTrade() throws BizServiceException {
        FinTxHistorySearchDto searchDto = new FinTxHistorySearchDto();
        searchDto.setTxType(ETxType.APPLY_PUR);
        searchDto.setTxStatus(ETxStatus.INPROCESS);
        List<FinTxHistoryDto> txHistoryList = finTxHistoryService.getFinTxHistory(searchDto);

        List<Integer> successConfirmFlags =
                Arrays.asList(EConfirmFlag.CONFIRM_SUCCESS.getFlag(), EConfirmFlag.REALTIME_CONFIRM_SUCCESS.getFlag());

        if (!CollectionUtils.isEmpty(txHistoryList)){
            for (int i = 0; i < txHistoryList.size();i++){
                FinTxHistoryDto item = txHistoryList.get(i);
                if (StringUtils.isEmpty(item.getExtTxAccId())){
                    continue;
                }

                String applicationNo = item.getFinTxId();
                String userId = item.getUserId();

                SuperCashTradeQueryReq reqDto = new SuperCashTradeQueryReq();
                reqDto.setTransactionAccountID(item.getExtTxAccId());
                reqDto.setApplicationNo(applicationNo);
                SuperCashTradeQueryResp resp = zrFundsHttpService.querySuperCashTrade(reqDto, salt);

                if (resp == null){
                    updateTxHistoryStatus(applicationNo, ETxStatus.FAILURE);
                    continue;
                }
                if (!EReturnCode.SUCCESS.getText().equals(resp.getReturnCode())){
                    updateTxHistoryStatus(applicationNo, ETxStatus.FAILURE);
                    continue;
                }

                if (!CollectionUtils.isEmpty(resp.getList())){
                    SuperCashTradeQuerySubResp subResp = resp.getList().get(0);

                    //申购成功
                    if (successConfirmFlags.contains(subResp.getConfirmFlag()) && EPayFlag.VALID.getFlag() == subResp.getPayFlag()){

                        FinFundDetailDto dto = finFundDetailService.getFinFundDetailById(item.getFinFundId());
                        if (dto == null){
                            continue;
                        }

                        updateTxHistoryStatus(applicationNo, ETxStatus.SUCCESS);
                        updateProfit(userId, dto.getFinFundId(), dto.getFinFundCode(), item.getExtTxAccId());

                    }else if (subResp.getConfirmFlag() == EConfirmFlag.CONFIRM_FAILURE.getFlag()
                            || EPayFlag.INVALID.getFlag() == subResp.getPayFlag()){//申购失败
                        updateTxHistoryStatus(applicationNo, ETxStatus.FAILURE);
                    }
                }

            }
        }
    }

    @Override
    @Transactional
    public void queryRedeemTrade() throws BizServiceException {
        FinTxHistorySearchDto searchDto = new FinTxHistorySearchDto();
        searchDto.setTxType(ETxType.REDEEM);
        searchDto.setTxStatus(ETxStatus.INPROCESS);
        List<FinTxHistoryDto> txHistoryList = finTxHistoryService.getFinTxHistory(searchDto);

        List<Integer> successConfirmFlags =
                Arrays.asList(EConfirmFlag.CONFIRM_SUCCESS.getFlag(), EConfirmFlag.REALTIME_CONFIRM_SUCCESS.getFlag());

        if (!CollectionUtils.isEmpty(txHistoryList)){
            for (int i = 0; i < txHistoryList.size();i++){
                FinTxHistoryDto item = txHistoryList.get(i);
                if (StringUtils.isEmpty(item.getExtTxAccId())){
                    continue;
                }

                String applicationNo = item.getFinTxId();
                String userId = item.getUserId();

                SuperCashTradeQueryReq reqDto = new SuperCashTradeQueryReq();
                reqDto.setTransactionAccountID(item.getExtTxAccId());
                reqDto.setApplicationNo(applicationNo);
                SuperCashTradeQueryResp resp = zrFundsHttpService.querySuperCashTrade(reqDto, salt);

                if (resp == null){
                    updateTxHistoryStatus(applicationNo, ETxStatus.FAILURE);
                    continue;
                }
                if (!EReturnCode.SUCCESS.getText().equals(resp.getReturnCode())){
                    updateTxHistoryStatus(applicationNo, ETxStatus.FAILURE);
                    continue;
                }

                if (!CollectionUtils.isEmpty(resp.getList())){
                    SuperCashTradeQuerySubResp subResp = resp.getList().get(0);

                    //赎回成功
                    if (successConfirmFlags.contains(subResp.getConfirmFlag())){

                        FinFundDetailDto dto = finFundDetailService.getFinFundDetailById(item.getFinFundId());
                        if (dto == null){
                            continue;
                        }

                        updateTxHistoryStatus(applicationNo, ETxStatus.SUCCESS);
                        updateProfit(userId, dto.getFinFundId(), dto.getFinFundCode(), item.getExtTxAccId());

                    }else if (subResp.getConfirmFlag() == EConfirmFlag.CONFIRM_FAILURE.getFlag()){//赎回失败
                        updateTxHistoryStatus(applicationNo, ETxStatus.FAILURE);
                    }
                }

            }
        }
    }

    private void updateProfit(String userId, Long finFundId, String fundCode, String extTxAccId){

        RedisLock redisLock = redisLockFactory.getLock("ZRFUNDS_UPDATE_PROFIT");

        try{
            boolean blockLock = redisLock.blockLock(20000, TimeUnit.MILLISECONDS);
            if (blockLock){
                try{
                    //查询超级现金宝份额
                    FinTxHistoryDto finTxHistoryDto = new FinTxHistoryDto();
                    finTxHistoryDto.setUserId(userId);
                    finTxHistoryDto.setOrderDate(new Date());
                    finTxHistoryDto.setTxType(ETxType.QUERY);
                    finTxHistoryDto.setFinFundId(finFundId);
                    finTxHistoryDto.setTxStatus(ETxStatus.INPROCESS);

                    finTxHistoryDto = finTxHistoryService.addFinTxHistory(finTxHistoryDto);

                    SuperCashQueryShareReq scShareReq = new SuperCashQueryShareReq();
                    scShareReq.setTransactionAccountID(extTxAccId);
                    scShareReq.setApplicationNo(finTxHistoryDto.getFinTxId());
                    scShareReq.setFundCode(fundCode);

                    SuperCashQueryShareResp superCashQueryShareResp = zrFundsHttpService.querySuperCashShare(scShareReq, salt);
                    if (superCashQueryShareResp == null){
                        updateTxHistoryStatus(finTxHistoryDto.getFinTxId(),  ETxStatus.FAILURE);
                        return;
                    }
                    if (!EReturnCode.SUCCESS.getText().equals(superCashQueryShareResp.getReturnCode())){
                        updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), ETxStatus.FAILURE);
                        return;
                    }

                    updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), ETxStatus.SUCCESS);

                    if (!CollectionUtils.isEmpty(superCashQueryShareResp.getList())){
                        SuperCashQueryShareSubResp subResp = superCashQueryShareResp.getList().get(0);
                        Date profitDate = DateHelper.getStartDate(new Date());
                        //更新收益记录
                        FinProfitHistoryDto finProfitHistoryDto = new FinProfitHistoryDto();
                        finProfitHistoryDto.setUserId(userId);
                        finProfitHistoryDto.setExtTxAccId(extTxAccId);
                        finProfitHistoryDto.setFinFundId(finFundId);
                        finProfitHistoryDto.setProfitDate(profitDate);
                        finProfitHistoryDto.setAssetAmt(subResp.getTotalFundVol());
                        finProfitHistoryDto.setTotalProfit(subResp.getTotalIncome());
                        finProfitHistoryDto.setProfitAmt(subResp.getFundDayIncome());
                        finProfitHistoryDto.setFinOrg(EFinOrg.ZRFUNDS);

                        finProfitHistoryService.updateProfitHistory(finProfitHistoryDto);
                        finProfitHistoryService.updateProfitHistorySummary(userId, profitDate);

                        FinAccFundProfitDto profitDto = new FinAccFundProfitDto();
                        profitDto.setUserId(userId);
                        profitDto.setFinOrg(EFinOrg.ZRFUNDS);
                        profitDto.setExtTxAccId(extTxAccId);
                        profitDto.setFinFundId(finFundId);
                        finAccFundProfitService.updateFinAccProdProfit(profitDto);
                    }
                }finally {
                    redisLock.unlock();
                }
            }
        }catch (Exception e){
            LOGGER.error("更新收益异常", e);
        }

    }

    private void updateTxHistoryStatus(String finTxId, ETxStatus txStatus){
        FinTxHistoryDto historyDto = new FinTxHistoryDto();
        historyDto.setFinTxId(finTxId);
        historyDto.setTxStatus(txStatus);
        finTxHistoryService.updateFinTxHistoryStatus(historyDto);
    }

}
