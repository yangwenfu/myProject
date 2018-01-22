package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.acct.dao.AcctDao;
import com.xinyunlian.jinfu.acct.entity.AcctPo;
import com.xinyunlian.jinfu.acct.service.InnerAcctReserveService;
import com.xinyunlian.jinfu.cashier.dto.LoanPayDto;
import com.xinyunlian.jinfu.cashier.service.LoanPayService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.sms.SmsUtil;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.depository.dto.LoanDepositoryAcctDto;
import com.xinyunlian.jinfu.depository.service.LoanDepositoryAcctService;
import com.xinyunlian.jinfu.loan.dao.LoanApplDao;
import com.xinyunlian.jinfu.loan.dao.LoanDtlDao;
import com.xinyunlian.jinfu.loan.dto.req.LoanPayReq;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.entity.LoanApplPo;
import com.xinyunlian.jinfu.loan.entity.LoanDtlPo;
import com.xinyunlian.jinfu.loan.enums.EApplStatus;
import com.xinyunlian.jinfu.loan.enums.ELoanStat;
import com.xinyunlian.jinfu.loan.enums.ETransferStat;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.pay.enums.EOrdStatus;
import com.xinyunlian.jinfu.pay.enums.EPrType;
import com.xinyunlian.jinfu.pay.service.InnerPayRecvOrdService;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.promo.dao.PromoDao;
import com.xinyunlian.jinfu.promo.dto.PromoDto;
import com.xinyunlian.jinfu.promo.entity.PromoPo;
import com.xinyunlian.jinfu.router.dto.FinanceSourceConfigDto;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import com.ylfin.depository.dto.PayOutDto;
import com.ylfin.depository.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.Oneway;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author willwang
 */
