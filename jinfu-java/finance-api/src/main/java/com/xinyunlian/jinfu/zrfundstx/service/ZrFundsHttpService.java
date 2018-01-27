package com.xinyunlian.jinfu.zrfundstx.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.zrfundstx.dto.*;

/**
 * Created by dongfangchao on 2016/11/30/0030.
 */
public interface ZrFundsHttpService {

    /**
     * 中融开户
     * @param req
     * @return
     */
    NormOpenAccResp openAcc(NormOpenAccReq req, String salt)throws BizServiceException;

    /**
     * 申购
     * @param reqDto
     * @return
     */
    NormApplyPurResp applyPurchase(NormApplyPurReq reqDto, String salt) throws BizServiceException;

    /**
     * 查询超级现金宝份额
     * @param reqDto
     * @return
     */
    SuperCashQueryShareResp querySuperCashShare(SuperCashQueryShareReq reqDto, String salt)throws BizServiceException;

    /**
     * 实时赎回
     * @param reqDto
     * @return
     */
    RedeemRealTimeResp redeemRealTime(RedeemRealTimeReq reqDto, String salt)throws BizServiceException;

    /**
     * 普通赎回
     * @param reqDto
     * @return
     */
    RedeemNormResp redeemNormal(RedeemNormReq reqDto, String salt)throws BizServiceException;

    /**
     * 行情查询
     * @param req
     * @return
     * @throws BizServiceException
     */
    SuperCashQnResp queryQuotation(SuperCashQnReq req, String salt)throws BizServiceException;

    /**
     * 银行后台签约
     * @param req
     * @param salt
     * @return
     * @throws BizServiceException
     */
    BankSignApplyResp applyBankSign(BankSignApplyReq req, String salt) throws BizServiceException;

    /**
     * 查询申购扣款结果
     * @param req
     * @param salt
     * @return
     * @throws BizServiceException
     */
    SuperCashTradeQueryResp querySuperCashTrade(SuperCashTradeQueryReq req, String salt) throws BizServiceException;
}
