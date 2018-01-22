package com.xinyunlian.jinfu.repay.domain;

import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.AmtUtils;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.enums.ETermType;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.product.enums.EFineType;
import com.xinyunlian.jinfu.promo.dto.PromoDto;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 按月等额本息
 * Created by JL on 2016/11/21.
 */
public class MonthAverageCapitalPlusInterest extends RepayMethod {
    private Integer period;

    private Integer months;

    private BigDecimal averageCapitalPlusInterest;

    private BigDecimal capital;

    private BigDecimal interest;

    public MonthAverageCapitalPlusInterest(LoanDtlDto loanDtlDto, PromoDto promoDto, Date now) {
        super(loanDtlDto, promoDto, now);
        calcMonths();
        calRt();
    }

    void calcAverageCapitalPlusInterest() {
        this.averageCapitalPlusInterest = NumberUtil.roundTwo((getLoanAmt().multiply(getRt())
                .multiply((BigDecimal.ONE.add(getRt())).pow(getMonths()))).divide(
                ((BigDecimal.ONE.add(getRt()).pow(getMonths()).subtract(BigDecimal.ONE))), 2));
    }

    void calcCapital() {
        this.capital = NumberUtil.roundTwo(this.averageCapitalPlusInterest.subtract(this.interest));
    }

    void calcInterest() {
        this.interest = NumberUtil.roundTwo((getLoanAmt().multiply(getRt()).subtract(this.averageCapitalPlusInterest))
                .multiply((BigDecimal.ONE.add(getRt())).pow(this.getPeriod() - 1)
                ).add(this.averageCapitalPlusInterest));
    }

    void calcMonths() {
        //等额本息分期不支持日类型的计算
        if (this.getTermType() != ETermType.MONTH && this.getTermType() != ETermType.YEAR) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID);
        }

        if (this.getTermType() == ETermType.YEAR) {
            this.months = this.getTermLen() * 12;
        } else {
            this.months = this.getTermLen();
        }
    }

    void calRt() {
        //转算成月利率
        switch (this.getIntrRateType()) {
            case YEAR:
                this.setRt(this.getRt().divide(new BigDecimal(12)));
                break;
            case DAY:
                this.setRt(this.getRt().multiply(new BigDecimal(30)));
                break;
            case MONTH:
                break;
            default:
                throw new BizServiceException(EErrorCode.TECH_DATA_INVALID);
        }
    }


    public Integer getMonths() {
        return months;
    }

    public Integer getPeriod() {
        return period;
    }

    public BigDecimal getAverageCapitalPlusInterest() {
        return averageCapitalPlusInterest.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal getCapital() {
        return capital.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal getInterest() {
        return interest.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public List<ScheduleDto> getRepaySchedule() {
        List<ScheduleDto> list = new ArrayList<>();
        for (int period = 1; period <= this.getMonths(); period++) {
            this.setPeriod(period, true);
            ScheduleDto schedule = new ScheduleDto();
            schedule.setLoanId(this.getLoanDtlDto().getLoanId());
            schedule.setAcctNo(this.getLoanDtlDto().getAcctNo());
            Date transferDate = new Date();
            if(this.getLoanDtlDto() != null && this.getLoanDtlDto().getTransferDate() != null){
                transferDate = this.getLoanDtlDto().getTransferDate();
            }
            schedule.setDueDate(DateHelper.afterSeveralMonths(transferDate, period));
            schedule.setSeqNo(period);
            schedule.setShouldCapital(this.getCapital());
            schedule.setShouldInterest(this.getInterest());
            schedule.setScheduleStatus(EScheduleStatus.NOTYET);
            list.add(schedule);
        }
        return list;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    /**
     * @param period 期数
     */
    public void setPeriod(Integer period, boolean calPromo) {

        if (period > this.getMonths()) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID);
        }

        this.period = period;

        if (calPromo) {
            calPromo();
        } else {
            calcAll();
        }
    }

    private void calcAll() {
        calcAverageCapitalPlusInterest();
        calcInterest();
        //本金计算，存在尾差，最后一笔要按往期推算
        if (this.period < this.months) {
            calcCapital();
        } else {
            BigDecimal surplus = this.getLoanAmt();
            BigDecimal lastInterest = this.getInterest();

            for (int i = 1; i < this.getMonths(); i++) {
                this.setPeriod(i, false);
                calcCapital();
                surplus = surplus.subtract(this.getCapital().setScale(2, BigDecimal.ROUND_HALF_UP));
            }

            //计算尾差
            this.setCapital(surplus);
            //尾差的利率保存
            this.interest = lastInterest;

        }
    }

    /**
     * 计算促销
     */
    private void calPromo() {
        PromoDto promoDto = this.getPromoDto();
        if (promoDto != null) {
            switch (promoDto.getPromoType()) {
                case RATE:
                    //按利率优惠
                    if (promoDto.getPromoLen() < months && promoDto.getPromoLen() > 0) {
                        //非整期优惠，在优惠期内免利息
                        if (this.period <= promoDto.getPromoLen() ) {
                            calcAll();
                            this.interest = this.interest.multiply(promoDto.getPromoValue());
                            return;
                        }
                    } else {
                        //整期优惠，全部利率打折
                        if (this.period.equals(1)) {
                            this.setRt(this.getRt().multiply(promoDto.getPromoValue()));
                        }
                    }
                    break;
                case MONEY:
                    //按金额优惠
                    if (this.period <= promoDto.getPromoLen()) {
                        //优惠期内利息减免一定金额
                        calcAll();
                        BigDecimal result = this.interest.subtract(promoDto.getPromoValue());
                        this.interest = result.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : result;
                        return;
                    }
                    break;
                case OFFLINE:
                    //线下优惠，不做任何操作
                    break;
            }
        }
        calcAll();
    }

    @Override
    public BigDecimal getFine() {

        LoanDtlDto loanDtlDto = this.getLoanDtlDto();

        Date transferDate = loanDtlDto.getTransferDate();
        int days = DateHelper.betweenDaysNew(transferDate, this.getNow());
        if (transferDate.getTime() > this.getNow().getTime()) {
            days = 0;
        }

        BigDecimal surplus = AmtUtils.max(
            loanDtlDto.getLoanAmt().subtract(loanDtlDto.getRepayedAmt()), BigDecimal.ZERO
        );

        return this.getFineType().getFine(surplus, days, this.getFineValue());
    }
}