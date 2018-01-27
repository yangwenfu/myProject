import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.spider.dto.HttpContent;
import com.xinyunlian.jinfu.spider.dto.req.AuthLoginDto;
import com.xinyunlian.jinfu.spider.enums.EHttpMethod;
import com.xinyunlian.jinfu.spider.service.EnterpriseInfoService;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import com.xinyunlian.jinfu.spider.util.Crawler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by bright on 2016/12/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:standard-code-dao-test.xml"})
public class CrawlerTest {
    @Autowired
    private RiskUserInfoService riskUserInfoService;

    @Autowired
    private EnterpriseInfoService enterpriseInfoService;

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Test
    public void testGetIp() throws Exception{
        String proxyIp = redisCacheManager.getCache(CacheType.APPLICATIONAL).get(
                "Spider.ProxyIp", String.class
        );
        String proxyPort = redisCacheManager.getCache(CacheType.APPLICATIONAL).get(
                "Spider.proxyPort", String.class
        );
        String proxyUser = redisCacheManager.getCache(CacheType.APPLICATIONAL).get(
                "Spider.proxyUser", String.class
        );
        String proxyPass = redisCacheManager.getCache(CacheType.APPLICATIONAL).get(
                "Spider.proxyPass", String.class
        );
        Crawler crawler = new Crawler(proxyIp, proxyPort, proxyUser, proxyPass);
        HttpContent content = crawler.getPage("http://httpbin.org/ip", null, EHttpMethod.GET);
        String resp = new String(content.getData(), content.getCharset());
        System.out.println(resp);
    }

    @Test
    public void testSingleLogin() {
        String testAccountJson = "{\n" +
                "\t\t\"userId\": \"697\",\n" +
                "\t\t\"username\": \"330204111925\",\n" +
                "\t\t\"password\": \"15158338751\",\n" +
                "\t\t\"cityId\": 941\n" +
                "\t}";
        AuthLoginDto authLoginDto = JsonUtil.toObject(AuthLoginDto.class, testAccountJson);
        riskUserInfoService.authLogin(authLoginDto);
    }

    @Test
    public void testSingleClimb(){   //  1234567   13254678
        riskUserInfoService.spiderUserInfo("697");
    }

    @Test
    public void testGetMethod(){

    }

    @Test
    public void testMultiLogin() {
        String testAccountsJson = "[{\n" +
                "\t\t\"userId\": \"TEST001\",\n" +
                "\t\t\"username\": \"370202200084\",\n" +
                "\t\t\"password\": \"1\",\n" +
                "\t\t\"provinceId\": 1357,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST002\",\n" +
                "\t\t\"username\": \"370781100722\",\n" +
                "\t\t\"password\": \"1\",\n" +
                "\t\t\"provinceId\": 1357,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST003\",\n" +
                "\t\t\"username\": \"460330209503\",\n" +
                "\t\t\"password\": \"175643\",\n" +
                "\t\t\"provinceId\": 2213,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST004\",\n" +
                "\t\t\"username\": \"460330004103\",\n" +
                "\t\t\"password\": \"725390\",\n" +
                "\t\t\"provinceId\": 2213,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST005\",\n" +
                "\t\t\"username\": \"460330111964\",\n" +
                "\t\t\"password\": \"548609\",\n" +
                "\t\t\"provinceId\": 2213,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST006\",\n" +
                "\t\t\"username\": \"610301197107\",\n" +
                "\t\t\"password\": \"316767\",\n" +
                "\t\t\"provinceId\": 3321,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST007\",\n" +
                "\t\t\"username\": \"610113126563\",\n" +
                "\t\t\"password\": \"679244\",\n" +
                "\t\t\"provinceId\": 3321,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST008\",\n" +
                "\t\t\"username\": \"610115103340\",\n" +
                "\t\t\"password\": \"000000\",\n" +
                "\t\t\"provinceId\": 3321,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST009\",\n" +
                "\t\t\"username\": \"621002101229\",\n" +
                "\t\t\"password\": \"863\",\n" +
                "\t\t\"provinceId\": 2925,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST010\",\n" +
                "\t\t\"username\": \"620902103920\",\n" +
                "\t\t\"password\": \"918018\",\n" +
                "\t\t\"provinceId\": 2925,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST011\",\n" +
                "\t\t\"username\": \"620423101448\",\n" +
                "\t\t\"password\": \"224983\",\n" +
                "\t\t\"provinceId\": 2925,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST012\",\n" +
                "\t\t\"username\": \"500104102305\",\n" +
                "\t\t\"password\": \"102305\",\n" +
                "\t\t\"provinceId\": 3335,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST013\",\n" +
                "\t\t\"username\": \"500106101134\",\n" +
                "\t\t\"password\": \"123456\",\n" +
                "\t\t\"provinceId\": 3335,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST014\",\n" +
                "\t\t\"username\": \"500111100467\",\n" +
                "\t\t\"password\": \"210445\",\n" +
                "\t\t\"provinceId\": 3335,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST015\",\n" +
                "\t\t\"username\": \"511902109006\",\n" +
                "\t\t\"password\": \"008198\",\n" +
                "\t\t\"provinceId\": 2279,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST016\",\n" +
                "\t\t\"username\": \"411425104462\",\n" +
                "\t\t\"password\": \"631898\",\n" +
                "\t\t\"provinceId\": null,\n" +
                "\t\t\"cityId\": 1648,\n" +
                "\t\t\"areaId\": null\n" +
                "\t} ]\n";
        List<AuthLoginDto> dtos = JsonUtil.toObject(List.class, AuthLoginDto.class, testAccountsJson);
        dtos.forEach(authLoginDto -> {
            riskUserInfoService.authLogin(authLoginDto);
        });
    }

