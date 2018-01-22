package com.xinyunlian.jinfu.yunma.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.yunma.enums.ERouterAgentStatus;
import com.xinyunlian.jinfu.yunma.enums.converter.ERouterAgentStatusConverter;

import javax.persistence.*;

/**
 * Created by menglei on 2017年01月04日.
 */
@Entity
@Table(name = "ym_router_agent")
public class YMRouterAgentPo extends BaseMaintainablePo {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "USER_AGENT")
    private String userAgent;

    @Column(name = "URL")
    private String url;

    @Column(name = "STATUS")
    @Convert(converter = ERouterAgentStatusConverter.class)
    private ERouterAgentStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ERouterAgentStatus getStatus() {
        return status;
    }

    public void setStatus(ERouterAgentStatus status) {
        this.status = status;
    }
}
