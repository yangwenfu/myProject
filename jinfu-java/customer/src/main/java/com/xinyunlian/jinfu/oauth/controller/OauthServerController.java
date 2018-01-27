package com.xinyunlian.jinfu.oauth.controller;

import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.oauth.dto.B2bOauthToken;
import com.xinyunlian.jinfu.oauth.service.OauthServerService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by JL on 2016/10/24.
 */
@RequestMapping(value = "oauthServer")
@Controller
public class OauthServerController {

    private final static Logger LOGGER = LoggerFactory.getLogger(OauthServerController.class);

    @Autowired
    private StoreService storeService;

    @Autowired
    private OauthServerService oauthServerService;

    @Autowired
    private RedisCacheManager cacheManager;

    @RequestMapping("forward_{clientKey}")
    public String forward(@PathVariable(value = "clientKey") String clientKey) {
        return "redirect:" + oauthServerService.getClientAuthorizeUrl(clientKey);
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    public ModelAndView authorize(HttpServletRequest request) {
        try {
            // 构建OAuth请求
            OAuthAuthzRequest oAuthzRequest = new OAuthAuthzRequest(request);
            // 获取OAuth客户端Id
            String clientId = oAuthzRequest.getClientId();
            // 获取客户端重定向地址
            String redirectURI = oAuthzRequest.getParam(OAuth.OAUTH_REDIRECT_URI);

            // 校验客户端Id是否正确
            if (!oauthServerService.checkClientId(clientId)) {
                throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "invalid client Id");
            }

            if (redirectURI == null) {
                LOGGER.info("redirect_uri cannot be null.");
                throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "redirect_uri cannot be null");
            }

            //获得当前用户的店铺信息列表
            List<StoreInfDto> storeInfDtos = storeService.findByUserId(SecurityContext.getCurrentUserId());
            String tobaccoNo = "NoStore";
            if (!storeInfDtos.isEmpty()) {
                tobaccoNo = storeInfDtos.get(0).getTobaccoCertificateNo();
            }
            // 生成授权码
            String authCode;
            String responseType = oAuthzRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            // ResponseType仅支持CODE
            if (responseType.equals(ResponseType.CODE.toString())) {
                authCode = oauthServerService.createOauth2Code(tobaccoNo);
            } else {
                LOGGER.info("Not support response type " + responseType);
                throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "unsupport oauth response type: " + responseType);
            }

            return new ModelAndView("redirect:" + redirectURI + (redirectURI.indexOf("?") > 0 ? "&" : "?")
                    + OAuth.OAUTH_CODE + "=" + authCode);

        } catch (OAuthSystemException | OAuthProblemException e) {
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "operation failure. " + e.getMessage());
        }

    }

    /**
     * 获得accessToken
     *
     * @param clientId
     * @param code
     * @param clientSecret
     * @param grantType
     * @return
     */
    @RequestMapping(value = "/access_token", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public B2bOauthToken accessToken(@RequestParam("client_id") String clientId, String code, @RequestParam("client_secret") String clientSecret, @RequestParam("grant_type") String grantType) {

        if (!oauthServerService.checkClientId(clientId)) {
            return new B2bOauthToken(null, null, OAuthError.TokenResponse.INVALID_CLIENT, "Invalid client " + clientId);
        }

        if (!oauthServerService.checkClientSecret(clientId, clientSecret)) {
            return new B2bOauthToken(null, null, OAuthError.TokenResponse.UNAUTHORIZED_CLIENT, "Unauthorized client " + clientId);
        }

        if (StringUtils.isBlank(code)) {
            return new B2bOauthToken(null, null, OAuthError.TokenResponse.INVALID_GRANT, "Code can't be empty. ");
        }

        if (!GrantType.AUTHORIZATION_CODE.toString().equals(grantType)) {
            return new B2bOauthToken(null, null, OAuthError.TokenResponse.UNSUPPORTED_GRANT_TYPE, "Unsupported grant type: " + grantType);
        }
        Cache cache = cacheManager.getCache(CacheType.OAUTH);
        String tobaccoNo = cache.get(code, String.class);
        cache.evict(code);

        if (StringUtils.isBlank(tobaccoNo)) {
            return new B2bOauthToken(null, null, OAuthError.TokenResponse.INVALID_GRANT, "Invaild code. ");
        }

        String accessToken = oauthServerService.createOauth2Token(tobaccoNo);
        return new B2bOauthToken(accessToken, null, null);

    }


    /**
     * 获得用户信息
     *
     * @param access_token
     * @return
     */
    @RequestMapping(value = "/user_profile")
    @ResponseBody
    public String userProfile(String access_token) {
        Cache cache = cacheManager.getCache(CacheType.OAUTH);
        String tobaccoNo = cache.get(access_token, String.class);
        cache.evict(access_token);
        return tobaccoNo;
    }
}
