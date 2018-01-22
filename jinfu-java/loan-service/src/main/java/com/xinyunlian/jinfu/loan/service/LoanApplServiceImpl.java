package com.xinyunlian.jinfu.loan.service;

import com.alibaba.fastjson.JSONObject;
import com.xinyunlian.jinfu.acct.dao.AcctDao;
import com.xinyunlian.jinfu.acct.dto.req.AcctReserveReq;
import com.xinyunlian.jinfu.acct.entity.AcctPo;
import com.xinyunlian.jinfu.acct.enums.EAcctStatus;
import com.xinyunlian.jinfu.acct.service.AcctReserveService;
import com.xinyunlian.jinfu.acct.service.InnerAcctReserveService;
import com.xinyunlian.jinfu.appl.dto.BeforeTrialDetailDto;
import com.xinyunlian.jinfu.audit.dao.LoanAuditDao;
import com.xinyunlian.jinfu.audit.entity.LoanAuditPo;
import com.xinyunlian.jinfu.audit.enums.EAuditStatus;
import com.xinyunlian.jinfu.audit.enums.EAuditType;
import com.xinyunlian.jinfu.audit.service.LoanAuditService;
import com.xinyunlian.jinfu.common.constant.ApplicationConstant;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.BasePagingDto;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.ThreadContextUtils;
import com.xinyunlian.jinfu.creditline.dao.LoanUserCreditLineDao;
import com.xinyunlian.jinfu.creditline.entity.LoanUserCreditLinePo;
import com.xinyunlian.jinfu.creditline.enums.ELoanUserCreditLineStatus;
import com.xinyunlian.jinfu.external.enums.ConfirmationType;
import com.xinyunlian.jinfu.external.service.ExternalService;
import com.xinyunlian.jinfu.loan.dao.LoanApplDao;
import com.xinyunlian.jinfu.loan.dao.LoanDtlDao;
import com.xinyunlian.jinfu.loan.dto.jms.JmsLoanReportDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanApplListDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanApplRespDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanCustomerApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.dto.resp.apply.LoanApplyDetailCusDto;
import com.xinyunlian.jinfu.loan.entity.LoanApplPo;
import com.xinyunlian.jinfu.loan.entity.LoanDtlPo;
import com.xinyunlian.jinfu.loan.enums.EApplStatus;
import com.xinyunlian.jinfu.log.dto.LoanAuditLogDto;
import com.xinyunlian.jinfu.log.enums.ELoanAuditLogType;
import com.xinyunlian.jinfu.log.service.LoanAuditLogService;
import com.xinyunlian.jinfu.product.dao.ProductInfoDao;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.product.entity.LoanProductInfoPo;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.dto.FinanceSourceLoanDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import com.xinyunlian.jinfu.schedule.enums.ELoanCustomerStatus;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author willwang
 */
