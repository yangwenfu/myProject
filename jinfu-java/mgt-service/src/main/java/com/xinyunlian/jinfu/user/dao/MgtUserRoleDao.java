package com.xinyunlian.jinfu.user.dao;

import com.xinyunlian.jinfu.user.entity.MgtUserRolePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by DongFC on 2016-08-19.
 */
public interface MgtUserRoleDao extends JpaRepository<MgtUserRolePo, Long>, JpaSpecificationExecutor<MgtUserRolePo> {

    @Modifying
    @Query(nativeQuery = true, value = "delete from mgt_user_role where USER_ID = ?1")
    void deleteByUserId(String userId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from mgt_user_role where ROLE_ID = ?1")
    void deleteByRoleId(Long roleId);

}
