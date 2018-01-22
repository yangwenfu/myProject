package com.xinyunlian.jinfu.loan.controller;

import com.xinyunlian.jinfu.cashier.api.JinfuCashierSignature;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.dealer.service.DealerServiceFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/loan/dealer/callback")
public class ServiceFeeCallbackController {

    @Autowired
    private DealerServiceFeeService dealerServiceFeeService;

    @RequestMapping
    @ResponseBody
    public String callback(HttpServletRequest request) {
        Map<String, String> responseMap = new HashMap<>();
        Enumeration em = request.getParameterNames();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = request.getParameter(name);
            responseMap.put(name, value);
        }
        if (!JinfuCashierSignature.verifySign(responseMap, AppConfigUtil.getConfig("cashier.pay.key"), true)) {
            return "FAILED";
        }
        return dealerServiceFeeService.withholdCallBack(responseMap);
    }
}
