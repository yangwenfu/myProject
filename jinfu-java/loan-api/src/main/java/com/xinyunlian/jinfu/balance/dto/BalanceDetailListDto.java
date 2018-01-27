package com.xinyunlian.jinfu.balance.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Willwang on 2017/5/23.
 */
public class BalanceDetailListDto implements Serializable{

    private List<BalanceDetailListCashierDto> cashiers;

    private List<BalanceDetailListLoanDto> loans;

    public List<BalanceDetailListCashierDto> getCashiers() {
        return cashiers;
    }

    public void setCashiers(List<BalanceDetailListCashierDto> cashiers) {
        this.cashiers = cashiers;
    }

    public List<BalanceDetailListLoanDto> getLoans() {
        return loans;
    }

    public void setLoans(List<BalanceDetailListLoanDto> loans) {
        this.loans = loans;
    }
}
