package com.xinyunlian.jinfu.spider.dto.resp;

import java.io.Serializable;

/**
 * Created by lenovo on on 2017/7/25.
 */
public class EnterpriseInfoParseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String date; // 日期
    private String judgeDoc; //裁判文书
    private String caseType;// 案件类型
    private String caseNum;//案件号

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJudgeDoc() {
        return judgeDoc;
    }

    public void setJudgeDoc(String judgeDoc) {
        this.judgeDoc = judgeDoc;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(String caseNum) {
        this.caseNum = caseNum;
    }
}
