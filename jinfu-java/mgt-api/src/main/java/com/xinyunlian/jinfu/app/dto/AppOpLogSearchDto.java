package com.xinyunlian.jinfu.app.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by dongfangchao on 2016/12/30/0030.
 */
public class AppOpLogSearchDto extends PagingDto<AppOpLogDto> {

    private static final long serialVersionUID = 400392865625258129L;

    private Long appId;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
