package com.xinyunlian.jinfu.dealer.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by menglei on 2016年08月29日.
 */
public class DealerUserFeedbackSearchDto extends PagingDto<DealerUserFeedbackDto> {

    private static final long serialVersionUID = 1L;

    private String name;

    private String mobile;

    private String beginTime;

    private String endTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
