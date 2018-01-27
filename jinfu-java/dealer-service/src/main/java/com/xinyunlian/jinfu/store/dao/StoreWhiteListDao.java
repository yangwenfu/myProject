package com.xinyunlian.jinfu.store.dao;

import com.xinyunlian.jinfu.store.entity.StoreWhiteListPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2017年06月20日.
 */
public interface StoreWhiteListDao extends JpaRepository<StoreWhiteListPo, Long>, JpaSpecificationExecutor<StoreWhiteListPo> {

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE store_white_list SET DEALER_ID = ?1,USER_ID=?2 where ID IN ?3 and STATUS='NOSIGNIN'")
    void updateDealerIdByIds(String dealerId, String userId, List<Long> ids);

}
