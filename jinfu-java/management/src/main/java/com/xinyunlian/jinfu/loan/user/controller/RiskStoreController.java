package com.xinyunlian.jinfu.loan.user.controller;

import com.xinyunlian.jinfu.api.service.ApiBaiduService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
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
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by KimLL on 2017/2/20.
 */
@Controller
@RequestMapping(value = "loan/user/store")
public class RiskStoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private PrivateLoanUserService privateLoanUserService;

    @Autowired
    private PrivateLoanService privateLoanService;

    @Autowired
    private LoanApplUserService loanApplUserService;

    /**
     * 修改店铺
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> update(@RequestParam String applId, @RequestBody JsonDataDto json) {
        LoanApplUserDto loanApplUserDto = loanApplUserService.findByApplId(applId);
        loanApplUserDto.setStoreBase(json.getData());
        loanApplUserService.save(loanApplUserDto);
        return ResultDtoFactory.toAck("修改成功");
    }

    /**
     * 获取该用户店铺信息
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> get(@RequestParam String userId, @RequestParam String applId) {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),
                privateLoanUserService.getStore(userId, applId));
    }

    @RequestMapping(value = "/getByID", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getById(@RequestParam Long storeId) {
        StoreInfDto storeInfDto = storeService.findByStoreId(storeId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), storeInfDto);
    }

}
