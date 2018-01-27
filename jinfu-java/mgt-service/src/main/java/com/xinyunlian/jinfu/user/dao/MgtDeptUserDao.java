package com.xinyunlian.jinfu.user.dao;

import com.xinyunlian.jinfu.user.entity.MgtDeptUserPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by dongfangchao on 2016/12/6/0006.
 */
public interface MgtDeptUserDao extends JpaRepository<MgtDeptUserPo, Long>, JpaSpecificationExecutor<MgtDeptUserPo> {

    @Modifying
    @Query(nativeQuery = true, value = "delete from mgt_dept_user where USER_ID = ?1")
    void deleteByUserId(String userId);

}
