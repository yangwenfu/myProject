package com.xinyunlian.jinfu.dealer.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserLogType;

/**
 * Created by menglei on 2016年08月29日.
 */
public class DealerUserLogSearchDto extends PagingDto<DealerUserLogDto> {

    private static final long serialVersionUID = 1L;

    private String name;

    private String mobile;

    private String dealerName;

    private EDealerUserLogType type;

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

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
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

    public EDealerUserLogType getType() {
        return type;
    }

    public void setType(EDealerUserLogType type) {
        this.type = type;
    }
}
