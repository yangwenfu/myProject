package com.xinyunlian.jinfu.cms.dao;

import com.xinyunlian.jinfu.cms.entity.NoticePlatformPo;
import com.xinyunlian.jinfu.cms.enums.ENoticePlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by dongfangchao on 2017/1/24/0024.
 */
public interface NoticePlatformDao extends JpaRepository<NoticePlatformPo, Long>, JpaSpecificationExecutor<NoticePlatformPo> {

    @Modifying
    @Query(nativeQuery = true, value = "delete from notice_platform where NOTICE_ID = ?1")
    void deleteByNoticeId(Long noticeId);

    List<NoticePlatformPo> findByNoticeId(Long noticeId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from notice_platform where NOTICE_ID IN ?1")
    void deleteBatchByNoticeIds(List<Long> noticeIds);

    List<NoticePlatformPo> findByNoticeIdAndNoticePlatform(Long noticeId, ENoticePlatform noticePlatform);

}
