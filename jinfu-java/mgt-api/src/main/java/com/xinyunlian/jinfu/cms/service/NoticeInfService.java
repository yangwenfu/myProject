package com.xinyunlian.jinfu.cms.service;

import com.xinyunlian.jinfu.cms.dto.NoticeInfDto;
import com.xinyunlian.jinfu.cms.dto.NoticeInfSearchDto;
import com.xinyunlian.jinfu.cms.enums.ENoticePlatform;
import com.xinyunlian.jinfu.common.exception.BizServiceException;

import java.util.List;

/**
 * Created by dongfangchao on 2017/1/24/0024.
 */
public interface NoticeInfService {

    /**
     * 新增公告
     * @param noticeInfDto
     * @throws BizServiceException
     */
    void addNotice(NoticeInfDto noticeInfDto) throws BizServiceException;

    /**
     * 删除公告
     * @param noticeId
     * @throws BizServiceException
     */
    void deleteNotice(Long noticeId) throws BizServiceException;

    /**
     * 更新公告
     * @param noticeInfDto
     * @throws BizServiceException
     */
    void updateNotice(NoticeInfDto noticeInfDto) throws BizServiceException;

    /**
     * 分页查询公告
     * @param noticeInfSearchDto
     * @return
     * @throws BizServiceException
     */
    NoticeInfSearchDto getNoticeInfPage(NoticeInfSearchDto noticeInfSearchDto) throws BizServiceException;

    /**
     * 根据公告id查询公告详情
     * @param noticeId
     * @return
     * @throws BizServiceException
     */
    NoticeInfDto getNoticeById(Long noticeId) throws BizServiceException;

    /**
     * 批量删除公告
     * @param noticeIds
     * @throws BizServiceException
     */
    void deleteNoticeBatch(List<Long> noticeIds) throws BizServiceException;

    /**
     * 获取平台对应的有效的公告
     * @param noticePlatform
     * @return
     * @throws BizServiceException
     */
    List<NoticeInfDto> getNoticeByPlatform(ENoticePlatform noticePlatform) throws BizServiceException;

}
