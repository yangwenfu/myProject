package com.xinyunlian.jinfu.dealer.dealer.dto.resp;

import java.io.Serializable;

/**
 * Created by menglei on 2017年05月10日.
 */
public class DealerOpLogDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private DealerDetailDto dealerDetailFront;

    private DealerDetailDto dealerDetailAfter;

    public DealerDetailDto getDealerDetailFront() {
        return dealerDetailFront;
    }

    public void setDealerDetailFront(DealerDetailDto dealerDetailFront) {
        this.dealerDetailFront = dealerDetailFront;
    }

    public DealerDetailDto getDealerDetailAfter() {
        return dealerDetailAfter;
    }

    public void setDealerDetailAfter(DealerDetailDto dealerDetailAfter) {
        this.dealerDetailAfter = dealerDetailAfter;
    }
}
