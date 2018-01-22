package com.ylfin.wallet.provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylfin.wallet.provider.shangqi.ShangqiApi;
import com.ylfin.wallet.provider.shangqi.ShangqiProperties;
import com.ylfin.wallet.provider.shangqi.req.PaymentReq;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EnableAutoConfiguration
public class ShangqiApiTest {

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    @Autowired
    private ShangqiApi shangqiApi;

    @Test
    public void paymentReq() {
        PaymentReq paymentReq = new PaymentReq();
        paymentReq.setPayType("NC01");
        paymentReq.setMerchOrderNo("T" + DateTime.now().toString("yyyyMMddHHmmssSSS"));
//        paymentReq.setGoodsName(Base64Utils.encodeToString(("测试订单" + paymentReq.getMerchOrderNo()).getBytes(UTF_8)));
        paymentReq.setAmount("1");
        paymentReq.setPhoneNo("18058557118");
        paymentReq.setCustomerName(Base64.getEncoder().encodeToString("陈立之".getBytes(UTF_8)));
        paymentReq.setCerdType("01");
        paymentReq.setCerdId("330203198502160614");
        paymentReq.setCardNo("6225768709510927");
        paymentReq.setCvn2("187");
        paymentReq.setValidDate("1906");
        System.out.println(shangqiApi.paymentReq(paymentReq));
    }

    @Test
    public void sign() {
        String stringToSign = "11111111111111111111111111111111account_code8989898989898account_name89898989account_type1bank_account_id320621199022587892bank_branch_name中国工商银行股份有限公司北京索家坟支行bank_city北京市bank_code102100023962bank_name中国工商银行bank_province北京notify_phone_no18721370338platform1settle_day3settle_typeTtimestamp20150916135203user_mailchen@163.comuser_name测试实体商户5091601user_phone_no18721370338user_tel18721370338v1.0v_account_type111111111111111111111111111111111";
        String sign = DigestUtils.md5DigestAsHex(stringToSign.getBytes(UTF_8)).toUpperCase();
        System.out.println(sign);
        System.out.println("557166F286E06B9450F83A59040D0A37".equals(sign));
    }

    @Configuration
    protected static class TestConfig {

        @Bean
        public ShangqiApi shangqiApi(RestOperations restOperations) throws Exception {
            ShangqiApi api = new ShangqiApi();
            api.setRestOperations(restOperations);
            api.setShangqiProperties(shangqiProperties());
            api.setObjectMapper(objectMapper());
            return api;
        }

        private ShangqiProperties shangqiProperties() throws Exception {
            ShangqiProperties properties = new ShangqiProperties();
            properties.setAgentCode("372155");
            properties.setIntMhtCode("037290058120162");
            properties.setSecret("5F99C90453545B47E050007F0100A09C");
            properties.setBackEndUrl("http://ylpay.ylfin.com/cashier-web/web/callback/shangqi/async");
            properties.setFrontEndUrl("http://ylpay.ylfin.com/cashier-web/web/callback/shangqi/sync");
            properties.setApiGateway(new URI("http://211.152.37.242:8091/posnc-api/api"));
            return properties;
        }

        private ObjectMapper objectMapper() throws Exception {
            Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
            return builder.build();
        }
    }
}