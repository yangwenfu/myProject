package com.xinyunlian.jinfu.contract.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by JL on 2016/9/20.
 */
@Entity
@Table(name = "CONTRACT_TEMPLATE")
public class ContractTemplatePo extends BaseMaintainablePo {

    @Id
    @Column(name = "TMPLT_ID")
    private Long templateId;

    @Column(name = "TMPLT_NAME")
    private String templateName;

    @Column(name = "TMPLT_CONTENT")
    private String templateContent;

    @Column(name = "TMPLT_PREFIX")
    private String idPrefix;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public String getIdPrefix() {
        return idPrefix;
    }

    public void setIdPrefix(String idPrefix) {
        this.idPrefix = idPrefix;
    }
}
