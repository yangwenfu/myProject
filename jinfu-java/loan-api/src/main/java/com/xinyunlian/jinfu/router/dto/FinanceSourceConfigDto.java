package com.xinyunlian.jinfu.router.dto;

import java.io.Serializable;

/**
 * @author willwang
 */
public class FinanceSourceConfigDto implements Serializable{

    private Long id;
    private String sourceFundName;
    private Boolean isUnionRisk;
    private Integer contractTemplate = Integer.valueOf(0);
    private String contractTemplateName;
    private Integer rejectPostProcessing = Integer.valueOf(0);
    private String rejectPostProcessingName;
    private Boolean isConnectionTube;
    private Integer bankCard = Integer.valueOf(0);
    private String bankCardType;
    private Boolean advancReturnSchedule;
    private Boolean replaceAllAdvance;
    private Boolean overdueRepayment;
    private Boolean jinfuCashier;

    /**
     * 小贷端自定义参数
     */
    private Boolean isDepository;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceFundName() {
        return sourceFundName;
    }

    public void setSourceFundName(String sourceFundName) {
        this.sourceFundName = sourceFundName;
    }

    public Boolean getUnionRisk() {
        return isUnionRisk;
    }

    public void setUnionRisk(Boolean unionRisk) {
        isUnionRisk = unionRisk;
    }

    public Integer getContractTemplate() {
        return contractTemplate;
    }

    public void setContractTemplate(Integer contractTemplate) {
        this.contractTemplate = contractTemplate;
    }

    public String getContractTemplateName() {
        return contractTemplateName;
    }

    public void setContractTemplateName(String contractTemplateName) {
        this.contractTemplateName = contractTemplateName;
    }

    public Integer getRejectPostProcessing() {
        return rejectPostProcessing;
    }

    public void setRejectPostProcessing(Integer rejectPostProcessing) {
        this.rejectPostProcessing = rejectPostProcessing;
    }

    public String getRejectPostProcessingName() {
        return rejectPostProcessingName;
    }

    public void setRejectPostProcessingName(String rejectPostProcessingName) {
        this.rejectPostProcessingName = rejectPostProcessingName;
    }

    public Boolean getConnectionTube() {
        return isConnectionTube;
    }

    public void setConnectionTube(Boolean connectionTube) {
        isConnectionTube = connectionTube;
    }

    public Integer getBankCard() {
        return bankCard;
    }

    public void setBankCard(Integer bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(String bankCardType) {
        this.bankCardType = bankCardType;
    }

    public Boolean getAdvancReturnSchedule() {
        return advancReturnSchedule;
    }

    public void setAdvancReturnSchedule(Boolean advancReturnSchedule) {
        this.advancReturnSchedule = advancReturnSchedule;
    }

    public Boolean getReplaceAllAdvance() {
        return replaceAllAdvance;
    }

    public void setReplaceAllAdvance(Boolean replaceAllAdvance) {
        this.replaceAllAdvance = replaceAllAdvance;
    }

    public Boolean getOverdueRepayment() {
        return overdueRepayment;
    }

    public void setOverdueRepayment(Boolean overdueRepayment) {
        this.overdueRepayment = overdueRepayment;
    }

    public Boolean getJinfuCashier() {
        return jinfuCashier;
    }

    public void setJinfuCashier(Boolean jinfuCashier) {
        this.jinfuCashier = jinfuCashier;
    }

    public Boolean getDepository() {
        return isDepository;
    }

    public void setDepository(Boolean depository) {
        isDepository = depository;
    }
}
