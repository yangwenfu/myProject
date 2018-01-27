package com.xinyunlian.jinfu.contract.enums;

/**
 * Created by JL on 2016/9/26.
 */
public enum ECntrctTmpltType {

    ZXSQ("ZXSQ", "个人征信客户授权书", "ZXSQ.vm"),
    ZXCX("ZXCX", "用户征信综合查询授权书", "ZXCX.vm"),
    ZCXY("ZCXY", "云联金服用户注册协议", "ZCXY.vm"),
    DKL01001("DKL01001", "委托扣款协议书", "DKL01001.vm"),
    YDL01001("YDL01001", "云速贷业务贷款协议", "YDL01001.vm"),
    YDL01002("YDL01002", "云随贷业务综合协议", "YDL01002.vm"),
    YDL01006("YDL01006", "小烟贷贷款综合协议", "YDL01006.vm"),
    ZXL01006("ZXL01006", "贷款咨询与服务协议", "ZXL01006.vm"),
    XSMSQ("XSMSQ", "全国烟草订购网站授权协议", "XSMSQ.vm"),
    ZFXY("ZFXY", "云联金服用户支付服务协议", "ZFXY.vm"),
    YM01001("YM01001", "中国民生银行条码支付收款业务申请表及商户服务协议", "YM01001.vm"),
    ATZ_LOAN("ATZ_LOAN", "借款合同", "ATZ_LOAN.vm");

    private String code;

    private String text;

    private String path;

    ECntrctTmpltType(String code, String text, String path) {
        this.code = code;
        this.text = text;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
