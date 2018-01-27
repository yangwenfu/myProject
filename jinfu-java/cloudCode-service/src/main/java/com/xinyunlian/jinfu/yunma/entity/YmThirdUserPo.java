package com.xinyunlian.jinfu.yunma.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by menglei on 2017年05月31日.
 */
@Entity
@Table(name = "ym_third_user")
public class YmThirdUserPo extends BaseMaintainablePo {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "THIRD_CONFIG_ID")
    private Long thirdConfigId;

    @Column(name = "OUT_ID")
    private String outId;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getThirdConfigId() {
        return thirdConfigId;
    }

    public void setThirdConfigId(Long thirdConfigId) {
        this.thirdConfigId = thirdConfigId;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
