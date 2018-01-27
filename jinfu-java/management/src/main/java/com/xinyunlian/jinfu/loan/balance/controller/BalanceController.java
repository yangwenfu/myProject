package com.xinyunlian.jinfu.loan.balance.controller;

import com.xinyunlian.jinfu.balance.dto.BalanceDetailDto;
import com.xinyunlian.jinfu.balance.dto.BalanceDetailListDto;
import com.xinyunlian.jinfu.balance.dto.BalanceOutlineDto;
import com.xinyunlian.jinfu.balance.service.BalanceCashierService;
import com.xinyunlian.jinfu.balance.service.BalanceDetailService;
import com.xinyunlian.jinfu.balance.service.BalanceOutlineService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.loan.balance.service.PrivateBalanceService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Willwang on 2017/5/19.
 */
@RestController
@RequestMapping(value = "loan/balance")
@RequiresPermissions({"LOAN_BALANCE"})
public class BalanceController {

    @Autowired
    private BalanceCashierService balanceCashierService;

    @Autowired
    private BalanceOutlineService balanceOutlineService;

    @Autowired
    private BalanceDetailService balanceDetailService;

    @Autowired
    private PrivateBalanceService privateBalanceService;

    /**
     * 对账概要
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "outline", method = RequestMethod.GET)
    public ResultDto outline(@RequestParam(value = "start", required = false) String start, @RequestParam(value = "end", required = false) String end){
        
        if(StringUtils.isEmpty(start)){
            start = DateHelper.getNow();
        }
        if(StringUtils.isEmpty(end)){
            end = DateHelper.getNow();
        }

        List<BalanceOutlineDto> list = balanceOutlineService.listByDate(start, end);
        list = privateBalanceService.completeMgtUserName(list);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), list);
    }

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public ResultDto detail(@RequestParam Long detailId){
        BalanceDetailDto balanceDetailDto = balanceDetailService.detail(detailId);
        balanceDetailDto = privateBalanceService.completeUserName(balanceDetailDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), balanceDetailDto);
    }


    /**
     * 自动勾对
     * @param outlineId
     * @return
     */
    @RequestMapping(value = "auto", method = RequestMethod.POST)
    public ResultDto auto(@RequestParam Long outlineId){

        //触发强制自动勾对接口，更新开关为未进行过自动勾对
        balanceOutlineService.updateAutoed(outlineId, false);

        balanceDetailService.auto(outlineId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 取消勾对
     * @param detailId
     * @return
     */
    @RequestMapping(value = "cancel", method = RequestMethod.POST)
    public ResultDto cancel(@RequestParam Long detailId){
        balanceDetailService.cancel(detailId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResultDto list(@RequestParam Long outlineId){
        BalanceDetailListDto balanceDetailListDto = balanceDetailService.list(outlineId);
        balanceDetailListDto = privateBalanceService.completeUserName(balanceDetailListDto);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), balanceDetailListDto);
    }

    /**
     * 更新账单
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResultDto update(@RequestParam Long outlineId){
        balanceCashierService.refresh(outlineId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 完成对账
     * @return
     */
    @RequestMapping(value = "finish", method = RequestMethod.POST)
    public ResultDto finish(@RequestParam Long outlineId){

        String mgtUserId = SecurityContext.getCurrentOperatorId();

        balanceOutlineService.finish(mgtUserId, outlineId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 手动单笔勾对
     * @param balanceDetailDto
     * @return
     */
    @RequestMapping(value = "sure", method = RequestMethod.POST)
    public ResultDto sure(@RequestBody BalanceDetailDto balanceDetailDto){
        String mgtUserId = SecurityContext.getCurrentOperatorId();

        balanceDetailService.manual(mgtUserId, balanceDetailDto);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

}
