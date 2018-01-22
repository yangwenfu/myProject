package com.xinyunlian.jinfu.loan.user.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.loan.dto.LoanApplUserDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.loan.service.PrivateLoanService;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.loan.service.LoanApplUserService;
import com.xinyunlian.jinfu.loan.user.JsonDataDto;
import com.xinyunlian.jinfu.loan.user.service.PrivateLoanUserService;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.user.dto.UserLinkmanDto;
import com.xinyunlian.jinfu.user.dto.ext.*;
import com.xinyunlian.jinfu.user.service.UserExtService;
import com.xinyunlian.jinfu.user.service.UserLinkmanService;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by King on 2017/2/20.
 */
@Controller
@RequestMapping(value = "loan/user")
public class RiskUserController {

    @Autowired
    private UserExtService userExtService;

    @Autowired
    private UserLinkmanService userLinkmanService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private PrivateLoanUserService privateLoanUserService;

    @Autowired
    private PrivateLoanService privateLoanService;

    @Autowired
    private LoanApplUserService loanApplUserService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RiskUserController.class);

    /**
     * 获得用户基本信息
     *
     * @return
     */
    @RequestMapping(value = "/base", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getBase(@RequestParam String userId, @RequestParam String applId) {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),
                privateLoanUserService.getBase(userId, applId));
    }

    /**
     * 获得用户家庭信息
     *
     * @return
     */
    @RequestMapping(value = "/family", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getFamily(@RequestParam String userId, @RequestParam String applId) {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),
                privateLoanUserService.getFamily(userId, applId));
    }

    /**
     * 保存用户基本信息
     * @return
     */
    @RequestMapping(value = "/base", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> setBase(@RequestParam String applId, @RequestBody JsonDataDto json) {
        LoanApplUserDto loanApplUserDto = loanApplUserService.findByApplId(applId);
        loanApplUserDto.setUserBase(json.getData());
        loanApplUserService.save(loanApplUserDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 保存用户家庭信息
     * @return
     */
    @RequestMapping(value = "/family", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> setFamily(@RequestParam String applId, @RequestBody JsonDataDto json) {
        LoanApplUserDto loanApplUserDto = loanApplUserService.findByApplId(applId);
        loanApplUserDto.setUserBase(json.getData());
        loanApplUserService.save(loanApplUserDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 获得用户财务信息
     *
     * @return
     */
    @RequestMapping(value = "/fin", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getFin(@RequestParam String userId, @RequestParam String applId) {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),
                privateLoanUserService.getFin(userId, applId));
    }

    /**
     * 保存用户财务信息
     * @return
     */
    @RequestMapping(value = "/fin", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> setFin(@RequestParam String applId, @RequestBody JsonDataDto json) {
        LoanApplUserDto loanApplUserDto = loanApplUserService.findByApplId(applId);
        loanApplUserDto.setUserBase(json.getData());
        loanApplUserService.save(loanApplUserDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 获得用户社保公积金信息
     *
     * @return
     */
    @RequestMapping(value = "/fund", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getFund(@RequestParam String userId, @RequestParam String applId) {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),
                privateLoanUserService.getFund(userId, applId));
    }

    /**
     * 保存用户社保公积金信息
     * @return
     */
    @RequestMapping(value = "/fund", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> setFund(@RequestParam String applId, @RequestBody JsonDataDto json) {
        LoanApplUserDto loanApplUserDto = loanApplUserService.findByApplId(applId);
        loanApplUserDto.setUserBase(json.getData());
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 获得联系人信息
     *
     * @return
     */
    @RequestMapping(value = "/linkman", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getLinkman(@RequestParam String userId) {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), userLinkmanService.findByUserId(userId));
    }

    /**
     * 保存联系人信息
     *
     * @return
     */
    @RequestMapping(value = "/linkman", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> setLinkman(@RequestBody List<UserLinkmanDto> userLinkmanDtos) {
        userLinkmanService.saveUserLinkman(userLinkmanDtos);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 删除联系人信息
     *
     * @return
     */
    @RequestMapping(value = "/linkman/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> deleteLinkman(@RequestParam Long linkmanId) {
        userLinkmanService.deleteUserLinkman(linkmanId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 获得用户图片
     *
     * @return
     */
    @RequestMapping(value = "/userPic", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getUserPic(@RequestParam String userId) {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), pictureService.list(userId));
    }

}
