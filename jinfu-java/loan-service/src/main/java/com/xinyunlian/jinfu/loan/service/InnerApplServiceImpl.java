package com.xinyunlian.jinfu.loan.service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xinyunlian.jinfu.audit.dao.LoanAuditDao;
import com.xinyunlian.jinfu.audit.entity.LoanAuditPo;
import com.xinyunlian.jinfu.audit.enums.EAuditStatus;
import com.xinyunlian.jinfu.audit.enums.EAuditType;
import com.xinyunlian.jinfu.common.constant.ApplicationConstant;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.external.dao.LoanApplOutAuditDao;
import com.xinyunlian.jinfu.external.entity.LoanApplOutAuditPo;
import com.xinyunlian.jinfu.loan.dao.LoanApplDao;
import com.xinyunlian.jinfu.loan.dao.LoanDtlDao;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.dto.resp.apply.LoanApplyDetailCusDto;
import com.xinyunlian.jinfu.loan.entity.LoanApplPo;
import com.xinyunlian.jinfu.loan.entity.LoanDtlPo;
import com.xinyunlian.jinfu.loan.entity.RepayDtlPo;
import com.xinyunlian.jinfu.loan.enums.*;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.repay.dao.RepayDao;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.enums.ELoanCustomerStatus;
import com.xinyunlian.jinfu.schedule.service.InnerScheduleService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author willwang
 */
@Service
public class InnerApplServiceImpl implements InnerApplService {

    @Autowired
    private LoanApplDao loanApplDao;

    @Autowired
    private LoanDtlDao loanDtlDao;

    @Autowired
    private LoanAuditDao loanAuditDao;

    @Autowired
    private RepayDao repayDao;

    @Autowired
    private InnerScheduleService innerScheduleService;

    @Autowired
    private FinanceSourceService financeSourceService;

    @Autowired
    private LoanApplOutAuditDao loanApplOutAuditDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(InnerApplServiceImpl.class);

    @Override
    @Transactional
    public void save(LoanApplDto loanApplDto) {
        LoanApplPo po;
        if (loanApplDto.getApplId() == null) {
            po = new LoanApplPo();
        } else {
            po = loanApplDao.findOne(loanApplDto.getApplId());
        }
        if (po.getCreateTs() != null) {
            po.setVersionCt(0L);
        }
        ConverterService.convert(loanApplDto, po);
        loanApplDao.save(po);
    }

    @Override
    public LoanApplDto get(String applyId) {
        LoanApplPo loanApplPo = loanApplDao.findOne(applyId);
        LoanApplDto loanApplDto = ConverterService.convert(loanApplPo, LoanApplDto.class);
        if (loanApplDto != null) {
            loanApplDto.setCreateDate(loanApplPo.getCreateTs());
        }
        return loanApplDto;
    }

    @Override
    @Transactional
    public LoanProductDetailDto getProduct(String applyId) {
        LoanApplPo loanApplPo = loanApplDao.findOne(applyId);
        return JsonUtil.toObject(LoanProductDetailDto.class, loanApplPo.getExtraParams());
    }

    @Override
    public ELoanCustomerStatus getStatus(LoanDtlDto loan, LoanApplDto apply) {
        ELoanCustomerStatus status;
        if (apply == null) {
            status = ELoanCustomerStatus.ERROR;
            return status;
        }
        status = statusConvert(apply.getApplStatus());
        if (status == null) {
            status = ELoanCustomerStatus.DEALING;
        }

        //根据路由规则做爱投资状态判定
        FinanceSourceDto financeSourceDto = financeSourceService.findById(apply.getFinanceSourceId());
        if (financeSourceDto.getType() == EFinanceSourceType.AITOUZI) {

            if (status == ELoanCustomerStatus.NEEDSURE) { //内部审核成功后，才需要外部状态配合
                LoanApplOutAuditPo loanApplOutAuditPo = loanApplOutAuditDao.findByApplIdLatest(apply.getApplId());
                if (loanApplOutAuditPo == null) {
                    status = ELoanCustomerStatus.DEALING;
                } else {
                    switch (loanApplOutAuditPo.getAuditType()) {
                        case SUCCESS:
                            status = ELoanCustomerStatus.NEEDSURE;
                            break;
                        case REJECT:
                            status = ELoanCustomerStatus.REJECT;
                            break;
                    }
                }
            }


        }

        if (null != loan) {
            if (loan.getTransferStat() == ETransferStat.SUCCESS) {
                status = ELoanCustomerStatus.USE;
                if (loan.getLoanStat() == ELoanStat.PAID) {
                    status = ELoanCustomerStatus.PAID;
                } else if (loan.getLoanStat() == ELoanStat.OVERDUE) {
                    status = ELoanCustomerStatus.OVERDUE;
                }
            } else {
                status = ELoanCustomerStatus.PROCESS;
            }
        }
        return status;
    }

