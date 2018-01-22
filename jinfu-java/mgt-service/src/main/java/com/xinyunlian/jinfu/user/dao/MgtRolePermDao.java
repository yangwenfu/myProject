package com.xinyunlian.jinfu.user.dao;

import com.xinyunlian.jinfu.user.entity.MgtRolePermPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by DongFC on 2016-08-19.
 */
public interface MgtRolePermDao extends JpaRepository<MgtRolePermPo, Long>, JpaSpecificationExecutor<MgtRolePermPo> {

    @Modifying
    @Query(nativeQuery = true, value = "delete from mgt_role_perm where ROLE_ID = ?1")
    void deleteByRoleId(Long roleId);

}
