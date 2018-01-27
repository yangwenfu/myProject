package com.xinyunlian.jinfu.yunma.dto;

import com.xinyunlian.jinfu.yunma.enums.ERouterAgentStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by menglei on 2017年01月04日.
 */
public class YMRouterAgentDto implements Serializable {

    private static final long serialVersionUID = -1L;

    private Long id;
    private String name;
    private String userAgent;
    private String url;
    private ERouterAgentStatus status;
    private Date createTs;

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

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }
}
