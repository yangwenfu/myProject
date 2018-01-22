package com.xinyunlian.jinfu.cms.dao;

import com.xinyunlian.jinfu.cms.entity.NoticeInfPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by dongfangchao on 2017/1/24/0024.
 */
public interface NoticeInfDao extends JpaRepository<NoticeInfPo, Long>, JpaSpecificationExecutor<NoticeInfPo> {

    @Modifying
    @Query(nativeQuery = true, value = "delete from notice_inf where ID IN ?1")
    void deleteBatchByIds(List<Long> list);

    @Query(nativeQuery = true, value = "select t1.ID, t1.NOTICE_CONTENT, t1.LINK_URL, t1.START_DATE, t1.END_DATE, t1.CREATE_OPID, t1.CREATE_TS, t1.LAST_MNT_OPID, t1.LAST_MNT_TS, t1.VERSION_CT " +
            "FROM notice_inf t1 INNER JOIN notice_platform t2 ON t1.ID = t2.NOTICE_ID WHERE t2.NOTICE_PLATFORM = ?1 AND t1.START_DATE <= NOW() AND t1.END_DATE >= NOW()")
    List<NoticeInfPo> findByPlatformAndValid(String platform);

}
