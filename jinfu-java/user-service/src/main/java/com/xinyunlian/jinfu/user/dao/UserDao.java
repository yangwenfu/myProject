package com.xinyunlian.jinfu.user.dao;

import com.xinyunlian.jinfu.user.entity.UserInfoPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 用户DAO接口
 * @author KimLL
 * @version 
 */

public interface UserDao extends JpaRepository<UserInfoPo, String>, JpaSpecificationExecutor<UserInfoPo> {

    UserInfoPo findByMobile(String mobile);

    UserInfoPo findByEmail(String email);

    List<UserInfoPo> findByUserIdIn(Iterable<String> userIds);

    List<UserInfoPo> findByUserNameLike(String userName);

    List<UserInfoPo> findByIdCardNoLike(String idCardNo);

    @Query("from UserInfoPo u , StoreInfPo s where  u.userId = s.userId and s.tobaccoCertificateNo = ?1 and s.status='0' ")
    UserInfoPo findByTobaccoNo(String tobaccoNo);

    UserInfoPo findByUuid(String uuid);
}
