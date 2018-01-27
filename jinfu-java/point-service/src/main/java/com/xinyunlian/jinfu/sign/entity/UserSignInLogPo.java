package com.xinyunlian.jinfu.sign.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dongfangchao on 2017/5/19/0019.
 */
@Entity
@Table(name = "user_sign_in_log")
public class UserSignInLogPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 969532773944630343L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "SIGN_IN_DATE")
    private Date signInDate;

    @Column(name = "CON_DAYS")
    private Long conDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(Date signInDate) {
        this.signInDate = signInDate;
    }

    public Long getConDays() {
        return conDays;
    }

    public void setConDays(Long conDays) {
        this.conDays = conDays;
    }
}
