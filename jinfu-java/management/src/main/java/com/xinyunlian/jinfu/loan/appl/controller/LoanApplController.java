package com.xinyunlian.jinfu.loan.appl.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.external.dto.LoanRecordMqDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xinyunlian.jinfu.appl.dto.BeforeTrialDetailDto;
import com.xinyunlian.jinfu.audit.dto.LoanAuditDto;
import com.xinyunlian.jinfu.audit.enums.EAuditStatus;
import com.xinyunlian.jinfu.audit.enums.EAuditType;
import com.xinyunlian.jinfu.audit.service.LoanAuditService;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.util.excel.DataTablesExcelService;
import com.xinyunlian.jinfu.loan.appl.dto.LoanApplEachExcelDto;
import com.xinyunlian.jinfu.loan.appl.dto.TrialFallbackDto;
import com.xinyunlian.jinfu.loan.appl.service.PrivateLoanApplService;
import com.xinyunlian.jinfu.loan.dto.req.LoanApplySearchDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.log.enums.ELoanAuditLogType;
import com.xinyunlian.jinfu.schedule.dto.management.MSContainerDto;
import com.xinyunlian.jinfu.schedule.service.ScheduleService;

@Controller
@RequestMapping("loan/apply")
public class LoanApplController{
    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PrivateLoanApplService privateLoanApplService;

    @Autowired
    private LoanAuditService loanAuditService;

