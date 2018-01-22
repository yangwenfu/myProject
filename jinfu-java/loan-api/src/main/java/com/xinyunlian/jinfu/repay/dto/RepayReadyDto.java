package com.xinyunlian.jinfu.repay.dto;

import com.xinyunlian.jinfu.loan.dto.RepayDtlDto;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;

import java.io.Serializable;

/**
 * 扣款流程中预生成数据DTO
 * Created by Willwang on 2017/2/8.
 */
public class RepayReadyDto implements Serializable{

    private PayRecvOrdDto payRecvOrdDto;

    private RepayDtlDto repayDtlDto;

    public PayRecvOrdDto getPayRecvOrdDto() {
        return payRecvOrdDto;
    }

    public void setPayRecvOrdDto(PayRecvOrdDto payRecvOrdDto) {
        this.payRecvOrdDto = payRecvOrdDto;
    }

    public RepayDtlDto getRepayDtlDto() {
        return repayDtlDto;
    }

    public void setRepayDtlDto(RepayDtlDto repayDtlDto) {
        this.repayDtlDto = repayDtlDto;
    }
}
