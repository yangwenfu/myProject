package com.xinyunlian.jinfu.fintask.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;
import com.xinyunlian.jinfu.finfunddetail.dto.FinFundDetailDto;
import com.xinyunlian.jinfu.finfunddetail.service.FinFundDetailService;
import com.xinyunlian.jinfu.fintxhistory.dto.FinTxHistoryDto;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxStatus;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxType;
import com.xinyunlian.jinfu.fintxhistory.service.FinTxHistoryService;
import com.xinyunlian.jinfu.zrfundstx.dto.SuperCashQnReq;
import com.xinyunlian.jinfu.zrfundstx.dto.SuperCashQnResp;
import com.xinyunlian.jinfu.zrfundstx.dto.SuperCashQnSubResp;
import com.xinyunlian.jinfu.zrfundstx.enums.EReturnCode;
import com.xinyunlian.jinfu.zrfundstx.service.ZrFundsHttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dongfangchao on 2017/6/5/0005.
 */
@Service
public class QuotationJobServiceImpl implements QuotationJobService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuotationJobServiceImpl.class);

    @Value("${zrfunds.salt}")
    private String salt;
    @Autowired
    private FinFundDetailService finFundDetailService;
    @Autowired
    private FinTxHistoryService finTxHistoryService;
    @Autowired
    private ZrFundsHttpService zrFundsHttpService;

    @Override
    @Transactional
    public void updateQuotation() throws BizServiceException {
        List<FinFundDetailDto> fundDetailList = finFundDetailService.getFinFundDetailList(null);

        if (!CollectionUtils.isEmpty(fundDetailList)){
            for (FinFundDetailDto fundDetailDto:fundDetailList){
                //插入交易历史表
                FinTxHistoryDto finTxHistoryDto = new FinTxHistoryDto();
                finTxHistoryDto.setUserId("system");
                finTxHistoryDto.setOrderDate(new Date());
                finTxHistoryDto.setFinFundId(fundDetailDto.getFinFundId());
                finTxHistoryDto.setTxType(ETxType.SYC_QN);
                finTxHistoryDto.setTxStatus(ETxStatus.INPROCESS);
                finTxHistoryDto = finTxHistoryService.addFinTxHistory(finTxHistoryDto);
                String finTxId = finTxHistoryDto.getFinTxId();

                SuperCashQnReq req = new SuperCashQnReq();
                req.setApplicationNo(finTxId);
                req.setFundCode(fundDetailDto.getFinFundCode());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Calendar ytd = Calendar.getInstance();
                ytd.add(Calendar.DATE, -1);
                Long ytdLong = Long.parseLong(sdf.format(ytd.getTime()));
                req.setBeginDate(ytdLong);
                req.setEndDate(ytdLong);

                SuperCashQnResp resp = zrFundsHttpService.queryQuotation(req, salt);

                if (resp == null){
                    updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
                    LOGGER.error("行情查询失败");
                    return;
                }
                if (!EReturnCode.SUCCESS.getText().equals(resp.getReturnCode())){
                    updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.FAILURE);
                    LOGGER.error("行情查询失败," + resp.getReturnMsg());
                    return;
                }

                updateTxHistoryStatus(finTxHistoryDto.getFinTxId(), null, ETxStatus.SUCCESS);

                if (!CollectionUtils.isEmpty(resp.getList())){
                    SuperCashQnSubResp subResp = resp.getList().get(0);
                    FinFundDetailDto dto = new FinFundDetailDto();
                    dto.setFinFundCode(subResp.getFundCode());
                    dto.setFinOrg(EFinOrg.ZRFUNDS);
                    dto.setYield(subResp.getYield());
                    dto.setFoundIncome(subResp.getFundIncome());
                    Long updateDate = subResp.getUpdateDate();
                    Date date = DateHelper.getDate(updateDate.toString(), "yyyyMMdd");
                    dto.setUpdateDate(date);

                    finFundDetailService.updateFinFundDetail(dto);
                }
            }
        }
    }

    private void updateTxHistoryStatus(String finTxId, Object extTxId, ETxStatus txStatus){
        FinTxHistoryDto historyDto = new FinTxHistoryDto();
        historyDto.setFinTxId(finTxId);
        historyDto.setTxStatus(txStatus);
        if (extTxId != null){
            historyDto.setExtTxId(extTxId.toString());
        }
        finTxHistoryService.updateFinTxHistoryStatus(historyDto);
    }

}
