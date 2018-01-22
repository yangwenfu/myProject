package com.xinyunlian.jinfu.dealer.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserNoteStatus;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserSubscribeType;
import com.xinyunlian.jinfu.dealer.enums.converter.EDealerUserSubscribeTypeConverter;

import javax.persistence.*;

/**
 * Created by menglei on 2017年08月03日.
 */
@Entity
@Table(name = "dealer_user_subscribe")
public class DealerUserSubscribePo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "OPEN_ID")
    private String openId;

    @Column(name = "WECHAT_TYPE")
    @Convert(converter = EDealerUserSubscribeTypeConverter.class)
    private EDealerUserSubscribeType wechatType;

    @Column(name = "DEALER_ID")
    private String dealerId;

    @Column(name = "USER_ID")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public EDealerUserSubscribeType getWechatType() {
        return wechatType;
    }

    public void setWechatType(EDealerUserSubscribeType wechatType) {
        this.wechatType = wechatType;
    }
}
