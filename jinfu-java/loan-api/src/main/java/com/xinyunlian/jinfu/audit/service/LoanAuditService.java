package com.xinyunlian.jinfu.audit.service;

import com.xinyunlian.jinfu.audit.dto.LoanAttDto;
import com.xinyunlian.jinfu.audit.dto.LoanAuditDto;
import com.xinyunlian.jinfu.audit.dto.LoanAuditNoteDto;
import com.xinyunlian.jinfu.audit.dto.req.LoanAuditNoteSearchDto;
import com.xinyunlian.jinfu.audit.dto.resp.LoanAuditNoteRespDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author willwang
 */
public interface LoanAuditService {

    /**
     * 核实信息编辑
     * @param loanAuditNoteDto
     */
    void saveNote(LoanAuditNoteDto loanAuditNoteDto);

    /**
     * 找到一条审核核实记录
     * @param noteId
     * @return
     */
    LoanAuditNoteDto find(String noteId);

    /**
     * 核实记录列表
     * @param loanAuditNoteSearchDto
     * @return
     */
    List<LoanAuditNoteRespDto> listNotes(LoanAuditNoteSearchDto loanAuditNoteSearchDto);

    /**
     * 根据申请编号获取审核记录
     * @param applId 申请编号
     * @return
     */
    List<LoanAuditDto> list(String applId);

    /**
     * 根据申请单号删除审核记录信息
     * @param applId
     */
    void deleteByApplId(String applId);

    List<LoanAuditDto> findByApplIds(Collection<String> applIds);

    /**
     * 初审
     * @param loanAuditDto
     */
    LoanApplDto trial(String userId, LoanAuditDto loanAuditDto) throws BizServiceException;

    /**
     * 复审
     * @param loanAuditDto
     */
    LoanApplDto review(String userId, LoanAuditDto loanAuditDto) throws BizServiceException;

    /**
     * 初审分配
     * @param applId
     * @param assignUserId
     */
    void trialAssign(String applId, String assignUserId) throws BizServiceException;

    /**
     * 终审分配
     * @param applId
     * @param assignUserId
     */
    void reviewAssign(String applId, String assignUserId) throws BizServiceException;

    /**
     * 初审撤销
     * @param applId
     * @throws BizServiceException
     */
    void trialRevoke(String applId) throws BizServiceException;

    /**
     * 终审撤销
     * @param applId
     * @throws BizServiceException
     */
    void reviewRevoke(String applId) throws BizServiceException;

    /**
     * 初审任务领取,自动获取按时间顺序排列的X条
     * @param userId
     * @return 领取成功的申请单编号列表
     */
    List<String> trialAcquire(String userId);

    /**
     * 领取单一订单
     * @param applId
     * @param userId
     * @throws BizServiceException
     */
    void trialAcquire(String applId, String userId) throws BizServiceException;

    /**
     * 终审领取指定的单子
     * @param userId
     * @return
     */
    List<String> reviewAcquire(String userId, Set<String> applIds);

    /**
     * 获取附件列表
     * @param applyId
     * @return
     * @throws BizServiceException
     */
    List<LoanAttDto> getAttList(String applyId) throws BizServiceException;

    /**
     * 保存附件
     * @param dto
     * @throws BizServiceException
     */
    void saveAttachment(LoanAttDto dto) throws BizServiceException;

    /**
     * 删除附件
     * @param id
     * @throws BizServiceException
     */
    void deleteAttachment(Long id) throws BizServiceException;

    /**
     * 获取附件详情
     * @param id
     * @return
     * @throws BizServiceException
     */
    LoanAttDto getAttById(Long id) throws BizServiceException;

    /**
     * 根据贷款申请单重置贷款审核状态
     * @param apply
     */
    void resetAuditTemp(LoanApplDto apply);
}
