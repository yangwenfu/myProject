package com.xinyunlian.jinfu.bscontract.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.xinyunlian.jinfu.bscontract.entity.YmMemberSignPo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by dongfangchao on 2017/2/22/0022.
 */
public interface YmMemberSignDao extends JpaRepository<YmMemberSignPo, Long>, JpaSpecificationExecutor<YmMemberSignPo> {

    YmMemberSignPo findByStoreId(Long storeId);

    YmMemberSignPo findByQrcodeNo(String qrcodeNo);

    @Modifying
    @Query(nativeQuery = true, value = "delete from ym_member_sign where STORE_ID = ?1")
    void deleteByStoreId(Long storeId);

}
