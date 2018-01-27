package com.xinyunlian.jinfu.loan.dto.loan;

import com.xinyunlian.jinfu.promo.dto.CouponDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Willwang on 2017/3/27.
 */
public class LoanAllCouponDto implements Serializable {

    /**
     * 可用数量
     */
    private Long usableCnt;

    /**
     * 不可用数量
     */
    private Long disabledCnt;

    /**
     * 可用列表
     */
    private List<CouponDto> usableList;

    /**
     * 不可用列表
     */
    private List<CouponDto> disabledList;

    public Long getUsableCnt() {
        return usableCnt;
    }

    public void setUsableCnt(Long usableCnt) {
        this.usableCnt = usableCnt;
    }

    public Long getDisabledCnt() {
        return disabledCnt;
    }

    public void setDisabledCnt(Long disabledCnt) {
        this.disabledCnt = disabledCnt;
    }

    public List<CouponDto> getUsableList() {
        return usableList;
    }

    public void setUsableList(List<CouponDto> usableList) {
        this.usableList = usableList;
    }

    public List<CouponDto> getDisabledList() {
        return disabledList;
    }

    public void setDisabledList(List<CouponDto> disabledList) {
        this.disabledList = disabledList;
    }
}
