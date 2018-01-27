package com.xinyunlian.jinfu.oauth.service;

import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.oauth.dto.OauthClientInfo;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JL on 2016/11/7.
 */
public class OauthServerService {
    private final static Logger LOGGER = LoggerFactory.getLogger(OauthServerService.class);

    private Map<String, OauthClientInfo> clients = new HashMap<>();

    @Autowired
    private OAuthIssuerImpl oAuthIssuerImpl;

    @Autowired
    private CacheManager cacheManager;

    public String getClientAuthorizeUrl(String clientId) {
        return clients.get(clientId).getAuthorizeUrl();

    }

    public boolean checkClientId(String clientId) {
        return clients.containsKey(clientId);
    }

    public boolean checkClientSecret(String clientId, String clientSecret) {
        return clients.get(clientId).getSecretKey().equals(clientSecret);
    }


    public String createOauth2Code(String tobaccoNo) {
        try {
            String authCode = oAuthIssuerImpl.authorizationCode();
            cacheManager.getCache(CacheType.OAUTH).put(authCode, tobaccoNo);
            return authCode;
        } catch (OAuthSystemException e) {
            LOGGER.error("Generate oauth code failure. " + e.getMessage(), e);
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "Generate oauth code failure. " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Generate oauth code failure. " + e.getMessage(), e);
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "Generate oauth code failure. " + e.getMessage());
        }
    }

    public String createOauth2Token(String tobaccoNo) {
        try {
            String token = oAuthIssuerImpl.accessToken();
            cacheManager.getCache(CacheType.OAUTH).put(token, tobaccoNo);
            return token;
        } catch (OAuthSystemException e) {
            LOGGER.error("Generate oauth access token failure. " + e.getMessage(), e);
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "Generate oauth access token failure. " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Generate oauth access token failure. " + e.getMessage(), e);
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "Generate oauth access token failure. " + e.getMessage());
        }
    }

    public void setClients(Map<String, OauthClientInfo> clientMap) {
        this.clients = clientMap;
    }
}
