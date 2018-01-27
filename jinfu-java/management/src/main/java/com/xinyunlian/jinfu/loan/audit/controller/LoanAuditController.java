package com.xinyunlian.jinfu.loan.audit.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xinyunlian.jinfu.audit.dto.LoanAttDto;
import com.xinyunlian.jinfu.audit.dto.LoanAuditDto;
import com.xinyunlian.jinfu.audit.dto.LoanAuditNoteDto;
import com.xinyunlian.jinfu.audit.dto.resp.LoanAuditNoteRespDto;
import com.xinyunlian.jinfu.audit.enums.EAuditNoteType;
import com.xinyunlian.jinfu.audit.enums.EAuditStatus;
import com.xinyunlian.jinfu.audit.enums.EAuditType;
import com.xinyunlian.jinfu.audit.service.LoanAuditService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.fileStore.FileStoreService;
import com.xinyunlian.jinfu.common.fileStore.enums.EFileType;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.loan.audit.service.PrivateLoanAuditService;
import com.xinyunlian.jinfu.log.dto.LoanAuditLogDto;
import com.xinyunlian.jinfu.log.service.LoanAuditLogService;
import com.xinyunlian.jinfu.user.dto.MgtUserDto;
import com.xinyunlian.jinfu.user.service.MgtUserService;

@RestController
@RequestMapping("loan/audit")
public class LoanAuditController{

    @Autowired
    private LoanAuditService loanAuditService;

    @Autowired
    private PrivateLoanAuditService privateLoanAuditService;

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private LoanAuditLogService loanAuditLogService;

