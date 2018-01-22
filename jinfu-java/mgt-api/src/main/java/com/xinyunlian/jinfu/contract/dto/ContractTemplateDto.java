package com.xinyunlian.jinfu.contract.dto;

import java.io.Serializable;

/**
 * Created by JL on 2016/9/20.
 */
public class ContractTemplateDto implements Serializable {

    private Long templateId;

    private String templateName;

    private String templateContent;

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
