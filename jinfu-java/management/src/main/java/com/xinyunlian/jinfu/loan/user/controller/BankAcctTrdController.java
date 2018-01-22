package com.xinyunlian.jinfu.loan.user.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.loan.dto.LoanApplUserDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.loan.service.PrivateLoanService;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.loan.service.LoanApplUserService;
import com.xinyunlian.jinfu.loan.user.service.PrivateLoanUserService;
import com.xinyunlian.jinfu.user.dto.UserBankAcctDto;
import com.xinyunlian.jinfu.user.dto.UserBankAcctTrdDto;
import com.xinyunlian.jinfu.user.service.UserBankAcctTrdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by King on 2017/2/16.
 */
@Controller
@RequestMapping(value = "/loan/user/bankAcctTrd")
public class BankAcctTrdController {
    @Autowired
    private UserBankAcctTrdService userBankAcctTrdService;

    @Autowired
    private PrivateLoanUserService privateLoanUserService;

    @Autowired
    private PrivateLoanService privateLoanService;

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultDto<Object> save(@RequestParam String applId, @RequestBody UserBankAcctTrdDto userBankAcctTrdDto) {
        userBankAcctTrdService.save(userBankAcctTrdDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultDto<Object> delete(@RequestParam String applId, @RequestParam Long id) {
        userBankAcctTrdService.delete(id);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResultDto<Object> info(@RequestParam Long id) {
        UserBankAcctTrdDto userBankAcctTrdDto = userBankAcctTrdService.get(id);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), userBankAcctTrdDto);
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<Object> list(@RequestParam Long bankAccountId, @RequestParam String applId) {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),
                privateLoanUserService.getBankTrd(bankAccountId, applId));
    }

}
