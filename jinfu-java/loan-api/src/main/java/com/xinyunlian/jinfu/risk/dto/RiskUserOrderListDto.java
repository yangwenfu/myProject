package com.xinyunlian.jinfu.risk.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by willwang on 2016/10/14.
 */
public class RiskUserOrderListDto implements Serializable{

    private List<RiskUserOrderDto> present;

    private List<RiskUserOrderDto> last;

    private List<RiskUserOrderDto> history;

    public List<RiskUserOrderDto> getPresent() {
        return present;
    }

    public void setPresent(List<RiskUserOrderDto> present) {
        this.present = present;
    }

    public List<RiskUserOrderDto> getLast() {
        return last;
    }

    public void setLast(List<RiskUserOrderDto> last) {
        this.last = last;
    }

    public List<RiskUserOrderDto> getHistory() {
        return history;
    }

    public void setHistory(List<RiskUserOrderDto> history) {
        this.history = history;
    }
}
