package com.xinyunlian.jinfu.stats.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017年05月17日.
 */
public class StatsStoreDto implements Serializable {

    private String statsName;

    private String describe;

    private Long count;

    private String type;//SIGN=签到 STORE=添加 YUNMA=云码

    public String getStatsName() {
        return statsName;
    }

    public void setStatsName(String statsName) {
        this.statsName = statsName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
