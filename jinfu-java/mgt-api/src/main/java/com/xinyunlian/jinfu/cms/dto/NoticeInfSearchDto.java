package com.xinyunlian.jinfu.cms.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by DongFC on 2016-08-23.
 */
public class NoticeInfSearchDto extends PagingDto<NoticeInfDto> {

    private static final long serialVersionUID = 7871185933687340713L;

    private ECmsValidStatus cmsValidStatus;

    public ECmsValidStatus getCmsValidStatus() {
        return cmsValidStatus;
    }

    public void setCmsValidStatus(ECmsValidStatus cmsValidStatus) {
        this.cmsValidStatus = cmsValidStatus;
    }
}
