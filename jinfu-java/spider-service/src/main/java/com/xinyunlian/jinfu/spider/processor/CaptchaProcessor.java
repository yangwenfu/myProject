package com.xinyunlian.jinfu.spider.processor;

import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.spider.dto.HttpContent;
import com.xinyunlian.jinfu.spider.enums.EHttpMethod;
import com.xinyunlian.jinfu.spider.util.ChaoJiYing;
import com.xinyunlian.jinfu.spider.util.Crawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by bright on 2016/12/29.
 */
public class CaptchaProcessor implements Processor {
    public static final Logger LOGGER = LoggerFactory.getLogger(CaptchaProcessor.class);

    private static final String CHAOJIYING_CDOE_TYPE = "4005";

    private static final String CHAOJIYING_MIN_LENGTH = "4";

    @Override
    public String process(byte[] data, Charset charset, String config, Map<String, String> context, Object cra) {
        Crawler crawler = (Crawler)cra;
        Boolean isSuccess = null;
        try {
            Document doc = Jsoup.parse(new String(data,charset));
            Element e = doc.getElementById(config);
            String href =  e.absUrl("src");
            HttpContent hc = crawler.getPage(href,null, EHttpMethod.GET);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(hc.getData())); //读取连接的流，赋值给BufferedImage对象
            ByteArrayOutputStream img = new ByteArrayOutputStream();
            ImageIO.write(image, "png",img);
            isSuccess = getChaojiyingCode(img,context);
        } catch (Exception e){
            LOGGER.error("内容解析失败, 内容为{}, 解析脚本为{}", new String(data), config, e);
            throw new BizServiceException(EErrorCode.CRAWLER_CLIMB_FAILURE, "内容解析失败", e);
        }
        return isSuccess.toString();
    }

    private Boolean getChaojiyingCode(ByteArrayOutputStream img, Map<String, String> context){
        //调用第三方验证码解析接口
        String yzm_err_no = null;
        int yzm_post_time = 0;
        String yzmCode = null;
        while (!"0".equals(yzm_err_no) && yzm_post_time < 3) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start("超级鹰验证码接口");
            String res = ChaoJiYing.PostPic(CHAOJIYING_CDOE_TYPE, CHAOJIYING_MIN_LENGTH, img.toByteArray());
            stopWatch.stop();
            LOGGER.debug(stopWatch.prettyPrint());

            LOGGER.debug("yzm res : " + res);
            com.alibaba.fastjson.JSONObject codeResult = com.alibaba.fastjson.JSONObject.parseObject(res);
            yzm_err_no = codeResult.getString("err_no");
            yzmCode = codeResult.getString("pic_str");
            yzm_post_time++;
        }
        if(!StringUtils.isEmpty(yzmCode)){
            context.put("confirmpassword",yzmCode);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


}
