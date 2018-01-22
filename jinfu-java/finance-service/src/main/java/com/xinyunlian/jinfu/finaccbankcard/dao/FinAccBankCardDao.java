package com.xinyunlian.jinfu.finaccbankcard.dao;

import com.xinyunlian.jinfu.finaccbankcard.entity.FinAccBankCardPo;
import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by dongfangchao on 2016/11/21.
 */
public interface FinAccBankCardDao extends JpaRepository<FinAccBankCardPo, String>, JpaSpecificationExecutor<FinAccBankCardPo> {

    FinAccBankCardPo findByExtTxAccId(String extTxAccId);

    List<FinAccBankCardPo> findByUserIdAndFinOrg(String userId, String finOrg);

    FinAccBankCardPo findByBankCardNoAndUserIdAndFinOrg(String bankCardNo, String userId, EFinOrg finOrg);

    List<FinAccBankCardPo> findByBankCardNo(String bankCardNo);

}
