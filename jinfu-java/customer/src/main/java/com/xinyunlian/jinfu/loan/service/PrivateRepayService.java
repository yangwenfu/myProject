package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.calc.dto.LoanCalcDto;
import com.xinyunlian.jinfu.cashier.api.JinfuCashierSignature;
import com.xinyunlian.jinfu.cashier.dto.req.CloseReqDto;
import com.xinyunlian.jinfu.cashier.dto.req.QueryReqDto;
import com.xinyunlian.jinfu.cashier.dto.resp.CloseRespDto;
import com.xinyunlian.jinfu.cashier.dto.resp.QueryRespDto;
import com.xinyunlian.jinfu.cashier.service.CashierService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.coupon.dto.LoanCouponDto;
import com.xinyunlian.jinfu.coupon.dto.LoanCouponRepayDto;
import com.xinyunlian.jinfu.coupon.service.LoanCouponService;
import com.xinyunlian.jinfu.depository.dto.LoanDepositoryAcctDto;
import com.xinyunlian.jinfu.depository.service.LoanDepositoryAcctService;
import com.xinyunlian.jinfu.loan.dto.RepayDtlDto;
import com.xinyunlian.jinfu.loan.dto.repay.RepayHistoryItemDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.enums.ERepayStatus;
import com.xinyunlian.jinfu.loan.enums.ETransMode;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.pay.enums.EPrType;
import com.xinyunlian.jinfu.pay.service.PayRecvOrdService;
import com.xinyunlian.jinfu.promo.dto.UserCouponDto;
import com.xinyunlian.jinfu.promo.dto.UserDto;
import com.xinyunlian.jinfu.promo.service.UserCouponService;
import com.xinyunlian.jinfu.repay.dto.LoanRepayRespDto;
import com.xinyunlian.jinfu.repay.dto.req.RepayReqDto;
import com.xinyunlian.jinfu.repay.dto.resp.LoanCashierCallbackDto;
import com.xinyunlian.jinfu.repay.service.RepayService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import com.ylfin.depository.dto.RepayDto;
import com.ylfin.depository.service.TransactionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Willwang on 2017/1/6.
 */
@Service
public class PrivateRepayService {

    @Autowired
    private RepayService repayService;

    @Autowired
    private LoanCouponService loanCouponService;

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private UserService userService;

    @Autowired
    private PrivateLoanService privateLoanService;

    @Autowired
    private CashierService cashierService;

    @Autowired
    private PayRecvOrdService payRecvOrdService;

    @Autowired
    private PrivateCalcService privateCalcService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private LoanDepositoryAcctService loanDepositoryAcctService;

