package com.xinyunlian.jinfu.depository.dao;

import com.xinyunlian.jinfu.depository.entity.LoanDepositoryAcctPo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author willwang
 */
public interface LoanDepositoryAcctDao extends JpaRepository<LoanDepositoryAcctPo, Long> {

    LoanDepositoryAcctPo findByBankCardId(Long bankCardId);

    LoanDepositoryAcctPo findByBankCardNo(String bankCardNo);

}