    @Autowired
    private FinanceSourceService financeSourceService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanApplController.class);

    /**
     * 贷前-进件-列表
     */
    @ResponseBody
    @RequestMapping(value = "before/apply/list", method = RequestMethod.POST)
    @RequiresPermissions({"BEFORE_APPLY_LIST"})
    public Object applyList(@RequestBody LoanApplySearchDto search) {
        LoanApplySearchDto rs = privateLoanApplService.applyList(search);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), rs);
    }

    /**
     * 贷前-领取初审单
     */
    @ResponseBody
    @RequestMapping(value = "before/apply/acquire", method = RequestMethod.GET)
    @RequiresPermissions({"BEFORE_APPLY_ACQUIRE"})
    public Object trialAcquire() {
        String mgtUserId = SecurityContext.getCurrentOperatorId();
        List<String> applIds = loanAuditService.trialAcquire(mgtUserId);

        applIds.forEach(applId -> privateLoanApplService.saveAuditLog(mgtUserId, applId, ELoanAuditLogType.TRIAL_PULL));

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), applIds.size());
    }

    /**
     * 贷前-申请单详情接口
     */
    @ResponseBody
    @RequestMapping(value = "before/apply/detail", method = RequestMethod.GET)
    @RequiresPermissions(value={"BEFORE_APPLY_LIST", "BEFORE_TRIAL_LIST", "BEFORE_REVIEW_LIST", "DURING_SIGN_LIST", "DURING_TRANSFER_LIST", "AFTER_LOAN_LIST", "GENERAL_LIST"}, logical = Logical.OR)
    public Object applyDetail(@RequestParam String applId) {
        BeforeTrialDetailDto beforeTrialDetailDto = privateLoanApplService.getBeforeTrialDetail(applId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), beforeTrialDetailDto);
    }

    /**
     * 挂起状态toggle
     */
    @ResponseBody
    @RequestMapping(value = "before/trial/hangup", method = RequestMethod.GET)
    @RequiresPermissions({"BEFORE_TRIAL_LIST"})
    public Object trialHangup(@RequestParam String applId, @RequestParam Boolean hangup) {
        String mgtUserId = SecurityContext.getCurrentOperatorId();

        try{
            LoanApplDto apply = loanApplService.get(applId);
            apply.setHangUp(hangup);
            loanApplService.save(apply);

            ELoanAuditLogType auditLogType;
            if(hangup != null && hangup){
                auditLogType = ELoanAuditLogType.TRIAL_HANG_UP;
            }else{
                auditLogType = ELoanAuditLogType.TRIAL_CANCEL_HANG_UP;
            }
            privateLoanApplService.saveAuditLog(mgtUserId, applId, auditLogType);
            return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
        }catch(Exception e){
            LOGGER.warn("hangup occur exception", e);
            return ResultDtoFactory.toNack(MessageUtil.getMessage("common.operate.fail"));
        }
    }

    /**
     * 初审退回
     */
    @ResponseBody
    @RequestMapping(value = "before/trial/fallback", method = RequestMethod.POST)
    @RequiresPermissions({"BEFORE_TRIAL_LIST"})
    public Object trialFallback(@RequestBody TrialFallbackDto fallback) {
        String mgtUserId = SecurityContext.getCurrentOperatorId();
        LoanAuditDto audit = new LoanAuditDto();
        audit.setApplId(fallback.getApplId());
        audit.setAuditType(EAuditType.TRIAL);
        audit.setAuditStatus(EAuditStatus.FALLBACK);
        audit.setReason(fallback.getReason());
        audit.setRemark(fallback.getRemark());
        loanAuditService.trial(mgtUserId, audit);
        privateLoanApplService.saveAuditLog(mgtUserId, fallback.getApplId(), ELoanAuditLogType.TRIAL_FALLBACK,
            fallback.getReason(), fallback.getRemark()
        );
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 终审退回
     */
    @ResponseBody
    @RequestMapping(value = "before/review/fallback", method = RequestMethod.POST)
    @RequiresPermissions({"BEFORE_REVIEW_LIST"})
    public Object reviewFallback(@RequestBody TrialFallbackDto fallback) {
        String mgtUserId = SecurityContext.getCurrentOperatorId();
        LoanAuditDto audit = new LoanAuditDto();
        audit.setApplId(fallback.getApplId());
        audit.setAuditType(EAuditType.REVIEW);
        audit.setAuditStatus(EAuditStatus.FALLBACK);
        audit.setRemark(fallback.getRemark());
        audit.setTemp(false);
        loanAuditService.review(mgtUserId, audit);
        privateLoanApplService.saveAuditLog(mgtUserId, fallback.getApplId(), ELoanAuditLogType.REVIEW_FALLBACK,
            fallback.getRemark()
        );
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 初审撤销
     */
    @ResponseBody
    @RequestMapping(value = "before/trial/revoke", method = RequestMethod.GET)
    @RequiresPermissions({"BEFORE_TRIAL_LIST"})
    public Object trialRevoke(@RequestParam String applId) {
        String mgtUserId = SecurityContext.getCurrentOperatorId();

        loanAuditService.trialRevoke(applId);

        privateLoanApplService.saveAuditLog(mgtUserId, applId, ELoanAuditLogType.TRIAL_REVOKE);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 终审撤销
     */
    @ResponseBody
    @RequestMapping(value = "before/review/revoke", method = RequestMethod.GET)
    @RequiresPermissions({"BEFORE_REVIEW_LIST"})
    public Object reviewRevoke(@RequestParam String applId) {
        String mgtUserId = SecurityContext.getCurrentOperatorId();

        loanAuditService.reviewRevoke(applId);

        privateLoanApplService.saveAuditLog(mgtUserId, applId, ELoanAuditLogType.REVIEW_CANCEL);

        financeSourceService.revertAmt(applId);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 初审列表
     */
    @ResponseBody
    @RequestMapping(value = "before/trial/list", method = RequestMethod.POST)
    @RequiresPermissions({"BEFORE_TRIAL_LIST"})
    public Object trialList(@RequestBody LoanApplySearchDto search) {
        LoanApplySearchDto rs = privateLoanApplService.trialList(search);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), rs);
    }

    /**
     * 贷前-终审-领取
     */
    @ResponseBody
    @RequestMapping(value = "before/review/acquire", method = RequestMethod.POST)
    @RequiresPermissions({"BEFORE_REVIEW_LIST"})
    public Object reviewAcquire(@RequestBody Set<String> list) {
        String mgtUserId = SecurityContext.getCurrentOperatorId();
        List<String> applIds = loanAuditService.reviewAcquire(mgtUserId, list);

        applIds.forEach(applId -> privateLoanApplService.saveAuditLog(mgtUserId, applId, ELoanAuditLogType.REVIEW_PULL));

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), applIds.size());
    }

    /**
     * 贷前-终审-列表
     */
    @ResponseBody
    @RequestMapping(value = "before/review/list", method = RequestMethod.POST)
    @RequiresPermissions({"BEFORE_REVIEW_LIST"})
    public Object reviewList(@RequestBody LoanApplySearchDto search) {
        LoanApplySearchDto rs = privateLoanApplService.reviewList(search);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), rs);
    }

    /**
     * 贷前-终审-待领取列表
     */
    @ResponseBody
    @RequestMapping(value = "before/review/unclaimed", method = RequestMethod.GET)
    @RequiresPermissions({"BEFORE_REVIEW_LIST"})
    public Object reviewUnclaimedList() {
        LoanApplySearchDto rs = privateLoanApplService.reviewUnclaimedList();
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), rs);
    }

    /**
     * 贷中-签约-列表
     */
    @ResponseBody
    @RequestMapping(value = "during/sign/list", method = RequestMethod.POST)
    @RequiresPermissions({"DURING_SIGN_LIST"})
    public Object signList(@RequestBody LoanApplySearchDto search) {
        LoanApplySearchDto rs = privateLoanApplService.signList(search);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), rs);
    }

    /**
     * 贷中-放款-列表
     */
    @ResponseBody
    @RequestMapping(value = "during/transfer/list", method = RequestMethod.POST)
    @RequiresPermissions({"DURING_TRANSFER_LIST"})
    public Object transferList(@RequestBody LoanApplySearchDto search) {
        LoanApplySearchDto rs = privateLoanApplService.transferList(search);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), rs);
    }

    /**
     * 贷款申请综合查询列表
     */
    @RequestMapping(value = "base/general/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions({"GENERAL_LIST"})
    public Object generalList(@RequestBody LoanApplySearchDto search) {
        LoanApplySearchDto rs = privateLoanApplService.general(search);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), rs);
    }

    /**
     * 贷款申请综合查询导出
     */
    @RequestMapping(value = "base/general/export", method = RequestMethod.POST)
    @RequiresPermissions({"GENERAL_EXPORT"})
    public Object generalExport(@RequestBody LoanApplySearchDto search) {
        List<LoanApplEachExcelDto> list = privateLoanApplService.generalExport(search);
        Map<String, Object> model = new HashedMap();
        model.put("data", list);
        model.put("fileName","贷款业务综合报表.xls");
        model.put("tempPath","/templates/贷款业务综合报表.xls");
        DataTablesExcelService dataTablesExcelService = new DataTablesExcelService();
        return new ModelAndView(dataTablesExcelService, model);
    }

    /**
     * 还款计划与还款列表
     */
    @ResponseBody
    @RequestMapping(value = "schedule", method = RequestMethod.GET)
    public Object schedule(@RequestParam String applId) {
        List<MSContainerDto> list = scheduleService.getManagementSchedule(applId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), list);
    }

}