    @Test
    public void testMultiClimb() {
        String testAccountsJson = "[{\n" +
                "\t\t\"userId\": \"TEST001\",\n" +
                "\t\t\"username\": \"370202200084\",\n" +
                "\t\t\"password\": \"1\",\n" +
                "\t\t\"provinceId\": 1357,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST002\",\n" +
                "\t\t\"username\": \"370781100722\",\n" +
                "\t\t\"password\": \"1\",\n" +
                "\t\t\"provinceId\": 1357,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST003\",\n" +
                "\t\t\"username\": \"460330209503\",\n" +
                "\t\t\"password\": \"175643\",\n" +
                "\t\t\"provinceId\": 2213,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST004\",\n" +
                "\t\t\"username\": \"460330004103\",\n" +
                "\t\t\"password\": \"725390\",\n" +
                "\t\t\"provinceId\": 2213,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST005\",\n" +
                "\t\t\"username\": \"460330111964\",\n" +
                "\t\t\"password\": \"548609\",\n" +
                "\t\t\"provinceId\": 2213,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST007\",\n" +
                "\t\t\"username\": \"610113126563\",\n" +
                "\t\t\"password\": \"679244\",\n" +
                "\t\t\"provinceId\": 3321,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST008\",\n" +
                "\t\t\"username\": \"610115103340\",\n" +
                "\t\t\"password\": \"000000\",\n" +
                "\t\t\"provinceId\": 3321,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST009\",\n" +
                "\t\t\"username\": \"621002101229\",\n" +
                "\t\t\"password\": \"863\",\n" +
                "\t\t\"provinceId\": 2925,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST010\",\n" +
                "\t\t\"username\": \"620902103920\",\n" +
                "\t\t\"password\": \"918018\",\n" +
                "\t\t\"provinceId\": 2925,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST011\",\n" +
                "\t\t\"username\": \"620423101448\",\n" +
                "\t\t\"password\": \"224983\",\n" +
                "\t\t\"provinceId\": 2925,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST012\",\n" +
                "\t\t\"username\": \"500104102305\",\n" +
                "\t\t\"password\": \"102305\",\n" +
                "\t\t\"provinceId\": 3335,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST013\",\n" +
                "\t\t\"username\": \"500106101134\",\n" +
                "\t\t\"password\": \"123456\",\n" +
                "\t\t\"provinceId\": 3335,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST014\",\n" +
                "\t\t\"username\": \"500111100467\",\n" +
                "\t\t\"password\": \"210445\",\n" +
                "\t\t\"provinceId\": 3335,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST015\",\n" +
                "\t\t\"username\": \"511902109006\",\n" +
                "\t\t\"password\": \"008198\",\n" +
                "\t\t\"provinceId\": 2279,\n" +
                "\t\t\"cityId\": null,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}, {\n" +
                "\t\t\"userId\": \"TEST016\",\n" +
                "\t\t\"username\": \"411425104462\",\n" +
                "\t\t\"password\": \"631898\",\n" +
                "\t\t\"provinceId\": null,\n" +
                "\t\t\"cityId\": 1648,\n" +
                "\t\t\"areaId\": null\n" +
                "\t}]\n";
        List<AuthLoginDto> dtos = JsonUtil.toObject(List.class, AuthLoginDto.class, testAccountsJson);
        dtos.forEach(authLoginDto -> {
            try {
                riskUserInfoService.spiderUserInfo(authLoginDto.getUserId());
            } catch (Exception e) {
//                e.printStackTrace();
            }
        });
    }

    @Test
    public void retry()  {
        String userId = "UC0000005009";
        riskUserInfoService.spiderUserInfo(userId);
    }


    @Test
    public void getGrowthRate(){
        Double rate = riskUserInfoService.getGrowthRate("TEST017");
        System.out.println(rate);
    }

    @Test
    public void getOrderAmt(){
        Double orderAmt = riskUserInfoService.getOrderAmtForThisMonth("TEST017");
        System.out.println(orderAmt);
    }
    @Test
    public void testCrawlerEnterpriseInfo(){
        enterpriseInfoService.crawlerEnterpriseInfo("人人乐商店","UC0000044034");
        System.out.println("insert success");
    }


}
