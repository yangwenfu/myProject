package com.xinyunlian.jinfu.point.dao;


import com.xinyunlian.jinfu.point.entity.UserScoreChangelogPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by dongfangchao on 2017/5/19/0019.
 */
public interface UserScoreChangelogDao extends JpaRepository<UserScoreChangelogPo, Long>, JpaSpecificationExecutor<UserScoreChangelogPo> {

    UserScoreChangelogPo findByUserIdAndSourceAndTranSeq(String userId, String source, String tranSeq);

    @Query(nativeQuery = true, value = "SELECT * FROM user_score_changelog WHERE CREATE_TS < ?1 AND CREATE_TS >= ?2 AND SOURCE = ?3")
    List<UserScoreChangelogPo> findContinuityDays(Date end, Date start, String source);

}
