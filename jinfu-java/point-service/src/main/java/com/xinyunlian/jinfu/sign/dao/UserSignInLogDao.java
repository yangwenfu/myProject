package com.xinyunlian.jinfu.sign.dao;

import com.xinyunlian.jinfu.sign.entity.UserSignInLogPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by dongfangchao on 2017/5/19/0019.
 */
public interface UserSignInLogDao extends JpaRepository<UserSignInLogPo, Long>, JpaSpecificationExecutor<UserSignInLogPo> {

    @Query(nativeQuery = true, value = "SELECT * FROM `user_sign_in_log` WHERE USER_ID = ?1 AND SIGN_IN_DATE = ?2")
    UserSignInLogPo findByUserIdAndSignInDate(String userId, String signInDate);

}
