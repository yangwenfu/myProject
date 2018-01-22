package com.xinyunlian.jinfu.gather.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/3/15/0015.
 */
public class MessageGaDto implements Serializable{

    private static final long serialVersionUID = 4030495995503873462L;

    private Long id;

    private String text;

    private String sendTime;

    private String phoneNo;

    private String receive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

}
