package com.xinyunlian.jinfu.clerk.dao;

import com.xinyunlian.jinfu.clerk.entity.ClerkAuthPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2016-12-05.
 */
public interface ClerkAuthDao extends JpaRepository<ClerkAuthPo, String>, JpaSpecificationExecutor<ClerkAuthPo> {

    @Modifying
    @Query(nativeQuery = true, value = "delete from clerk_auth where USER_ID=?1 and CLERK_ID=?2")
    void deleteByuserIdAndClerkId(String userId, String clerkId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from clerk_auth where STORE_ID=?1 ")
    void deleteByStoreId(Long storeId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from clerk_auth where USER_ID=?1 ")
    void deleteByUserId(String userId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from clerk_auth where CLERK_ID=?1 ")
    void deleteByClerkId(String clerkId);

    List<ClerkAuthPo> findByClerkId(String clerkId);

    ClerkAuthPo findByClerkIdAndStoreId(String clerkId, String storeId);

    List<ClerkAuthPo> findByStoreId(String storeId);

    /**
     * 根据店主id获取已授权列表
     * @return
     */
    @Query(nativeQuery = true, value = "select ca.USER_ID,Group_concat(STORE_ID),ca.CLERK_ID,ca.CREATE_TS,ci.NAME,ci.MOBILE,ci.OPEN_ID from clerk_auth ca " +
            "left join clerk_inf ci on ci.CLERK_ID=ca.CLERK_ID where ca.USER_ID=?1 group by ca.CLERK_ID order by AUTH_ID desc")
    List<Object[]> findByUserId(String userId);

}
