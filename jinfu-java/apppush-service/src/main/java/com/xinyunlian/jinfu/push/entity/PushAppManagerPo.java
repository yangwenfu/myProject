package com.xinyunlian.jinfu.push.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by apple on 2017/3/8.
 */
@Entity
@Table(name = "PUSH_APP_MANAGER")
public class PushAppManagerPo extends BaseMaintainablePo {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "APP_KEY")
    private Long appKey;

    @Column(name = "MASTER_SECRET")
    private String masterSecret;

    @Column(name = "APP_TYPE")
    private String appType;
}
