package com.xinyunlian.jinfu.audit.service;

import com.xinyunlian.jinfu.acct.dto.req.AcctReserveReq;
import com.xinyunlian.jinfu.acct.service.InnerAcctReserveService;
import com.xinyunlian.jinfu.appl.enums.ELoanApplClaimedType;
import com.xinyunlian.jinfu.appl.enums.ELoanApplSortType;
import com.xinyunlian.jinfu.audit.dao.LoanAttDao;
import com.xinyunlian.jinfu.audit.dao.LoanAuditDao;
import com.xinyunlian.jinfu.audit.dao.LoanAuditNoteDao;
import com.xinyunlian.jinfu.audit.dto.LoanAttDto;
import com.xinyunlian.jinfu.audit.dto.LoanAuditDto;
import com.xinyunlian.jinfu.audit.dto.LoanAuditNoteDto;
import com.xinyunlian.jinfu.audit.dto.req.LoanAuditNoteSearchDto;
import com.xinyunlian.jinfu.audit.dto.resp.LoanAuditNoteRespDto;
import com.xinyunlian.jinfu.audit.entity.LoanAttPo;
import com.xinyunlian.jinfu.audit.entity.LoanAuditNotePo;
import com.xinyunlian.jinfu.audit.entity.LoanAuditPo;
import com.xinyunlian.jinfu.audit.enums.EAuditNoteType;
import com.xinyunlian.jinfu.audit.enums.EAuditStatus;
import com.xinyunlian.jinfu.audit.enums.EAuditType;
import com.xinyunlian.jinfu.common.constant.ApplicationConstant;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.StaticResourceSecurity;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.loan.dao.LoanApplDao;
import com.xinyunlian.jinfu.loan.dto.req.LoanApplySearchDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.apply.LoanApplyListEachDto;
import com.xinyunlian.jinfu.loan.entity.LoanApplPo;
import com.xinyunlian.jinfu.loan.enums.EApplStatus;
import com.xinyunlian.jinfu.loan.service.InnerLoanApplQueryService;
import com.xinyunlian.jinfu.pay.service.InnerPayRecvOrdService;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author willwang
 */
@Service
public class LoanAuditServiceImpl implements LoanAuditService {

    @Autowired
    private LoanAuditDao auditDao;

    @Autowired
    private LoanAuditNoteDao auditNoteDao;

    @Autowired
    private LoanApplDao loanApplDao;

    @Autowired
    private InnerAcctReserveService innerAcctReserveService;

    @Autowired
    private InnerLoanApplQueryService innerLoanApplQueryService;

    @Autowired
    private FinanceSourceService financeSourceService;