@Service
public class LoanApplServiceImpl implements LoanApplService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanApplServiceImpl.class);

    @Autowired
    private LoanApplDao loanApplDao;

    @Autowired
    private AcctDao acctDao;

    @Autowired
    private InnerAcctReserveService innerAcctReserveService;

    @Autowired
    private InnerApplService innerApplService;

    @Autowired
    private LoanAuditDao loanAuditDao;

    @Autowired
    private LoanDtlDao loanDtlDao;

    @Autowired
    private LoanAuditService loanAuditService;

    @Autowired
    private AcctReserveService acctReserveService;

    @Autowired
    private LoanUserCreditLineDao loanUserCreditLineDao;

    @Autowired
    private FinanceSourceService financeSourceService;

    @Autowired
    private LoanAuditLogService loanAuditLogService;

    @Autowired
    private ExternalService externalService;

    @Autowired
    private ProductInfoDao productInfoDao;

    @Override
    public void save(LoanApplDto loanApplDto) {
        innerApplService.save(loanApplDto);
    }

    @Override
    public LoanApplDto get(String applId) {
        LoanApplPo loanApplPo = loanApplDao.findOne(applId);
        LoanApplDto loanApplDto = ConverterService.convert(loanApplPo, LoanApplDto.class);

        if (loanApplDto != null) {
            loanApplDto.setCreateDate(loanApplPo.getCreateTs());
        }

        return loanApplDto;
    }

    @Override
    public LoanProductDetailDto getProduct(String applId) {
        return innerApplService.getProduct(applId);
    }

    @Override
    public BeforeTrialDetailDto getBeforeTrialDetail(String applId) {
        BeforeTrialDetailDto detail = new BeforeTrialDetailDto();
        LoanApplDto apply = innerApplService.get(applId);
        if (apply == null) {
            return null;
        }
        detail.setApplId(apply.getApplId());
        detail.setApplDate(DateHelper.formatDate(apply.getCreateDate(), ApplicationConstant.TIMESTAMP_FORMAT));
        detail.setPeriod(apply.getTermLen());
        detail.setUnit(apply.getTermType().getUnit());
        detail.setUserId(apply.getUserId());
        detail.setDealerId(apply.getDealerId());
        detail.setDealerUserId(apply.getDealerUserId());
        detail.setStatus(apply.getApplStatus());
        detail.setSigned(apply.getSigned());
        detail.setApplAmt(apply.getApplAmt());
        detail.setHangUp(apply.getHangUp());
        detail.setRiskScore(apply.getRiskScore());
        detail.setRiskResult(apply.getRiskResult());

        LoanProductDetailDto product = innerApplService.getProduct(applId);
        detail.setProduct(product);

        if (apply.getApplStatus() == EApplStatus.SUCCEED) {
            LoanDtlPo loanDtlPo = loanDtlDao.findByApplId(apply.getApplId());
            if (loanDtlPo != null) {
                detail.setLoanId(loanDtlPo.getLoanId());
            }
        }

        return detail;
    }

    @Override
    public LoanApplListDto list(String userId, BasePagingDto basePagingDto) {
        LoanApplListDto loanApplListDto = new LoanApplListDto();
        Specification<LoanApplPo> spec = (Root<LoanApplPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            expressions.add(cb.equal(root.<LoanApplPo>get("userId"), userId));
            return predicate;
        };
        Sort sort = new Sort(Sort.Direction.DESC, "createTs");
        Pageable pageable = new PageRequest(basePagingDto.getCurrentPage() - 1, basePagingDto.getPageSize(), sort);
        Page<LoanApplPo> page = loanApplDao.findAll(spec, pageable);
        List<LoanApplRespDto> data = new ArrayList<>();
        for (LoanApplPo po : page.getContent()) {
            LoanApplDto apply = ConverterService.convert(po, LoanApplDto.class);
            LoanDtlDto loan = innerApplService.getLoan(po.getApplId());
            LoanApplRespDto item = new LoanApplRespDto();

            item.setApplId(po.getApplId());
            item.setStatus(innerApplService.getStatus(loan, apply));
            LoanProductInfoPo productInfoPo = productInfoDao.findOne(po.getProductId());
            item.setName(productInfoPo.getProductName());
            item.setAmt(po.getApplAmt());
            if (loan != null) {
                item.setAmt(loan.getLoanAmt());
            }
            item.setCreateDate(DateHelper.formatDate(po.getCreateTs(), DateHelper.SIMPLE_DATE_YMDHM_CN));

            if (ELoanCustomerStatus.NEEDSURE.equals(item.getStatus()) && apply.getApprAmt() != null && apply.getApprAmt().compareTo(BigDecimal.ZERO) >= 0) {
                item.setAmt(apply.getApprAmt());
            }
            item.setRepayMode(po.getRepayMode());
            data.add(item);
        }
        loanApplListDto.setList(data);
        loanApplListDto.setTotalPages(page.getTotalPages());
        loanApplListDto.setTotalRecord(page.getTotalElements());
        loanApplListDto.setPageSize(basePagingDto.getPageSize());
        loanApplListDto.setCurrentPage(basePagingDto.getCurrentPage());
        return loanApplListDto;
    }

    @Override
    public LoanApplyDetailCusDto detail(String userId, String applId) {
        return innerApplService.detail(userId, applId);
    }

    @Override
    public void checkStart(String userId, LoanCustomerApplDto applDto) throws BizServiceException {
        //查询用户所有的申请
        List<LoanApplPo> list = loanApplDao.findByUserId(userId);

        //贷款map
        List<LoanDtlPo> loanDtlPos = loanDtlDao.findByUserId(userId);
        Map<String, LoanDtlPo> loanDtlPoMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(loanDtlPos)) {
            for (LoanDtlPo loanDtlPo : loanDtlPos) {
                loanDtlPoMap.put(loanDtlPo.getApplId(), loanDtlPo);
            }
        }

        //资金路由map
        List<FinanceSourceDto> financeSourceDtos = financeSourceService.getAll();
        Map<Integer, FinanceSourceDto> financeSourceDtoMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(financeSourceDtos)) {
            for (FinanceSourceDto financeSourceDto : financeSourceDtos) {
                financeSourceDtoMap.put(financeSourceDto.getId(), financeSourceDto);
            }
        }

        //todo 审核信息的批处理逻辑较为复杂

        for (LoanApplPo loanApplPo : list) {
            LoanDtlPo loanDtlPo = loanDtlPoMap.get(loanApplPo.getApplId());
            ELoanCustomerStatus status = innerApplService.getStatus(
                    ConverterService.convert(loanDtlPo, LoanDtlDto.class),
                    ConverterService.convert(loanApplPo, LoanApplDto.class),
                    financeSourceDtoMap
            );
            if (this.isNotFinished(status)) {
                throw new BizServiceException(EErrorCode.LOAN_APPL_CANT_APPL);
            }
        }

        if (this.isRejectedInOneMonth(list)) {
            throw new BizServiceException(EErrorCode.LOAN_APPL_CANT_APPL);
        }

        assertProduct(applDto);
        AcctPo acctPo = acctDao.findByUserIdAndProductId(userId, applDto.getProductId());
        assertApply(applDto, acctPo);
    }

    @Override
    public ELoanCustomerStatus getStatus(LoanDtlDto loanDtlDto, LoanApplDto loanApplDto) {
        return innerApplService.getStatus(loanDtlDto, loanApplDto);
    }

    @Override
    @Transactional
    public void updateSigned(String applyId) {
        LoanApplPo po = loanApplDao.findOne(applyId);
        po.setSignDate(new Date());
        po.setSigned(true);
    }

    @Override
    public void closeLongtimeNoused() {
        List<String> applIds = loanApplDao.findLongtimeNoused();
        LOGGER.info("{} applies should closed", applIds.size());
        cancelApply(applIds);
        LOGGER.info("apply close finish");
    }

    @Override
    public void closeAvgCapAvgIntrTenDaysNoUsed() {
        List<String> applIds = loanApplDao.findAvgCapAvgIntrTenDaysNoUsed();
        LOGGER.info("{} applies should closed", applIds.size());
        cancelApply(applIds);
        LOGGER.info("apply close finish");
    }


    private void cancelApply(List<String> applIds) {
        Map<Integer, FinanceSourceDto> fSources = financeSourceService.getAllMap();
        for (String applId : applIds) {
            try {
                this.cancelApply(applId, fSources);
                LOGGER.info("{} apply closed", applId);
            } catch (Exception e) {
                LOGGER.warn(applId + "apply closed error", e);
            }
        }
    }

    @Override
    @Transactional
    public LoanApplDto bTestStart(String userId, LoanCustomerApplDto loanCustomerApplDto) throws BizServiceException {

        LoanUserCreditLinePo loanUserCreditLinePo = loanUserCreditLineDao.findByUserId(userId);

        if (loanUserCreditLinePo == null || loanUserCreditLinePo.getStatus() != ELoanUserCreditLineStatus.UNUSED) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "用户额度无效，无法申请贷款");
        }

        //额度检测
        if (loanCustomerApplDto.getApplAmt().compareTo(loanUserCreditLinePo.getAvailable()) > 0) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "额度超过，无法贷款");
        }

        //重复申请检测
        this.checkStart(userId, loanCustomerApplDto);

        //申请
        loanCustomerApplDto.setTestSource("B");
        LoanApplDto loanApplDto = this.apply(userId, loanCustomerApplDto);

        //审批
        this.autoAudit(loanApplDto);

        return loanApplDto;
    }

    @Transactional
    private void autoAudit(LoanApplDto loanApplDto) throws BizServiceException {
        String mgtUserId = "UM0000000000";

        //直接设置初审人
        loanApplDto.setTrialUserId(mgtUserId);

        //初审建议通过
        LoanAuditPo trial = new LoanAuditPo();

        trial.setAuditType(EAuditType.TRIAL);
        trial.setApplId(loanApplDto.getApplId());
        trial.setAuditStatus(EAuditStatus.ADVISE_PASS);
        trial.setLoanAmt(loanApplDto.getApplAmt());
        trial.setTemp(false);
        trial.setAuditUserId(mgtUserId);
        trial.setTermLen(loanApplDto.getTermLen());
        trial.setAuditDate(new Date());
        trial.setReason("自动化审批");
        trial.setRemark("自动化审批");
        loanAuditDao.save(trial);

        LOGGER.info("appl_id:{} trial success ", loanApplDto.getApplId());

        //初审日志
        this.autoAuditLog(mgtUserId, loanApplDto, trial);

        //直接设置终审人
        loanApplDto.setReviewUserId(mgtUserId);

        //终审通过
        LoanAuditPo review = new LoanAuditPo();
        review.setAuditType(EAuditType.REVIEW);
        review.setApplId(loanApplDto.getApplId());
        review.setAuditStatus(EAuditStatus.SUCCEED);
        review.setLoanAmt(loanApplDto.getApplAmt());
        review.setTemp(false);
        review.setTermLen(loanApplDto.getTermLen());
        review.setAuditDate(new Date());
        review.setAuditUserId(mgtUserId);
        review.setReason("自动化审批");
        review.setRemark("自动化审批");
        loanAuditDao.save(review);
        LOGGER.info("appl_id:{} review success ", loanApplDto.getApplId());

        this.autoAuditLog(mgtUserId, loanApplDto, review);

        //变更申请单
        loanApplDto.setApplStatus(EApplStatus.SUCCEED);
        loanApplDto.setApprAmt(loanApplDto.getApplAmt());
        loanApplDto.setApprTermLen(loanApplDto.getTermLen());

        FinanceSourceLoanDto financeSourceLoanDto = new FinanceSourceLoanDto();

        financeSourceLoanDto.setApplId(loanApplDto.getApplId());
        financeSourceLoanDto.setProdId(loanApplDto.getProductId());
        financeSourceLoanDto.setLoanAmt(loanApplDto.getApprAmt());

        FinanceSourceDto financeSourceDto = financeSourceService.getOwn(financeSourceLoanDto);

        if (financeSourceDto != null) {
            loanApplDto.setFinanceSourceId(Integer.parseInt(Long.toString(financeSourceDto.getId())));
        }

        this.save(loanApplDto);
    }

    private void autoAuditLog(String mgtUserId, LoanApplDto loanApplDto, LoanAuditPo audit) {
        ELoanAuditLogType auditLogType = null;
        if (audit.getAuditType() == EAuditType.TRIAL) {
            auditLogType = ELoanAuditLogType.TRIAL_COMMIT;
        } else if (audit.getAuditType() == EAuditType.REVIEW) {
            auditLogType = ELoanAuditLogType.REVIEW_COMMIT;
        }

        String loanAmt = "", periodDec = "";
        if (audit.getLoanAmt() != null) {
            loanAmt = audit.getLoanAmt().toString();
        }
        if (audit.getTermLen() != null) {
            periodDec = audit.getTermLen().toString() + loanApplDto.getTermType().getUnit();
        }

        LoanAuditLogDto log = new LoanAuditLogDto("Administrators", loanApplDto.getApplId(), auditLogType,
                audit.getAuditStatus().getText(), loanAmt, periodDec);
        loanAuditLogService.save(log);
    }

    @Transactional
    private void cancelApply(String applId, Map<Integer, FinanceSourceDto> fSources) {
        LoanApplPo loanApplPo = loanApplDao.findOne(applId);
        loanApplPo.setApplStatus(EApplStatus.CANCEL);
        loanApplDao.save(loanApplPo);
        acctReserveService.unReserve(loanApplPo.getCreditLineRsrvId());

        Integer sourceId = loanApplPo.getFinanceSourceId();
        if (sourceId != null && fSources.get(sourceId) != null &&
                fSources.get(sourceId).getType() == EFinanceSourceType.AITOUZI) {
            externalService.loanContractConfirm(applId, ConfirmationType.cancle);
        }
    }

    @Override
    @Transactional
    public LoanApplDto apply(String userId, LoanCustomerApplDto applDto) throws BizServiceException {
        //产品检测
        assertProduct(applDto);
        AcctPo acctPo = getOrGenerateAcct(userId, applDto.getProduct());
        assertApply(applDto, acctPo);
        //增加保留，把额度占用掉
        String creditLineRsrvId = reserveCreditLine(applDto, acctPo.getAcctNo(), null);
        //增加申请记录
        LoanApplPo loanApplPo = saveLoanAppl(userId, applDto, creditLineRsrvId, acctPo.getAcctNo(), applDto.getProduct());
        return ConverterService.convert(loanApplPo, LoanApplDto.class);
    }

    @Override
    @Transactional
    public LoanApplDto restart(String userId, String applId) throws BizServiceException {
        LoanApplPo loanApplPo = loanApplDao.findOne(applId);

        assertNotNull(loanApplPo);
        assertYours(userId, loanApplPo);

        innerAcctReserveService.reserve(loanApplPo.getCreditLineRsrvId());
        if (!EApplStatus.FALLBACK.equals(loanApplPo.getApplStatus())) {
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "非退回状态，无法重新发起贷款申请");
        }
        String traceId = ThreadContextUtils.getTraceId();
        loanApplPo.setTraceId(traceId);
        loanApplPo.setApplStatus(EApplStatus.TRIAL_CLAIMED);

        LoanApplDto apply = ConverterService.convert(loanApplPo, LoanApplDto.class);

        loanAuditService.resetAuditTemp(apply);
        return apply;
    }

    private boolean isNotFinished(ELoanCustomerStatus status) {
        return Arrays.asList(new ELoanCustomerStatus[]{
                ELoanCustomerStatus.DEALING,
                ELoanCustomerStatus.NEEDSURE,
                ELoanCustomerStatus.PROCESS,
                ELoanCustomerStatus.USE,
                ELoanCustomerStatus.OVERDUE,
                ELoanCustomerStatus.FALLBACK
        }).contains(status);
    }

    private boolean isRejectedInOneMonth(List<LoanApplPo> applies) {
        Set<String> applIds = new HashSet<>();
        if (CollectionUtils.isEmpty(applies)) {
            return false;
        }
        applies.forEach(item -> {
            applIds.add(item.getApplId());
        });

        if (CollectionUtils.isEmpty(applIds)) {
            return false;
        }

        List<LoanAuditPo> audits = loanAuditDao.findByApplIdIn(applIds);

        for (LoanAuditPo audit : audits) {
            if (audit.getAuditStatus() == EAuditStatus.REJECT && DateHelper.betweenDaysNew(audit.getAuditDate(), new Date()) <= 30) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param loanApplDto 小贷申请数据
     * @param acctNo      账号
     * @return
     */
    private String reserveCreditLine(LoanCustomerApplDto loanApplDto, String acctNo, String rsrvId) {
        AcctReserveReq acctReserveReq = new AcctReserveReq(acctNo, loanApplDto.getApplAmt(), rsrvId);
        return innerAcctReserveService.reserve(acctReserveReq);
    }

    /**
     * 拿用户的某个产品的账户信息
     *
     * @param userId  用户编号
     * @param product 产品详情信息
     * @return
     */
    private AcctPo getOrGenerateAcct(String userId, LoanProductDetailDto product) {
        AcctPo acctPo = acctDao.findByUserIdAndProductId(userId, product.getProductId());
        //如果用户是第一次申请使用这个产品，会自动创建一个账户
        if (null == acctPo) {
            acctPo = new AcctPo();
            acctPo.setUserId(userId);
            acctPo.setCreditLine(product.getLoanAmtMax());
            acctPo.setStatus(EAcctStatus.NORMAL);
            acctPo.setProductId(product.getProductId());
            acctPo.setCreditLineUsed(BigDecimal.ZERO);
            acctPo.setCreditLineRsrv(BigDecimal.ZERO);
            acctDao.save(acctPo);
        }
        return acctPo;
    }

    /**
     * 申请数据的保存
     *
     * @param loanApplDto
     * @param creditLineRsrvId
     * @param acctNo
     * @param product
     * @return
     */
    private LoanApplPo saveLoanAppl(
            String userId, LoanCustomerApplDto loanApplDto, String creditLineRsrvId,
            String acctNo, LoanProductDetailDto product) {
        LoanApplPo loanApplPo = ConverterService.convert(loanApplDto, LoanApplPo.class);
        loanApplPo.setAcctNo(acctNo);
        loanApplPo.setUserId(userId);
        loanApplPo.setApplDate(DateHelper.getWorkDate());
        loanApplPo.setApplStatus(EApplStatus.TRIAL_UNCLAIMED);
        loanApplPo.setLoanRt(product.getIntrRate());
        loanApplPo.setIntrRateType(product.getIntrRateType());
        loanApplPo.setCreditLineRsrvId(creditLineRsrvId);
        loanApplPo.setTermType(product.getTermType());
        loanApplPo.setTermLen(Integer.parseInt(loanApplDto.getTermLen()));
        loanApplPo.setProductId(product.getProductId());
        loanApplPo.setExtraParams(JsonUtil.toJson(product));
        loanApplPo.setRepayMode(product.getRepayMode());
        loanApplPo.setChannel(loanApplDto.getChannel());
        loanApplPo.setTestSource(loanApplDto.getTestSource());
        loanApplPo.setServiceFeeRt(loanApplDto.getDealerServiceFeeRt());
        if (loanApplDto.getFeeRt() != null) {
            loanApplPo.setServiceFeeMonthRt(loanApplDto.getFeeRt().subtract(product.getIntrRate()));
        } else {
            loanApplPo.setServiceFeeMonthRt(BigDecimal.ZERO);
        }
        String traceId = ThreadContextUtils.getTraceId();
        loanApplPo.setTraceId(traceId);
        loanApplPo.setHangUp(false);
        loanApplPo = loanApplDao.save(loanApplPo);
        if (traceId == null) {
            LOGGER.warn("loan apply {} trace id is null", String.valueOf(loanApplPo.getApplId()));
        }
        return loanApplPo;
    }

    /**
     * 申请数据校验
     *
     * @param applDto
     */
    private void assertApply(LoanCustomerApplDto applDto, AcctPo acct) {
        if (acct == null) {
            return;
        }
        //用户可用额度检测
        BigDecimal surplusCreditLine = acct.getCreditLine().subtract(acct.getCreditLineUsed())
                .subtract(acct.getCreditLineRsrv());
        //剩余额度如果不足
        if (applDto.getApplAmt().compareTo(surplusCreditLine) > 0) {
            throw new BizServiceException(EErrorCode.LOAN_APPL_CREDITLINE_NOT_ENOUGH);
        }
    }

    private void assertProduct(LoanCustomerApplDto applDto) {
        LoanProductDetailDto product = applDto.getProduct();

        //产品有效性检测
        if (null == product || null == product.getProductName()) {
            throw new BizServiceException(EErrorCode.APPL_PRODUCT_NOT_EXISTS);
        }
        //产品期限有效性检测
        if (product.getTermLen().contains(",")) {
            String[] termLenList = product.getTermLen().split(",");
            List<String> list = Arrays.asList(termLenList);
            if (!list.contains(applDto.getTermLen())) {
                throw new BizServiceException(EErrorCode.APPL_PRODUCT_ERROR_TERMLEN);
            }
        } else {
            if (!product.getTermLen().equals(applDto.getTermLen())) {
                throw new BizServiceException(EErrorCode.APPL_PRODUCT_ERROR_TERMLEN);
            }
        }
        //产品额度范围检测
        if (applDto.getApplAmt().compareTo(BigDecimal.ZERO) <= 0 ||
                applDto.getApplAmt().compareTo(product.getLoanAmtMax()) > 0 ||
                applDto.getApplAmt().compareTo(product.getLoanAmtMin()) < 0) {
            throw new BizServiceException(EErrorCode.LOAN_APPL_AMT_INVALID);
        }
    }

    private void assertYours(String userId, LoanApplPo loanApplPo) {
        if (!userId.equals(loanApplPo.getUserId())) {
            throw new BizServiceException(EErrorCode.LOAN_APPL_NOT_YOURS);
        }
    }

    private void assertNotNull(LoanApplPo loanApplPo) {
        if (loanApplPo == null) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "贷款申请不存在");
        }
    }

    @Override
    @JmsListener(destination = "GENERATE_REPORT_EVENT")
    public void getLoanReport(String jmsLoanReport) throws BizServiceException {
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("loanReport :{},", jmsLoanReport);
        }

        JmsLoanReportDto loanReport = JSONObject.parseObject(jmsLoanReport, JmsLoanReportDto.class);
        LoanApplPo loanApplPo = loanApplDao.findOne(loanReport.getApplId());
        if(loanApplPo != null) {
            loanApplPo.setRiskScore(loanReport.getScore());
            loanApplPo.setRiskResult(loanReport.getResult().getText());
            loanApplDao.save(loanApplPo);
        }
    }
}