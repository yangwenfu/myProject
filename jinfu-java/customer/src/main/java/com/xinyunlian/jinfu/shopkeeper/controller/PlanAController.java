package com.xinyunlian.jinfu.shopkeeper.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.contract.dto.UserContractDto;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.creditline.dto.LoanUserCreditLineDto;
import com.xinyunlian.jinfu.creditline.enums.ELoanUserCreditLineStatus;
import com.xinyunlian.jinfu.creditline.service.LoanUserCreditLineService;
import com.xinyunlian.jinfu.mq.sender.QueueSender;
import com.xinyunlian.jinfu.shopkeeper.dto.card.CardCheckDto;
import com.xinyunlian.jinfu.shopkeeper.dto.card.CardDto;
import com.xinyunlian.jinfu.shopkeeper.service.MyInfoService;
import com.xinyunlian.jinfu.spider.dto.req.AuthLoginDto;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserExtService;
import com.xinyunlian.jinfu.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author jll
 */
@RestController
@RequestMapping(value = "PlanA")
@Api(description = "AbTest-A方案相关接口")
public class PlanAController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlanAController.class);
    @Autowired
    private MyInfoService myInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserExtService userExtService;
    @Autowired
    private LoanUserCreditLineService loanUserCreditLineService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private RiskUserInfoService riskUserInfoService;
    @Autowired
    private QueueSender queueSender;

    /**
     * 额度申请当前步骤
     */
    @ApiOperation(value = "额度申请当前步骤")
    @RequestMapping(value = "/apply/step", method = RequestMethod.GET)
    public Object applyStep() {
        int step = this.getStep();
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),step);
    }

    /**
     * 获取信用额度
     */
    @ApiOperation(value = "获取信用额度")
    @RequestMapping(value = "/getCredit", method = RequestMethod.GET)
    public Object getCredit() {
        LoanUserCreditLineDto loanUserCreditLineDto = loanUserCreditLineService
                .get(SecurityContext.getCurrentUserId());
        if(loanUserCreditLineDto.getStatus() == ELoanUserCreditLineStatus.NOT_EXISTS){
            loanUserCreditLineService.apply(SecurityContext.getCurrentUserId());
            loanUserCreditLineDto = loanUserCreditLineService
                    .get(SecurityContext.getCurrentUserId());
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"),loanUserCreditLineDto);
    }

    /**
     * 申请信用额度
     */
    @ApiOperation(value = "申请信用额度")
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Object apply() {
        loanUserCreditLineService.apply(SecurityContext.getCurrentUserId());
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    @ApiOperation(value = "个人信息变化通知")
    @RequestMapping(value = "/changeInfo", method = RequestMethod.POST)
    public Object changeInfo() {
        queueSender.send("USER_DETAIL_UPDATED_EVENT", SecurityContext.getCurrentUserId());
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }


    /**
     * 授权认证
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "authorize/user", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto authorizeUser(@RequestBody AuthLoginDto user) {
        String userId = SecurityContext.getCurrentUserId();
        user.setUserId(userId);
        boolean rs = riskUserInfoService.authLogin(user);
        if(rs){
            riskUserInfoService.spiderUserInfo(user.getUserId());
        }

        UserInfoDto userInfoDto = userService.findUserByUserId(userId);

        UserContractDto userContractDto = new UserContractDto();
        userContractDto.setUserId(userId);
        userContractDto.setTemplateType(ECntrctTmpltType.ZXCX);
        UserContractDto existsContract = contractService.getUserContract(userId, ECntrctTmpltType.ZXCX);

        if(existsContract == null){
            contractService.saveContract(userContractDto, userInfoDto);
        }else{
            contractService.updateContract(userContractDto, userInfoDto);
        }

        if(rs) {
            return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), rs);
        } else {
            return ResultDtoFactory.toNack(MessageUtil.getMessage("tobacco.authorization.failed"), rs);
        }
    }

    private int getStep(){
        String userId = SecurityContext.getCurrentUserId();
        UserInfoDto userInfoDto = userService.findUserByUserId(userId);
        UserExtDto userExtDto = userExtService.findUserByUserId(userId);
        //UserContractDto userContractDto = contractService.getUserContract(userId, ECntrctTmpltType.ZXCX);

        CardDto cardDto = myInfoService.getCard(userId);
        CardCheckDto cardCheck = myInfoService.getCardCheck(cardDto);

        if(!userInfoDto.getIdentityAuth() || !cardCheck.isIdAuthIsFull()){
            return 0;
        }
        if(!cardCheck.isLinkmanIsFull() || cardDto.getLinkmanDtos().size() < 2){
            return 1;
        }
        if(userExtDto == null || userExtDto.getLived() == null || !userExtDto.getLived()){
            return 2;
        }
        if(!cardCheck.isRiskAuthIsFull()){
            return 3;
        }
        if(!cardCheck.isBankAuthIsFull()){
            return 4;
        }
        return 5;
    }

}
