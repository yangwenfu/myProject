package com.xinyunlian.jinfu.balance.service;

import com.xinyunlian.jinfu.balance.dao.BalanceCashierDao;
import com.xinyunlian.jinfu.balance.dao.BalanceDetailDao;
import com.xinyunlian.jinfu.balance.dao.BalanceOutlineDao;
import com.xinyunlian.jinfu.balance.dto.*;
import com.xinyunlian.jinfu.balance.entity.BalanceCashierPo;
import com.xinyunlian.jinfu.balance.entity.BalanceDetailPo;
import com.xinyunlian.jinfu.balance.entity.BalanceOutlinePo;
import com.xinyunlian.jinfu.balance.enums.EBalanceOutlineStatus;
import com.xinyunlian.jinfu.balance.enums.EBalanceStatus;
import com.xinyunlian.jinfu.calc.dto.LoanCalcDto;
import com.xinyunlian.jinfu.calc.service.LoanCalcService;
import com.xinyunlian.jinfu.common.constant.ApplicationConstant;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.AmtUtils;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.LoanUtils;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.coupon.dto.LoanCouponDto;
import com.xinyunlian.jinfu.coupon.dto.LoanCouponRepayDto;
import com.xinyunlian.jinfu.coupon.service.LoanCouponService;
import com.xinyunlian.jinfu.loan.dao.LoanDtlDao;
import com.xinyunlian.jinfu.loan.dto.repay.LinkRepayDto;
import com.xinyunlian.jinfu.loan.entity.LoanDtlPo;
import com.xinyunlian.jinfu.loan.entity.RepayDtlPo;
import com.xinyunlian.jinfu.loan.enums.ERepayStatus;
import com.xinyunlian.jinfu.loan.service.InnerApplService;
import com.xinyunlian.jinfu.loan.service.InnerLinkRepayService;
import com.xinyunlian.jinfu.pay.dao.PayRecvOrdDao;
import com.xinyunlian.jinfu.pay.entity.PayRecvOrdPo;
import com.xinyunlian.jinfu.pay.enums.EOrdStatus;
import com.xinyunlian.jinfu.pay.enums.EPrType;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.product.enums.EFineType;
import com.xinyunlian.jinfu.repay.dao.RepayDao;
import com.xinyunlian.jinfu.schedule.dao.ScheduleDao;
import com.xinyunlian.jinfu.schedule.entity.SchedulePo;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Willwang on 2017/5/23.
 */
@Component
public class BalanceDetailServiceImpl implements BalanceDetailService {

    @Autowired
    private RepayDao repayDao;

    @Autowired
    private PayRecvOrdDao payRecvOrdDao;

    @Autowired
    private LoanDtlDao loanDtlDao;

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private LoanCalcService loanCalcService;

    @Autowired
    private InnerLinkRepayService innerLinkRepayService;

    @Autowired
    private InnerApplService innerApplService;

    @Autowired
    private BalanceCashierDao balanceCashierDao;

    @Autowired
    private BalanceDetailDao balanceDetailDao;

    @Autowired
    private BalanceOutlineDao balanceOutlineDao;

