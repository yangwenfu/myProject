import com.alibaba.dubbo.common.json.JSONObject;
import com.xinyunlian.jinfu.common.util.RandomUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.Map;

/**
* UserController Tester.
*
* @author <Authors name>
* @since <pre>���� 30, 2016</pre>
* @version 1.0
*/
public class UserControllerTest {
    @Autowired
    private RedisCacheManager redisCacheManager;
    private String url = "http://192.168.93.79:8080/jinfu/web/";

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: register(@RequestBody PasswordDto passwordDto, @RequestParam String verifyCode) 
* 
*/ 
@Test
public void testRegister() throws Exception {
    String mobile = "13805" + RandomUtil.getNumberStr(6);

    String ret = "";

    //验证手机号
    ret = HttpUtilsTest.doGet(url + "register/mobile/verify?mobile=" + mobile,HttpUtilsTest.UTF8);
    System.out.println(ret);
    if(ret.equals("{\"code\":\"NACK\",\"message\":\"手机号不存在\",\"data\":null}") == false){
        throw new Exception("get regiser code error");
    }


    //获取短信
    ret = HttpUtilsTest.doGet(url + "register/mobile/code?mobile=" + mobile + "&type=register",HttpUtilsTest.UTF8);
    System.out.println(ret);
    String code  = RedisUtil.getRu().get("REGISTER_SMS_" + mobile);
    code = code.substring(7, code.length());
    System.out.println(code);

    JSONObject obj = new JSONObject();
    obj.put("mobile", mobile);
    obj.put("newPassword", "12345");
    //注册用户
    ret = HttpUtilsTest.doPost(url + "register/user?verifyCode=" + code,
            "{\"mobile\":"+mobile+",\"newPassword\":\"12345\"}", HttpUtilsTest.UTF8);
    System.out.println(ret);
    if(ret.equals("{\"code\":\"ACK\",\"message\":\"注册成功\",\"data\":null}") == false){
        throw new Exception("get regiser code error");
    }

    //获取短信
    ret = HttpUtilsTest.doGet(url + "register/mobile/code?mobile=" + mobile + "&type=forget",HttpUtilsTest.UTF8);
    System.out.println(ret);
    code  = RedisUtil.getRu().get("FORGET_SMS_" + mobile);
    code = code.substring(7, code.length());
    System.out.println(code);


    ret = HttpUtilsTest.doPost(url + "forget/reset/password?verifyCode=" + code,
            "{\"mobile\":" + mobile + ",\"newPassword\":\"54321\"}", HttpUtilsTest.UTF8);
    System.out.println(ret);
    if(ret.equals("{\"code\":\"ACK\",\"message\":\"修改成功\",\"data\":null}") == false){
        throw new Exception("forget error");
    }

    Map<String,String> map = HttpUtilsTest.doPostCookie(url + "user/auth/login",
            "{\"userName\":"+mobile+",\"password\":\"54321\"}", HttpUtilsTest.UTF8,null);
    ret = map.get("body");
            System.out.println(ret);
    if(ret.equals("{\"code\":\"ACK\",\"message\":\"登录成功\",\"data\":null}") == false){
        throw new Exception("login error");
    }

    ret = HttpUtilsTest.doPostCookie(url + "user/certify",
            "{" +
                    "\"mobile\":" + mobile + "," +
                    "\"userName\":\"sd\"," +
                    "\"idCardNo\":\"231231231313\"," +
                    "\"bankCardNo\":\""+code+"\"," +
                    "\"bankId\":1" +
                    "}",
            HttpUtilsTest.UTF8,map.get("cookie")).get("body");
    System.out.println(ret);
    if(ret.equals("{\"code\":\"ACK\",\"message\":\"认证成功\",\"data\":null}") == false){
        throw new Exception("certify error");
    }


    ret = HttpUtilsTest.doPostCookie(url + "user/store/save",
            "{" +
                    "\"storeName\":\"aa\"," +
                    "\"areaId\":\"1\"," +
                    "\"province\":\"北京\"," +
                    "\"city\":\"北京\"," +
                    "\"area\":\"北京\"," +
                    "\"street\":\"北京\"," +
                    "\"address\":\"广场\"," +
                    "\"tobaccoCertificateNo\":\"23w23\"," +
                    "\"bizLicence\":\"232w32\"," +
                    "\"tobaccoEndDate\":\"2016-09-02\"," +
                    "\"bizEndDate\":\"2016-09-02\"" +
                    "}", HttpUtilsTest.UTF8,map.get("cookie")).get("body");
    System.out.println(ret);
    if(ret.equals("{\"code\":\"ACK\",\"message\":\"添加成功\",\"data\":null}") == false){
        throw new Exception("store error");
    }



   /* ret = HttpUtilsTest.doPostCookie(url + "user/bank/save?verifyCode=",
            "{" +
                    "\"bankCardNo\":\""+code+"\"," +
                    "\"bankCardName\":\"金\"," +
                    "\"mobileNo\":\"23\"," +
                    "\"idCardNo\":\"232\"," +
                    "\"bankId\":1" +
                    "}", HttpUtilsTest.UTF8,map.get("cookie")).get("body");
    System.out.println(ret);
    if(ret.equals("{\"code\":\"ACK\",\"message\":\"认证成功\",\"data\":null}") == false){
        throw new Exception("certify error");
    }*/

} 

/** 
* 
* Method: detail(@RequestParam String mobile) 
* 
*/ 
@Test
public void testDetail() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: verifyMobile(@RequestParam String mobile) 
* 
*/ 
@Test
public void testVerifyMobile() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getVerifyCode(@RequestParam String mobile, @RequestParam String type) 
* 
*/ 
@Test
public void testGetVerifyCode() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: confirmMobile(@RequestParam String mobile, @RequestParam String verifyCode) 
* 
*/ 
@Test
public void testConfirmMobile() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: resetPassword(@RequestBody PasswordDto passwordDto, @RequestParam String verifyCode) 
* 
*/ 
@Test
public void testResetPassword() throws Exception { 
//TODO: Test goes here... 
}


} 
