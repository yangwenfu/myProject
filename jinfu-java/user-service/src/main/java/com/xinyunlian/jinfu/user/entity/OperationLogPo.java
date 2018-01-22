package com.xinyunlian.jinfu.user.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xinyunlian.jinfu.common.entity.BasePo;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.user.enums.EOperationType;
import com.xinyunlian.jinfu.user.enums.converter.EOperationLogConverter;

/**
 * Created by JL on 2016/9/5.
 */
@Entity
@Table(name = "user_operation_log")
public class OperationLogPo extends BasePo {

    @Id
    @Column(name = "LOG_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long logId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "OPERATION")
    @Convert(converter = EOperationLogConverter.class)
    private EOperationType operationType;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "TRACE_ID")
    private String traceId;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public EOperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(EOperationType operationType) {
        this.operationType = operationType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public void setContentObject(Object content) {
        this.content = JsonUtil.toJson(content);
    }
}
