package com.xinyunlian.jinfu.user.dao;

import com.xinyunlian.jinfu.user.entity.UserLinkmanPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 用户联系人DAO接口
 * @author KimLL
 * @version 
 */
public interface UserLinkmanDao extends JpaRepository<UserLinkmanPo, Long>, JpaSpecificationExecutor<UserLinkmanPo> {
    List<UserLinkmanPo> findByUserId(String userId);

    List<UserLinkmanPo> findByMobileContains(String phone);
}
