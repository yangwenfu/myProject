package com.xinyunlian.jinfu.user.dao;

import com.xinyunlian.jinfu.user.entity.MgtUserPo;
import com.xinyunlian.jinfu.user.enums.EMgtUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

/**
 * Created by DongFC on 2016-08-19.
 */
public interface MgtUserDao extends JpaRepository<MgtUserPo, String>, JpaSpecificationExecutor<MgtUserPo> {

    MgtUserPo findByLoginIdAndStatusNot(String loginId, EMgtUserStatus status);

    MgtUserPo findByLoginIdAndPasswordAndStatus(String loginId, String password, EMgtUserStatus status);

    @Query(nativeQuery = true, value = "select DISTINCT t1.USER_ID, t1.LOGIN_ID, t1.PASSWORD, t1.NAME,t1.STATUS, t1.MOBILE, t1.EMAIL, t1.DUTY, " +
            "t1.CREATE_OPID, t1.CREATE_TS, t1.LAST_MNT_OPID, t1.LAST_MNT_TS, t1.VERSION_CT " +
            "from mgt_user t1 inner join mgt_dept_user t2 on t1.USER_ID = t2.USER_ID where DEPT_ID = ?1")
    List<MgtUserPo> findUserByDirectDept(Long deptId);

    @Query(nativeQuery = true, value = "select DISTINCT t1.USER_ID, t1.LOGIN_ID, t1.PASSWORD, t1.NAME,t1.STATUS, t1.MOBILE, t1.EMAIL, t1.DUTY, " +
            "t1.CREATE_OPID, t1.CREATE_TS, t1.LAST_MNT_OPID, t1.LAST_MNT_TS, t1.VERSION_CT " +
            "from mgt_user t1 inner join mgt_dept_user t2 on t1.USER_ID = t2.USER_ID where DEPT_TREE_PATH LIKE '?1%'")
    List<MgtUserPo> findUserByDept(String deptTreePath);

    @Query("from MgtUserPo i where i.userId in ?1")
    List<MgtUserPo> findByMgtUserIds(Collection<String> userIds);

    MgtUserPo findByLoginId(String loginId);

    MgtUserPo findByMobile(String mobile);

    List<MgtUserPo> findByNameLike(String name);

    @Query("from MgtUserPo a where a.userId not in (select userId from ChannelUserRelationPo) and a.duty= ?1")
    List<MgtUserPo> findNotInChannel(String duty);

    List<MgtUserPo> findByDuty(String duty);

}
