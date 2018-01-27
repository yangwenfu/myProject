package com.xinyunlian.jinfu.user.dao;

import com.xinyunlian.jinfu.user.entity.UserInfoPo;
import com.xinyunlian.jinfu.user.enums.EUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by JL on 2016/8/19.
 */
public interface UserInfoDao extends JpaRepository<UserInfoPo, String> {

    UserInfoPo findByMobileAndLoginPwdAndStatus(String mobile, String password, EUserStatus status);
}
