package com.xinyunlian.jinfu.alipay.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by menglei on 2016年12月29日.
 */
@Service
public class ApiAliPayServiceImpl implements ApiAliPayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiAliPayServiceImpl.class);

    @Value("${alipay.appid}")
    private String APP_ID;

    @Value("${alipay.rsa2.private.key}")
    private String APP_PRIVATE_KEY;//私钥

    @Value("${alipay.rsa2.public.key}")
    private String APP_PUBLIC_KEY;//公钥

    @Value("${alipay.rsa2.alipay.public.key}")
    private String ALIPAY_PUBLIC_KEY;//公钥

    private final static String
            API_ALIPAY_URL = "https://openapi.alipay.com",
            OPEN_ALIPAY_URL = "https://openauth.alipay.com";

    /**
     * 获取code的url，下一步获取userid
     */
    @Override
    @Transactional
    public String getAuthCodeUrl(String uri, String state) {
        String auth_url = null;
        try {
            auth_url = OPEN_ALIPAY_URL + "/oauth2/publicAppAuthorize.htm?"
                    + "app_id=" + APP_ID + "&scope=auth_base" + "&redirect_uri=" + URLEncoder.encode(uri, "UTF-8") + "&state=" + URLEncoder.encode(state, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取code的url失败", e);
        }
        return auth_url;
    }

    /**
     * 通过code获取userid
     */
    @Override
    @Transactional
    public String getAuthUserid(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "通过code获取userid失败:code不能为空");
        }
        AlipayClient alipayClient = new DefaultAlipayClient(API_ALIPAY_URL + "/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", AlipayConstants.CHARSET_UTF8, ALIPAY_PUBLIC_KEY, "RSA2");
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(code);
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            return oauthTokenResponse.getUserId();
        } catch (AlipayApiException e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "通过code获取openid失败", e);
        }
    }

}