    @Autowired
    private LoanAttDao loanAttDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanAuditServiceImpl.class);

    @Value("${file.addr}")
    private String fileAddr;

    private static final int MAX_LENGTH = 500;

    @Override
    @Transactional
    public void saveNote(LoanAuditNoteDto dto) {
        LoanAuditNotePo po;
        if(StringUtils.isNotEmpty(dto.getContent()) && dto.getContent().length() > MAX_LENGTH){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "内容过长，保存失败");
        }
        if(dto.getNoteId() == null){
            po = new LoanAuditNotePo();
            ConverterService.convert(dto, po);
        }else{
            po = auditNoteDao.findOne(dto.getNoteId());
            po.setContent(dto.getContent());
        }

        auditNoteDao.save(po);
    }

    @Override
    public LoanAuditNoteDto find(String noteId) {
        LoanAuditNotePo po = auditNoteDao.findOne(noteId);

        return ConverterService.convert(po, LoanAuditNoteDto.class);
    }

    @Override
    public List<LoanAuditNoteRespDto> listNotes(LoanAuditNoteSearchDto dto) {

        List<LoanAuditNoteRespDto> data = new ArrayList<>();

        Specification<LoanAuditNotePo> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != dto) {
                if (StringUtils.isNotBlank(dto.getApplId())) {
                    expressions.add(cb.equal(root.get("applId"), dto.getApplId()));
                }

                if (null != dto.getAuditNoteType() && EAuditNoteType.ALL != dto.getAuditNoteType()) {
                    expressions.add(cb.equal(root.<EAuditNoteType>get("noteType"), dto.getAuditNoteType()));
                }
            }
            return predicate;
        };

        Sort sort = new Sort(Sort.Direction.DESC, "createTs");
        List<LoanAuditNotePo> list = auditNoteDao.findAll(spec, sort);

        list.forEach(po -> {
            LoanAuditNoteRespDto item = ConverterService.convert(po, LoanAuditNoteRespDto.class);
            item.setDate(DateHelper.formatDate(po.getCreateTs(), ApplicationConstant.TIMESTAMP_FORMAT));
            item.setMgtUserId(po.getCreateOpId());
            item.setId(po.getNoteId());
            data.add(item);
        });

        return data;
    }

    @Override
    @Transactional
    public LoanApplDto trial(String userId, LoanAuditDto auditDto) throws BizServiceException {
        if(StringUtils.isNotEmpty(auditDto.getReason()) && auditDto.getReason().length() > MAX_LENGTH){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "reason too long");
        }
        if(StringUtils.isNotEmpty(auditDto.getRemark()) && auditDto.getRemark().length() > MAX_LENGTH){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "remark too long");
        }

        LoanAuditPo auditPo = this.getAuidtPo(auditDto);

        LoanApplPo apply = loanApplDao.findOne(auditDto.getApplId());
        assertApply(auditDto.getApplId(), apply);
        assertNotHangUp(apply);

        if(apply.getApplStatus() != EApplStatus.TRIAL_CLAIMED){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "trial status is error");
        }
        if(!userId.equals(apply.getTrialUserId())){
            throw new BizServiceException(EErrorCode.LOAN_CANT_OP_OTHERS, "can't trial others");
        }

        //暂存状态，不做贷款申请的状态提交
        if(auditDto.getTemp()){
            auditDao.save(auditPo);
            return ConverterService.convert(apply, LoanApplDto.class);
        }

        apply.setApplStatus(this.getApplStatus(auditDto.getAuditStatus(), this.getTrialMapper()));
        apply.setTrialUserId(userId);

        //如果此时流转向终审待领取，判断一下是否曾经终审领取过，领取过的话，状态直接流转向终审已领取
        if(apply.getApplStatus() == EApplStatus.REVIEW_UNCLAIMED && StringUtils.isNotEmpty(apply.getReviewUserId())){
            apply.setApplStatus(EApplStatus.REVIEW_CLAIMED);
        }

        if(this.isEndingApplyStatus(apply.getApplStatus())){
            this.release(apply);
        }

        auditPo.setAuditUserId(userId);
        auditPo.setAuditDate(new Date());

        auditDao.save(auditPo);
        loanApplDao.save(apply);

        resetAuditTemp(apply);

        return ConverterService.convert(apply, LoanApplDto.class);
    }

    @Override
    @Transactional
    public void trialAssign(String applId, String assignUserId) throws BizServiceException{
        LoanApplPo apply = loanApplDao.findOne(applId);
        assertAssign(apply, "trial");
        apply.setTrialUserId(assignUserId);
        loanApplDao.save(apply);
    }

    @Override
    @Transactional
    public LoanApplDto review(String userId, LoanAuditDto auditDto) {
        if(StringUtils.isNotEmpty(auditDto.getReason()) && auditDto.getReason().length() > MAX_LENGTH){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "reason too long");
        }
        if(StringUtils.isNotEmpty(auditDto.getRemark()) && auditDto.getRemark().length() > MAX_LENGTH){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "remark too long");
        }

        if(auditDto.getAuditStatus() == null){ return null;}
        if(auditDto.getAuditStatus() == EAuditStatus.SUCCEED){
            if(auditDto.getLoanAmt() == null || auditDto.getLoanAmt().compareTo(BigDecimal.ZERO) <= 0){
                throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "Amt can not be null when review success");
            }
        }

        LoanAuditPo auditPo = this.getAuidtPo(auditDto);
        LoanApplPo apply = loanApplDao.findOne(auditDto.getApplId());

        assertApply(auditDto.getApplId(), apply);

        if(apply.getApplStatus() != EApplStatus.REVIEW_CLAIMED){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "review status is error");
        }
        if(!userId.equals(apply.getReviewUserId())){
            throw new BizServiceException(EErrorCode.LOAN_CANT_OP_OTHERS, "can't review others");
        }

        //暂存状态，不做贷款申请的状态提交
        if(auditDto.getTemp()){
            auditDao.save(auditPo);
            return ConverterService.convert(apply, LoanApplDto.class);
        }

        apply.setApplStatus(this.getApplStatus(auditDto.getAuditStatus(), this.getReviewMapper()));
        apply.setReviewUserId(userId);

        auditPo.setAuditUserId(userId);
        auditPo.setAuditDate(new Date());

        if(this.isEndingApplyStatus(apply.getApplStatus())){
            this.release(apply);
        }

        if(apply.getApplStatus() == EApplStatus.SUCCEED){
            apply.setApprTermLen(auditDto.getPeriod());
            apply.setApprAmt(auditDto.getLoanAmt());
        }

        //如果回归到初审已领取,重置初审记录为暂存,如果回归到终审已领取，重置终审记录为暂存

        auditDao.save(auditPo);
        loanApplDao.save(apply);

        resetAuditTemp(apply);

        return ConverterService.convert(apply, LoanApplDto.class);
    }

    @Override
    @Transactional
    public void reviewAssign(String applId, String assignUserId) throws BizServiceException{
        LoanApplPo apply = loanApplDao.findOne(applId);
        assertAssign(apply, "review");
        apply.setReviewUserId(assignUserId);
        loanApplDao.save(apply);
    }

    @Override
    @Transactional
    public void trialRevoke(String applId) throws BizServiceException {
        List<LoanAuditPo> audits = auditDao.findByApplId(applId);
        LoanApplPo apply = loanApplDao.findOne(applId);

        assertNotHangUp(apply);
        assertTrialRevoke(audits, apply);

        this.revertReserve(apply);

        apply.setApplStatus(EApplStatus.TRIAL_CLAIMED);

        resetAuditTemp(apply);

        loanApplDao.save(apply);
    }

    @Override
    @Transactional
    public void reviewRevoke(String applId) throws BizServiceException {
        List<LoanAuditPo> audits = auditDao.findByApplId(applId);

        LoanAuditDto auditDto = new LoanAuditDto();
        auditDto.setAuditType(EAuditType.REVIEW);
        auditDto.setApplId(applId);
        LoanAuditPo auditPo = auditDao.findByApplIdAndAuditType(auditDto.getApplId(), EAuditType.REVIEW);
        LoanApplPo applPo = loanApplDao.findOne(applId);

        assertReviewRevoke(audits, applPo);

        this.revertReserve(applPo);

        auditPo.setTemp(true);
        applPo.setApplStatus(EApplStatus.REVIEW_CLAIMED);

        auditDao.save(auditPo);
        loanApplDao.save(applPo);

        resetAuditTemp(applPo);
    }

    private void revertReserve(LoanApplPo loanApplPo){
        if(this.isEndingApplyStatus(loanApplPo.getApplStatus())){
            //重新占用金额
            AcctReserveReq acctReserveReq = new AcctReserveReq(loanApplPo.getAcctNo(), loanApplPo.getApplAmt(), loanApplPo.getCreditLineRsrvId());
            innerAcctReserveService.reserve(acctReserveReq);
        }
    }

    private LoanAuditPo getAuidtPo(LoanAuditDto auditDto){
        LoanAuditPo auditPo = auditDao.findByApplIdAndAuditType(auditDto.getApplId(), auditDto.getAuditType());
        if(auditPo == null){
            auditPo = new LoanAuditPo();
        }else{
            auditDto.setAuditId(auditPo.getAuditId());
            auditDto.setAuditUserId(auditPo.getAuditUserId());
            auditDto.setAuditDate(auditPo.getAuditDate());
        }
        ConverterService.convert(auditDto, auditPo);
        auditPo.setTermLen(auditDto.getPeriod());
        return auditPo;
    }

    @Override
    public List<LoanAuditDto> list(String applId) {
        List<LoanAuditPo> loanAuditPos = auditDao.findByApplId(applId);

        List<LoanAuditDto> list = new ArrayList<>();

        for (LoanAuditPo loanAuditPo : loanAuditPos) {
            LoanAuditDto loanAuditDto = ConverterService.convert(loanAuditPo, LoanAuditDto.class);
            loanAuditDto.setCreateDate(loanAuditPo.getCreateTs());
            loanAuditDto.setPeriod(loanAuditPo.getTermLen());
            list.add(loanAuditDto);
        }

        return list;
    }

    @Override
    @Transactional
    public void deleteByApplId(String applId) {
        auditDao.deleteByApplId(applId);
    }

    @Override
    public List<LoanAuditDto> findByApplIds(Collection<String> applIds) {
        List<LoanAuditDto> rs = new ArrayList<>();
        if(!applIds.isEmpty()){
            List<List<String>> chunks = this.chunk(this.convertList(applIds), 500);
            for (List<String> chunk : chunks) {
                List<LoanAuditPo> list = auditDao.findByApplIdIn(chunk);
                list.forEach(item -> rs.add(ConverterService.convert(item, LoanAuditDto.class)));
            }
        }
        return rs;
    }

    @Override
    @Transactional
    public List<String> trialAcquire(String userId) {
        List<String> rs = new ArrayList<>();
        LoanApplySearchDto condition = new LoanApplySearchDto();
        condition.setCurrentPage(1);
        condition.setPageSize(2);
        condition.setSortType(ELoanApplSortType.ASC);
        condition.setTrialClaimedType(ELoanApplClaimedType.UNCLAIMED);
        LoanApplySearchDto list = innerLoanApplQueryService.listLoanAppl(condition);

        for (LoanApplyListEachDto item : list.getList()) {
            try{
                this.trialAcquire(item.getApplId(), userId);
                rs.add(item.getApplId());
            }catch(Exception e){
                LOGGER.warn("trial acquire occur exception", e);
            }
        }
        return rs;
    }


    @Override
    @Transactional
    public List<String> reviewAcquire(String userId, Set<String> applIds) {
        List<String> rs = new ArrayList<>();

        for (String applId : applIds) {
            try{
                this.reviewAcquire(applId, userId);
                rs.add(applId);
            }catch(Exception e){
                LOGGER.warn("review acquire occur exception", e);
            }
        }
        return rs;
    }

    @Override
    public List<LoanAttDto> getAttList(String applyId) throws BizServiceException {
        List<LoanAttPo> poList = loanAttDao.findByApplyId(applyId);

        List<LoanAttDto> retList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(poList)){
            poList.forEach(po -> {
                LoanAttDto dto = ConverterService.convert(po, LoanAttDto.class);
                if (StringUtils.isNotEmpty(dto.getFilePath())){
                    dto.setFilePath(fileAddr + StaticResourceSecurity.getSecurityURI(dto.getFilePath()));
                }
                retList.add(dto);
            });
        }

        return retList;
    }

    @Override
    @Transactional
    public void saveAttachment(LoanAttDto dto) throws BizServiceException {
        LoanAttPo po = ConverterService.convert(dto, LoanAttPo.class);
        loanAttDao.save(po);
    }

    @Override
    @Transactional
    public void deleteAttachment(Long id) throws BizServiceException {
        loanAttDao.delete(id);
    }

    @Override
    public LoanAttDto getAttById(Long id) throws BizServiceException {
        LoanAttPo po = loanAttDao.findOne(id);
        LoanAttDto dto = ConverterService.convert(po, LoanAttDto.class);
        if (StringUtils.isNotEmpty(dto.getFilePath())){
            dto.setFilePath(fileAddr + dto.getFilePath());
        }
        return dto;
    }

    @Override
    @Transactional
    public void resetAuditTemp(LoanApplDto apply) {
        LoanApplPo po = ConverterService.convert(apply, LoanApplPo.class);
        this.resetAuditTemp(po);
    }

    public void trialAcquire(String applId, String userId) throws BizServiceException{
        LoanApplPo apply = loanApplDao.findOne(applId);

        assertApply(applId, apply);

        //初审人不存在且状态处于待初审领取的单子才可以进行领取操作
        if(StringUtils.isNotEmpty(apply.getTrialUserId()) || apply.getApplStatus() != EApplStatus.TRIAL_UNCLAIMED){
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID,
                    String.format("applyId:[%s] apply status is not unclaimed or trial user id not null", applId));
        }

        apply.setTrialUserId(userId);
        apply.setApplStatus(EApplStatus.TRIAL_CLAIMED);

        loanApplDao.save(apply);
    }


    private void reviewAcquire(String applId, String userId) throws BizServiceException{
        LoanApplPo apply = loanApplDao.findOne(applId);

        assertApply(applId, apply);

        //初审人不存在且状态处于待初审领取的单子才可以进行领取操作
        if(StringUtils.isNotEmpty(apply.getReviewUserId()) || apply.getApplStatus() != EApplStatus.REVIEW_UNCLAIMED){
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID,
                String.format("applyId:[%s] apply status is not unclaimed or review user id not null", applId));
        }

        apply.setReviewUserId(userId);
        apply.setApplStatus(EApplStatus.REVIEW_CLAIMED);

        loanApplDao.save(apply);
    }

    /**
     * 审核结束，释放占用金额
     * @param loanApplPo
     */
    private void release(LoanApplPo loanApplPo) {
        innerAcctReserveService.unReserve(loanApplPo.getCreditLineRsrvId());
    }

    /**
     * 贷款申请基础信息监测
     * @param applId
     * @param apply
     */
    private void assertApply(String applId, LoanApplPo apply){
        if(apply == null){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, String.format("applyId:[%s] not exists", applId));
        }
        if(apply.getApplStatus() == null){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, String.format("applyId:[%s] appl status is null", applId));
        }
    }

    private void assertNotHangUp(LoanApplPo apply) throws BizServiceException{
        if(apply != null && apply.getHangUp() != null && apply.getHangUp()){
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID,
                    String.format("applyId:[%s] is hang up", apply.getApplId()));
        }
    }

    /**
     * 特定状态下，审批信息需要回归到暂存状态
     * @param apply
     */
    private void resetAuditTemp(LoanApplPo apply){
        EAuditType auditType = null;
        switch (apply.getApplStatus()){
            case TRIAL_CLAIMED:
                auditType = EAuditType.TRIAL;
                break;
            case REVIEW_CLAIMED:
                auditType = EAuditType.REVIEW;
                break;
            default:
                break;
        }
        if(auditType == null){
            return;
        }
        LoanAuditPo audit = auditDao.findByApplIdAndAuditType(apply.getApplId(), auditType);
        if(audit != null){
            audit.setTemp(true);
            auditDao.save(audit);
        }
    }

    private List<String> convertList(Collection<String> applIds){
        List<String> list = new ArrayList<>();
        Iterator<String> iter =  applIds.iterator();
        while(iter.hasNext()){
            list.add(iter.next());
        }
        return list;
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

    /**
     * 初审状态映射
     * @return
     */
    private Map<EAuditStatus, EApplStatus> getTrialMapper() {
        Map<EAuditStatus, EApplStatus> statusMapper = new HashMap<>();

        statusMapper.put(EAuditStatus.ADVISE_PASS, EApplStatus.REVIEW_UNCLAIMED);
        statusMapper.put(EAuditStatus.ADVISE_REJECT, EApplStatus.REVIEW_UNCLAIMED);
        statusMapper.put(EAuditStatus.SUCCEED, EApplStatus.REVIEW_UNCLAIMED);

        statusMapper.put(EAuditStatus.REJECT, EApplStatus.REJECT);
        statusMapper.put(EAuditStatus.CANCEL, EApplStatus.CANCEL);

        statusMapper.put(EAuditStatus.FALLBACK, EApplStatus.FALLBACK);
        return statusMapper;
    }

    /**
     * 终审状态映射
     * @return
     */
    private Map<EAuditStatus, EApplStatus> getReviewMapper() {
        Map<EAuditStatus, EApplStatus> statusMapper = new HashMap<>();

        statusMapper.put(EAuditStatus.ADVISE_PASS, EApplStatus.SUCCEED);
        statusMapper.put(EAuditStatus.ADVISE_REJECT, EApplStatus.REJECT);
        statusMapper.put(EAuditStatus.SUCCEED, EApplStatus.SUCCEED);

        statusMapper.put(EAuditStatus.REJECT, EApplStatus.REJECT);
        statusMapper.put(EAuditStatus.CANCEL, EApplStatus.CANCEL);

        statusMapper.put(EAuditStatus.FALLBACK, EApplStatus.TRIAL_CLAIMED);
        return statusMapper;
    }

    private EApplStatus getApplStatus(EAuditStatus auditStatus, Map<EAuditStatus, EApplStatus> mapper){
        return mapper.get(auditStatus);
    }

    /**
     * 是否贷款申请的终结状态
     * @param applStatus
     * @return
     */
    private boolean isEndingApplyStatus(EApplStatus applStatus){
        return applStatus == EApplStatus.REJECT
                || applStatus == EApplStatus.FALLBACK
                || applStatus == EApplStatus.CANCEL;
    }

    private void assertAssign(LoanApplPo apply, String action){

        if(apply == null){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "不存在的贷款申请");
        }

        switch (action){
            case "trial":
                //不属于初审待领取、初审已领取的单子，不能被初审重新分配
                if(!Arrays.asList(EApplStatus.TRIAL_CLAIMED, EApplStatus.TRIAL_UNCLAIMED).contains(apply.getApplStatus())){
                    throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "当前状态无法进行初审人员分配");
                }
                break;
            case "review":
                if(!Arrays.asList(EApplStatus.REVIEW_CLAIMED, EApplStatus.REVIEW_UNCLAIMED).contains(apply.getApplStatus())){
                    throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "当前状态无法进行终审人员分配");
                }
                break;
            default:
                break;
        }
    }

    private LoanAuditPo getAudit(List<LoanAuditPo> audits, EAuditType auditType){
        if(CollectionUtils.isEmpty(audits)){
            return null;
        }

        for (LoanAuditPo audit : audits) {
            if(audit.getAuditType() == auditType){
                return audit;
            }
        }

        return null;
    }

    private void assertTrialRevoke(List<LoanAuditPo> audits, LoanApplPo apply){
        if(apply == null){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "不存在的贷款申请");
        }

        LoanAuditPo trial = this.getAudit(audits, EAuditType.TRIAL);

        if(trial == null || (trial.getTemp() != null && trial.getTemp())){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "不存在初审信息，或初审为暂存状态");
        }

        if(!Arrays.asList(EAuditStatus.CANCEL, EAuditStatus.REJECT).contains(trial.getAuditStatus())){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "初审记录不是拒绝或取消，无法进行初审撤销");
        }

        if(!Arrays.asList(EApplStatus.CANCEL, EApplStatus.REJECT).contains(apply.getApplStatus())){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "贷款申请不是拒绝或取消，无法进行初审撤销");
        }
    }

    private void assertReviewRevoke(List<LoanAuditPo> audits, LoanApplPo apply){
        if(apply == null || StringUtils.isEmpty(apply.getApplId())){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "申请信息不存在，无法撤销");
        }

        //如果已签约，也无法撤销
        if(apply.getSigned() != null && apply.getSigned()){
            throw new BizServiceException(EErrorCode.LOAN_CANT_REVOKE, "已签约贷款，无法撤销");
        }

        LoanAuditPo review = this.getAudit(audits, EAuditType.REVIEW);

        if(review == null || (review.getTemp() != null && review.getTemp())){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "不存在初审信息，或终审为暂存状态");
        }

        if(!Arrays.asList(EAuditStatus.SUCCEED, EAuditStatus.REJECT).contains(review.getAuditStatus())){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "终审记录不是同意或拒绝，无法撤销");
        }

        //爱投资资金且为同意，已经到爱投资那边生成订单了，无法撤销
        FinanceSourceDto financeSourceDto = financeSourceService.findById(apply.getFinanceSourceId());
        if(financeSourceDto.getType() == EFinanceSourceType.AITOUZI && review.getAuditStatus() == EAuditStatus.SUCCEED){
            throw new BizServiceException(EErrorCode.LOAN_EXTERNAL_CANT_REVOKE, "非自有资金，无法撤销");
        }

    }

}
