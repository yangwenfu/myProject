package com.xinyunlian.jinfu.user.dao;

import com.xinyunlian.jinfu.user.entity.MgtDeptPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by dongfangchao on 2016/12/6/0006.
 */
public interface MgtDeptDao extends JpaRepository<MgtDeptPo, Long>, JpaSpecificationExecutor<MgtDeptPo> {

    @Modifying
    @Query(nativeQuery = true, value = "delete from mgt_dept where DEPT_TREE_PATH LIKE '?1%'")
    void deleteLikePath(String path);

}
