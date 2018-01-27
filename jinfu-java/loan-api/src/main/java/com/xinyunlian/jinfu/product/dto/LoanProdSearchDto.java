package com.xinyunlian.jinfu.product.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.product.enums.ELoanProductType;

/**
 * Created by JL on 2016/9/2.
 */
public class LoanProdSearchDto extends PagingDto<LoanProductInfoDto> {

    private String prodId;

    private String prodName;

    private ELoanProductType loanProductType;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public ELoanProductType getLoanProductType() {
        return loanProductType;
    }

    public void setLoanProductType(ELoanProductType loanProductType) {
        this.loanProductType = loanProductType;
    }
}
