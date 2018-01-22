package com.xinyunlian.jinfu.loan;

import com.alibaba.dubbo.rpc.RpcContext;
import com.xinyunlian.jinfu.cashier.api.JinfuCashierConstants;
import com.xinyunlian.jinfu.cashier.api.JinfuCashierSignature;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.OkHttpUtil;
import com.xinyunlian.jinfu.loan.dto.NBCBRiskInfoDto;
import com.xinyunlian.jinfu.loan.dto.UserRiskDto;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import com.xinyunlian.jinfu.thirdparty.nbcb.service.NBCBOrderService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

/**
 * Created by bright on 2017/5/26.
 */
@Component
public class NBCBJob {
    public static final Logger LOGGER = LoggerFactory.getLogger(NBCBJob.class);

    @Autowired
    private NBCBOrderService nbcbOrderService;

    @Autowired
    private RiskUserInfoService riskUserInfoService;

    @Autowired
    private UserService userService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void pushRiskItem(){
        List<String> userIds = nbcbOrderService.getAllAppliedUserId();
        List<UserRiskDto> userRiskDtos = new ArrayList<>();
        userIds.forEach(userId -> {
            riskUserInfoService.spiderUserInfo(userId);

            Future<Void> future = RpcContext.getContext().getFuture();

            try {
                future.get();
            } catch (Exception e) {
                LOGGER.error("抓取异常", e);
            }

            Double rate = riskUserInfoService.getGrowthRate(userId);
            Double amt = riskUserInfoService.getOrderAmtForThisMonth(userId);

            Integer riskType = null;

            if(Objects.nonNull(rate) && Math.abs(rate) > 50){
                riskType = 0;
            }

            if(Objects.nonNull(amt) &&  0 == amt){
                if(Objects.nonNull(riskType)){
                    riskType = 2;
                } else {
                    riskType = 1;
                }
            }

            if(Objects.nonNull(riskType)){
                UserRiskDto userRiskDto = new UserRiskDto();
                userRiskDto.setRiskType(String.valueOf(riskType));
                UserInfoDto userInfoDto = userService.findUserByUserId(userId);
                userRiskDto.setName(userInfoDto.getUserName());
                userRiskDto.setIdNo(userInfoDto.getIdCardNo());
                userRiskDtos.add(userRiskDto);
            }
        });

        NBCBRiskInfoDto nbcbRiskInfoDto = new NBCBRiskInfoDto();
        nbcbRiskInfoDto.setRiskUsers(JsonUtil.toJson(userRiskDtos));
        nbcbRiskInfoDto.setTimestamp(String.valueOf(System.currentTimeMillis()));

        String sign = JinfuCashierSignature.computeSign(JsonUtil.toMap(nbcbRiskInfoDto),
                AppConfigUtil.getConfig("nbcb.privatekey"),
                JinfuCashierConstants.DEFAULT_CHARSET,
                JinfuCashierConstants.SIGN_TYPE_RSA);
        nbcbRiskInfoDto.setSign(sign);

        String url = AppConfigUtil.getConfig("nbcb.riskurl");
        try {
            String request = JsonUtil.toJson(nbcbRiskInfoDto);
            LOGGER.info("request to NBCB {}", request);
            String response =
            OkHttpUtil.postJson(url,
                    request,
                    url.startsWith("https"));
            LOGGER.info("response from NBCB {}", response);
        } catch (IOException e) {
            LOGGER.error("推送风险提示数据到宁波银行出错", e);
        }
    }
}
