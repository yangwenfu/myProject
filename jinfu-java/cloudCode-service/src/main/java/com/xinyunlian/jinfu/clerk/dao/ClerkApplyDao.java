package com.xinyunlian.jinfu.clerk.dao;

import com.xinyunlian.jinfu.clerk.entity.ClerkApplyPo;
import com.xinyunlian.jinfu.clerk.enums.EClerkApplyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2016-12-05.
 */
public interface ClerkApplyDao extends JpaRepository<ClerkApplyPo, String>, JpaSpecificationExecutor<ClerkApplyPo> {

    List<ClerkApplyPo> findByUserIdAndStatus(String userId, EClerkApplyStatus status);

    @Query(nativeQuery = true, value = "select * from clerk_apply where CLERK_ID=?1 and (STATUS='SUCCESS' or STATUS='APPLY') order by APPLY_ID desc")
    List<ClerkApplyPo> findByClerkId(String clerkId);

    ClerkApplyPo findByUserIdAndClerkIdAndStatus(String userId, String clerkId, EClerkApplyStatus status);

    /**
     * 根据店主id获取申请中的列表
     * @return
     */
    @Query(nativeQuery = true, value = "select ca.APPLY_ID,ca.USER_ID,ca.CLERK_ID,ca.STATUS,ca.CREATE_TS,ci.NAME,ci.MOBILE,ci.OPEN_ID from clerk_apply ca " +
            "left join clerk_inf ci on ca.CLERK_ID=ci.CLERK_ID where ca.USER_ID=?1 and STATUS='APPLY' order by APPLY_ID desc")
    List<Object[]> findByUserId(String userId);

    @Modifying
    @Query(nativeQuery = true, value = "update clerk_apply set STATUS='DELETE' where USER_ID=?1 ")
    void deleteByUserId(String userId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from clerk_apply where CLERK_ID=?1 ")
    void deleteByClerkId(String clerkId);

}
