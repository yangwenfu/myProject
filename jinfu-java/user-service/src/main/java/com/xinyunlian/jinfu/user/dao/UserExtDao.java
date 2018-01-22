package com.xinyunlian.jinfu.user.dao;

import com.xinyunlian.jinfu.user.entity.UserExtPo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jll on 2016/8/19.
 */
public interface UserExtDao extends JpaRepository<UserExtPo, String> {
}
