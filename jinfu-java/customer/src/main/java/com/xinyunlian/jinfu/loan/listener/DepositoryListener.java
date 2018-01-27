package com.xinyunlian.jinfu.loan.listener;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.cashier.dto.ProcessRetDto;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.coupon.dto.LoanCouponDto;
import com.xinyunlian.jinfu.coupon.service.LoanCouponService;
import com.xinyunlian.jinfu.creditline.service.LoanUserCreditLineService;
import com.xinyunlian.jinfu.loan.dto.RepayDtlDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.enums.ERepayStatus;
import com.xinyunlian.jinfu.loan.enums.ETransferStat;
import com.xinyunlian.jinfu.loan.service.LoanService;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.pay.enums.EOrdStatus;
import com.xinyunlian.jinfu.pay.enums.EPrType;
import com.xinyunlian.jinfu.pay.service.PayRecvOrdService;
import com.xinyunlian.jinfu.promo.dto.UserDto;
import com.xinyunlian.jinfu.promo.service.UserCouponService;
import com.xinyunlian.jinfu.repay.dto.req.RepayReqDto;
import com.xinyunlian.jinfu.repay.dto.resp.LoanCashierCallbackDto;
import com.xinyunlian.jinfu.repay.service.RepayService;
import com.xinyunlian.jinfu.schedule.service.ScheduleService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import com.ylfin.depository.dto.TransactionResultDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author willwang
 */
@Component
public class DepositoryListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepositoryListener.class);

    @Autowired
    private PayRecvOrdService payRecvOrdService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private BankService bankService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private LoanUserCreditLineService loanUserCreditLineService;

    @Autowired
    private RepayService repayService;

    @Autowired
    private LoanCouponService loanCouponService;

    @Autowired
    private UserCouponService userCouponService;

    @JmsListener(destination = DestinationDefine.DEPOSITORY_ASYNC_NOTIFY)
    public void receive(String data){
        LOGGER.info("DepositoryListener, data:{}", data);

        TransactionResultDto transactionResultDto = JsonUtil.toObject(TransactionResultDto.class, data);

        if(EPrType.PAY.getText().equals(transactionResultDto.getType())){
            this.pay(transactionResultDto);
        }else if(EPrType.RECEIVE.getText().equals(transactionResultDto.getType())){
            this.repay(transactionResultDto);
        }
    }

    private void pay(TransactionResultDto transactionResultDto){

        String outTradeNo = transactionResultDto.getOutTradeNo();
        String resultCode = transactionResultDto.getResultCode();
        String resultMessage = transactionResultDto.getResultMessage();
        String channelCode = transactionResultDto.getChannelCode();
        String channelName = transactionResultDto.getChannelName();

        PayRecvOrdDto payRecvOrdDto = payRecvOrdService.findByBizId(outTradeNo);
        LoanDtlDto loanDtlDto = loanService.get(payRecvOrdDto.getBizId());

        //找到银行卡
        BankCardDto bankCardDto = bankService.getBankCard(loanDtlDto.getBankCardId());
        //找到手机号
        UserInfoDto userInfoDto = userService.findUserByUserId(loanDtlDto.getUserId());

        //贷款状态变更控制
        if(loanDtlDto.getTransferDate() != null){
            LOGGER.info("loanId:{}已处理过回调", loanDtlDto.getLoanId());
            return;
        }

        ProcessRetDto processRetDto = new ProcessRetDto();
        processRetDto.setDesc(resultCode);
        processRetDto.setRetCode(resultCode);
        processRetDto.setRetMsg(resultMessage);
        payRecvOrdDto = this.completeReturnResult(payRecvOrdDto, processRetDto);
        payRecvOrdDto.setRetChannelCode(channelCode);
        payRecvOrdDto.setRetChannelName(channelName);

        loanDtlDto = loanService.payCallback(loanDtlDto, payRecvOrdDto);
        if (loanDtlDto.getTransferStat() == ETransferStat.SUCCESS) {
            scheduleService.generate(loanDtlDto.getUserId(), loanDtlDto.getApplId());
            loanService.sendPayOkMessage(loanDtlDto, bankCardDto.getBankCardNo(), userInfoDto.getMobile());
            if("B".equals(loanDtlDto.getTestSource())){
                loanUserCreditLineService.use(loanDtlDto.getUserId());
            }
        }
    }

    private void repay(TransactionResultDto transactionResultDto){

        String passbackParams = transactionResultDto.getPassbackParams();
        String outTradeNo = transactionResultDto.getOutTradeNo();
        String resultCode = transactionResultDto.getResultCode();
        String resultMessage = transactionResultDto.getResultMessage();
        String channelCode = transactionResultDto.getChannelCode();
        String channelName = transactionResultDto.getChannelName();

        RepayReqDto repayReqDto = null;
        try {
            repayReqDto = JsonUtil.toObject(RepayReqDto.class, passbackParams);
        } catch (Exception e) {
            LOGGER.error("自定义参数转换失败", e);
            return;
        }

        if (repayReqDto == null) {
            LOGGER.error("RepayReqDto 转换异常");
            return;
        }

        RepayDtlDto repayDtlDto = repayService.get(outTradeNo);

        if(ERepayStatus.SUCCESS.equals(repayDtlDto.getStatus())){
            LOGGER.info("repay_id:{}已成功还款", repayDtlDto.getRepayId());
            return;
        }

        LoanCashierCallbackDto callback = new LoanCashierCallbackDto();
        callback.setDesc(resultCode);
        callback.setRetMsg(resultMessage);
        callback.setRetChannelCode(channelCode);
        callback.setRetChannelName(channelName);
        try {
            repayService.repay(repayReqDto, repayDtlDto, callback, null);
            //还款成功后，才使用优惠券,标记优惠券为使用状态
            if("SUCCESS".equals(resultCode)){
                this.useCoupon(outTradeNo);
            }
        } catch (Exception e) {
            LOGGER.error("发起还款发生异常", e);
            return;
        }
    }

    private PayRecvOrdDto completeReturnResult(PayRecvOrdDto payRecvOrdDto, ProcessRetDto processRetDto) {
        if (processRetDto == null) {
            return payRecvOrdDto;
        }
        if (StringUtils.isEmpty(processRetDto.getDesc())) {
            LOGGER.error("processRetDto desc is null");
            return payRecvOrdDto;
        }
        Map<String, EOrdStatus> statusMap = new HashMap<>();
        statusMap.put("PROCESS", EOrdStatus.PROCESS);
        statusMap.put("SUCCESS", EOrdStatus.SUCCESS);
        statusMap.put("FAILED", EOrdStatus.FAILED);
        EOrdStatus status = statusMap.get(processRetDto.getDesc());

        payRecvOrdDto.setOrdStatus(status != null ? status : EOrdStatus.PROCESS);
        payRecvOrdDto.setRetCode(processRetDto.getRetCode());
        payRecvOrdDto.setRetMsg(processRetDto.getRetMsg());

        return payRecvOrdDto;
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

}
