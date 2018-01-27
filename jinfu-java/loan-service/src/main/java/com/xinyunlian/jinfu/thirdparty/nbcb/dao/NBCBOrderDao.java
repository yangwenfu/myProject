package com.xinyunlian.jinfu.thirdparty.nbcb.dao;

import com.xinyunlian.jinfu.thirdparty.nbcb.entity.NBCBOrderPo;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBApprStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBCreditStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBReceiveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by bright on 2017/5/15.
 */
public interface NBCBOrderDao extends JpaRepository<NBCBOrderPo, String>, JpaSpecificationExecutor<NBCBOrderPo> {
    List<NBCBOrderPo> findByUserIdAndCreditStatus(String userId, ENBCBCreditStatus creditStatus);

    List<NBCBOrderPo> findByUserIdAndApprStatus(String userId, ENBCBApprStatus apprStatus);

    List<NBCBOrderPo> findByUserIdAndReceiveStatus(String userId, ENBCBReceiveStatus receiveStatus);

    @Query(nativeQuery = true, value = "SELECT DISTINCT USER_ID FROM EXTERNAL_NBCB_LOAN")
    List<String> findUserIds();
}
