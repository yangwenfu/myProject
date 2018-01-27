package com.xinyunlian.jinfu.pay.dao;

import com.xinyunlian.jinfu.pay.entity.PayRecvOrdPo;
import com.xinyunlian.jinfu.pay.enums.EOrdStatus;
import com.xinyunlian.jinfu.pay.enums.EPrType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 
 * @author willwang
 *
 */
public interface PayRecvOrdDao extends JpaRepository<PayRecvOrdPo, String>, JpaSpecificationExecutor<PayRecvOrdPo> {

    List<PayRecvOrdPo> findByOrdStatusAndPrType(EOrdStatus ordStatus, EPrType prType);

    @Query("from PayRecvOrdPo where bizId = ?1 order by ordId desc")
    List<PayRecvOrdPo> findByBizId(String bizId);

    List<PayRecvOrdPo> findByUserId(String userId);

    @Query("from PayRecvOrdPo where bizId in ?1 order by ordId desc")
    List<PayRecvOrdPo> findByBizIdIn(List<String> bizIds);

    PayRecvOrdPo findByOrdId(String ordId);

}
