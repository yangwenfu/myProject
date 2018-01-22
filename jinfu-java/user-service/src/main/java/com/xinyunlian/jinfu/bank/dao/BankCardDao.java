package com.xinyunlian.jinfu.bank.dao;

import com.xinyunlian.jinfu.bank.entity.BankCardPo;
import com.xinyunlian.jinfu.bank.enums.EBankCardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by dell1 on 2016/8/23.
 */
public interface BankCardDao extends JpaRepository<BankCardPo, Long>, JpaSpecificationExecutor<BankCardPo> {
    List<BankCardPo> findByUserId(String userId);

    List<BankCardPo> findByUserIdAndStatus(String userId, EBankCardStatus status);

    Long countByUserIdAndStatus(String userId, EBankCardStatus status);

    @Query("select count(a.bankCardId) from BankCardPo a,BankInfPo b " +
            "where a.bankCode=b.bankCode and a.status = 0 and  b.support=1 and  a.userId = ?1")
    Long countByUserIdSupport(String userId);

    BankCardPo findByUserIdAndBankCardNoAndStatus(String UserIdAnd,String bankCardNo, EBankCardStatus status);

    @Query(nativeQuery = true, value ="SELECT ID_CARD_NO,BP.BANK_CARD_NO,BANK_CARD_NAME,BANK_CODE,STORE_ID" +
            " FROM BANK_CARD AS BP INNER JOIN STORE_INF AS SP ON BP.USER_ID=SP.USER_ID " +
            "AND BP.BANK_CARD_NO=SP.BANK_CARD_NO AND SP.STORE_ID in ?1")
    List<Object[]> findByStoreId(List<Long> storeId);
}
