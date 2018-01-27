package com.xinyunlian.jinfu.loan.user.controller;

import com.xinyunlian.jinfu.car.dto.UserCarDto;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.loan.dto.LoanApplUserDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.loan.service.LoanApplUserService;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.loan.loan.service.PrivateLoanService;
import com.xinyunlian.jinfu.loan.user.service.PrivateLoanUserService;
import com.xinyunlian.jinfu.user.dto.UserBankAcctDto;
import com.xinyunlian.jinfu.user.service.UserBankAcctService;

import java.util.List;

/**
 * Created by King on 2017/2/16.
 */
@Controller
@RequestMapping(value = "/loan/user/bankAcct")
public class BankAcctController {
    @Autowired
    private UserBankAcctService userBankAcctService;

    @Autowired
    private PrivateLoanUserService privateLoanUserService;

    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private LoanApplUserService loanApplUserService;

    @Autowired
    private PictureService pictureService;

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultDto<Object> save(@RequestParam String applId, @RequestBody UserBankAcctDto userBankAcctDto) {
        userBankAcctService.save(userBankAcctDto);

        LoanApplDto apply = loanApplService.get(applId);
        List<UserBankAcctDto> userBankAcctDtos = userBankAcctService.list(apply.getUserId());
        if(!CollectionUtils.isEmpty(userBankAcctDtos)){
            userBankAcctDtos.forEach(item -> {
                item.setPictureDtos(pictureService.list(item.getBankAccountId().toString(), EPictureType.BANK_TRADE));
            });
        }
        LoanApplUserDto loanApplUserDto = loanApplUserService.findByApplId(applId);
        loanApplUserDto.setBankBase(JsonUtil.toJson(userBankAcctDto));
        loanApplUserService.save(loanApplUserDto);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultDto<Object> delete(@RequestParam String applId, @RequestParam Long id) {
        userBankAcctService.delete(id);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResultDto<Object> info(@RequestParam Long id) {
        UserBankAcctDto userBankAcctDto = userBankAcctService.get(id);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),userBankAcctDto);
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<Object> list(@RequestParam String userId, @RequestParam String applId) {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),
                privateLoanUserService.getBank(userId, applId));
    }

}
