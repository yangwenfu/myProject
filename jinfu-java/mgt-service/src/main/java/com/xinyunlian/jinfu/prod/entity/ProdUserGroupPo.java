package com.xinyunlian.jinfu.prod.entity;

import com.xinyunlian.jinfu.prod.enums.EUserGroup;
import com.xinyunlian.jinfu.prod.enums.converter.EUserGroupConverter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/22/0022.
 */
@Entity
@Table(name = "prod_user_group")
public class ProdUserGroupPo implements Serializable{
    private static final long serialVersionUID = -7262986269683691077L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PROD_ID")
    private String prodId;

    @Column(name = "USER_GROUP")
    @Convert(converter = EUserGroupConverter.class)
    private EUserGroup userGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public EUserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(EUserGroup userGroup) {
        this.userGroup = userGroup;
    }
}
