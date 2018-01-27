package com.xinyunlian.jinfu.oauth.dto;

import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.ApplicationContextUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.insurance.dto.AioInsOrderReq;
import com.xinyunlian.jinfu.insurance.service.AioInsService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;

/**
 * Created by jl062 on 2017/2/15.
 */
public class AioOauthServerInfoForStoreIns extends AioOauthServerInfo {

    private final static String AIO_CALLBACK_URL = AppConfigUtil.getConfig("local_domain") + "/jinfu/web/oauthClient/callback_aio_storeIns";


    private AioInsOrderReq insOrderReq = new AioInsOrderReq();

    public AioOauthServerInfoForStoreIns(String target) {
        super(target);
    }


    @Override
    public StoreInfDto getStoreInfo(String userInfoStr) {
        AioUserInfoDto userInfoDto = JsonUtil.toObject(AioUserInfoDto.class, userInfoStr);
        StoreInfDto storeInfDto = userInfoDto.getStoreInfo();
        // build保险入参
        insOrderReq.setContactName(userInfoDto.getName());
        insOrderReq.setContactMobile(storeInfDto.getMobile());
        insOrderReq.setStoreName(storeInfDto.getStoreName());
        insOrderReq.setTobaccoCertificateNo(storeInfDto.getTobaccoCertificateNo());
        insOrderReq.setProvince(storeInfDto.getProvince());
        insOrderReq.setCity(storeInfDto.getCity());
        insOrderReq.setTown(storeInfDto.getArea());
        insOrderReq.setDetailAddress(storeInfDto.getAddress());
        return storeInfDto;
    }

    @Override
    public String getRedirectUrl() {
        AioInsService insService = ApplicationContextUtil.getBean(AioInsService.class);
        return insService.getPinganUrl(insOrderReq);
    }

    @Override
    public String getActiveRedirectUrl() {
        return this.getRedirectUrl();
    }


    @Override
    public String getCallbackUrlStr() {
        return AIO_CALLBACK_URL;
    }
}
