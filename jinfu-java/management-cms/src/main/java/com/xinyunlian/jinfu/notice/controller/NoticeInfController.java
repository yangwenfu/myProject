package com.xinyunlian.jinfu.notice.controller;

import com.xinyunlian.jinfu.cms.dto.NoticeInfDto;
import com.xinyunlian.jinfu.cms.dto.NoticeInfSearchDto;
import com.xinyunlian.jinfu.cms.service.NoticeInfService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * Created by dongfangchao on 2017/1/24/0024.
 */
@RestController
@RequestMapping("noticeInf")
public class NoticeInfController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoticeInfController.class);

    @Autowired
    private NoticeInfService noticeInfService;

    /**
     * 分页查询公告
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public ResultDto<Object> getNoticePage(NoticeInfSearchDto searchDto){
        try {
            NoticeInfSearchDto retDto = noticeInfService.getNoticeInfPage(searchDto);
            return ResultDtoFactory.toAck("查询成功", retDto);
        } catch (BizServiceException e) {
            LOGGER.error("分页查询公告失败", e);
            return ResultDtoFactory.toNack(e.getError());
        }
    }

    /**
     * 新增公告
     * @param noticeInfDto
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResultDto<Object> addNotice(@RequestBody NoticeInfDto noticeInfDto){
        try {
            noticeInfService.addNotice(noticeInfDto);
            return ResultDtoFactory.toAck("新增成功");
        } catch (BizServiceException e) {
            LOGGER.error("新增公告失败", e);
            return ResultDtoFactory.toNack(e.getError());
        }
    }

    /**
     * 删除公告
     * @param noticeId
     * @return
     */
    @RequestMapping(value = "/{noticeId}", method = RequestMethod.DELETE)
    public ResultDto<Object> deleteNotice(@PathVariable Long noticeId){
        try {
            noticeInfService.deleteNotice(noticeId);
            return ResultDtoFactory.toAck("删除成功");
        } catch (BizServiceException e) {
            LOGGER.error("删除公告失败", e);
            return ResultDtoFactory.toNack(e.getError());
        }
    }

    /**
     * 更新公告
     * @param noticeInfDto
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResultDto<Object> updateNotice(@RequestBody NoticeInfDto noticeInfDto){
        try {
            noticeInfService.updateNotice(noticeInfDto);
            return ResultDtoFactory.toAck("更新成功");
        } catch (BizServiceException e) {
            LOGGER.error("更新公告失败", e);
            return ResultDtoFactory.toNack(e.getError());
        }
    }

    /**
     * 根据公告id查询公告详情
     * @param noticeId
     * @return
     */
    @RequestMapping(value = "/{noticeId}", method = RequestMethod.GET)
    public ResultDto<Object> getNoticeById(@PathVariable Long noticeId){
        try {
            NoticeInfDto noticeInfDto = noticeInfService.getNoticeById(noticeId);
            return ResultDtoFactory.toAck("查询成功", noticeInfDto);
        } catch (BizServiceException e) {
            LOGGER.error("查询指定公告失败", e);
            return ResultDtoFactory.toNack(e.getError());
        }
    }

    /**
     * 批量删除公告
     * @param noticeIds
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultDto<Object> deleteBatch(@RequestBody Long[] noticeIds){
        if (noticeIds == null || noticeIds.length == 0){
            return ResultDtoFactory.toNack("待删除公告不能为空");
        }
        try {
            noticeInfService.deleteNoticeBatch(Arrays.asList(noticeIds));
            return ResultDtoFactory.toAck("批量删除成功");
        } catch (BizServiceException e) {
            LOGGER.error("批量删除公告失败", e);
            return ResultDtoFactory.toNack(e.getError());
        }
    }

}
