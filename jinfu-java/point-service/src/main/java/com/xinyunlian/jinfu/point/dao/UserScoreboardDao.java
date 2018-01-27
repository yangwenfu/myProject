package com.xinyunlian.jinfu.point.dao;


import com.xinyunlian.jinfu.point.entity.UserScoreboardPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dongfangchao on 2017/5/19/0019.
 */
public interface UserScoreboardDao extends JpaRepository<UserScoreboardPo, Long>, JpaSpecificationExecutor<UserScoreboardPo> {

    UserScoreboardPo findByUserId(String userId);

}
