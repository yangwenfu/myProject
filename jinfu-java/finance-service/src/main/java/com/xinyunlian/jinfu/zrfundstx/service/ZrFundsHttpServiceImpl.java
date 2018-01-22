package com.xinyunlian.jinfu.zrfundstx.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.fintxhistory.enums.EShareClass;
import com.xinyunlian.jinfu.fintxhistory.enums.ETradeBusinType;
import com.xinyunlian.jinfu.zrfundstx.dto.*;
import com.xinyunlian.jinfu.zrfundstx.enums.EBusinType;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by dongfangchao on 2016/11/30/0030.
 */
@Service
public class ZrFundsHttpServiceImpl implements ZrFundsHttpService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ZrFundsHttpService.class);

    @Value("${zrfunds.url}")
    private String zrfundsUrl;
    @Value("${zrfunds.version}")
    private String zrfundsVersion;
    @Value("${zrfunds.distributor.code}")
    private String distributorCode;
    @Autowired
    private RestTemplate simpleRestTemplate;
    @Value("${zrfunds.merchant.id}")
    private String merchantId;
    @Value("${zrfunds.https}")
    private Boolean https;

    @Override
    public NormOpenAccResp openAcc(NormOpenAccReq req, String salt)throws BizServiceException {
        NormOpenAccResp resp = new NormOpenAccResp();

        req.setVersion(zrfundsVersion);
        req.setMerchantId(merchantId);
        req.setBusinType(EBusinType.openAccount.getCode());
        req.setDistributorCode(distributorCode);

        ResponseEntity<String> responseEntity = okHttpPost(req, salt, "开户");

        if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK){
            LOGGER.debug("用户开户失败!");
            return null;
        }

        String responseBody = responseEntity.getBody();
        LOGGER.debug("没有解析之前的消息对象：");
        LOGGER.debug(responseBody);
        if (!StringUtils.isEmpty(responseBody)){
            try {
                InputStream is = new ByteArrayInputStream(responseBody.getBytes("UTF-8"));
                StreamSource streamSource = new StreamSource(is);

                NormOpenAccRespMsg msg = unmarshaller(streamSource, new NormOpenAccRespMsg());

                String signature = msg.getSignature();

                //验签
                boolean verifySuccess = verifySign(signature,  responseBody, salt, "开户");
                if (!verifySuccess){
                    return null;
                }

                resp = msg.getNormOpenAccRespBody().getNormOpenAccResp();
            } catch (Exception e) {
                LOGGER.error("开户异常", e);
                return null;
            }
        }

        return resp;
    }

    @Override
    public NormApplyPurResp applyPurchase(NormApplyPurReq req, String salt)throws BizServiceException{
        NormApplyPurResp resp = new NormApplyPurResp();

        req.setVersion(zrfundsVersion);
        req.setBusinType(EBusinType.buyApply.getCode());
        req.setMerchantId(merchantId);
        req.setDistributorCode(distributorCode);
        req.setShareClass(EShareClass.BEFORE_PAID.getValue());
        req.setTradeBusinType(ETradeBusinType.SUPER_CASH.getValue());

        ResponseEntity<String> responseEntity = okHttpPost(req, salt, "申购");

        if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK){
            LOGGER.debug("申购失败!");
            return null;
        }

        String responseBody = responseEntity.getBody();
        LOGGER.debug("没有解析之前的消息对象：");
        LOGGER.debug(responseBody);
        if (!StringUtils.isEmpty(responseBody)){
            try {
                InputStream is = new ByteArrayInputStream(responseBody.getBytes("UTF-8"));
                StreamSource streamSource = new StreamSource(is);

                NormApplyPurRespMsg msg = unmarshaller(streamSource, new NormApplyPurRespMsg());

                String signature = msg.getSignature();

                //验签
                boolean verifySuccess = verifySign(signature, responseBody, salt, "申购");
                if (!verifySuccess){
                    return null;
                }

                resp = msg.getNormApplyPurRespBody().getNormApplyPurResp();
            } catch (Exception e) {
                LOGGER.error("申购异常", e);
                return null;
            }
        }

        return resp;
    }

    @Override
    public SuperCashQueryShareResp querySuperCashShare(SuperCashQueryShareReq req, String salt) throws BizServiceException{
        SuperCashQueryShareResp resp = new SuperCashQueryShareResp();

        req.setVersion(zrfundsVersion);
        req.setMerchantId(merchantId);
        req.setDistributorCode(distributorCode);
        req.setBusinType(EBusinType.staticShareQuerySuper.getCode());

        ResponseEntity<String> responseEntity = okHttpPost(req, salt, "超级现金宝份额查询");

        if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK){
            LOGGER.debug("查询超级现金宝份额失败!");
            return null;
        }

        String responseBody = responseEntity.getBody();
        LOGGER.debug("没有解析之前的消息对象：");
        LOGGER.debug(responseBody);
        if (!StringUtils.isEmpty(responseBody)){
            try {
                InputStream is = new ByteArrayInputStream(responseBody.getBytes("UTF-8"));
                StreamSource streamSource = new StreamSource(is);

                SuperCashQueryShareRespMsg msg = unmarshaller(streamSource, new SuperCashQueryShareRespMsg());

                String signature = msg.getSignature();

                //验签
                boolean verifySuccess = verifySign(signature, responseBody, salt, "超级现金宝份额查询");
                if (!verifySuccess){
                    return null;
                }

                resp = msg.getSuperCashQueryShareRespBody().getSuperCashQueryShareResp();
            } catch (Exception e) {
                LOGGER.error("超级现金宝份额查询异常", e);
                return null;
            }
        }

        return resp;
    }

    @Override
    public RedeemRealTimeResp redeemRealTime(RedeemRealTimeReq req, String salt) throws BizServiceException{
        RedeemRealTimeResp resp = new RedeemRealTimeResp();

        req.setVersion(zrfundsVersion);
        req.setMerchantId(merchantId);
        req.setDistributorCode(distributorCode);
        req.setBusinType(EBusinType.realTimeRedeemApply.getCode());
        req.setTradeBusinType(ETradeBusinType.SUPER_CASH.getValue());

        ResponseEntity<String> responseEntity = okHttpPost(req, salt, "实时赎回");

        if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK){
            LOGGER.debug("实时赎回失败!");
            return null;
        }

        String responseBody = responseEntity.getBody();
        LOGGER.debug("没有解析之前的消息对象：");
        LOGGER.debug(responseBody);
        if (!StringUtils.isEmpty(responseBody)){
            try {
                InputStream is = new ByteArrayInputStream(responseBody.getBytes("UTF-8"));
                StreamSource streamSource = new StreamSource(is);

                RedeemRealTimeRespMsg msg = unmarshaller(streamSource, new RedeemRealTimeRespMsg());

                String signature = msg.getSignature();

                //验签
                boolean verifySuccess = verifySign(signature, responseBody, salt, "实时赎回");
                if (!verifySuccess){
                    return null;
                }

                resp = msg.getRedeemRealTimeRespBody().getRedeemRealTimeResp();
            } catch (Exception e) {
                LOGGER.error("实时赎回异常", e);
                return null;
            }
        }

        return resp;
    }

    @Override
    public RedeemNormResp redeemNormal(RedeemNormReq req, String salt) throws BizServiceException{
        RedeemNormResp resp = new RedeemNormResp();

        req.setVersion(zrfundsVersion);
        req.setMerchantId(merchantId);
        req.setDistributorCode(distributorCode);
        req.setBusinType(EBusinType.redeemApply.getCode());
        req.setShareClass(EShareClass.BEFORE_PAID.getValue());
        req.setTradeBusinType(ETradeBusinType.SUPER_CASH.getValue());

        ResponseEntity<String> responseEntity = okHttpPost(req, salt, "普通赎回");

        if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK){
            LOGGER.debug("普通赎回失败!");
            return null;
        }

        String responseBody = responseEntity.getBody();
        LOGGER.debug("没有解析之前的消息对象：");
        LOGGER.debug(responseBody);
        if (!StringUtils.isEmpty(responseBody)){
            try {
                InputStream is = new ByteArrayInputStream(responseBody.getBytes("UTF-8"));
                StreamSource streamSource = new StreamSource(is);

                RedeemNormRespMsg msg = unmarshaller(streamSource, new RedeemNormRespMsg());

                String signature = msg.getSignature();

                //验签
                boolean verifySuccess = verifySign(signature, responseBody, salt, "普通赎回");
                if (!verifySuccess){
                    return null;
                }

                resp = msg.getRedeemNormRespBody().getRedeemNormResp();
            } catch (Exception e) {
                LOGGER.error("普通赎回异常", e);
                return null;
            }
        }

        return resp;
    }

    @Override
    public SuperCashQnResp queryQuotation(SuperCashQnReq req, String salt) throws BizServiceException {
        SuperCashQnResp resp = new SuperCashQnResp();

        req.setVersion(zrfundsVersion);
        req.setBusinType(EBusinType.productInfoQuerySuper.getCode());
        req.setMerchantId(merchantId);
        req.setDistributorCode(distributorCode);

        ResponseEntity<String> responseEntity = okHttpPost(req, salt, "行情查询");

        if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK){
            LOGGER.debug("行情查询失败!");
            return null;
        }

        String responseBody = responseEntity.getBody();
        LOGGER.debug("没有解析之前的消息对象：");
        LOGGER.debug(responseBody);
        if (!StringUtils.isEmpty(responseBody)){
            try {
                InputStream is = new ByteArrayInputStream(responseBody.getBytes("UTF-8"));
                StreamSource streamSource = new StreamSource(is);

                SuperCashQnRespMsg msg = unmarshaller(streamSource, new SuperCashQnRespMsg());

                String signature = msg.getSignature();

                //验签
                boolean verifySuccess = verifySign(signature, responseBody, salt, "行情查询");
                if (!verifySuccess){
                    return null;
                }

                resp = msg.getSuperCashQnRespBody().getSuperCashQnResp();
            } catch (Exception e) {
                LOGGER.error("行情查询异常", e);
                return null;
            }
        }

        return resp;
    }

    @Override
    public BankSignApplyResp applyBankSign(BankSignApplyReq req, String salt) throws BizServiceException {
        BankSignApplyResp resp = new BankSignApplyResp();

        req.setVersion(zrfundsVersion);
        req.setBusinType(EBusinType.bankBgSign.getCode());
        req.setMerchantId(merchantId);
        req.setDistributorCode(distributorCode);

        ResponseEntity<String> responseEntity = okHttpPost(req, salt, "银行后台签约申请");

        if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK){
            LOGGER.debug("银行后台签约申请失败!");
            return null;
        }

        String responseBody = responseEntity.getBody();
        LOGGER.debug("没有解析之前的消息对象：");
        LOGGER.debug(responseBody);

        if (!StringUtils.isEmpty(responseBody)){
            try {
                InputStream is = new ByteArrayInputStream(responseBody.getBytes("UTF-8"));
                StreamSource streamSource = new StreamSource(is);

                BankSignApplyRespMsg msg = unmarshaller(streamSource, new BankSignApplyRespMsg());

                LOGGER.debug("中融返回的消息：");
                LOGGER.debug(JsonUtil.toJson(msg));

                String signature = msg.getSignature();

                //验签
                boolean verifySuccess = verifySign(signature, responseBody, salt, "银行后台签约申请");
                if (!verifySuccess){
                    return null;
                }

                resp = msg.getBankSignApplyRespBody().getBankSignApplyResp();
            } catch (Exception e) {
                LOGGER.error("银行后台签约申请异常", e);
                return null;
            }
        }

        return resp;
    }

    @Override
    public SuperCashTradeQueryResp querySuperCashTrade(SuperCashTradeQueryReq req, String salt) throws BizServiceException {
        SuperCashTradeQueryResp resp = new SuperCashTradeQueryResp();

        req.setVersion(zrfundsVersion);
        req.setBusinType(EBusinType.transResultQuerySuper.getCode());
        req.setMerchantId(merchantId);
        req.setDistributorCode(distributorCode);

        ResponseEntity<String> responseEntity = okHttpPost(req, salt, "超级现金宝交易结果查询");

        if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK){
            LOGGER.debug("超级现金宝交易结果查询失败!");
            return null;
        }

        String responseBody = responseEntity.getBody();
        LOGGER.debug("没有解析之前的消息对象：");
        LOGGER.debug(responseBody);

        if (!StringUtils.isEmpty(responseBody)){
            try {
                InputStream is = new ByteArrayInputStream(responseBody.getBytes("UTF-8"));
                StreamSource streamSource = new StreamSource(is);

                SuperCashTradeQueryRespMsg msg = unmarshaller(streamSource, new SuperCashTradeQueryRespMsg());

                LOGGER.debug("中融返回的消息：");
                LOGGER.debug(JsonUtil.toJson(msg));

                String signature = msg.getSignature();

                //验签
                boolean verifySuccess = verifySign(signature, responseBody, salt, "超级现金宝交易结果查询");
                if (!verifySuccess){
                    return null;
                }

                resp = msg.getSuperCashTradeQueryRespBody().getSuperCashTradeQueryResp();
            } catch (Exception e) {
                LOGGER.error("超级现金宝交易结果查询异常", e);
                return null;
            }
        }

        return resp;
    }

    /**
     * 调用restTemplate的post方法进行请求
     * @param req
     * @param salt
     * @param biz
     * @return
     */
    private ResponseEntity<String> post(BaseReq req, String salt, String biz){
        ResponseEntity<String> responseEntity = null;
        try {
            Map<String, String> requestBody = JsonUtil.toMap(req);

            Map<String, String> tmpRequestBody = new HashMap<>();
            Iterator<String> it = requestBody.keySet().iterator();
            while (it.hasNext()){
                String key = it.next();
                Object value = requestBody.get(key);
                key = StringUtils.capitalize(key);
                tmpRequestBody.put(key, value.toString());
            }

            //构造签名
            Map<String, String> sortedRequestBody = SortUtils.sortFieldString(tmpRequestBody, true, true);

            String params = UrlUtils.generateUrlParam(sortedRequestBody);
            String mySign = EncryptUtil.encryptMd5(params + "&key=" +salt).toUpperCase();

            //构造请求参数
            sortedRequestBody.put("sign", mySign);
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = MediaType.parseMediaType("application/x-www-form-urlencoded;charset=UTF-8");
            headers.setContentType(mediaType);
            headers.add("Accept", MediaType.APPLICATION_XML.toString());

            HttpEntity<Object> formEntity = new HttpEntity<>(null, headers);

            LOGGER.debug(biz + " 请求参数: "  + zrfundsUrl + "?" + params + "&sign=" + mySign);

            //发送请求
            responseEntity = simpleRestTemplate.postForEntity(zrfundsUrl + "?" + params + "&sign=" + mySign, formEntity, String.class);

            return responseEntity;
        } catch (Exception e) {
            LOGGER.error("http请求异常", e);
            return null;
        }
    }

    /**
     * 调用OKHttp的post方法进行请求
     * @param req
     * @param salt
     * @param biz
     * @return
     */
    private ResponseEntity<String> okHttpPost(BaseReq req, String salt, String biz){
        ResponseEntity<String> responseEntity = null;
        try {
            Map<String, String> requestBody = JsonUtil.toMap(req);

            Map<String, String> tmpRequestBody = new HashMap<>();
            Iterator<String> it = requestBody.keySet().iterator();
            while (it.hasNext()){
                String key = it.next();
                Object value = requestBody.get(key);
                key = StringUtils.capitalize(key);
                tmpRequestBody.put(key, value.toString());
            }

            //构造签名
            Map<String, String> sortedRequestBody = SortUtils.sortFieldString(tmpRequestBody, true, true);

            String params = UrlUtils.generateUrlParam(sortedRequestBody);
            String mySign = EncryptUtil.encryptMd5(params + "&key=" +salt).toUpperCase();

            //构造请求参数
            sortedRequestBody.put("sign", mySign);

            LOGGER.debug(biz + " 请求参数: "  + JsonUtil.toJson(sortedRequestBody));

            //发送请求，暂时没有开启ssl
            String response = OkHttpUtil.postForm(zrfundsUrl, sortedRequestBody, https);
            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

            return responseEntity;
        } catch (Exception e) {
            LOGGER.error("http请求异常", e);
            return null;
        }
    }

    private boolean verifySign(String zrFundsSign, String responseBody, String salt, String biz) throws Exception{

        Map<String, String> responseMap = new HashMap<>();
        SAXReader sax = new SAXReader();
        Document doc = sax.read(new ByteArrayInputStream(responseBody.getBytes("UTF-8")));
        Element root = doc.getRootElement();
        Element response = root.element("Responsebody").element("Response");
        List<Element> list = response.elements();
        for (Element el: list) {
            if (!"assetList".equals(el.getName())){
                responseMap.put(el.getName(), el.getStringValue());
            }
        }

        Map<String, String> sortedMap = SortUtils.sortFieldString(responseMap, true, true);

        String signSrc = UrlUtils.generateUrlParam(sortedMap);
        LOGGER.debug(biz + " 待签名字符串：" + signSrc + "&key=" +salt);
        String jinfuSign = EncryptUtil.encryptMd5(signSrc + "&key=" +salt);
        LOGGER.debug(biz + " 中融的签名：" + zrFundsSign + ";金服的签名：" + jinfuSign.toUpperCase());
        if (!zrFundsSign.equalsIgnoreCase(jinfuSign.toUpperCase())){
            LOGGER.error(biz + " 验签失败！");
            LOGGER.debug("response：" + JsonUtil.toJson(responseBody));
            return false;
        }
        return true;
    }

    private <T> T unmarshaller(Source source, T msg){

        Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
        unmarshaller.setClassesToBeBound(msg.getClass());
        T retMsg = (T)unmarshaller.unmarshal(source);

        return retMsg;
    }

}