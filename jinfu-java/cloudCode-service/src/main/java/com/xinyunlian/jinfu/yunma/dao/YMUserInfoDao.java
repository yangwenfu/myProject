package com.xinyunlian.jinfu.yunma.dao;

import com.xinyunlian.jinfu.yunma.entity.YMUserInfoPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by menglei on 2017年01月04日.
 */
public interface YMUserInfoDao extends JpaRepository<YMUserInfoPo, String>, JpaSpecificationExecutor<YMUserInfoPo> {

    YMUserInfoPo findByOpenId(String openId);

    YMUserInfoPo findByUserId(String userId);


    YMUserInfoPo findByYmUserId(String ymUserId);

    YMUserInfoPo findByYmUserIdAndOpenId(String ymUserId, String openId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from ym_userinfo where USER_ID=?1")
    void deleteByUserId(String userId);

}