@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private AcctDao acctDao;

    @Autowired
    private LoanDtlDao loanDtlDao;

    @Autowired
    private InnerApplService innerApplService;

    @Autowired
    private InnerAcctReserveService innerAcctReserveService;

    @Autowired
    private InnerPayRecvOrdService innerPayRecvOrdService;

    @Autowired
    private InnerLoanService innerLoanService;

    @Autowired
    private PromoDao promoDao;

    @Autowired
    private LoanApplDao loanApplDao;

    @Autowired
    private LoanPayService loanPayService;

    @Autowired
    private LoanDepositoryAcctService loanDepositoryAcctService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FinanceSourceService financeSourceService;

    @Autowired
    private LoanService loanService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanService.class);

    @Override
    @Transactional
    public LoanDtlDto agree(String userId, String applId, Long cardId, PromoDto promoDto, LoanProductDetailDto product) {
        LoanApplDto applDto = innerApplService.get(applId);

        assertAppl(applDto);
        AcctPo acctPo = acctDao.findByUserIdAndProductId(userId, product.getProductId());
        //账户状态检测
        asserAcct(acctPo, applDto);

        //同意后，新增贷款记录
        LoanDtlPo loanDtlPo = saveLoanDetail(acctPo, applDto, product, cardId);

        //贷款促销信息保存
        if (promoDto != null) {
            PromoPo promoPo = ConverterService.convert(promoDto, PromoPo.class);
            promoPo.setLoanId(loanDtlPo.getLoanId());
            promoDao.save(promoPo);
        }
        return ConverterService.convert(loanDtlPo, LoanDtlDto.class);
    }

    @Override
    public LoanDtlDto get(String loanId) {
        return innerLoanService.get(loanId);
    }

    @Override
    @Transactional
    public void save(LoanDtlDto loanDtlDto) {
        innerLoanService.save(loanDtlDto);
    }

    @Override
    @Transactional
    public void updateTransferSuccessByApplId(String applId) {
        LOGGER.info("loan transfer status update, appl_id:{}", applId);

        LoanDtlPo loanDtlPo = loanDtlDao.findByApplId(applId);
        loanDtlPo.setTransferStat(ETransferStat.SUCCESS);
        loanDtlPo.setTransferDate(new Date());
        loanDtlDao.save(loanDtlPo);

        LoanApplPo loanApplPo = loanApplDao.findOne(applId);

        innerAcctReserveService.unReserve(loanApplPo.getCreditLineRsrvId());
        innerAcctReserveService.useCreditLine(loanDtlPo.getAcctNo(), loanDtlPo.getLoanAmt());
    }

    @Override
    @Transactional
    public LoanDtlDto payCallback(LoanDtlDto loanDtlDto, PayRecvOrdDto payRecvOrdDto) {

        if (payRecvOrdDto.getOrdStatus() == EOrdStatus.SUCCESS) {

            //如果转账时间已经由外部带进来了，就不需要再取当前时间进行更新了
            if (loanDtlDto.getTransferDate() == null) {
                loanDtlDto.setTransferDate(new Date());
            }

            //更新贷款状态状态
            loanDtlDto.setTransferStat(ETransferStat.SUCCESS);

            //更新申请的保留
            LoanApplDto applDto = innerApplService.get(loanDtlDto.getApplId());

            innerAcctReserveService.unReserve(applDto.getCreditLineRsrvId());

            //占用额度
            innerAcctReserveService.useCreditLine(loanDtlDto.getAcctNo(), loanDtlDto.getLoanAmt());

            Date transferDate = new Date();
            if (loanDtlDto.getTransferDate() != null) {
                transferDate = loanDtlDto.getTransferDate();
            }

            //贷款成功放款后更新到期时间
            loanDtlDto.setDutDate(
                    DateHelper.formatDate(applDto.getTermType().add(transferDate, applDto.getApprTermLen()))
            );

        } else if (payRecvOrdDto.getOrdStatus() == EOrdStatus.FAILED) {
            loanDtlDto.setTransferStat(ETransferStat.FAILED);
        } else if (payRecvOrdDto.getOrdStatus() == EOrdStatus.PROCESS) {
            loanDtlDto.setTransferStat(ETransferStat.PROCESS);
        }

        //更新贷款信息
        this.save(loanDtlDto);

        //保存状态
        innerPayRecvOrdService.save(payRecvOrdDto);

        return loanDtlDto;

    }

    @Override
    public Long count(String userId) {
        return loanDtlDao.countByUserIdAndTransferStat(userId, ETransferStat.SUCCESS);
    }

    @Override
    public Long count(String userId, String prodId) {
        return loanDtlDao.countByUserIdAndProdId(userId, prodId);
    }

    @Override
    public PayRecvOrdDto pay(LoanPayReq loanPayReq) {

        LoanDtlDto loanDtlDto = loanPayReq.getLoanDtlDto();

        PayRecvOrdDto payRecvOrdDto = this.prePay(loanPayReq);
        payRecvOrdDto.setTrxAmt(loanDtlDto.getLoanAmt());

        if (EPrType.DUMMY_PAY.equals(loanPayReq.getPrType())) {
            return payRecvOrdDto;
        }


        LOGGER.debug("pay,{}", loanPayReq);
        LOGGER.debug("pay,{}", loanPayReq.getLoanDtlDto());

        if (loanPayReq.getLoanDtlDto().getDepository() != null && loanPayReq.getLoanDtlDto().getDepository()) {
            return this.depositoryPay(loanPayReq, payRecvOrdDto);
        } else {
            return this.cashierPay(loanPayReq, payRecvOrdDto);
        }
    }

    private PayRecvOrdDto cashierPay(LoanPayReq loanPayReq, PayRecvOrdDto payRecvOrdDto) {
        LoanDtlDto loanDtlDto = loanPayReq.getLoanDtlDto();

        //发起打款
        String appId = AppConfigUtil.getConfig("cashier.pay.appId");
        String sellerId = AppConfigUtil.getConfig("cashier.pay.sellerId");
        String partnerId = AppConfigUtil.getConfig("cashier.pay.partnerId");
        String callback = AppConfigUtil.getConfig("cashier.pay.callback.url");
        LoanPayDto payRequest = new LoanPayDto();
        payRequest.setAppId(appId);
        payRequest.setSellerId(sellerId);
        payRequest.setPartnerId(partnerId);
        payRequest.setBuyerId(loanDtlDto.getUserId());
        payRequest.setBankCardId(loanDtlDto.getBankCardId().toString());
        payRequest.setOutTradeNo(payRecvOrdDto.getOrdId());
        payRequest.setNotifyUrl(callback);
        payRequest.setSrcAmt(NumberUtil.roundTwo(payRecvOrdDto.getTrxAmt()).toString());

        try {
            loanPayService.payForAnother(payRequest);
        } catch (Exception e) {
            LOGGER.error("收银台打款发生异常", e);
        }

        return payRecvOrdDto;
    }

    private PayRecvOrdDto depositoryPay(LoanPayReq loanPayReq, PayRecvOrdDto payRecvOrdDto) {

        LoanDtlDto loanDtlDto = loanPayReq.getLoanDtlDto();

        //账户检测
        LoanDepositoryAcctDto loanDepositoryAcctDto = loanDepositoryAcctService.findByBankCardId(loanDtlDto.getBankCardId());

        //没有开过户则走开户
        if (loanDepositoryAcctDto == null) {
            loanDepositoryAcctDto = new LoanDepositoryAcctDto();
            loanDepositoryAcctDto.setBankCardId(loanDtlDto.getBankCardId());
            loanDepositoryAcctDto.setName(loanPayReq.getBankCardName());
            loanDepositoryAcctDto.setMobile(loanPayReq.getBankCardMobile());
            loanDepositoryAcctDto.setIdCardNo(loanPayReq.getIdCardNo());
            loanDepositoryAcctDto.setBankCardNo(loanPayReq.getBankCardNo());
            loanDepositoryAcctDto = loanDepositoryAcctService.open(loanDepositoryAcctDto);
        }

        PayOutDto payOutDto = new PayOutDto();
        payOutDto.setLoanId(loanDtlDto.getLoanId());
        payOutDto.setAcctNo(loanDepositoryAcctDto.getAcctNo());
        payOutDto.setInterestRt(loanDtlDto.getIntrRateType().getDay(loanDtlDto.getLoanRt()));
        payOutDto.setProjectPeriod(loanDtlDto.getTermType().getDay(loanDtlDto.getTermLen()));
        payOutDto.setProjectAmt(loanDtlDto.getLoanAmt());

        try {
            transactionService.payOut(payOutDto);
        } catch (Exception e) {
            LOGGER.warn("存管打款发生异常:", e);
            payRecvOrdDto.setOrdStatus(EOrdStatus.FAILED);
            loanService.payCallback(loanDtlDto, payRecvOrdDto);
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "存管打款发生异常");
        }

        return payRecvOrdDto;
    }

    @Override
    public void sendPayOkMessage(LoanPayReq loanPayReq, LoanDtlDto loanDtlDto) {
        Map<String, String> params = new HashMap<>();
        params.put("loanDate", loanDtlDto.getLoanDate());
        params.put("loanAmt", loanDtlDto.getLoanAmt().toString());
        String bankCardNo = loanPayReq.getBankCardNo();
        params.put("bankCardNo", bankCardNo.substring(bankCardNo.length() - 4, bankCardNo.length()));
        SmsUtil.send("137", params, loanPayReq.getUserMobile());
    }

    @Override
    public void sendPayOkMessage(LoanDtlDto loanDtlDto, String bankCardNo, String moile) {
        Map<String, String> params = new HashMap<>();
        params.put("loanDate", loanDtlDto.getLoanDate());
        params.put("loanAmt", loanDtlDto.getLoanAmt().toString());
        params.put("bankCardNo", bankCardNo.substring(bankCardNo.length() - 4, bankCardNo.length()));
        SmsUtil.send("137", params, moile);
    }

    @Override
    public List<LoanDtlDto> findByApplIds(Collection<String> applIds) {
        List<LoanDtlDto> rs = new ArrayList<>();
        if (!applIds.isEmpty()) {
            List<String> applIdList = this.toList(applIds);
            List<List<String>> chunks = this.chunk(applIdList, 300);
            for (List<String> chunk : chunks) {
                List<LoanDtlPo> list = loanDtlDao.findByApplIdIn(chunk);
                list.forEach(item -> rs.add(ConverterService.convert(item, LoanDtlDto.class)));
            }
        }
        return rs;
    }

    @Override
    public List<LoanDtlDto> findByBankCardId(Long bankCardId) {
        return ConverterService.convertToList(loanDtlDao.findByBankCardId(bankCardId), LoanDtlDto.class);
    }

    private LoanDtlPo saveLoanDetail(AcctPo acctPo, LoanApplDto applDto, LoanProductDetailDto product, Long cardId) {
        LoanDtlPo loanDtlPo = ConverterService.convert(applDto, LoanDtlPo.class);
        loanDtlPo.setProdId(product.getProductId());
        loanDtlPo.setLoanName(product.getProductName());
        loanDtlPo.setUserId(acctPo.getUserId());
        loanDtlPo.setLoanDate(DateHelper.getWorkDate());
        loanDtlPo.setTermLen(applDto.getApprTermLen());
        loanDtlPo.setLoanAmt(applDto.getApprAmt());
        loanDtlPo.setIntrRateType(applDto.getIntrRateType());
        loanDtlPo.setDutDate(
                DateHelper.formatDate(applDto.getTermType().add(new Date(), applDto.getApprTermLen()))
        );
        loanDtlPo.setLoanStat(ELoanStat.NORMAL);
        loanDtlPo.setRepayedAmt(BigDecimal.ZERO);
        loanDtlPo.setRsrvAmt(BigDecimal.ZERO);
        loanDtlPo.setBankCardId(cardId);

        FinanceSourceConfigDto financeSourceConfigDto = financeSourceService.getConfig(applDto.getFinanceSourceId());
        if (financeSourceConfigDto == null) {
            loanDtlPo.setDepository(false);
        }
        loanDtlPo.setDepository(financeSourceConfigDto.getDepository());
        loanDtlPo.setFinanceSourceId(applDto.getFinanceSourceId());
        loanDtlPo.setTestSource(applDto.getTestSource());
        loanDtlPo.setServiceFeeMonthRt(applDto.getServiceFeeMonthRt());
        loanDtlPo.setServiceFeeRt(applDto.getServiceFeeRt());
        loanDtlDao.save(loanDtlPo);
        return loanDtlPo;
    }

    private void assertAppl(LoanApplDto applDto) {

        if (null == applDto.getApplId() || applDto.getApplStatus() != EApplStatus.SUCCEED) {
            throw new BizServiceException(EErrorCode.LOAN_APPL_CANT_AGREE, "申请单不存在，或申请还未被同意");
        }

        LoanDtlDto loanDtlDto = innerApplService.getLoan(applDto.getApplId());

        if (loanDtlDto != null) {
            throw new BizServiceException(EErrorCode.LOAN_APPL_CANT_AGREE, "已经存在贷款单");
        }

        if (null == applDto.getApprAmt() || applDto.getApprAmt().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizServiceException(EErrorCode.LOAN_APPL_CANT_AGREE, "审批金额错误，无法使用");
        }
    }

    private void asserAcct(AcctPo acctPo, LoanApplDto applDto) {
        if (null == acctPo) {
            throw new BizServiceException(EErrorCode.LOAN_ACCT_STATUS_ERROR);
        }

        if (!acctPo.getAcctNo().equals(applDto.getAcctNo())) {
            throw new BizServiceException(EErrorCode.LOAN_APPL_NOT_YOURS);
        }

    }


    private List<List<String>> chunk(List<String> list, int chunkSize) {
        List<List<String>> chunks = new ArrayList<>();
        int size = list.size() / chunkSize + 1;
        for (int i = 0; i < size; i++) {
            int toIndex = (i + 1) == size ? list.size() : (i + 1) * chunkSize;
            chunks.add(list.subList(i * chunkSize, toIndex));
        }
        return chunks;
    }

    private List<String> toList(Collection<String> c) {
        List<String> list = new ArrayList<>();
        Iterator<String> iter = c.iterator();
        while (iter.hasNext()) {
            list.add(iter.next());
        }
        return list;
    }

    private PayRecvOrdDto prePay(LoanPayReq loanPayReq) {

        LoanDtlPo loanDtlPo = loanDtlDao.findOne(loanPayReq.getLoanId());

        LoanDtlDto loanDtlDto = ConverterService.convert(loanDtlPo, LoanDtlDto.class);

        //状态状态检测，在处理中的订单无法重复发起支付
        if (loanDtlDto.getTransferStat() == ETransferStat.PROCESS) {
            LOGGER.info("{} 有处理中的贷款订单，无法重复发起转账交易", loanDtlDto.getLoanId());
            throw new BizServiceException(EErrorCode.LOAN_RETRY_PAY_DOING, "有处理中的贷款订单，无法重复发起转账交易");
        }

        //更新为转账中状态
        loanDtlPo.setTransferStat(ETransferStat.PROCESS);
        loanDtlDao.save(loanDtlPo);

        PayRecvOrdDto payRecvOrdDto = new PayRecvOrdDto();
        payRecvOrdDto.setPrType(loanPayReq.getPrType());
        payRecvOrdDto.setOrdStatus(EOrdStatus.PROCESS);
        payRecvOrdDto.setUserId(loanDtlDto.getUserId());
        payRecvOrdDto.setAcctNo(loanDtlDto.getAcctNo());
        payRecvOrdDto.setTrxDate(DateHelper.getWorkDate());
        payRecvOrdDto.setBizId(loanDtlDto.getLoanId());
        payRecvOrdDto.setBankCardName(loanPayReq.getBankCardName());
        payRecvOrdDto.setBankCardNo(loanPayReq.getBankCardNo());
        payRecvOrdDto.setTrxAmt(loanDtlDto.getLoanAmt());
        return innerPayRecvOrdService.save(payRecvOrdDto);
    }

}
