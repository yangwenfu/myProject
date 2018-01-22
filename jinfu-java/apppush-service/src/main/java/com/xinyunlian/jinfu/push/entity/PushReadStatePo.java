package com.xinyunlian.jinfu.push.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by apple on 2017/1/4.
 */
@Entity
@Table(name = "PUSH_READSTATE")
public class PushReadStatePo extends BaseMaintainablePo {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MESSAGE_ID")
    private Long messageId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "READ_STATE")
    private Integer readState;

    @Column(name = "PUSH_STATES")
    private Integer pushStates;

    @Column(name = "RETRY_TIMES")
    private Integer retryTimes;

    @OneToOne(optional = false)
    @JoinColumn(name = "MESSAGE_ID", insertable = false, updatable = false)
    private PushMessagePo pushMessagePo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getReadState() {
        return readState;
    }

    public void setReadState(Integer readState) {
        this.readState = readState;
    }

    public Integer getPushStates() {
        return pushStates;
    }

    public void setPushStates(Integer pushStates) {
        this.pushStates = pushStates;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public PushMessagePo getPushMessagePo() {
        return pushMessagePo;
    }

    public void setPushMessagePo(PushMessagePo pushMessagePo) {
        this.pushMessagePo = pushMessagePo;
    }
}
