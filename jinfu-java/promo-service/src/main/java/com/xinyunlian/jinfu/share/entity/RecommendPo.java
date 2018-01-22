package com.xinyunlian.jinfu.share.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * 推荐Entity
 *
 * @author jll
 */
@Entity
@Table(name = "RECOMMEND")
public class RecommendPo extends BaseMaintainablePo {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "REFEREE_USER_ID")
    private String refereeUserId;

    @Column(name = "IS_ORDER")
    private Boolean order;

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

    public String getRefereeUserId() {
        return refereeUserId;
    }

    public void setRefereeUserId(String refereeUserId) {
        this.refereeUserId = refereeUserId;
    }

    public Boolean getOrder() {
        return order;
    }

    public void setOrder(Boolean order) {
        this.order = order;
    }
}


