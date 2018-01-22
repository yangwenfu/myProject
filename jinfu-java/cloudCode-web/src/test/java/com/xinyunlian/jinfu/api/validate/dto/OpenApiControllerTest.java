package com.xinyunlian.jinfu.api.validate.dto;

import com.xinyunlian.jinfu.api.dto.xyl.*;
import com.xinyunlian.jinfu.api.validate.util.SignUtil;
import com.xinyunlian.jinfu.common.util.HttpUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * UserController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 30, 2016</pre>
 */
public class OpenApiControllerTest {
    @Autowired
    private String URL = "http://yltest.xylpay.com/jinfu-cloudCode/web/open-api";

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testRegister() throws Exception {
        //签名方法
        String mobile = "13967885168";
        String userName = "孟磊";
        String idCardNo = "341126197709218366";
        String storeName = "磊的小店";
        String province = "浙江省";
        String city = "宁波市";
        String area = "江北区";
        String street = "洪塘街道";
        String address = "中策世纪博园";
        String tobaccoCertificateNo = "610100128366";
        String bizLicence = "610139600018366";
        String appId = "xsm";

        RegisterOpenApiDto registerOpenApiDto = new RegisterOpenApiDto();
        registerOpenApiDto.setMobile(mobile);
        registerOpenApiDto.setAppId(appId);
        registerOpenApiDto.setSign(SignUtil.createSign(registerOpenApiDto, "gEV9GhV413vDlDtc"));

        String result = HttpUtil.doPost(URL + "/register", "application/json", JsonUtil.toJson(registerOpenApiDto));
        System.out.println(result);

    }

    @Test
    public void testQrCode() throws Exception {
        String tobaccoCertificateNo = "123123";
        String appId = "xsm";

        TobaccoCertificateNoOpenApiDto tobaccoCertificateNoOpenApiDto = new TobaccoCertificateNoOpenApiDto();
        tobaccoCertificateNoOpenApiDto.setTobaccoCertificateNo(tobaccoCertificateNo);
        tobaccoCertificateNoOpenApiDto.setAppId(appId);
        tobaccoCertificateNoOpenApiDto.setSign(SignUtil.createSign(tobaccoCertificateNoOpenApiDto, "gEV9GhV413vDlDtc"));

        String result = HttpUtil.doPost(URL + "/qrcode", "application/json", JsonUtil.toJson(tobaccoCertificateNoOpenApiDto));
        System.out.println(result);

    }

    @Test
    public void testTrade() throws Exception {
        String tradeNo = "81487240608477";
        String appId = "xsm";

        TradeOpenApiDto tradeOpenApiDto = new TradeOpenApiDto();
        tradeOpenApiDto.setTradeNo(tradeNo);
        tradeOpenApiDto.setAppId(appId);
        tradeOpenApiDto.setSign(SignUtil.createSign(tradeOpenApiDto, "gEV9GhV413vDlDtc"));

        String result = HttpUtil.doPost(URL + "/trade", "application/json", JsonUtil.toJson(tradeOpenApiDto));
        System.out.println(result);

    }

    @Test
    public void testMerchantscan() throws Exception {
        String qrCodeNo = "ym20170316111";
        String payCode = "130087134802938606";
        BigDecimal amount = BigDecimal.valueOf(0.01);
        String appId = "xsm";

        MerchantScanOpenApiDto merchantScanOpenApiDto = new MerchantScanOpenApiDto();
        merchantScanOpenApiDto.setQrCodeNo(qrCodeNo);
        merchantScanOpenApiDto.setPayCode(payCode);
        merchantScanOpenApiDto.setAmount(amount);
        merchantScanOpenApiDto.setAppId(appId);
        merchantScanOpenApiDto.setSign(SignUtil.createSign(merchantScanOpenApiDto, "gEV9GhV413vDlDtc"));

        String result = HttpUtil.doPost(URL + "/merchantscan", "application/json", JsonUtil.toJson(merchantScanOpenApiDto));
        System.out.println(result);

    }

    @Test
    public void testUserscan() throws Exception {
        String qrCodeNo = "ym2017082300017";
        BigDecimal amount = BigDecimal.valueOf(0.01);
        String appId = "xsm";

        UserScanOpenApiDto userScanOpenApiDto = new UserScanOpenApiDto();
        userScanOpenApiDto.setQrCodeNo(qrCodeNo);
        userScanOpenApiDto.setType("2");
        userScanOpenApiDto.setAmount(amount);
        userScanOpenApiDto.setAppId(appId);
        userScanOpenApiDto.setSign(SignUtil.createSign(userScanOpenApiDto, "gEV9GhV413vDlDtc"));

        String result = HttpUtil.doPost(URL + "/userscan", "application/json", JsonUtil.toJson(userScanOpenApiDto));
        System.out.println(result);

    }

}
