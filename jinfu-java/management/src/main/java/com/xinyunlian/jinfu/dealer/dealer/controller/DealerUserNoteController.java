package com.xinyunlian.jinfu.dealer.dealer.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.dealer.dto.DealerUserNoteSearchDto;
import com.xinyunlian.jinfu.dealer.service.DealerUserNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by menglei on 2016年09月19日.
 */
@Controller
@RequestMapping(value = "dealer/dealerNote")
public class DealerUserNoteController {

    @Autowired
    private DealerUserNoteService dealerUserNoteService;

    /**
     * 分页查询记录列表
     *
     * @param dealerUserNoteSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<DealerUserNoteSearchDto> getNoteList(DealerUserNoteSearchDto dealerUserNoteSearchDto) {
        DealerUserNoteSearchDto page = dealerUserNoteService.getNotePage(dealerUserNoteSearchDto);
        return ResultDtoFactory.toAck("获取成功", page);
    }

    /**
     * 删除记录
     *
     * @param noteId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultDto<String> delete(@RequestParam Long noteId) {
        dealerUserNoteService.deleteNote(noteId);
        return ResultDtoFactory.toAck("删除成功");
    }

}
