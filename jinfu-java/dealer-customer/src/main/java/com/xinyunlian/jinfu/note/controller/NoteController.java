package com.xinyunlian.jinfu.note.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.dealer.dto.DealerUserNoteDto;
import com.xinyunlian.jinfu.dealer.dto.DealerUserNoteSearchDto;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserLogType;
import com.xinyunlian.jinfu.dealer.service.DealerUserLogService;
import com.xinyunlian.jinfu.dealer.service.DealerUserNoteService;
import com.xinyunlian.jinfu.note.dto.req.NoteDto;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Created by menglei on 2016年09月18日.
 */
@Controller
@RequestMapping(value = "note")
public class NoteController {

    @Autowired
    private DealerUserNoteService dealerUserNoteService;
    @Autowired
    private DealerUserLogService dealerUserLogService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private UserService userService;

    /**
     * 添加记录
     *
     * @param noteDto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> save(@RequestBody @Valid NoteDto noteDto, BindingResult result) {
        if (result.hasErrors()){
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(Long.valueOf(noteDto.getStoreId()));
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(storeInfDto.getUserId());
        if (userInfoDto == null) {
            return ResultDtoFactory.toNack("商户不存在");
        }
        DealerUserDto dealerUserDto = (DealerUserDto) SecurityContext.getCurrentUser().getParamMap().get("dealerUserDto");
        DealerUserNoteDto dealerUserNoteDto = ConverterService.convert(noteDto, DealerUserNoteDto.class);
        dealerUserNoteDto.setUserId(dealerUserDto.getUserId());
        dealerUserNoteDto.setDealerId(dealerUserDto.getDealerId());
        dealerUserNoteDto.setStoreName(storeInfDto.getStoreName());
        dealerUserNoteDto.setStoreUserName(userInfoDto.getUserName());
        DealerUserNoteDto dealerUserNote = dealerUserNoteService.createNote(dealerUserNoteDto);
        //插入分销员操作日志
        dealerUserLogService.createDealerUserLog(dealerUserDto, noteDto.getLogLng(), noteDto.getLogLat(), noteDto.getLogAddress(),
                storeInfDto.getUserId(), noteDto.getStoreId(), "记录添加:noteId=" + dealerUserNote.getNoteId(), EDealerUserLogType.ADDNOTE);
        return ResultDtoFactory.toAck("添加成功");
    }

    /**
     * 查询记录列表
     *
     * @param dealerUserNoteSearchDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<DealerUserNoteSearchDto> getNoteList(DealerUserNoteSearchDto dealerUserNoteSearchDto) {
        if (StringUtils.isEmpty(dealerUserNoteSearchDto.getStoreId())) {
            dealerUserNoteSearchDto.setUserId(SecurityContext.getCurrentUserId());
        }
        DealerUserNoteSearchDto page = dealerUserNoteService.getNotePage(dealerUserNoteSearchDto);
        return ResultDtoFactory.toAck("获取成功", page);
    }

}
