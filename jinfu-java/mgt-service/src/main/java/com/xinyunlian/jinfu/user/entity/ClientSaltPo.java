package com.xinyunlian.jinfu.user.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by DongFC on 2016-10-19.
 */
@Entity
@Table(name = "client_salt")
public class ClientSaltPo implements Serializable {
    private static final long serialVersionUID = -2466373309785260771L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CLIENT_ID")
    private String clientId;

    @Column(name = "SALT")
    private String salt;

    @Column(name = "REMARK")
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
