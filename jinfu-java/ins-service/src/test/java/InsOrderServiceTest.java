import com.xinyunlian.jinfu.common.util.RSAUtils;
import com.xinyunlian.jinfu.insurance.dto.PerInsuranceInfoDto;
import com.xinyunlian.jinfu.insurance.enums.EInvokerType;
import com.xinyunlian.jinfu.insurance.service.InsuranceOrderService;
import com.xinyunlian.jinfu.zhongan.dto.ZhongAnRequestDto;
import com.zhongan.scorpoin.common.ZhongAnNotifyClient;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dongfangchao on 2016/11/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:jinfu.spring.xml")
public class InsOrderServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsOrderServiceTest.class);

    @Autowired
    private InsuranceOrderService insuranceOrderService;

    @Value("${yljf.zhongan.private.key}")
    private String PRK;

    @Test
    public void expireOrder(){
        insuranceOrderService.updateExpiryInsOrder();
    }

    @Test
    public void getZhonganUrl(){
        String userId = "INS" + Calendar.getInstance().getTimeInMillis();

        ZhongAnRequestDto request = new ZhongAnRequestDto();
        request.setUserID(userId);
        String url = insuranceOrderService.getZhonganUrl(request, EInvokerType.WEB);
        System.out.println("==url==");
        System.out.println(url);
    }

    @Test
    public void getZhonganData(){
        try {
//            String json = "{\"charset\":[\"UTF-8\"],\"sign\":[\"Gb1ucs2HYJssvkeOdM/YENJRJLEmSeRk9FnOroIJvQGH7hJua9xkXIIydTSozHQ0YOYOQEOGfyrtQoKQb7BihQiSRNsJBiw4q2SpfOwDBTFSGhkIhm1JKkcgs3kqxM0yGXP8qVKsEgcQ&#43;fm8SmkG0iUCDZuOjsYncJ7MzF022h0&#61;\"],\"bizContent\":[\"XMQo2L58J0&#43;wbLduxENuq&#43;SjuJLoaST3ltaU3N0XDXdGqO6F3krDWeEHsKJZnQ20bT&#43;gz4r9tdbFBpuCsyEe3ybOc5x&#43;QfI1M7u18NMhHcCBLIkd8BRnr&#43;eVrH24CGtt/BV/XJmNtWMNuCQ2Zr7tB4jiRpj&#43;zwFQwHXbFNMLOYmBvV4xfAVOuD06JXKapGe2fMImkhrz2lzmswjmY5SBQBLYB4Ld78/AYSAuHcfPgzUluEMhoKR&#43;LtYcMgbiavBz&#43;p37jsF6VmTkDHGvurQWID/f&#43;sKci2dIOYFpeCAy6JioVwfA780ou&#43;OVgKZe3Id6NB0/HtLeI4Hsp87ppY27/HZBpQCMuZXv7ZPEXGGkXc296A9XPZfm9mNW0kyihHhyqJGyLq32vPU9INUzQg50bHJ4mGnV4Xmmxln6SAzAz6Gko0rtoHFlgUMSLtPcnX9f/P6iq3SRX/zSKOeGcgbcPtE/iaRpMULoXrcoRN9GBkkuvF&#43;SICzlqNwLN/QomAXGq2AybbaRkMKflMB4K4PtuyybA3Laen7GNbpgzrtclAER29MkwOlLGg87Sym9jFSVY2Vyh/AjlP2fNzISNqsdpthB0w5QFgIYD0CTiPiylzrcsGAgRfdHfyGYM3rkWSiWleuYjC5bn/DWysmn5NjeHVwGzA4zhUUs4ef&#43;szh6sf2Wg3CW0lTm6EOpXRKQvIg9&#43;YfotkTd85XKImrk20ZDHi&#43;lHH1mi4/WRDmgtbkujm6mAunWXQiwJ0aJVfyGOm/hBFQpdyMX9hCL8dslPcnx1PKkmzEEthYtnyTHyvxYaQF6nWnLpNv&#43;wJ9JVEiWHtCdrXjEA2cjW6L06obhJweuAw&#61;&#61;\"],\"format\":[\"json\"],\"signType\":[\"RSA\"],\"serviceName\":[\"promotePolicyNotify\"],\"timestamp\":[\"20170320101155261\"]}";
            Map<String, String[]> map = new HashMap<>();
            String[] cs = {"UTF-8"};
            map.put("charset", cs);
            String[] sign = {"Gb1ucs2HYJssvkeOdM/YENJRJLEmSeRk9FnOroIJvQGH7hJua9xkXIIydTSozHQ0YOYOQEOGfyrtQoKQb7BihQiSRNsJBiw4q2SpfOwDBTFSGhkIhm1JKkcgs3kqxM0yGXP8qVKsEgcQ+fm8SmkG0iUCDZuOjsYncJ7MzF022h0="};
            map.put("sign",sign);
            String[] bizContent = {"XMQo2L58J0+wbLduxENuq+SjuJLoaST3ltaU3N0XDXdGqO6F3krDWeEHsKJZnQ20bT+gz4r9tdbFBpuCsyEe3ybOc5x+QfI1M7u18NMhHcCBLIkd8BRnr+eVrH24CGtt/BV/XJmNtWMNuCQ2Zr7tB4jiRpj+zwFQwHXbFNMLOYmBvV4xfAVOuD06JXKapGe2fMImkhrz2lzmswjmY5SBQBLYB4Ld78/AYSAuHcfPgzUluEMhoKR+LtYcMgbiavBz+p37jsF6VmTkDHGvurQWID/f+sKci2dIOYFpeCAy6JioVwfA780ou+OVgKZe3Id6NB0/HtLeI4Hsp87ppY27/HZBpQCMuZXv7ZPEXGGkXc296A9XPZfm9mNW0kyihHhyqJGyLq32vPU9INUzQg50bHJ4mGnV4Xmmxln6SAzAz6Gko0rtoHFlgUMSLtPcnX9f/P6iq3SRX/zSKOeGcgbcPtE/iaRpMULoXrcoRN9GBkkuvF+SICzlqNwLN/QomAXGq2AybbaRkMKflMB4K4PtuyybA3Laen7GNbpgzrtclAER29MkwOlLGg87Sym9jFSVY2Vyh/AjlP2fNzISNqsdpthB0w5QFgIYD0CTiPiylzrcsGAgRfdHfyGYM3rkWSiWleuYjC5bn/DWysmn5NjeHVwGzA4zhUUs4ef+szh6sf2Wg3CW0lTm6EOpXRKQvIg9+YfotkTd85XKImrk20ZDHi+lHH1mi4/WRDmgtbkujm6mAunWXQiwJ0aJVfyGOm/hBFQpdyMX9hCL8dslPcnx1PKkmzEEthYtnyTHyvxYaQF6nWnLpNv+wJ9JVEiWHtCdrXjEA2cjW6L06obhJweuAw=="};
            map.put("bizContent", bizContent);
            String[] format = {"json"};
            map.put("format", format);
            String[] signType = {"RSA"};
            map.put("signType", signType);
            String[] serviceName = {"promotePolicyNotify"};
            map.put("serviceName", serviceName);
            String[] timestamp = {"20170320101155261"};
            map.put("timestamp", timestamp);
//            System.out.println(JsonUtil.toJson(map));

            ZhongAnNotifyClient notify = new ZhongAnNotifyClient("uat", PRK);
            String e = notify.parseNotifyRequest(map);
            System.out.println("==解析后的对象==");
            System.out.println(e);
        } catch (Exception e) {
            LOGGER.error("error", e);
            e.printStackTrace();
        }
    }

    @Test
    public void urlDecode(){
        try {
            String str = "Gb1ucs2HYJssvkeOdM/YENJRJLEmSeRk9FnOroIJvQGH7hJua9xkXIIydTSozHQ0YOYOQEOGfyrtQoKQb7BihQiSRNsJBiw4q2SpfOwDBTFSGhkIhm1JKkcgs3kqxM0yGXP8qVKsEgcQ&#43;fm8SmkG0iUCDZuOjsYncJ7MzF022h0&#61;";
//            String dd = URLDecoder.decode(str, "UTF-8");
//            String dd = URLEncoder.encode(str, "UTF-8");
            String dd = StringEscapeUtils.unescapeHtml(str);
            System.out.println(dd);
        } catch (Exception e) {
            LOGGER.error("error", e);
            e.printStackTrace();
        }

    }

    @Test
    public void bizDecode(){
        try {
//            String biz = "a5f4b4b217285f4c720878c91c2c15f1c45a32100878bb820a96c938b9e26d4e976ee087cc91249fdb590f1b07b5f19c817215c86f852de210840577299c4793c3f7dfcec3c1f68a4be1a1cfecf3cee828f90d78fabd10cc6e98d6ef532c57b1f0ee84fe6ddc0db7e38161adb510dc51df451468c63e394bbe4b7fb28196b6e991a2b65cd62e8e9cd5ec1e50fbb790224bca47c04f9e563fbde0";
            String biz = "m7dWAvmoBCjnczksnzrYNMWQ/o9gXvcVVp6v7WjRQuuGM4TiwLJ8sMty9XDyzS8KIiEuO2zQUm6YIIRORHbge2+Wnwwj1kTwTpihmisFj0P3P0DDiWDWu/ljnX7Dvy9/MVqdygAPbCX7uYCBNelTJXRFhPgLThB9+RZ71mz9YwI=";
            String prk = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALXCxLIpne+yPVeI\n" +
                    "LqNqjNxM/F8NpEBpRBRDcCoAlZBtU/MWbhrx9ljrQI9vTuG2MEycjakCOPbFBUY4\n" +
                    "fLzp5ylEsKT03CJ+Hvy4PhaUM/Xi1GGEO4lFKwAPdL/8KBoEsElKVLByJE+LsvAi\n" +
                    "A9poFHRBin1iTu8RKLcSOAokn6ALAgMBAAECgYApMjnuv6wCVf29Ryp68311uSVC\n" +
                    "Tg86YiGCDj7v5i4ADCeI4z6VcN4LDVcWq33PfsG3u4wIEG7kz4cAgXEUSFeSCuks\n" +
                    "b84XRVN60QTzsTl5BIyHpaE79Qvako5CtIB8JEcNn0eN4YuhX1KMhudA/GSTQDWY\n" +
                    "/bH6X9k6lyJgfoOdAQJBAOgzTpB7IhnteEiyQiFzBLzjdgnCPwqSv5JlUaFsyngt\n" +
                    "nNkEwNNSyZV9EqV1DZenbOzDW4BWAMM5NJWqmmUvk8sCQQDIY/rKo4rUCCb7bLoK\n" +
                    "xo5lBt+D1YkhMchbis7I+hoTFzOW1BqeWCSJUyG+iPE4JnFPLeXQ0x4L+BW7Kmn5\n" +
                    "5RzBAkEAxXX8NbnAwjPDum589l0NhmQYmSvq2F77Ms+en5wYgiKn45W7NPqOGuYF\n" +
                    "2Va1fGYQpzdqtLRuaZZYNX6jVbEkCQJAEXAZ195pa1AWTFTGz789ju8NLkS7vSa+\n" +
                    "37BxlC2nbTfcpmhotJTJASY5zoHOM+useo5s7EIi4DnDclkbhcJ5QQJAKqLpBoET\n" +
                    "V03DTYGxI/9eBpofhxpltZaK4fnn4ce96lFlINKzwgjHFA2AE/t9xrNJEgoxxFg2\n" +
                    "rNCA95rfnMlVCQ==";
            String dec =
                    new String(RSAUtils.decryptByPrivateKey(biz.getBytes(), prk), "UTF-8");
            System.out.println(dec);
        } catch (Exception e) {
            LOGGER.error("error", e);
            e.printStackTrace();
        }
    }

    @Test
    public void addInsOrder(){
        PerInsuranceInfoDto dto = new PerInsuranceInfoDto();
        dto.setOperatorName("董方超");
        dto.setProductId("S01002");
        dto.setProductName("保骉车险");
        String perInsOrderNo = insuranceOrderService.addInsOrderInfo(dto);

        ZhongAnRequestDto zhongAnRequestDto = new ZhongAnRequestDto();
        zhongAnRequestDto.setUserID(perInsOrderNo);//订单号
        String url = insuranceOrderService.getZhonganUrl(zhongAnRequestDto, EInvokerType.WEB);
        System.out.println("==跳转的url==");
        System.out.println(url);
    }

}
