package com.ylfin.wallet.provider.xmccb;

import com.ylfin.core.exception.OuterServiceException;
import com.ylfin.core.exception.SystemException;
import com.ylfin.core.tool.JacksonUtils;
import com.ylfin.core.tool.security.sign.SignatureAlgorithm;
import com.ylfin.core.tool.security.sign.SignatureUtils;
import com.ylfin.wallet.provider.xmccb.consts.XmccbServiceConst;
import com.ylfin.wallet.provider.xmccb.req.OpenAccountReq;
import com.ylfin.wallet.provider.xmccb.req.UserQueryReq;
import com.ylfin.wallet.provider.xmccb.resp.OpenAccountResp;
import com.ylfin.wallet.provider.xmccb.resp.UserQueryResp;
import com.ylfin.wallet.provider.xmccb.resp.XmccbResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class XmccbApiProvider implements XmccbApi, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(XmccbApiProvider.class);

    private static final String DEFAULT_KEY_SERIAL = "1";

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private XmccbProperties xmccbProperties;

    private RestOperations restOperations;

    public XmccbApiProvider(XmccbProperties xmccbProperties) {
        this.xmccbProperties = xmccbProperties;
    }

    public void setRestOperations(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @Override
    public OpenAccountResp openAccount(OpenAccountReq req) {
        return postData(buildRequest(req, XmccbServiceConst.ENTRUST_IMPORT_USER), OpenAccountResp.class);
    }

    @Override
    public UserQueryResp queryUser(UserQueryReq req) {
        return postData(buildRequest(req, XmccbServiceConst.QUERY_USER_INFORMATION), UserQueryResp.class);
    }

    private MultiValueMap<String, String> buildRequest(Object req, String serviceName) {
        String reqData = JacksonUtils.toJson(req);
        String sign = sign(reqData);
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>(5);
        map.add("serviceName", serviceName);
        map.add("platformNo", xmccbProperties.getPlatformNo());
        map.add("reqData", reqData);
        map.add("keySerial", DEFAULT_KEY_SERIAL);
        map.add("sign", sign);

        return map;

//        XmccbRequest request = new XmccbRequest();
//        request.setPlatformNo(xmccbProperties.getPlatformNo());
//        String reqData = JacksonUtil.toJson(req);
//        request.setReqData(reqData);
//        request.setKeySerial(DEFAULT_KEY_SERIAL);
//        request.setServiceName(serviceName);
//        request.setSign(sign(reqData));
//        return request;
    }

    private <E> E postData(MultiValueMap<String, String> xmccbRequest, Class<E> clazz) {
        logger.info("厦门银行钱包接口-请求数据: {}", xmccbRequest.toString());
        ResponseEntity<XmccbResponse> entity = restOperations.postForEntity(xmccbProperties.getService(), xmccbRequest, XmccbResponse.class);
        XmccbResponse body = entity.getBody();
        logger.info("厦门银行钱包接口-返回数据: {}", body.toString());
        if (verifySign(body.getRespData(), body.getSign())) {
            throw new OuterServiceException("签名校验失败", "INVALID_SIGN");
        }
        return null;
    }

    private String sign(String stringToSign) {
        try {
            String sign = Base64.getEncoder().encodeToString(SignatureUtils.sign(stringToSign.getBytes(UTF_8), xmccbProperties.getPrivateKey(),
                    SignatureAlgorithm.SHA1_WITH_RSA));
            logger.info("stringToSign: {}, sign: {}", stringToSign, sign);
            return sign;
        } catch (Exception e) {
            throw new SystemException("签名失败", e);
        }
    }

    private boolean verifySign(String stringToSign, String sign) {
        try {
            return SignatureUtils.verify(stringToSign.getBytes(UTF_8), Base64.getDecoder().decode(sign), xmccbProperties.getPublicKey(),
                    SignatureAlgorithm.SHA1_WITH_RSA);
        } catch (Exception e) {
            throw new SystemException("验签失败", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
