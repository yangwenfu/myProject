package com.xinyunlian.jinfu.spider.service;

import com.alibaba.fastjson.JSONObject;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.spider.dto.HttpContent;
import com.xinyunlian.jinfu.spider.enums.EHttpMethod;
import com.xinyunlian.jinfu.spider.util.Crawler;
import org.apache.commons.collections.map.HashedMap;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/8/15.
 */
public class TestPost {

    public static void main(String[] arags){
        Crawler crawler = new Crawler(null,null,null,null);
         String url1 = "http://center.xinshangmeng.com/st/users/dologin/up"; // 登录页面
         String url2 = "http://ah.tobacco.com.cn:90/websale/authorize.shtml?a=code&xsm_user=SZ13075826";  // 登录1
         String url3 = "http://ah.tobacco.com.cn:90/websale/saleCircs.shtml";
         String url4 = "http://ah.tobacco.com.cn:90/websale/myOrderHis.shtml?a=search";  //查找订单
        // String url5 = "http://ah.tobacco.com.cn:90/websale/e-sales/retailer/saleCigInfoQuery.page";
        HttpContent hc = null;
        String yzm = null;
        try {
            Document doc ;
            Map<String,String > params = new HashedMap();
            params.put("j_username","sz13075826");
            params.put("j_mcmm","b33aecb37588067e2f7e9a6efd016fa7");
            // 登录
            HttpContent login = crawler.getPage(url1,params,EHttpMethod.POST);
            doc = Jsoup.parse(new String(login.getData(), login.getCharset()));

            // 授权
            HttpContent content = crawler.getPage(url2,null,EHttpMethod.GET);

            System.out.println("context data"+content.getData().toString());
            doc = Jsoup.parse(new String(content.getData(), content.getCharset()));
            System.out.println(content.getCharset());

           /* Map<String, String> page = new HashedMap();
            page.put("start","0");
            page.put("limit","25");
            hc =  crawler.getPage(url5,page, EHttpMethod.POST);
            doc = Jsoup.parse(new String(hc.getData(), hc.getCharset()));
            Elements es =  doc.select(".DPYear");
            System.out.println("es:"+es.size());
*/
            //用户信息
           /* hc =  crawler.getPage(url3,null, EHttpMethod.GET);
            doc = Jsoup.parse(new String(hc.getData(), hc.getCharset()));
            Elements esd =  doc.select(".DPYear");
            System.out.println("es:"+esd.size());*/

            // 订单信息
            Map<String, String> times = new HashedMap();
            times.put("startDate","2017-05-17");
            times.put("endDate","2017-08-17");
            hc =  crawler.getPage(url4,times, EHttpMethod.POST);

           Map<String,String> map = JsonUtil.toMap(new String(hc.getData(),hc.getCharset()));
           //String rows = map.get("rows");
           //List<Map<String,String>> ss = map.get("rows");
           // doc = Jsoup.parse(new String(hc.getData(), hc.getCharset()));
          //  Elements ess =  doc.select(".DPYear");
           // System.out.println("es:"+es.size());




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