    @Autowired
    private LoanService loanService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateRepayService.class);

    /**
     * 还款/重新还款
     * @param request
     */
    private Object[] start(RepayReqDto request){

        String userId = SecurityContext.getCurrentUserId();
        RepayDtlDto repayDtlDto;
        LoanCalcDto loanCalcDto;
        //不存在repayId属于用户第一次发起还款操作
        if(StringUtils.isEmpty(request.getRepayId())){
            loanCalcDto = privateCalcService.calc(userId, request);
            repayDtlDto = repayService.inAdvanceRepayWithCalc(userId, request, EPrType.CASHIER_RECEIVE, loanCalcDto);
            //还款使用了优惠券，进行记录
            this.saveCoupon(repayDtlDto, loanCalcDto.getCoupon().getCouponId());
        }else{
            repayDtlDto = repayService.get(request.getRepayId());
            //构建request
            request.setLoanId(repayDtlDto.getLoanId());
            request.setAmt(NumberUtil.roundTwo(repayDtlDto.getTotal()));
            request.setRepayType(repayDtlDto.getRepayType());
            PayRecvOrdDto payRecvOrdDto = payRecvOrdService.findByBizId(request.getRepayId());
            if(payRecvOrdDto == null || payRecvOrdDto.getTrxAmt() == null || payRecvOrdDto.getTrxAmt().compareTo(BigDecimal.ZERO) <= 0){
                throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "重新还款金额异常，无法发起");
            }

            //更新还款时间
            repayDtlDto.setRepayDateTime(new Date());
            repayService.save(repayDtlDto);
            loanCalcDto = new LoanCalcDto();
            loanCalcDto.setPromoTotal(payRecvOrdDto.getTrxAmt());
        }

        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("start repay, request:{}, calc:{}", request, loanCalcDto);
        }

        return new Object[]{repayDtlDto, loanCalcDto};
    }

    /**
     * 收银台发起还款
     * @param request
     * @return
     */
    public Map<String, String> cashierRepayStart(RepayReqDto request){
        String userId = SecurityContext.getCurrentUserId();

        Object[] params = this.start(request);

        return this.getCashierParams(userId, (RepayDtlDto)params[0], request, (LoanCalcDto)params[1]);
    }

    /**
     * 存管发起还款
     */
    public void depositoryRepayStart(RepayReqDto request) throws BizServiceException{

        Object[] params = this.start(request);
        RepayDtlDto repayDtlDto = (RepayDtlDto)params[0];
        LoanCalcDto loanCalcDto = (LoanCalcDto)params[1];

        if(loanCalcDto.getPromoTotal().compareTo(request.getExpected()) != 0){
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "还款金额已过期，请重新刷新");
        }

        RepayDto repayDto = new RepayDto();

        LoanDtlDto loanDtlDto = loanService.get(repayDtlDto.getLoanId());

        LoanDepositoryAcctDto loanDepositoryAcctDto = loanDepositoryAcctService.findByBankCardId(loanDtlDto.getBankCardId());

        repayDto.setLoanId(repayDtlDto.getLoanId());
        repayDto.setAcctNo(loanDepositoryAcctDto.getAcctNo());
        repayDto.setRepayId(repayDtlDto.getRepayId());
        repayDto.setRepayCapital(repayDtlDto.getRepayPrinAmt());
        repayDto.setRepayInterest(
            repayDtlDto.getRepayIntr().add(repayDtlDto.getRepayFine()).add(repayDtlDto.getRepayFee())
        );


        if (request.getCouponIds() == null) {
            List<Long> tt = new ArrayList<>();
            request.setCouponIds(tt);
        }

        repayDto.setPassbackParams(JsonUtil.toJson(request));

        try{
            transactionService.repay(repayDto);
        }catch(Exception e){
            LOGGER.error("存管还款发起失败", e);

            //强制性调用还款失败逻辑
            repayService.repayFailed(repayDtlDto);
            
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "存管还款发起失败", e);
        }
    }

    /**
     * 获取唤起收银台所需的参数
     *
     * @param request
     * @return
     */
    public Map<String, String> getCashierParams(String userId, RepayDtlDto repayDtlDto, RepayReqDto request, LoanCalcDto loanCalcDto) {
        String appId = AppConfigUtil.getConfig("cashier.pay.appId");
        String sellerId = AppConfigUtil.getConfig("cashier.pay.sellerId");
        String partnerId = AppConfigUtil.getConfig("cashier.pay.partnerId");
        String key = AppConfigUtil.getConfig("cashier.pay.key");
        String callbackUrl = AppConfigUtil.getConfig("cashier.repay.callback.url");
        Map<String, String> params = new HashMap<>();

        if (request.getCouponIds() == null) {
            List<Long> tt = new ArrayList<>();
            request.setCouponIds(tt);
        }

        params.put("appId", appId);
        params.put("sellerId", sellerId);
        params.put("partnerId", partnerId);
        params.put("buyerId", userId);
        params.put("totalAmount", NumberUtil.roundTwo(loanCalcDto.getPromoTotal()).toString());
        params.put("outTradeNo", repayDtlDto.getRepayId());
        params.put("timestamp", String.valueOf(System.currentTimeMillis()).substring(0, 10));
        String goodsDesc = "[{\"goodName\":\"" + "贷款:" + request.getLoanId() + "\", \"unitPrice\":\"1\",\"quantity\":\"1\"}]";
        params.put("goodsDesc", goodsDesc);
        params.put("notifyMerchantUrl", callbackUrl);
        params.put("passbackParams", JsonUtil.toJson(request));
        params.put("charset", "UTF-8");
        params.put("signType", "MD5");
        params.put("validMinutes", "15");
        params.put("apiVersion", "2.0");

        String sign = JinfuCashierSignature.computeSign(params, key, "UTF-8", "MD5");
        params.put("sign", sign);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("repay request:{}", request);
            LOGGER.debug("repay params:{}", params);
        }

        return params;
    }

    /**
     * 收银台异步回调处理
     *
     * @param request
     * @return
     */
    public String cashierCallback(HttpServletRequest request) {
        Map<String, String> responseMap = new HashMap<>();
        Enumeration em = request.getParameterNames();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = privateLoanService.xssDecoder(request.getParameter(name));
            responseMap.put(name, value);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("repay callback params,{}", this.getMapString(responseMap));
        }


        if (!JinfuCashierSignature.verifySign(responseMap, AppConfigUtil.getConfig("cashier.pay.key"), true)) {
            LOGGER.error("验签失败");
            return "FAILED";
        }

        return this.callback(responseMap);
    }

    private String callback(Map<String, String> params){
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("repay callback params,{}", this.getMapString(params));
        }

        RepayReqDto repayReqDto;
        try {
            repayReqDto = JsonUtil.toObject(RepayReqDto.class, params.get("passbackParams"));
        } catch (Exception e) {
            LOGGER.error("自定义参数转换失败", e);
            LOGGER.error("passbackParams:{}", params.get("passbackParams"));
            return "FAILED";
        }
        if (repayReqDto == null) {
            LOGGER.error("RepayReqDto 转换异常");
            return "FAILED";
        }

        RepayDtlDto repayDtlDto = repayService.get(params.get("outTradeNo"));


        if(ERepayStatus.SUCCESS.equals(repayDtlDto.getStatus())){
            LOGGER.info("repay_id:{}已成功还款", repayDtlDto.getRepayId());
            return "SUCCESS";
        }

        LoanCashierCallbackDto callback = new LoanCashierCallbackDto();
        callback.setDesc(params.get("resultCode"));
        callback.setRetMsg(params.get("resultMessage"));
        callback.setRetChannelCode(params.get("channelCode"));
        callback.setRetChannelName(params.get("channelName"));
        try {
            repayService.repay(repayReqDto, repayDtlDto, callback, null);
            //还款成功后，才使用优惠券,标记优惠券为使用状态
            if("SUCCESS".equals(params.get("resultCode"))){
                this.useCoupon(params.get("outTradeNo"));
            }
            return "SUCCESS";
        } catch (Exception e) {
            LOGGER.error("发起还款发生异常", e);
            return "FAILED";
        }
    }

    /**
     * 关闭还款单
     *
     * @param repayId
     * @return
     * @throws BizServiceException
     */
    public void close(String repayId) throws BizServiceException {
        String appId = AppConfigUtil.getConfig("cashier.pay.appId");
        String partnerId = AppConfigUtil.getConfig("cashier.pay.partnerId");
        String key = AppConfigUtil.getConfig("cashier.pay.key");
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        String charset = "UTF-8";
        String signType = "MD5";
        String apiVersion = "2.0";

        Map<String, String> params = new HashMap<>();
        params.put("appId", appId);
        params.put("partnerId", partnerId);
        params.put("timestamp", timestamp);
        params.put("charset", charset);
        params.put("signType", signType);
        params.put("outTradeNo", repayId);
        params.put("apiVersion", apiVersion);

        String sign = JinfuCashierSignature.computeSign(params, key, charset, signType);

        CloseReqDto closeReqDto = new CloseReqDto();
        closeReqDto.setAppId(appId);
        closeReqDto.setPartnerId(partnerId);
        closeReqDto.setCharset("UTF-8");
        closeReqDto.setSignType("MD5");
        closeReqDto.setTimestamp(timestamp);
        closeReqDto.setOutTradeNo(repayId);
        closeReqDto.setSign(sign);
        closeReqDto.setApiVersion(apiVersion);

        try {
            CloseRespDto closeRespDto = cashierService.close(closeReqDto);
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("close repay, repay_id:{}, status:{}, message:{}", repayId, closeRespDto.getResultCode(), closeRespDto.getResultMessage());
            }
            if("SUCCESS".equals(closeRespDto.getResultCode())){
                RepayDtlDto repayDtlDto = repayService.get(repayId);
                repayDtlDto.setStatus(ERepayStatus.FAILED);
                repayService.save(repayDtlDto);
            }
        } catch (Exception e) {
            LOGGER.error("cashier-service close error", e);
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "调用收银台关闭订单发生异常");
        }
    }

    /**
     * 收银台状态查询
     *
     * @param repayId
     * @return
     * @throws BizServiceException
     */
    private RepayDtlDto getLatestCashierStatus(String repayId) throws BizServiceException {
        RepayDtlDto repayDtlDto = repayService.get(repayId);

        String appId = AppConfigUtil.getConfig("cashier.pay.appId");
        String partnerId = AppConfigUtil.getConfig("cashier.pay.partnerId");
        String key = AppConfigUtil.getConfig("cashier.pay.key");
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        String charset = "UTF-8";
        String signType = "MD5";
        String apiVersion = "2.0";

        Map<String, String> params = new HashMap<>();
        params.put("appId", appId);
        params.put("partnerId", partnerId);
        params.put("timestamp", timestamp);
        params.put("charset", charset);
        params.put("signType", signType);
        params.put("outTradeNo", repayId);
        params.put("apiVersion", apiVersion);

        String sign = JinfuCashierSignature.computeSign(params, key, "UTF-8", "MD5");

        QueryReqDto queryReqDto = new QueryReqDto();
        queryReqDto.setAppId(appId);
        queryReqDto.setPartnerId(partnerId);
        queryReqDto.setCharset("UTF-8");
        queryReqDto.setSignType("MD5");
        queryReqDto.setTimestamp(timestamp);
        queryReqDto.setOutTradeNo(repayId);
        queryReqDto.setSign(sign);
        queryReqDto.setApiVersion(apiVersion);

        try {
            QueryRespDto queryRespDto = cashierService.query(queryReqDto);

            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("query repay, repay_id:{}, status:{}, message:{}", repayId, queryRespDto.getResultCode(), queryRespDto.getResultMessage());
            }

            if (StringUtils.isEmpty(queryRespDto.getResultCode())) {
                throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "收银台参数:resultCode缺失");
            }

            ERepayStatus repayStatus = this.cashierStatusToRepayStatus(queryRespDto.getResultCode());

            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("repay_id:{}, status:{}", repayId, repayStatus.getText());
            }

            //如果状态不一致，则同步收银台状态
            if (!repayDtlDto.getStatus().equals(repayStatus)) {
                repayDtlDto.setStatus(repayStatus);
                repayService.save(repayDtlDto);
            }

        } catch (Exception e) {
            LOGGER.error("cashier-service status error", e);
        }

        return repayDtlDto;
    }

    /**
     * 还款记录列表
     * @param loanId
     * @return
     */
    public List<RepayHistoryItemDto> list(String loanId) {
        List<RepayHistoryItemDto> rs = new ArrayList<>();

        List<RepayDtlDto> repayDtlDtos = repayService.listByLoanId(loanId);

        //提取RepayIds,进行优惠券查询
        List<String> repayIds = new ArrayList<>();
        Map<String, List<LoanCouponDto>> couponMap = new HashMap<>();
        repayDtlDtos.forEach(repayDtlDto -> repayIds.add(repayDtlDto.getRepayId()));
        List<LoanCouponDto> allLoanCouponDtos = loanCouponService.listByRepayIds(repayIds);
        allLoanCouponDtos.forEach(loanCouponDto -> {
            List<LoanCouponDto> loanCouponDtos = couponMap.get(loanCouponDto.getRepayId());
            if(loanCouponDtos == null){
                loanCouponDtos = new ArrayList<>();
            }
            loanCouponDtos.add(loanCouponDto);
            couponMap.put(loanCouponDto.getRepayId(), loanCouponDtos);
        });

        for (RepayDtlDto repayDtlDto : repayDtlDtos) {
            RepayHistoryItemDto item = ConverterService.convert(repayDtlDto, RepayHistoryItemDto.class);
            item.setRepayDate(
                    DateHelper.formatDate(repayDtlDto.getRepayDateTime(), DateHelper.SIMPLE_DATE_YMDHM_CN)
            );

            item.setRepayStatus(repayDtlDto.getStatus());

            LoanCouponRepayDto loanCouponRepayDto = this.getCoupon(couponMap.get(repayDtlDto.getRepayId()), repayDtlDto.getRepayIntr());

            BigDecimal couponPrice = BigDecimal.ZERO;
            if (loanCouponRepayDto != null && loanCouponRepayDto.getPrice() != null) {
                couponPrice = loanCouponRepayDto.getPrice();
            }

            BigDecimal capital = AmtUtils.positive(repayDtlDto.getRepayPrinAmt());
            BigDecimal interest = AmtUtils.positive(repayDtlDto.getRepayIntr());
            BigDecimal fine = AmtUtils.positive(repayDtlDto.getRepayFine());
            BigDecimal fee = AmtUtils.positive(repayDtlDto.getRepayFee());

            item.setAmt(
                AmtUtils.positive(capital.add(interest).add(fine).add(fee).subtract(couponPrice))
            );

            item.setAmt(NumberUtil.roundTwo(item.getAmt()));

            rs.add(item);
        }

        return rs;
    }

    /**
     * 还款详情
     * @return
     */
    public LoanRepayRespDto detail(String repayId){
        RepayDtlDto repayDtlDto = repayService.get(repayId);

        LoanRepayRespDto resp = new LoanRepayRespDto();

        resp.setRepayDate(
            DateHelper.formatDate(repayDtlDto.getRepayDateTime(), DateHelper.SIMPLE_DATE_YMDHM_CN)
        );

        resp.setRepayStatus(repayDtlDto.getStatus());
        resp.setTransMode(repayDtlDto.getTransMode());
        resp.setRepayId(repayDtlDto.getRepayId());

        BigDecimal capital = AmtUtils.positive(repayDtlDto.getRepayPrinAmt());
        BigDecimal interest = AmtUtils.positive(repayDtlDto.getRepayIntr());
        BigDecimal fine = AmtUtils.positive(repayDtlDto.getRepayFine());
        BigDecimal fee = AmtUtils.positive(repayDtlDto.getRepayFee());
        resp.setCapital(capital);
        resp.setInterest(interest);
        resp.setFine(fine);
        resp.setFee(fee);

        List<LoanCouponDto> loanCouponDtos = loanCouponService.listByRepayId(repayId);
        LoanCouponRepayDto loanCouponRepayDto = this.getCoupon(loanCouponDtos, repayDtlDto.getRepayIntr());

        BigDecimal couponPrice = BigDecimal.ZERO;
        if (loanCouponRepayDto != null && loanCouponRepayDto.getPrice() != null) {
            couponPrice = loanCouponRepayDto.getPrice();
        }

        if(loanCouponRepayDto != null){
            resp.setCoupon(loanCouponRepayDto);
        }

        resp.setAmt(
            AmtUtils.positive(capital.add(interest).add(fine).add(fee).subtract(couponPrice))
        );

        resp.setCapital(NumberUtil.roundTwo(resp.getCapital()));
        resp.setInterest(NumberUtil.roundTwo(resp.getInterest()));
        resp.setFine(NumberUtil.roundTwo(resp.getFine()));
        resp.setFee(NumberUtil.roundTwo(resp.getFee()));
        resp.setAmt(NumberUtil.roundTwo(resp.getAmt()));

        PayRecvOrdDto payRecvOrdDto = payRecvOrdService.findByBizId(repayId);
        if(payRecvOrdDto != null && ERepayStatus.FAILED.equals(repayDtlDto.getStatus())){
            String message = StringUtils.isEmpty(payRecvOrdDto.getRetMsg()) ? "订单已关闭" : payRecvOrdDto.getRetMsg();
            resp.setMessage(message);
        }

        return resp;
    }

    private LoanCouponRepayDto getCoupon( List<LoanCouponDto> coupons, BigDecimal calcPrice){
        if(CollectionUtils.isEmpty(coupons)){
            return null;
        }
        LoanCouponDto coupon = coupons.get(0);
        if(coupon == null){
            return null;
        }
        LoanCouponRepayDto loanCouponRepayDto = new LoanCouponRepayDto();

        //todo 认为目前的券都是免息券
        BigDecimal price = AmtUtils.min(calcPrice, coupon.getPrice());
        loanCouponRepayDto.setPrice(price);

        loanCouponRepayDto.setCouponType(coupon.getCouponType());

        return loanCouponRepayDto;
    }

    private Set<String> getCashierRepayList(){

        String userId = SecurityContext.getCurrentUserId();

        List<PayRecvOrdDto> payRecvOrdDtos = payRecvOrdService.findByUserId(userId);

        Set<String> rs = new HashSet<>();

        if(CollectionUtils.isNotEmpty(payRecvOrdDtos)){
            for (PayRecvOrdDto payRecvOrdDto : payRecvOrdDtos) {
                if(payRecvOrdDto.getPrType() == EPrType.CASHIER_RECEIVE) {
                    rs.add(payRecvOrdDto.getBizId());
                }
            }
        }

        return rs;
    }

    /**
     * 在列表中以收银台
     *
     * @param list
     * @return
     */
    public List<RepayHistoryItemDto> fixStatus(List<RepayHistoryItemDto> list) {

        Set<String> cashierRepayIds = this.getCashierRepayList();

        if (list.isEmpty()) {
            return list;
        }

        list.stream().filter(repayHistoryItemDto -> Arrays.asList(ERepayStatus.PAY, ERepayStatus.PROCESS).contains(repayHistoryItemDto.getRepayStatus())).filter(repayHistoryItemDto -> ETransMode.COUNTER.equals(repayHistoryItemDto.getTransMode())).forEach(repayHistoryItemDto -> {
            if(cashierRepayIds.contains(repayHistoryItemDto.getRepayId())){
                RepayDtlDto repayDtlDto = this.getLatestCashierStatus(repayHistoryItemDto.getRepayId());
                repayHistoryItemDto.setRepayStatus(repayDtlDto.getStatus());
            }
        });

        return list;
    }

    public LoanRepayRespDto fixStatus(LoanRepayRespDto loanRepayRespDto){

        Set<String> cashierRepayIds = this.getCashierRepayList();

        if(!Arrays.asList(ERepayStatus.PAY, ERepayStatus.PROCESS).contains(loanRepayRespDto.getRepayStatus())){
            return loanRepayRespDto;
        }
        if(!ETransMode.COUNTER.equals(loanRepayRespDto.getTransMode())){
            return loanRepayRespDto;
        }

        if(cashierRepayIds.contains(loanRepayRespDto.getRepayId())){
            RepayDtlDto repayDtlDto = this.getLatestCashierStatus(loanRepayRespDto.getRepayId());
            loanRepayRespDto.setRepayStatus(repayDtlDto.getStatus());
        }

        return loanRepayRespDto;
    }

    private ERepayStatus cashierStatusToRepayStatus(String cashierStatus) {
        Map<String, ERepayStatus> map = new HashMap<>();
        map.put("PAY", ERepayStatus.PAY);
        map.put("PROCESSING", ERepayStatus.PROCESS);
        map.put("SUCCESS", ERepayStatus.PROCESS);
        map.put("CLOSED", ERepayStatus.FAILED);
        return map.get(cashierStatus) != null ? map.get(cashierStatus) : ERepayStatus.PAY;
    }

    /**
     * 记录优惠券关联关系
     *
     * @param repay
     * @param couponId
     */
    private void saveCoupon(RepayDtlDto repay, Long couponId) {
        if (couponId == null || couponId.equals(0L)) {
            return;
        }
        UserCouponDto userCouponDto = userCouponService.getUserCoupon(couponId);
        if (userCouponDto == null) {
            return;
        }
        LoanCouponDto loanCouponDto = ConverterService.convert(userCouponDto, LoanCouponDto.class);
        loanCouponDto.setCouponType(userCouponDto.getCouponType().getText());
        loanCouponDto.setCouponId(userCouponDto.getId());
        loanCouponDto.setRepayId(repay.getRepayId());
        loanCouponDto.setLoanId(repay.getLoanId());

        loanCouponService.save(loanCouponDto);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("saveCoupon, loanCouponDto:{}", loanCouponDto);
        }
    }

    /**
     * 标记优惠券为使用状态
     *
     * @param repayId
     */
    private void useCoupon(String repayId) {
        List<LoanCouponDto> list = loanCouponService.listByRepayId(repayId);

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        for (LoanCouponDto loanCouponDto : list) {
            UserInfoDto userInfoDto = userService.findUserByUserId(loanCouponDto.getUserId());

            if (userInfoDto == null) {
                continue;
            }

            UserDto userDto = new UserDto();
            userDto.setUserId(userInfoDto.getUserId());
            userDto.setEmail(userInfoDto.getEmail());
            userDto.setIdCardNo(userInfoDto.getIdCardNo());
            userDto.setMobile(userInfoDto.getMobile());
            userDto.setUserName(userInfoDto.getUserName());

            userCouponService.useCoupon(userDto, loanCouponDto.getCouponId());
        }

    }

    private String getMapString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append("key:")
                    .append(entry.getKey())
                    .append(",value:")
                    .append(entry.getValue());
        }
        return sb.toString();
    }

}
