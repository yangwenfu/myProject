package com.xinyunlian.jinfu.loan.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.loan.dto.aitz.ATZSureDto;
import com.xinyunlian.jinfu.loan.dto.aitz.AitouziContractDto;
import com.xinyunlian.jinfu.loan.dto.aitz.AtzRepayDto;
import com.xinyunlian.jinfu.loan.dto.bestsign.LoanBestSignDto;
import com.xinyunlian.jinfu.loan.dto.aitz.AtzRepayDto;
import com.xinyunlian.jinfu.loan.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author willwang
 */
@RestController
@RequestMapping(value = "loan/atz")
public class LoanATZController {

    @Autowired
    private PrivateAitouziContractService privateAitouziService;

    @Autowired
    private PrivateBestSignService privateBestSignService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanATZController.class);

    /**
     * 爱投资合同列表
     * @param applId
     * @return
     */
    @RequestMapping(value = "contract/list", method = RequestMethod.GET)
    public ResultDto list(@RequestParam String applId){
        List<AitouziContractDto> list = privateAitouziService.list(applId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), list);
    }

    /**
     * 获取合同签署的地址
     * @param applId
     * @param type
     * @return
     */
    @RequestMapping(value = "contract/get", method = RequestMethod.GET)
    public ResultDto get(@RequestParam String applId, @RequestParam ECntrctTmpltType type){
        privateBestSignService.setSealName("爱投资专用章");
        privateBestSignService.setReturnUrl("/jinfu/web/loan/atz/contract/return");
        LoanBestSignDto loanBestSignDto = privateBestSignService.get(applId, type);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), loanBestSignDto);
    }

    /**
     * 合同同步回调
     * @return
     */
    @RequestMapping(value = "contract/return", method = RequestMethod.GET)
    public ResultDto returnBack(@RequestParam String code, @RequestParam String signID, @RequestParam String applId){
        privateBestSignService.signed(code, signID, applId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 爱投资绑定银行卡
     * @param applId
     * @return
     */
    @RequestMapping(value = "bind", method = RequestMethod.GET)
    public ResultDto bind(@RequestParam String applId){
        ATZSureDto atzSureDto = privateAitouziService.bind(applId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), atzSureDto);
    }

    /**
     * 查看爱投资贷款合同
     * @param applId
     * @return
     */
    @RequestMapping(value = "contract/view/loan", method = RequestMethod.GET)
    public Object viewLoan(@RequestParam String applId){
        return privateBestSignService.viewContract(applId, ECntrctTmpltType.ATZ_LOAN);
    }

    /**
     * 爱投资申请提前还款
     * @param loanId
     * @return
     */
    @RequestMapping(value = "repay/start" ,method = RequestMethod.GET)
    public ResultDto repayStart(@RequestParam String loanId){
        AtzRepayDto atzRepayDto = privateAitouziService.repayStart(loanId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), atzRepayDto);
    }

    /**
     * 爱投资提前还款确认
     * @param loanId
     * @return
     */
    @RequestMapping(value = "repay/confirm" ,method = RequestMethod.GET)
    public ResultDto repayConfirm(@RequestParam String loanId){
        privateAitouziService.repayConfirm(loanId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }
}
