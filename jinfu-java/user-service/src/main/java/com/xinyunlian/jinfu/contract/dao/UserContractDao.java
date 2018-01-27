package com.xinyunlian.jinfu.contract.dao;

import com.xinyunlian.jinfu.contract.entity.UserContractPo;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by JL on 2016/9/20.
 */
public interface UserContractDao extends JpaRepository<UserContractPo, String> {

    UserContractPo findByUserIdAndTemplateType(String userId, ECntrctTmpltType cntrctTmpltType);

    UserContractPo findByUserIdAndTemplateTypeAndBizId(String userId, ECntrctTmpltType cntrctTmpltType, String bizId);

    List<UserContractPo> findByUserIdAndBizId(String userId, String bizId);

    UserContractPo findByBsSignid(String bsSignid);

    @Modifying
    @Query(nativeQuery = true, value = "delete from user_contract where CNTRCT_ID=?1")
    void deleteById(String cntId);

}
