import com.xinyunlian.jinfu.api.dto.MobileOpenApi;
import com.xinyunlian.jinfu.api.validate.util.SignUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
* UserController Tester.
*
* @author <Authors name>
* @since <pre>���� 30, 2016</pre>
* @version 1.0
*/
public class OpenApiControllerTest {
    @Autowired
    private RedisCacheManager redisCacheManager;
    private String url = "http://183.136.134.207/jinfu/web/open-api/";

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

@Test
public void testOpenApi() throws Exception {
   // String mobile = "13805" + RandomUtil.getNumberStr(6);

    String ret = "";
    SortedMap<String, String> sortedMap = new TreeMap<>();
    String sign ="";
    String json = "";

    MobileOpenApi mobileOpenApiDto = new MobileOpenApi();
    mobileOpenApiDto.setAppId("wechat.key");
    sortedMap = new TreeMap<>();
    sortedMap.put("mobile","13805719250");
    sign = SignUtil.createSign(sortedMap, "QvHUJL7Kn2PE7pqn");
    mobileOpenApiDto.setSign(sign);
    json = JsonUtil.toJson(mobileOpenApiDto);
    Map map = JsonUtil.toMap(JsonUtil.toObject(Object.class,json));


    //验证手机号
    ret = HttpUtilsTest.doPost(url + "user/mobile/verify",json, HttpUtilsTest.UTF8);
    System.out.println(ret);

//    //获取短信
//    ret = HttpUtilsTest.doPost(url + "user/mobile/getCode",json, HttpUtilsTest.UTF8);
//    System.out.println(ret);
//    String code  = RedisUtil.getRu().get("WECHAT_BIND_SMS_13805719250");
//    code = code.substring(7, code.length());
//    System.out.println(code);
//
//    //验证短信
//    mobileOpenApiDto.setVerifyCode(code);
//    sortedMap.put("verifyCode",code);
//    sign =SignUtil.createSign(sortedMap,"123456");
//    mobileOpenApiDto.setSign(sign);
//    json = JsonUtil.toJson(mobileOpenApiDto);
//    ret = HttpUtilsTest.doPost(url + "user/mobile/confirm",json, HttpUtilsTest.UTF8);
//    System.out.println(ret);
//
//    //获取用户详细信息
//    ret = HttpUtilsTest.doPost(url + "user/detail",json, HttpUtilsTest.UTF8);
//    System.out.println(ret);
//
//    //获取店铺
//    ret = HttpUtilsTest.doPost(url + "store/getByMobile",json, HttpUtilsTest.UTF8);
//    System.out.println(ret);
//
//    //银行卡
//    ret = HttpUtilsTest.doPost(url + "bank/card/getByMobile",json, HttpUtilsTest.UTF8);
//    System.out.println(ret);
//
//
//    OpenApiBaseDto openApiBaseDto = new OpenApiBaseDto();
//    openApiBaseDto.setAppId("wechat.key");
//    SortedMap<String, String> sorted = new TreeMap<>();
//    sorted.put("appId","wechat.key");
//    String sign1 =SignUtil.createSign(sorted,"123456");
//    openApiBaseDto.setSign(sign1);
//    String json1 = JsonUtil.toJson(openApiBaseDto);
//
//    //获取银行信息
//    ret = HttpUtilsTest.doPost(url + "bank/list",json1, HttpUtilsTest.UTF8);
//    System.out.println(ret);
//
//    StoreOpenApiDto soreOpenApiDto = new StoreOpenApiDto();
//    soreOpenApiDto.setAppId("wechat.key");
//    soreOpenApiDto.setStoreId(8L);
//    soreOpenApiDto.setQrCodeUrl("www.baidu,com");
//    sortedMap = new TreeMap<>();
//    sortedMap.put("qrCodeUrl","www.baidu,com");
//    sign =SignUtil.createSign(sortedMap,"123456");
//    soreOpenApiDto.setSign(sign);
//    json = JsonUtil.toJson(soreOpenApiDto);
//    //上传云码
//    ret = HttpUtilsTest.doPost(url + "store/qrcode/upload",json, HttpUtilsTest.UTF8);
//    System.out.println(ret);
/*
    CertifyInfoOpenApiDto certifyInfoOpenApiDto = new CertifyInfoOpenApiDto();
    certifyInfoOpenApiDto.setAppId("wechat.key");
    certifyInfoOpenApiDto.setMobile("13552535506");
    certifyInfoOpenApiDto.setBankCardNo("6216261000000000018");
    certifyInfoOpenApiDto.setBankId(1L);
    certifyInfoOpenApiDto.setIdCardNo("341126197709218366");
    certifyInfoOpenApiDto.setBankMobile("13552535506");
    certifyInfoOpenApiDto.setUserName("全渠道");
    sortedMap = new TreeMap<>();
    sortedMap.put("mobile","13552535506");
    sortedMap.put("bankCardNo","6216261000000000018");
    sortedMap.put("idCardNo","341126197709218366");
    sortedMap.put("userName","全渠道");
    sortedMap.put("bankMobile","13552535506");
    sign =SignUtil.createSign(sortedMap,"123456");
    certifyInfoOpenApiDto.setSign(sign);
    json = JsonUtil.toJson(certifyInfoOpenApiDto);
    //实名认证
    ret = HttpUtilsTest.doPost(url + "user/certify",json, HttpUtilsTest.UTF8);
    System.out.println(ret);*/


}
    @Test
    public void testCreateSign() throws Exception {
        SortedMap<String, String> sortedMap = new TreeMap<>();
        MobileOpenApi mobileOpenApiDto = new MobileOpenApi();
        //mobileOpenApiDto.setAppId("dituibao.appId");
       /* sortedMap = new TreeMap<>();
        sortedMap.put("mobile","15722222200");
        System.out.println(SignUtil.createSign(sortedMap, "123456"));

        sortedMap = new TreeMap<>();
        sortedMap.put("mobile","13333333333");
        sortedMap.put("province","江苏省");
        sortedMap.put("city","苏州市");
        sortedMap.put("area","姑苏区");
        sortedMap.put("storeName","店名");
        sortedMap.put("tobaccoCertificateNo","0110456789");
        sortedMap.put("address","测试地址");
        System.out.println(SignUtil.createSign(sortedMap, "123456"));

        sortedMap = new TreeMap<>();
        sortedMap.put("bankCardNo","6213307777888800000");
        System.out.println(SignUtil.createSign(sortedMap, "123456"));

        sortedMap = new TreeMap<>();
        sortedMap.put("mobile","13899999998");
        System.out.println(SignUtil.createSign(sortedMap, "123456"));
*/
        sortedMap = new TreeMap<>();
        sortedMap.put("bankCardName","姓名");
        sortedMap.put("bankCardNo","6213335856666685470");
        sortedMap.put("idCardNo","610502199010016546");
        sortedMap.put("mobileNo","15233663366");
        sortedMap.put("subbranchName","支行名称");
        sortedMap.put("userId","UC0000001948");
        System.out.println(SignUtil.createSign(sortedMap, "123456"));



    }


} 
