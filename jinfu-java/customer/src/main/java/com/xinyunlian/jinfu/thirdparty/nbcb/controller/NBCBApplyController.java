package com.xinyunlian.jinfu.thirdparty.nbcb.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.house.dto.UserHouseDto;
import com.xinyunlian.jinfu.house.service.UserHouseService;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import com.xinyunlian.jinfu.thirdparty.nbcb.dto.NBCBOrderDto;
import com.xinyunlian.jinfu.thirdparty.nbcb.service.NBCBOrderService;
import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.enums.EHouseProperty;
import com.xinyunlian.jinfu.user.service.UserExtService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by bright on 2017/5/16.
 */
@RestController
@RequestMapping(value = "thirdparty/nbcb")
public class NBCBApplyController {
    public static final Logger LOGGER = LoggerFactory.getLogger(NBCBApplyController.class);

    public static final BigDecimal BD_10000 = BigDecimal.valueOf(10000);

    @Autowired
    private UserExtService userExtService;

    @Autowired
    private NBCBOrderService nbcbOrderService;

    @Autowired
    private RiskUserInfoService riskUserInfoService;

    @RequestMapping(value = "/preCredit", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取预授权额度")
    public ResultDto<BigDecimal> preCredit(){
        String userId = SecurityContext.getCurrentUserId();
        BigDecimal preCredit = BigDecimal.ZERO;
        BigDecimal tobaccoOrderAmount = riskUserInfoService.getYearlyOrderAmout(userId);

        preCredit = tobaccoOrderAmount.divide(BigDecimal.valueOf(4), BigDecimal.ROUND_HALF_UP);

        UserExtDto userExtDto = userExtService.findUserByUserId(userId);

        Boolean hasHourseProperty =  (userExtDto.getHouseProperty() == EHouseProperty.OWN ||
                userExtDto.getHouseProperty() == EHouseProperty.MORTGAGE);

        if(hasHourseProperty){
            preCredit = preCredit.add(BigDecimal.valueOf(50000));
        }

        preCredit = preCredit.divide(BD_10000, BigDecimal.ROUND_HALF_UP)
                .setScale(0, BigDecimal.ROUND_HALF_UP);

        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), preCredit);
    }

    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取宁波银行跳转地址")
    public ResultDto<String> apply(){
        String userId = SecurityContext.getCurrentUserId();
        if(!nbcbOrderService.canApply(userId)){
            return ResultDtoFactory.toNack("您有在途的提额申请，不能再次发起");
        }
        NBCBOrderDto orderDto = new NBCBOrderDto();
        orderDto.setUserId(userId);
        String orderNo = nbcbOrderService.createOrder(orderDto);
        String url = String.format(AppConfigUtil.getConfig("nbcbUrl"), userId, orderNo);
        LOGGER.info("宁波银行跳转地址:{}", url);
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), url);
    }
}
