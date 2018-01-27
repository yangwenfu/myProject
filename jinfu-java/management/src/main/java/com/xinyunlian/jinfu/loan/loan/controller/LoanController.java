package com.xinyunlian.jinfu.loan.loan.controller;

import com.xinyunlian.jinfu.common.constant.ApplicationConstant;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.loan.appl.service.PrivateLoanApplService;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.dto.resp.MLoanDetailDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanSearchListDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanSearchResultDto;
import com.xinyunlian.jinfu.loan.loan.service.PrivateLoanService;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.loan.service.LoanQueryService;
import com.xinyunlian.jinfu.loan.service.LoanService;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.pay.service.InnerPayRecvOrdService;
import com.xinyunlian.jinfu.pay.service.PayRecvOrdService;
import com.xinyunlian.jinfu.promo.dto.PromoDto;
import com.xinyunlian.jinfu.promo.dto.PromoInfDto;
import com.xinyunlian.jinfu.promo.dto.PromotionDto;
import com.xinyunlian.jinfu.promo.service.LoanPromoService;
import com.xinyunlian.jinfu.promo.service.PromotionService;
import com.xinyunlian.jinfu.schedule.dto.management.MSContainerDto;
import com.xinyunlian.jinfu.schedule.service.ScheduleService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JL on 2016/9/28.
 */
@Controller
@RequestMapping(value = "loan")
@RequiresPermissions({"AFTER_LOAN_LIST"})
public class LoanController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private LoanQueryService loanQueryService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private UserService userService;

    @Autowired
    private LoanPromoService loanPromoService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private PrivateLoanService privateLoanService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanController.class);

    /**
     * 贷后管理
     * @param search
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<LoanSearchListDto> getLoanList(@RequestBody LoanSearchListDto search) {
        LoanSearchListDto rs = privateLoanService.list(search);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), rs);
    }

    /**
     * 还款计划与还款列表
     */
    @ResponseBody
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public ResultDto<MLoanDetailDto> detail(@RequestParam String loanId) {

        MLoanDetailDto detail = loanQueryService.loanDetail(loanId);

        LoanApplDto apply = loanApplService.get(detail.getLoan().getApplId());

        PromoDto promoDto = loanPromoService.get(detail.getLoan().getLoanId());
        if(null != promoDto){
            PromotionDto promotionDto = promotionService.getByPromotionId(promoDto.getPromoId());
            if(null != promotionDto){
                if(null != promotionDto.getPromoInfDto()){
                    PromoInfDto promoInfDto = promotionDto.getPromoInfDto();
                    detail.setPromoId(promoInfDto.getPromoId());
                    detail.setPromoName(promoInfDto.getAlias());
                    detail.setPromoDesc(promoInfDto.getDescribe());
                }
            }
        }

        UserInfoDto userInfoDto = userService.findUserByUserId(detail.getLoan().getUserId());

        detail.setMobile(userInfoDto.getMobile());
        detail.setUserName(userInfoDto.getUserName());

        detail.setStatus(loanApplService.getStatus(detail.getLoan(), apply));
        detail.setIdCardNo(userInfoDto.getIdCardNo());
        detail.setApplDate(DateHelper.formatDate(apply.getCreateDate(), ApplicationConstant.TIMESTAMP_FORMAT));
        detail.setStatus(loanApplService.getStatus(detail.getLoan(), apply));

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), detail);
    }

    /**
     * 还款计划与还款列表
     */
    @ResponseBody
    @RequestMapping(value = "schedule", method = RequestMethod.GET)
    public Object schedule(@RequestParam String loanId) {
        List<MSContainerDto> list = scheduleService.getManagementScheduleByLoanId(loanId);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), list);
    }

    /**
     * 重新放款(不带放款指令)
     */
    @ResponseBody
    @RequestMapping(value = "retry/simple", method = RequestMethod.GET)
    @RequiresPermissions({"REMAKE_LOAN"})
    public Object retrySimple(@RequestParam String loanId, @RequestParam String time) {
        LoanDtlDto loanDtlDto = loanService.get(loanId);
        loanDtlDto.setTransferDate(DateHelper.getDate(time, ApplicationConstant.DATE_FORMAT));
        try{
            privateLoanService.dummyPay(loanDtlDto);
        }catch(Exception e){
            LOGGER.warn("loan retry pay simple error", e);
            return ResultDtoFactory.toNack(MessageUtil.getMessage("common.operate.fail"));
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

    /**
     * 重新放款(带放款指令)
     */
    @ResponseBody
    @RequestMapping(value = "retry/pay", method = RequestMethod.GET)
    @RequiresPermissions({"REMAKE_LOAN"})
    public Object retryPay(@RequestParam String loanId) {
        LoanDtlDto loanDtlDto = loanService.get(loanId);
        try{
            privateLoanService.pay(loanDtlDto);
        }catch(Exception e){
            LOGGER.warn("loan retry pay error", e);
            return ResultDtoFactory.toNack(MessageUtil.getMessage("common.operate.fail"));
        }
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"));
    }

}
