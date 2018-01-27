package com.xinyunlian.jinfu.user.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2016年08月30日.
 */
public class DealerUserTotalDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long orderCount;

    private Long storeCount;

    private Long noteCount;

    private Long unReadCount;

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }

    public Long getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(Long storeCount) {
        this.storeCount = storeCount;
    }

    public Long getNoteCount() {
        return noteCount;
    }

    public void setNoteCount(Long noteCount) {
        this.noteCount = noteCount;
    }

    public Long getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(Long unReadCount) {
        this.unReadCount = unReadCount;
    }
}
