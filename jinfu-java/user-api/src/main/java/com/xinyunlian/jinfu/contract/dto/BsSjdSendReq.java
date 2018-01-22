package com.xinyunlian.jinfu.contract.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dongfangchao on 2017/2/8/0008.
 */
public class BsSjdSendReq implements Serializable{

    private static final long serialVersionUID = -4068894805592656503L;

    private List<BsReceiveUser> receiveUserList;

    private BsSendUser sendUser;

    private byte[] fileData;

    private String filename;

    private Boolean selfSign = true;

    public List<BsReceiveUser> getReceiveUserList() {
        return receiveUserList;
    }

    public void setReceiveUserList(List<BsReceiveUser> receiveUserList) {
        this.receiveUserList = receiveUserList;
    }

    public BsSendUser getSendUser() {
        return sendUser;
    }

    public void setSendUser(BsSendUser sendUser) {
        this.sendUser = sendUser;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Boolean getSelfSign() {
        return selfSign;
    }

    public void setSelfSign(Boolean selfSign) {
        this.selfSign = selfSign;
    }
}
