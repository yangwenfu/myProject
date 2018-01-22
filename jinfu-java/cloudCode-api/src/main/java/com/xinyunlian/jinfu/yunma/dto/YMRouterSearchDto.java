package com.xinyunlian.jinfu.yunma.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.yunma.enums.ERouterAgentStatus;

/**
 * Created by menglei on 2017年01月04日.
 */
public class YMRouterSearchDto extends PagingDto<YMRouterAgentDto> {

    private static final long serialVersionUID = -1L;

    private Long id;
    private String name;
    private String userAgent;
    private String url;
    private ERouterAgentStatus status;

    private String createStartDate;

    private String createEndDate;

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

    public String getCreateStartDate() {
        return createStartDate;
    }

    public void setCreateStartDate(String createStartDate) {
        this.createStartDate = createStartDate;
    }

    public String getCreateEndDate() {
        return createEndDate;
    }

    public void setCreateEndDate(String createEndDate) {
        this.createEndDate = createEndDate;
    }

    public ERouterAgentStatus getStatus() {
        return status;
    }

    public void setStatus(ERouterAgentStatus status) {
        this.status = status;
    }
}