    @Override
    public ELoanCustomerStatus getStatus(LoanDtlDto loan, LoanApplDto apply, Map<Integer, FinanceSourceDto> financeSourceDtoMap) throws BizServiceException {
        ELoanCustomerStatus status;
        if (apply == null) {
            status = ELoanCustomerStatus.ERROR;
            return status;
        }
        status = statusConvert(apply.getApplStatus());
        if (status == null) {
            status = ELoanCustomerStatus.DEALING;
        }

        if (financeSourceDtoMap == null) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "初始化资金路由信息为空");
        }

        //根据路由规则做爱投资状态判定
        FinanceSourceDto financeSourceDto = financeSourceDtoMap.get(apply.getFinanceSourceId());
        if (financeSourceDto != null && financeSourceDto.getType() == EFinanceSourceType.AITOUZI) {

            if (status == ELoanCustomerStatus.NEEDSURE) { //内部审核成功后，才需要外部状态配合

                LoanApplOutAuditPo loanApplOutAuditPo = loanApplOutAuditDao.findByApplIdLatest(apply.getApplId());
                if (loanApplOutAuditPo == null) {
                    status = ELoanCustomerStatus.DEALING;
                } else {
                    switch (loanApplOutAuditPo.getAuditType()) {
                        case SUCCESS:
                            status = ELoanCustomerStatus.NEEDSURE;
                            break;
                        case REJECT:
                            status = ELoanCustomerStatus.REJECT;
                            break;
                    }
                }
            }


        }

        if (null != loan) {
            if (loan.getTransferStat() == ETransferStat.SUCCESS) {
                status = ELoanCustomerStatus.USE;
                if (loan.getLoanStat() == ELoanStat.PAID) {
                    status = ELoanCustomerStatus.PAID;
                } else if (loan.getLoanStat() == ELoanStat.OVERDUE) {
                    status = ELoanCustomerStatus.OVERDUE;
                }
            } else {
                status = ELoanCustomerStatus.PROCESS;
            }
        }
        return status;
    }

    @Override
    public LoanDtlDto getLoan(String applId) {
        LoanDtlPo loanDtlPo = loanDtlDao.findByApplId(applId);
        if (loanDtlPo != null) {
            return ConverterService.convert(loanDtlPo, LoanDtlDto.class);
        }
        return null;
    }

    /**
     * 获取贷款详情
     *
     * @param userId
     * @return
     */
    @Override
    public LoanApplyDetailCusDto detail(String userId, String applId) throws BizServiceException {
        LoanApplPo loanApplPo = loanApplDao.findOne(applId);
        if (null == loanApplPo) {
            throw new BizServiceException(EErrorCode.LOAN_APPL_NOT_EXISTS);
        }
        if (!loanApplPo.getUserId().equals(userId)) {
            throw new BizServiceException(EErrorCode.LOAN_APPL_NOT_YOURS);
        }

        //申请基础信息
        LoanApplyDetailCusDto item = new LoanApplyDetailCusDto();
        item.setProductId(loanApplPo.getProductId());
        item.setApplId(loanApplPo.getApplId());
        item.setAmt(loanApplPo.getApplAmt());
        item.setApplDate(DateHelper.formatDate(loanApplPo.getCreateTs(), DateHelper.SIMPLE_DATE_YMDHM_CN));
        item.setPeriod(loanApplPo.getTermLen());
        item.setAuditPeriod(loanApplPo.getApprTermLen());
        item.setUnit(loanApplPo.getTermType().getUnit());
        item.setSigned(loanApplPo.getSigned());

        //审批金额
        BigDecimal auditAmt = loanApplPo.getApprAmt() == null ? BigDecimal.ZERO : loanApplPo.getApprAmt();
        item.setAuditAmt(auditAmt);

        //产品信息
        LoanProductDetailDto product = getProduct(applId);
        item.setRepayMode(product.getRepayMode());
        item.setCanPrepay(product.getPrepay());

        //资金路由类型
        FinanceSourceDto financeSourceDto = financeSourceService.findById(loanApplPo.getFinanceSourceId());
        item.setFinanceSourceType(financeSourceDto.getType().getCode());

        //贷款状态
        LoanDtlDto loan = getLoan(applId);
        LoanApplDto apply = ConverterService.convert(loanApplPo, LoanApplDto.class);
        item.setStatus(getStatus(loan, apply));
        if (null != loan) {
            BigDecimal surplusCapital = loan.getLoanAmt().subtract(loan.getRepayedAmt());
            item.setSurplus(AmtUtils.max(surplusCapital, BigDecimal.ZERO));
            item.setTransferDate(DateHelper.formatDate(loan.getTransferDate(), DateHelper.SIMPLE_DATE_YMDHM_CN));
            item.setLoanId(loan.getLoanId());
            item.setRepayDate(this.getRepayDate(loan));
            item.setPaidDate(this.getPaidDate(loan));
            item.setAmt(loan.getLoanAmt());
            item.setDepository(loan.getDepository());
        }

        List<LoanAuditPo> audits = loanAuditDao.findByApplId(applId);

        item.setAuditDesc(this.getDesc(apply, audits));
        item.setAuditDate(this.getAuditDate(apply, audits));
        item.setAuditServiceFeeMonthRt(apply.getServiceFeeMonthRt());
        item.setServiceFeeMonthRt(apply.getServiceFeeMonthRt());
        item.setIntrRt(apply.getLoanRt());
        return item;
    }

    /**
     * 获取到期还款日
     *
     * @param loan 贷款信息
     * @return
     */
    private String getRepayDate(LoanDtlDto loan) {
        String repayDate = "";

        switch (loan.getRepayMode()) {
            case INTR_PER_DIEM:
                repayDate = loan.getDutDate();
                break;
            case MONTH_AVE_CAP_PLUS_INTR:
            case MONTH_AVE_CAP_AVG_INTR:
                ScheduleDto scheduleDto = null;
                try {
                    scheduleDto = innerScheduleService.getCurrentSchedule(loan.getLoanId());
                } catch (Exception e) {
                    LOGGER.warn("apply detail schedules occur exception", e);
                }

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("getRepayDate, schedule" + scheduleDto);
                }

                if (scheduleDto == null) {
                    return repayDate;
                }

                repayDate = scheduleDto.getDueDate();
                break;
            default:
                break;
        }

        if (StringUtils.isEmpty(repayDate)) {
            return repayDate;
        }

        Date repayDate1 = DateHelper.getDate(repayDate, ApplicationConstant.DATE_FORMAT);
        repayDate = DateHelper.formatDate(repayDate1, DateHelper.SIMPLE_DATE_YMD_CN);
        return repayDate;
    }

    /**
     * 获取贷款的结清时间
     *
     * @param loan
     * @return
     */
    private String getPaidDate(LoanDtlDto loan) {

        String paidDate = "";

        if (loan.getLoanStat() == ELoanStat.PAID) {
            List<RepayDtlPo> list = repayDao.getRepayedList(loan.getLoanId());

            if (CollectionUtils.isNotEmpty(list)) {
                for (RepayDtlPo repayDtlPo : list) {
                    if (repayDtlPo.getStatus() == ERepayStatus.SUCCESS) {
                        paidDate = DateHelper.formatDate(repayDtlPo.getCreateTs(), DateHelper.SIMPLE_DATE_YMDHM_CN);
                        break;
                    }
                }
            }
        }

        return paidDate;
    }

    private String getDesc(LoanApplDto apply, List<LoanAuditPo> audits) {
        String desc = "";
        LoanAuditPo reviewAudit;
        switch (apply.getApplStatus()) {
            case SUCCEED:

                if (apply.getApprAmt() != null && apply.getApprTermLen() != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(apply.getApprTermLen());
                    sb.append(apply.getTermType().getUnit());
                    desc = MessageUtil.getMessage("loan.appl.sure",
                            NumberUtil.roundTwo(apply.getApprAmt()), sb.toString()
                    );
                }
                break;
            case REJECT:
                desc = this.getRejectReason(apply, audits);
                reviewAudit = this.getAudit(audits, EAuditType.REVIEW);
                //爱投资审核状态判定,当被拒绝时候，显示原因
                if (reviewAudit != null && reviewAudit.getAuditStatus() == EAuditStatus.SUCCEED) {
                    FinanceSourceDto financeSourceDto = financeSourceService.findById(apply.getFinanceSourceId());
                    if (financeSourceDto != null && financeSourceDto.getType() == EFinanceSourceType.AITOUZI) {
                        LoanApplOutAuditPo loanApplOutAuditPo = loanApplOutAuditDao.findByApplIdLatest(apply.getApplId());
                        if (loanApplOutAuditPo != null && loanApplOutAuditPo.getAuditType() == EApplOutAuditType.REJECT) {
                            desc = loanApplOutAuditPo.getReason();
                        }
                    }
                }

                break;
            case FALLBACK:
                reviewAudit = this.getAudit(audits, EAuditType.REVIEW);
                if (reviewAudit != null
                        && reviewAudit.getAuditStatus() == EAuditStatus.FALLBACK
                        && StringUtils.isNotEmpty(reviewAudit.getReason())) {
                    desc = reviewAudit.getReason();
                    break;
                }
                LoanAuditPo trialAudit = this.getAudit(audits, EAuditType.TRIAL);
                if (trialAudit != null
                        && trialAudit.getAuditStatus() == EAuditStatus.FALLBACK
                        && StringUtils.isNotEmpty(trialAudit.getReason())) {
                    desc = trialAudit.getReason();
                    break;
                }
            default:
                break;
        }
        return desc;
    }

    private String getRejectReason(LoanApplDto apply, List<LoanAuditPo> audits) {
        LoanAuditPo reviewAudit = this.getAudit(audits, EAuditType.REVIEW);

        if (reviewAudit != null && reviewAudit.getAuditStatus() == EAuditStatus.REJECT) {
            return this.getRejectReason(reviewAudit);
        }
        LoanAuditPo trialAudit = this.getAudit(audits, EAuditType.TRIAL);
        if (trialAudit != null && trialAudit.getAuditStatus() == EAuditStatus.REJECT) {
            return this.getRejectReason(trialAudit);
        }


        return MessageUtil.getMessage("loan.appl.reject");
    }

    private String getRejectReason(LoanAuditPo audit) {
        if (audit == null || StringUtils.isEmpty(audit.getReason()) || audit.getReason().length() < 4) {
            return MessageUtil.getMessage("loan.appl.reject");
        }

        String reason = audit.getReason();
        String status = reason.substring(0, 1).toUpperCase();
        String code = reason.substring(1, 4);
        if ("D".equals(status)) {
            if (Integer.parseInt(code) >= 600) {
                return MessageUtil.getMessage("loan.appl.reject.serious");
            } else {
                return MessageUtil.getMessage("loan.appl.reject.normal");
            }
        }

        return MessageUtil.getMessage("loan.appl.reject");
    }

    /**
     * 获取审批时间
     *
     * @param apply
     * @return
     */
    private String getAuditDate(LoanApplDto apply, List<LoanAuditPo> audits) {
        if (CollectionUtils.isEmpty(audits)) {
            return "";
        }

        if (!this.isFinished(apply)) {
            return "";
        }

        LoanAuditPo reviewAudit = this.getAudit(audits, EAuditType.REVIEW);
        String reviewAuditDate = this.getAuditDate(reviewAudit);
        if (StringUtils.isNotEmpty(reviewAuditDate) && reviewAudit.getAuditStatus() == EAuditStatus.SUCCEED) {
            return reviewAuditDate;
        }

        LoanAuditPo trialAudit = this.getAudit(audits, EAuditType.TRIAL);
        String trialAuditDate = this.getAuditDate(trialAudit);
        if (StringUtils.isNotEmpty(trialAuditDate) && trialAudit.getAuditStatus() == EAuditStatus.SUCCEED) {
            return trialAuditDate;
        }

        return "";
    }

    private LoanAuditPo getAudit(List<LoanAuditPo> audits, EAuditType auditType) {
        if (CollectionUtils.isEmpty(audits)) {
            return null;
        }

        for (LoanAuditPo audit : audits) {
            if (audit.getAuditType() == auditType && (audit.getTemp() == null || !audit.getTemp())) {
                return audit;
            }
        }
        return null;
    }

    private boolean isFinished(LoanApplDto apply) {
        return apply.getApplStatus() == EApplStatus.SUCCEED
                || apply.getApplStatus() == EApplStatus.REJECT
                || apply.getApplStatus() == EApplStatus.CANCEL
                || apply.getApplStatus() == EApplStatus.FALLBACK;
    }

    private String getAuditDate(LoanAuditPo audit) {
        if (audit == null) {
            return null;
        }

        if (audit.getTemp() != null && audit.getTemp()) {
            return null;
        }

        return DateHelper.formatDate(audit.getAuditDate(), DateHelper.SIMPLE_DATE_YMDHM_CN);
    }

    private ELoanCustomerStatus statusConvert(EApplStatus applStatus) {
        Map<EApplStatus, ELoanCustomerStatus> mapper = new HashMap<>();

        mapper.put(EApplStatus.TRIAL_UNCLAIMED, ELoanCustomerStatus.DEALING);
        mapper.put(EApplStatus.REVIEW_UNCLAIMED, ELoanCustomerStatus.DEALING);
        mapper.put(EApplStatus.TRIAL_CLAIMED, ELoanCustomerStatus.DEALING);
        mapper.put(EApplStatus.REVIEW_CLAIMED, ELoanCustomerStatus.DEALING);

        mapper.put(EApplStatus.SUCCEED, ELoanCustomerStatus.NEEDSURE);
        mapper.put(EApplStatus.REJECT, ELoanCustomerStatus.REJECT);
        mapper.put(EApplStatus.FALLBACK, ELoanCustomerStatus.FALLBACK);
        mapper.put(EApplStatus.CANCEL, ELoanCustomerStatus.CANCEL);
        return mapper.get(applStatus);
    }
}