    @Autowired
    private LoanCouponService loanCouponService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BalanceDetailServiceImpl.class);

    @Override
    public BalanceDetailDto detail(Long detailId) throws BizServiceException {

        BalanceDetailPo balanceDetailPo = balanceDetailDao.findOne(detailId);

        BalanceDetailDto balanceDetailDto = new BalanceDetailDto();

        RepayDtlPo repayDtlPo = repayDao.findOne(balanceDetailPo.getRepayId());

        balanceDetailDto.setDetail(this.getBalanceDetailInteriorDto(detailId, repayDtlPo, balanceDetailPo));
        balanceDetailDto.setLoan(this.getBalanceDetailLoanDto(repayDtlPo));
        balanceDetailDto.setSchedules(this.roundTwo(this.getBalanceDetailScheduleDtos(repayDtlPo)));

        return balanceDetailDto;
    }

    private List<BalanceDetailScheduleDto> roundTwo(List<BalanceDetailScheduleDto> list){
        list.forEach(item -> {
            item.setShouldCapital(NumberUtil.roundTwo(item.getShouldCapital()));
            item.setShouldInterest(NumberUtil.roundTwo(item.getShouldInterest()));
            item.setShouldFine(NumberUtil.roundTwo(item.getShouldFine()));
            item.setShouldFee(NumberUtil.roundTwo(item.getShouldFee()));
        });

        return list;
    }

    @Override
    public BalanceDetailListDto list(Long outlineId) throws BizServiceException {

        BalanceDetailListDto balanceDetailListDto = new BalanceDetailListDto();

        balanceDetailListDto.setCashiers(this.getBalanceDetailListCashierDtos(outlineId));
        balanceDetailListDto.setLoans(this.getBalanceDetailListLoanDtos(outlineId));

        return balanceDetailListDto;
    }

    @Override
    @Transactional
    public void auto(Long outlineId) throws BizServiceException {

        BalanceOutlinePo balanceOutlinePo = balanceOutlineDao.findOne(outlineId);

        //已经自动勾对过，不用重新勾对
        if (balanceOutlinePo.getAutoed()) {
            return;
        }

        BalanceDetailListDto balanceDetailListDto = this.list(outlineId);

        if (balanceDetailListDto.getLoans() == null ||
                balanceDetailListDto.getLoans().isEmpty() ||
                balanceDetailListDto.getCashiers() == null ||
                balanceDetailListDto.getCashiers().isEmpty()) {
            throw new BizServiceException(EErrorCode.LOAN_BALANCE_DATA_NOT_ENOUGH, "对账基础数据不足");
        }

        int balancedCounter = 0;

        for (BalanceDetailListLoanDto item : balanceDetailListDto.getLoans()) {
            if (this.find(item, balanceDetailListDto.getCashiers())) {
                balancedCounter++;
            }
        }

        if (balancedCounter == balanceDetailListDto.getLoans().size()) {
            balanceOutlineDao.updateBalanceOutlineStatusAndBalanceDateById(EBalanceOutlineStatus.ALREADY, new Date(), outlineId);
        }

        //更新自动勾对状态
        balanceOutlinePo.setAutoed(true);
        balanceOutlineDao.save(balanceOutlinePo);
    }

    @Override
    @Transactional
    public void cancel(Long detailId) throws BizServiceException {
        BalanceDetailPo balanceDetailPo = balanceDetailDao.findOne(detailId);
        balanceDetailPo.setBalanceStatus(EBalanceStatus.NOT);
        balanceDetailPo.setBalanceDate(null);
        balanceDetailDao.save(balanceDetailPo);
    }

    @Override
    @Transactional
    public void manual(String mgtUserId, BalanceDetailDto balanceDetailDto) {
        Long detailId = balanceDetailDto.getDetail().getDetailId();
        String repayId = balanceDetailDto.getDetail().getRepayId();
        String channelCode = balanceDetailDto.getDetail().getChannelCode();
        String channelName = balanceDetailDto.getDetail().getChannelName();
        String remark = balanceDetailDto.getDetail().getRemark();


        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("manual balance begin,user:{},detailId:{}", mgtUserId, detailId);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("balance manual, repay_id:{}, channelCode:{}, channelName:{}, remark:{}", repayId, channelCode, channelName, remark);
        }

        List<PayRecvOrdPo> payRecvOrdPos = payRecvOrdDao.findByBizId(repayId);
        PayRecvOrdPo payRecvOrdPo = payRecvOrdPos.size() > 0 ? payRecvOrdPos.get(0) : null;

        if(payRecvOrdPo == null){
            //手动勾对后，如果不存在payRecvOrd，则增加一条
            payRecvOrdPo = new PayRecvOrdPo();
            payRecvOrdPo.setPrType(EPrType.CASHIER_RECEIVE);
            payRecvOrdPo.setBizId(repayId);
            payRecvOrdPo.setTrxMemo("手工对账新增记录：" + remark);
        }else{
            payRecvOrdPo.setTrxMemo(remark);
        }

        payRecvOrdPo.setRetChannelName(channelName);

        payRecvOrdDao.save(payRecvOrdPo);

        for (BalanceDetailScheduleDto scheduleDto : balanceDetailDto.getSchedules()) {
            SchedulePo schedulePo = scheduleDao.findOne(scheduleDto.getScheduleId());
            schedulePo.setPayDate(scheduleDto.getPayDate());
            schedulePo.setScheduleStatus(scheduleDto.getStatus());

            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("manual balance update schedule,scheduleId:{},payDate:{}, status:{}", scheduleDto.getScheduleId()
                        , scheduleDto.getPayDate(), scheduleDto.getStatus().getText());
            }

            scheduleDao.save(schedulePo);
        }

        BalanceDetailPo balanceDetailPo = balanceDetailDao.findOne(detailId);
        balanceDetailPo.setBalanceDate(new Date());
        balanceDetailPo.setBalanceStatus(EBalanceStatus.ALREADY);
        balanceDetailPo.setChannelName(channelName);

        balanceDetailDao.save(balanceDetailPo);
    }

    private boolean isClear(Long outlineId){

        List<BalanceDetailPo> balanceDetailPos = balanceDetailDao.findByOutlineId(outlineId);

        for (BalanceDetailPo balanceDetailPo : balanceDetailPos) {
            if(EBalanceStatus.NOT.equals(balanceDetailPo.getBalanceStatus())){
                return false;
            }
        }

        return true;
    }


    private boolean isMatch(BalanceDetailListLoanDto item, BalanceDetailListCashierDto cashier) {
        return item.getRepayId().equals(cashier.getBizId())
                && item.getRepayAmt().equals(cashier.getPayAmt())
                && ERepayStatus.SUCCESS.equals(item.getRepayStatus())
                && "成功".equals(cashier.getPayStatus());
    }

    private boolean find(BalanceDetailListLoanDto item, List<BalanceDetailListCashierDto> targetList) {

        for (BalanceDetailListCashierDto balanceDetailListCashierDto : targetList) {
            if (!this.isMatch(item, balanceDetailListCashierDto)) {
                continue;
            }

            Date now = new Date();

            balanceDetailDao.updateStatusAndBalanceDateById(EBalanceStatus.ALREADY.getCode(), now, item.getDetailId());
            balanceCashierDao.updateStatusAndBalanceDateById(EBalanceStatus.ALREADY.getCode(), now, balanceDetailListCashierDto.getId());

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("auto balance, find matched, detail_id:{},cashier_id:{}", item.getDetailId(), balanceDetailListCashierDto.getId());
            }

            return true;
        }

        return false;

    }

    private List<BalanceDetailListCashierDto> getBalanceDetailListCashierDtos(Long outlineId) {
        List<BalanceDetailListCashierDto> balanceDetailListDtos = new ArrayList<>();

        List<BalanceCashierPo> cashierPos = balanceCashierDao.findByOutlineId(outlineId);

        for (BalanceCashierPo cashierPo : cashierPos) {
            BalanceDetailListCashierDto balanceDetailListCashierDto =
                    ConverterService.convert(cashierPo, BalanceDetailListCashierDto.class);
            balanceDetailListCashierDto.setPayDate(DateHelper.formatDate(cashierPo.getPayDate(), ApplicationConstant.TIMESTAMP_FORMAT));
            balanceDetailListCashierDto.setBalanceDate(DateHelper.formatDate(cashierPo.getBalanceDate()));
            balanceDetailListDtos.add(balanceDetailListCashierDto);
        }

        return balanceDetailListDtos;
    }

    private List<BalanceDetailListLoanDto> getBalanceDetailListLoanDtos(Long outlineId) {
        List<BalanceDetailListLoanDto> balanceDetailListLoanDtos = new ArrayList<>();

        List<BalanceDetailPo> detailPos = balanceDetailDao.findByOutlineId(outlineId);

        for (BalanceDetailPo detailPo : detailPos) {
            BalanceDetailListLoanDto balanceDetailListLoanDto =
                    ConverterService.convert(detailPo, BalanceDetailListLoanDto.class);
            balanceDetailListLoanDto.setRepayDate(DateHelper.formatDate(detailPo.getRepayDate(), ApplicationConstant.TIMESTAMP_FORMAT));
            balanceDetailListLoanDto.setBalanceDate(DateHelper.formatDate(detailPo.getBalanceDate()));


            balanceDetailListLoanDto.setDetailId(detailPo.getId());

            balanceDetailListLoanDtos.add(balanceDetailListLoanDto);
        }

        return balanceDetailListLoanDtos;
    }

    private BalanceDetailInteriorDto getBalanceDetailInteriorDto(Long detailId, RepayDtlPo repayDtlPo, BalanceDetailPo balanceDetailPo) {
        BalanceDetailInteriorDto detail = new BalanceDetailInteriorDto();
        List<PayRecvOrdPo> payRecvOrdPos = payRecvOrdDao.findByBizId(repayDtlPo.getRepayId());
        PayRecvOrdPo payRecvOrdPo = payRecvOrdPos.size() > 0 ? payRecvOrdPos.get(0) : null;

        detail.setDetailId(detailId);
        detail.setRepayDate(repayDtlPo.getRepayDateTime());
        detail.setRepayDateStr(DateHelper.formatDate(repayDtlPo.getRepayDateTime(), DateHelper.SIMPLE_DATE_YMDHM_CN));
        detail.setRepayId(repayDtlPo.getRepayId());
        detail.setRepayAmt(repayDtlPo.getRepayPrinAmt().add(repayDtlPo.getRepayIntr()).add(repayDtlPo.getRepayFine()).add(repayDtlPo.getRepayFee()));

        detail.setCapital(repayDtlPo.getRepayPrinAmt());
        detail.setInterest(repayDtlPo.getRepayIntr());
        detail.setFine(repayDtlPo.getRepayFine());
        detail.setFee(repayDtlPo.getRepayFee());

        if (payRecvOrdPo != null) {
            detail.setChannelCode(payRecvOrdPo.getRetChannelCode());
            detail.setChannelName(payRecvOrdPo.getRetChannelName());
            detail.setRemark(payRecvOrdPo.getTrxMemo());
        }

        if (balanceDetailPo != null) {
            detail.setBalanceDate(DateHelper.formatDate(balanceDetailPo.getBalanceDate()));
        }

        detail.setCoupon(loanCouponService.calcPrice(repayDtlPo.getRepayId()));

        return detail;
    }


    private BalanceDetailLoanDto getBalanceDetailLoanDto(RepayDtlPo repayDtlPo) {
        BalanceDetailLoanDto loan = new BalanceDetailLoanDto();
        LoanDtlPo loanDtlPo = loanDtlDao.findOne(repayDtlPo.getLoanId());
        loan.setLoanId(loanDtlPo.getLoanId());
        loan.setUserId(loanDtlPo.getUserId());
        loan.setTransferDate(DateHelper.formatDate(loanDtlPo.getTransferDate(), ApplicationConstant.TIMESTAMP_FORMAT));
        loan.setPeriod(loanDtlPo.getTermLen().toString());
        loan.setUnit(loanDtlPo.getTermType().getUnit());
        loan.setProdName(loanDtlPo.getLoanName());
        loan.setLoanAmt(loanDtlPo.getLoanAmt());
        loan.setRepayMode(loanDtlPo.getRepayMode().getAlias());

        return loan;
    }

    private List<BalanceDetailScheduleDto> getBalanceDetailScheduleDtos(RepayDtlPo repayDtlPo) {
        List<BalanceDetailScheduleDto> rs = new ArrayList<>();
        List<SchedulePo> schedules = scheduleDao.getRepayedList(repayDtlPo.getLoanId());
        LoanDtlPo loanDtlPo = loanDtlDao.findOne(repayDtlPo.getLoanId());

        LoanProductDetailDto product = innerApplService.getProduct(loanDtlPo.getApplId());

        BalanceDetailScheduleDto item;
        switch (loanDtlPo.getRepayMode()) {
            case INTR_PER_DIEM:
                LoanCalcDto loanCalcDto = loanCalcService.anyForce(null, repayDtlPo.getLoanId(), repayDtlPo.getRepayPrinAmt(), repayDtlPo.getRepayDateTime());
                item = new BalanceDetailScheduleDto();
                SchedulePo schedule = schedules.get(0);
                item.setScheduleId(schedule.getScheduleId());
                item.setShouldCapital(schedule.getShouldCapital());
                item.setShouldInterest(loanCalcDto.getInterest());
                item.setShouldFine(loanCalcDto.getFine());
                item.setShouldFee(loanCalcDto.getFee());
                item.setStatus(schedule.getScheduleStatus());
                item.setPayDate(schedule.getPayDate());
                item.setDueDate(schedule.getDueDate());

                if (loanCalcDto.getFine().compareTo(BigDecimal.ZERO) > 0) {
                    item.setFineDays(loanCalcDto.getFineDays());
                }

                rs.add(item);
                break;
            case MONTH_AVE_CAP_PLUS_INTR:

                List<LinkRepayDto> links = innerLinkRepayService.findByRepayId(repayDtlPo.getRepayId());

                for (LinkRepayDto link : links) {
                    item = new BalanceDetailScheduleDto();

                    SchedulePo schedulePo = scheduleDao.findOne(link.getSchdId());

                    item.setScheduleId(schedulePo.getScheduleId());
                    item.setStatus(schedulePo.getScheduleStatus());
                    item.setPayDate(schedulePo.getPayDate());
                    item.setDueDate(schedulePo.getDueDate());
                    item.setShouldCapital(schedulePo.getShouldCapital());
                    item.setShouldInterest(schedulePo.getShouldInterest());
                    item.setShouldFine(BigDecimal.ZERO);
                    item.setShouldFee(BigDecimal.ZERO);

                    if(schedulePo.getActualFee().compareTo(BigDecimal.ZERO) >= 0){
                        item.setShouldFee(schedulePo.getActualFee());
                    }
                    
                    item.setFineDays(0);
                    if (schedulePo.getActualFineCapital().compareTo(BigDecimal.ZERO) > 0) {
                        int fineDays = LoanUtils.getFineDays(schedulePo.getDueDate(), repayDtlPo.getRepayDateTime());
                        BigDecimal shouldFine = EFineType.CAPITAL_EVERY_DAY.getFine(repayDtlPo.getSurplusCapital(), fineDays, product.getFineValue());
                        item.setShouldFine(shouldFine);
                        item.setFineDays(fineDays);
                    }

                    rs.add(item);
                }

                break;
        }

        return rs;
    }
}