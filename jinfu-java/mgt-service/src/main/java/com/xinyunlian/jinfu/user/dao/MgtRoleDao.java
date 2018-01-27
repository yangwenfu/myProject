package com.xinyunlian.jinfu.user.dao;

import com.xinyunlian.jinfu.user.entity.MgtRolePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by DongFC on 2016-08-19.
 */
public interface MgtRoleDao extends JpaRepository<MgtRolePo, Long>, JpaSpecificationExecutor<MgtRolePo> {

    @Query(nativeQuery = true, value="SELECT DISTINCT t3.* FROM mgt_user t1 INNER JOIN mgt_user_role t2 ON t1.USER_ID = t2.USER_ID" +
            " INNER JOIN mgt_role t3 ON t2.ROLE_ID = t3.ROLE_ID WHERE t1.USER_ID = ?1")
    List<MgtRolePo> findByUser(String userId);

    @Query(nativeQuery = true, value="SELECT DISTINCT t3.ROLE_CODE FROM mgt_user t1 INNER JOIN mgt_user_role t2 ON t1.USER_ID = t2.USER_ID" +
            " INNER JOIN mgt_role t3 ON t2.ROLE_ID = t3.ROLE_ID WHERE t1.USER_ID = ?1")
    List<String> findRoleCodeByUser(String userId);

    @Query(nativeQuery = true, value="SELECT * FROM mgt_role WHERE ROLE_ID NOT IN (select ROLE_ID from mgt_user_role where user_id = ?1)")
    List<MgtRolePo> findByUserNotHave(String userId);

    MgtRolePo findByRoleCode(String roleCode);
}
