package com.xinyunlian.jinfu.thirdparty.nbcb.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by dongfangchao on 2017/7/28/0028.
 */
public class NBCBOrderSearchDto extends PagingDto<NBCBOrderDto> {
    private static final long serialVersionUID = 1126264386572264857L;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
