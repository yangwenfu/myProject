package com.xinyunlian.jinfu.external.dto;

import com.xinyunlian.jinfu.external.dto.req.LoanApplyReq;
import com.xinyunlian.jinfu.external.dto.req.RegisterReq;
import com.xinyunlian.jinfu.loan.dto.req.LoanCustomerApplDto;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;

import java.io.Serializable;

/**
 * Created by godslhand on 2017/6/21.
 */
public class ExternalLoanApplyDto implements Serializable {


    private RegisterReq registerReq;

    private String applId; //小贷系统 贷款编号

    private String userId;

//    private LoanCustomerApplDto applDto;

    public RegisterReq getRegisterReq() {
        return registerReq;
    }

    public void setRegisterReq(RegisterReq registerReq) {
        this.registerReq = registerReq;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

//    public LoanCustomerApplDto getApplDto() {
//        return applDto;
//    }
//
//    public void setApplDto(LoanCustomerApplDto applDto) {
//        this.applDto = applDto;
//    }
}
