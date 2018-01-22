package com.xinyunlian.jinfu.bank.dto;

/**
 * Created by menglei on 2017年05月22日.
 */
public class CorpBankCardDto {

    private static final long serialVersionUID = 4810036887617955539L;

    private Long id;

    private String userId;

    private String bankShortName;

    private String openingBank;

    private String bankBranch;

    private String acctName;

    private String account;

    private Boolean uploadPic = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBankShortName() {
        return bankShortName;
    }

    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }

    public String getOpeningBank() {
        return openingBank;
    }

    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Boolean getUploadPic() {
        return uploadPic;
    }

    public void setUploadPic(Boolean uploadPic) {
        this.uploadPic = uploadPic;
    }
}
