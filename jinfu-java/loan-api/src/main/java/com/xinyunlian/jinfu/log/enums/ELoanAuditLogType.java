package com.xinyunlian.jinfu.log.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

import java.text.MessageFormat;

/**
 * Created by jl062 on 2017/2/20.
 */
public enum ELoanAuditLogType implements PageEnum{

    APPL("01", "申请提交", "申请人{0}提交了贷款申请{1},申请金额:{2},申请期限:{3},还款方式:{4}"),
    APPL_RETRY("04", "重新提交", "申请人{0}重新提交贷款申请{1},申请金额:{2},申请期限:{3},还款方式:{4}"),
    TRIAL_PULL("02", "初审领单", "初审员{0}领取了贷款申请{1}"),
    TRIAL_FALLBACK("03", "初审退回", "初审员{0}退回贷款申请，{1}，{2}"),
    TRIAL_HANG_UP("05", "申请挂起", "初审员{0}执行了挂起操作"),
    TRIAL_CANCEL_HANG_UP("11", "取消挂起", "初审员{0}执行了取消挂起操作"),
    TRIAL_COMMIT("06", "初审提交", "初审意见：{0}，建议额度：{1}，建议期限：{2}"),
    REVIEW_FALLBACK("07", "退回初审", "终审员{0}退回初审，{1}"),
    REVIEW_COMMIT("08", "终审提交", "终审意见：{0}，额度：{1}，期限：{2}"),
    REVIEW_CANCEL("09", "终审撤销", "终审员{0}执行了撤销审批结果"),
    REVIEW_PULL("10", "终审领单", "终审员{0}领取了贷款申请{1}"),
    AUDIT_ASSIGN("12", "重新分配审批员", "{0}对贷款申请{1}的{2},{3}调整为{4}"),
    TRIAL_REVOKE("13", "初审撤销", "{0}撤销初审审批结果");

    private String code;

    private String text;

    private String template;

    ELoanAuditLogType(String code, String text, String template) {
        this.code = code;
        this.text = text;
        this.template = template;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getContent(String... args) {
        return MessageFormat.format(template, args);
    }
}
