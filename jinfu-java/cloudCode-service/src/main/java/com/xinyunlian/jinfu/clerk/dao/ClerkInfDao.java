package com.xinyunlian.jinfu.clerk.dao;

import com.xinyunlian.jinfu.clerk.entity.ClerkInfPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by menglei on 2016-12-05.
 */
public interface ClerkInfDao extends JpaRepository<ClerkInfPo, String>, JpaSpecificationExecutor<ClerkInfPo> {

    ClerkInfPo findByMobile(String mobile);

    ClerkInfPo findByOpenId(String openId);

    @Query(nativeQuery = true, value = "select * from clerk_inf where CLERK_ID IN ?1")
    List<ClerkInfPo> findByClerkIds(List<String> clerkIds);

}
