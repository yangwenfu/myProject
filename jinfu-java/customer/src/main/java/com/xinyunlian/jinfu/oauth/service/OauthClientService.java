package com.xinyunlian.jinfu.oauth.service;

import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.entity.id.IdUtil;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.security.authc.ESourceType;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.OkHttpUtil;
import com.xinyunlian.jinfu.oauth.dto.OauthServerInfo;
import com.xinyunlian.jinfu.oauth.dto.OauthToken;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JL on 2016/10/31.
 */
@Service
public class OauthClientService {

    private final static String STORE_INFO_REDEIS_SUFFIX = "_OauthStoreInfo";

    @Autowired
    private UserService userService;

    @Autowired
    private RedisCacheManager cacheManager;


    /**
     * oauth认证
     *
     * @param oauthServerInfo
     * @param attrs
     * @return
     */
    public String authorize(OauthServerInfo oauthServerInfo, RedirectAttributes attrs) {
        attrs.addAttribute("client_id", oauthServerInfo.getClientId());
        attrs.addAttribute("response_type", "code");
        attrs.addAttribute("redirect_uri", oauthServerInfo.getCallbackUrl());
        return oauthServerInfo.getServiceDomain() + oauthServerInfo.getAuthorizeUrl();
    }

    /**
     * oauth回调函数
     *
     * @param oauthServerInfo
     * @param code
     * @param attrs
     * @return
     * @throws IOException
     */
    public String callback(OauthServerInfo oauthServerInfo, String code, RedirectAttributes attrs) throws IOException {
        StoreInfDto storeInfDto = oauthServerInfo.getUserProfile(oauthServerInfo.getToken(code));
        UserInfoDto userInfoDto = userService.getUserInfoByTobaccoNo(storeInfDto.getTobaccoCertificateNo());
        String url;
        if (userInfoDto != null) {
            SecurityContext.login(userInfoDto.getUserId(), ESourceType.JINFU_WEB);
            url = oauthServerInfo.getRedirectUrl();
        } else {
            //跳转到激活页
            String storeNo = IdUtil.produceUUID();
            attrs.addAttribute("storeNo", storeNo);
            cacheManager.getCache(CacheType.DEFAULT).put(storeNo + STORE_INFO_REDEIS_SUFFIX, storeInfDto);
            url = oauthServerInfo.getActiveRedirectUrl();
        }
        return url;
    }

    /**
     * 根据店铺编号获得店铺信息
     *
     * @param storeNo
     * @return
     */
    public StoreInfDto getStoreInfoByStoreNo(String storeNo) {
        return cacheManager.getCache(CacheType.DEFAULT).get(storeNo + STORE_INFO_REDEIS_SUFFIX, StoreInfDto.class);
    }

}
