package com.xinyunlian.jinfu.oauth.controller;

import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.oauth.dto.*;
import com.xinyunlian.jinfu.oauth.service.OauthClientService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by JL on 2016/10/24.
 */
@RequestMapping(value = "oauthClient")
@Controller
public class OauthClientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OauthClientController.class);

    @Autowired
    private OauthClientService oauthClientService;

    /**
     * 新商盟B2B认证
     *
     * @param attrs
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    public String authorize(RedirectAttributes attrs) {
        return "redirect:" + oauthClientService.authorize(new B2BOauthServerInfo(), attrs);
    }


    /**
     * 新商盟B2B回调
     *
     * @param code
     * @return
     * @throws IOException
     */
    @Deprecated
    @RequestMapping(value = "/callback")
    public String callback(String code, RedirectAttributes attrs) throws IOException {
        return "redirect:" + oauthClientService.callback(new B2BOauthServerInfo(), code, attrs);
    }


    /**
     * oauth跳转认证
     *
     * @param attrs
     * @return
     */
    @RequestMapping(value = "/authorize_{serverName}", method = RequestMethod.GET)
    public String authorize(@PathVariable(value = "serverName") String serverName, String target, RedirectAttributes attrs) {
        return "redirect:" + oauthClientService.authorize(getServerInfo(serverName, target), attrs);
    }

    /**
     * oauth回调
     *
     * @param code
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/callback_{serverName}")
    public String callback(@PathVariable(value = "serverName") String serverName, String code, String target, RedirectAttributes attrs) throws IOException {
        Long startTimes = Calendar.getInstance().getTimeInMillis();
        String url =  "redirect:" + oauthClientService.callback(getServerInfo(serverName, target), code, attrs);
        Long endTimes = Calendar.getInstance().getTimeInMillis();
        LOGGER.debug("==oauth回调==" + target);
        LOGGER.debug("cost time in ms: " + (endTimes - startTimes));
        return url;
    }


    /**
     * 获得OauthServer信息
     *
     * @param serverName
     * @param target
     * @return
     */
    private OauthServerInfo getServerInfo(String serverName, String target) {
        OauthServerInfo oauthServerInfo;
        switch (serverName) {
            case "xsm":
                oauthServerInfo = new B2BOauthServerInfo(target);
                break;
            case "aio":
                oauthServerInfo = new AioOauthServerInfo(target);
                break;
            case "aio_storeIns":
                //供聚合App调整店铺保
                oauthServerInfo = new AioOauthServerInfoForStoreIns(target);
                break;
            case "ysm":
                oauthServerInfo = new YsmOauthServerInfo(target);
                break;
            default:
                oauthServerInfo = new B2BOauthServerInfo(target);
                break;
        }
        return oauthServerInfo;
    }


    /**
     * 获得店铺信息
     *
     * @param storeNo
     * @return
     */
    @RequestMapping(value = "storeInfo/{storeNo}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<StoreInfDto> getStoreInfo(@PathVariable String storeNo) {
        return ResultDtoFactory.toAck(MessageUtil.getMessage("common.operate.success"), oauthClientService.getStoreInfoByStoreNo(storeNo));
    }

}