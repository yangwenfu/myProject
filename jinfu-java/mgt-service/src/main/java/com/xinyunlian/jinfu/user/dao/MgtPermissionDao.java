package com.xinyunlian.jinfu.user.dao;

import com.xinyunlian.jinfu.user.entity.MgtPermissionPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by DongFC on 2016-08-19.
 */
public interface MgtPermissionDao extends JpaRepository<MgtPermissionPo, Long>, JpaSpecificationExecutor<MgtPermissionPo> {

    @Query(nativeQuery=true,value="SELECT DISTINCT t4.* FROM mgt_user t1 INNER JOIN mgt_user_role t2 ON t1.USER_ID = t2.USER_ID " +
            "INNER JOIN mgt_role_perm t3 ON t2.ROLE_ID = t3.ROLE_ID INNER JOIN mgt_permission t4 ON t3.PERMISSION_ID = t4.PERMISSION_ID" +
            " WHERE t1.USER_ID = ?1 " )
    List<MgtPermissionPo> findByUser(String userId);

    @Query(nativeQuery=true,value="SELECT DISTINCT t4.PERMISSION_CODE FROM mgt_user t1 INNER JOIN mgt_user_role t2 ON t1.USER_ID = t2.USER_ID " +
            "INNER JOIN mgt_role_perm t3 ON t2.ROLE_ID = t3.ROLE_ID INNER JOIN mgt_permission t4 ON t3.PERMISSION_ID = t4.PERMISSION_ID" +
            " WHERE t1.USER_ID = ?1 " )
    List<String> findPermssionCodeByUser(String userId);

    @Query(nativeQuery=true,value="SELECT DISTINCT t2.* FROM mgt_role_perm t1 " +
            "INNER JOIN mgt_permission t2 ON t1.PERMISSION_ID = t2.PERMISSION_ID" +
            " WHERE t1.ROLE_ID = ?1 " )
    List<MgtPermissionPo> findPermsByRoleId(Long roleId);

}
