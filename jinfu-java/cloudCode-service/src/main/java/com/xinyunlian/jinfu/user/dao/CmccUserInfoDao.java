package com.xinyunlian.jinfu.user.dao;

import com.xinyunlian.jinfu.user.entity.CmccUserInfoPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by menglei on 2016年11月20日.
 */
public interface CmccUserInfoDao extends JpaRepository<CmccUserInfoPo, String>, JpaSpecificationExecutor<CmccUserInfoPo> {

    @Query(nativeQuery = true, value = "select * from cmcc_user_info where  MOBILE= ?1")
    CmccUserInfoPo findByMobile(String mobile);

}
