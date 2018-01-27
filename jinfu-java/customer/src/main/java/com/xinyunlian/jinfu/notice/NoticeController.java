package com.xinyunlian.jinfu.notice;

import com.xinyunlian.jinfu.cms.dto.NoticeInfDto;
import com.xinyunlian.jinfu.cms.enums.ENoticePlatform;
import com.xinyunlian.jinfu.cms.service.NoticeInfService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jll on 2017/1/24/0024.
 */
@RestController
@RequestMapping("notice")
public class NoticeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private NoticeInfService noticeInfService;

    /**
     * 获取公告
     * @param noticePlatform
     * @return
     */
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ResultDto<Object> getNotice(ENoticePlatform noticePlatform){
        List<NoticeInfDto> noticeInfDtos = noticeInfService.getNoticeByPlatform(noticePlatform);
        return ResultDtoFactory.toAck("获取成功", noticeInfDtos);
    }

}
