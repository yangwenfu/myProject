package com.xinyunlian.jinfu.user.dto;

import java.io.Serializable;

/**
 * Created by DongFC on 2016-10-19.
 */
public class ClientSaltDto implements Serializable {
    private static final long serialVersionUID = -2466373309785260771L;

    private Long id;

    private String clientId;

    private String salt;

    private String remark;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
