package com.xinyunlian.jinfu.loan.user.controller;

import com.xinyunlian.jinfu.car.dto.UserCarDto;
import com.xinyunlian.jinfu.car.service.UserCarService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.house.dto.UserHouseDto;
import com.xinyunlian.jinfu.loan.dto.LoanApplUserDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.loan.service.PrivateLoanService;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.loan.service.LoanApplUserService;
import com.xinyunlian.jinfu.loan.user.JsonDataDto;
import com.xinyunlian.jinfu.loan.user.service.PrivateLoanUserService;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by King on 2017/2/16.
 */
@Controller
@RequestMapping(value = "/loan/user/car")
public class CarController {
    @Autowired
    private UserCarService userCarService;

    @Autowired
    private PrivateLoanUserService privateLoanUserService;

    @Autowired
    private LoanApplUserService loanApplUserService;

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultDto<Object> save(@RequestParam String applId, @RequestBody JsonDataDto json) {
        LoanApplUserDto loanApplUserDto = loanApplUserService.findByApplId(applId);
        loanApplUserDto.setCarBase(json.getData());
        loanApplUserService.save(loanApplUserDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultDto<Object> delete(@RequestParam String applId, @RequestParam Long id) {
        userCarService.delete(id);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResultDto<Object> info(@RequestParam Long id) {
        UserCarDto userCarDto = userCarService.get(id);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),userCarDto);
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultDto<Object> list(@RequestParam String userId, @RequestParam String applId) {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),
                privateLoanUserService.getCar(userId, applId));
    }

}
