package com.xinyunlian.jinfu.user.enums;

/**
 * Created by dongfangchao on 2016/12/8/0008.
 */
public enum EPermission {

    JINFU_MGT("JINFU_MGT","金服后台"),
    CHAN_MGT("CHAN_MGT","渠道管理"),
    DT_MGT("DT_MGT","分销商管理"),
    PUSH_MGT("PUSH_MGT","推送管理"),
    CHAN_QA("CHAN_QA","渠道问题反馈"),
    DA_LOG("DA_LOG","分销日志"),
    ORDER_MGT("ORDER_MGT","订单管理"),
    DA_ORDER("DA_ORDER","分销订单"),
    INS_ORDER("INS_ORDER","保险订单"),
    CR_ORDER("CR_ORDER","信贷订单"),
    MALL_OP("MALL_OP","商城运营"),
    MALL_USER_MGT("MALL_USER_MGT","商城用户管理"),
    MALL_USER_EDIT("MALL_USER_EDIT","用户详情编辑"),
    STORE_MGT("STORE_MGT","店铺管理"),
    PROD_MGT("PROD_MGT","产品管理"),
    PROMO_MGT("PROMO_MGT","促销管理"),
    CONTENT_MGT("CONTENT_MGT","内容管理"),
    RISK_MGT("RISK_MGT","风控管理"),

    /**
     * 20170221-风控后台权限
     */
    BEFORE_APPLY_LIST("BEFORE_APPLY_LIST", "申请"),
    BEFORE_APPLY_ACQUIRE("BEFORE_APPLY_ACQUIRE", "初审领单"),
    BEFORE_TRIAL_LIST("BEFORE_TRIAL_LIST", "初审"),
    BEFORE_REVIEW_LIST("BEFORE_REVIEW_LIST", "终审"),
    DURING_SIGN_LIST("DURING_SIGN_LIST", "签约"),
    DURING_TRANSFER_LIST("DURING_TRANSFER_LIST", "放款"),
    AFTER_LOAN_LIST("AFTER_LOAN_LIST", "贷后已贷业务列表"),
    REMAKE_LOAN("REMAKE_LOAN","重新放款"),
    MANUAL_WITHHOLD("MANUAL_WITHHOLD","手动代扣"),

    REPORT_CENTER("REPORT_CENTER","报表中心"),
    LOAN_HISTORY("LOAN_HISTORY","贷款记录"),
    REPAYMENT_HISTORY("REPAYMENT_HISTORY","还款记录"),
    REPAYMENT_PLAN("REPAYMENT_PLAN","还款计划"),
    BASIC_SETUP("BASIC_SETUP","基础设置"),
    DEPT_EMP("DEPT_EMP","部门与员工"),
    ROLE_PERM("ROLE_PERM","角色与权限"),
    AUTHC_CHAN_SETUP("AUTHC_CHAN_SETUP","鉴权渠道配置"),
    V_TOBACC_CER_POOL("V_TOBACC_CER_POOL","虚拟烟草证池"),
    PROD_TYPE_MGT("PROD_TYPE_MGT","产品分类管理"),
    REGION_MGT("REGION_MGT","地区管理");

    private String code;
    private String text;

    EPermission(String code, String text) {
        this.code = code;
        this.text = text;
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
}