    @Autowired
    private MgtUserService mgtUserService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanAuditController.class);

    @ResponseBody
    @RequestMapping(value="test", method = RequestMethod.GET)
    public Object test(@RequestParam String applId, @RequestParam BigDecimal amt, @RequestParam Integer period)
    {
        if(AppConfigUtil.isProdEnv()){
            return ResultDtoFactory.toNack("生产环境无法执行该操作");
        }

        String mgtUserId = SecurityContext.getCurrentOperatorId();
        //初审领取
        loanAuditService.trialAcquire(applId, mgtUserId);
        //初审建议通过
        LoanAuditDto auditDto = new LoanAuditDto();
        auditDto.setAuditType(EAuditType.TRIAL);
        auditDto.setApplId(applId);
        auditDto.setAuditStatus(EAuditStatus.ADVISE_PASS);
        auditDto.setLoanAmt(amt);
        auditDto.setPeriod(period);
        auditDto.setReason("测试建议通过222222");
        auditDto.setRemark("测试建议通过");
        loanAuditService.trial(mgtUserId, auditDto);
        //终审领取
        Set<String> applIds = new HashSet<>();
        applIds.add(applId);
        loanAuditService.reviewAcquire(mgtUserId, applIds);
        //终审通过
        auditDto = new LoanAuditDto();
        auditDto.setAuditType(EAuditType.REVIEW);
        auditDto.setApplId(applId);
        auditDto.setAuditStatus(EAuditStatus.SUCCEED);
        auditDto.setLoanAmt(amt);
        auditDto.setPeriod(period);
        auditDto.setReason("终审通过");
        auditDto.setRemark("终审通过");
        loanAuditService.review(mgtUserId, auditDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 审核核实记录查询
     */
    @ResponseBody
    @RequestMapping(value = "/note/list", method = RequestMethod.GET)
    @RequiresPermissions(value={"BEFORE_TRIAL_LIST","BEFORE_REVIEW_LIST", "GENERAL_AUDIT_LOG", "DURING_SIGN_LIST", "DURING_TRANSFER_LIST"}, logical = Logical.OR)
    public ResultDto<List<LoanAuditNoteRespDto>> listNotes(@RequestParam String applId) {
        List<LoanAuditNoteRespDto> list = privateLoanAuditService.listNotes(applId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), list);
    }

    /**
     * 审核核实记录添加
     */
    @ResponseBody
    @RequestMapping(value = "/note/edit", method = RequestMethod.POST)
    @RequiresPermissions(value={"BEFORE_TRIAL_LIST","BEFORE_REVIEW_LIST"}, logical = Logical.OR)
    public ResultDto editNote(@RequestBody LoanAuditNoteDto dto) {
        try {
            dto.setNoteType(EAuditNoteType.PHONE);
            loanAuditService.saveNote(dto);
        } catch (BizServiceException ex) {
            return ResultDtoFactory.toNack(ex.getMessage());
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 审核核实记录添加
     */
    @ResponseBody
    @RequestMapping(value = "/note/get", method = RequestMethod.GET)
    @RequiresPermissions(value={"BEFORE_TRIAL_LIST","BEFORE_REVIEW_LIST", "GENERAL_AUDIT_LOG", "DURING_SIGN_LIST", "DURING_TRANSFER_LIST"}, logical = Logical.OR)
    public ResultDto getNote(@RequestParam String noteId) {
        LoanAuditNoteDto dto = loanAuditService.find(noteId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), dto);
    }

    /**
     * 初审
     */
    @ResponseBody
    @RequestMapping(value = "/trial", method = RequestMethod.POST)
    @RequiresPermissions({"BEFORE_TRIAL_LIST"})
    public ResultDto trial(@RequestBody LoanAuditDto audit) {
        String mgtUserId = SecurityContext.getCurrentOperatorId();
        privateLoanAuditService.trial(mgtUserId, audit);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 复审
     */
    @ResponseBody
    @RequestMapping(value = "/review", method = RequestMethod.POST)
    @RequiresPermissions({"BEFORE_REVIEW_LIST"})
    public ResultDto review(@RequestBody LoanAuditDto audit) {
        String mgtUserId = SecurityContext.getCurrentOperatorId();
        privateLoanAuditService.review(mgtUserId, audit);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 所有后台管理人员的列表
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = {"AUDIT_ASSIGN", "LOAN_SEARCH_PERSON"}, logical = Logical.OR)
    public ResultDto users() {
        List<MgtUserDto> users = mgtUserService.findByNameLike("");
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), users);
    }

    /**
     * 初审人员分配
     */
    @RequestMapping(value = "/trial/assign", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions({"AUDIT_ASSIGN"})
    public ResultDto trialAssigin(@RequestParam String applId, @RequestParam String userId) {
        privateLoanAuditService.assign(applId, userId, EAuditType.TRIAL);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 终审人员分配
     */
    @RequestMapping(value = "/review/assign", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions({"AUDIT_ASSIGN"})
    public ResultDto reviewAssigin(@RequestParam String applId, @RequestParam String userId) {
        privateLoanAuditService.assign(applId, userId, EAuditType.REVIEW);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 审核记录
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions(value={"BEFORE_TRIAL_LIST","BEFORE_REVIEW_LIST", "GENERAL_AUDIT_LOG", "DURING_SIGN_LIST", "DURING_TRANSFER_LIST"}, logical = Logical.OR)
    public ResultDto list(@RequestParam String applId) {
        List<LoanAuditDto> audits = privateLoanAuditService.list(applId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), audits);
    }

    /**
     * 附件列表
     * @param applyId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/applyId", method = RequestMethod.GET)
    @RequiresPermissions(value={"BEFORE_TRIAL_LIST","BEFORE_REVIEW_LIST", "GENERAL_FILE"}, logical = Logical.OR)
    public ResultDto<Object> attList(@RequestParam String applyId){
        List<LoanAttDto> list = loanAuditService.getAttList(applyId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), list);
    }

    /**
     * 上传附件
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "uploadAtt", method = RequestMethod.POST)
    @RequiresPermissions(value={"BEFORE_TRIAL_LIST","BEFORE_REVIEW_LIST"}, logical = Logical.OR)
    public ResultDto<Object> uploadAtt(@RequestParam("file") MultipartFile file, String applId){
        String mgtUserId = SecurityContext.getCurrentOperatorId();
        MgtUserDto mgtUser = mgtUserService.getMgtUserInf(mgtUserId);

        try {
            if (file != null){
                String filePath =
                        fileStoreService.upload(EFileType.LOAN_ATT, file.getInputStream(), file.getOriginalFilename());

                LoanAttDto loanAttDto = new LoanAttDto();
                loanAttDto.setApplyId(applId);
                loanAttDto.setUploadDate(new Date());
                loanAttDto.setUploader(mgtUser.getName());
                loanAttDto.setFilePath(filePath);
                loanAttDto.setFileName(file.getOriginalFilename());
                loanAuditService.saveAttachment(loanAttDto);
                return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
            }
        } catch (Exception e) {
            LOGGER.error("上传失败", e);
            return ResultDtoFactory.toNack("上传失败");
        }

        return ResultDtoFactory.toNack("上传文件不能为空");
    }

    /**
     * 删除附件
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteAtt", method = RequestMethod.GET)
    @RequiresPermissions(value={"BEFORE_TRIAL_LIST","BEFORE_REVIEW_LIST"}, logical = Logical.OR)
    public ResultDto<Object> deleteAtt(@RequestParam Long id){
        LoanAttDto dto = loanAuditService.getAttById(id);
        String deleteAppPath = dto.getFilePath();
        String deleteFileName = deleteAppPath.substring(deleteAppPath.lastIndexOf("/") + 1, deleteAppPath.length());
        fileStoreService.delete(EFileType.APP_FILE_PATH.getPath(), deleteFileName);

        loanAuditService.deleteAttachment(id);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 操作日志信息
     */
    @RequiresPermissions(value={"BEFORE_TRIAL_LIST","BEFORE_REVIEW_LIST", "GENERAL_AUDIT_LOG", "DURING_SIGN_LIST", "DURING_TRANSFER_LIST"}, logical = Logical.OR)
    @RequestMapping(value = "/log/list", method = RequestMethod.GET)
    public Object listLogs(@RequestParam String applId) {
        List<LoanAuditLogDto> list = loanAuditLogService.getByApplNo(applId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), list);
    }

}
