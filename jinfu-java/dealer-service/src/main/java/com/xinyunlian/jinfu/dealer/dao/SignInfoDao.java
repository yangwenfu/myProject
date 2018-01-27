package com.xinyunlian.jinfu.dealer.dao;

import com.xinyunlian.jinfu.dealer.entity.SignInfoPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by menglei on 2017年05月03日.
 */
public interface SignInfoDao extends JpaRepository<SignInfoPo, Long>, JpaSpecificationExecutor<SignInfoPo> {

    @Query(nativeQuery = true, value = "select * from sign_info where USER_ID=?1 and STORE_ID=?2 and to_days(CREATE_TS) = to_days(now())")
    SignInfoPo findByUserIdAndStoreId(String userId, Long storeId);

}
