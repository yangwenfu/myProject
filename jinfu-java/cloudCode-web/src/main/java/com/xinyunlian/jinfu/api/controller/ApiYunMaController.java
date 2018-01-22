package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.alipay.service.ApiAliPayService;
import com.xinyunlian.jinfu.api.dto.OpenidUrlDto;
import com.xinyunlian.jinfu.api.dto.ProdOpenApiDto;
import com.xinyunlian.jinfu.api.validate.OpenApi;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.cashier.dto.MerchantSupportedChannelDetailDto;
import com.xinyunlian.jinfu.cashier.dto.MerchantSupportedChannelDto;
import com.xinyunlian.jinfu.cashier.service.CloudCodeService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.wechat.service.ApiWeChatService;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.dto.YmPayChannelDto;
import com.xinyunlian.jinfu.yunma.enums.EYmPayChannel;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import com.xinyunlian.jinfu.yunma.service.YMProdService;
import com.xinyunlian.jinfu.yunma.service.YmPayChannelService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;


/**
 * 中移积分
 * Created by menglei on 2016年11月21日.
 */
@Controller
@RequestMapping(value = "open-api/yunma")
public class ApiYunMaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiYunMaController.class);

    @Autowired
    private YMProdService yMProdService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private ApiWeChatService apiWeChatService;
    @Autowired
    private ApiAliPayService apiAliPayService;
    @Autowired
    private YMMemberService yMMemberService;
    @Autowired
    private YmPayChannelService ymPayChannelService;
    @Autowired
    private CloudCodeService cloudCodeService;

    /**
     * 判断店铺所在的地区是否是业务配置的地区范围内
     *
     * @param prodOpenApiDto storeId prodId
     * @return
     */
    @OpenApi
    @ResponseBody
    @RequestMapping(value = "/checkProduct", method = RequestMethod.POST)
    public ResultDto<Object> checkProduct(@RequestBody ProdOpenApiDto prodOpenApiDto) {
        //判断店铺是否已实名认证
        StoreInfDto storeInfDto = storeService.findByStoreId(Long.valueOf(prodOpenApiDto.getStoreId()));
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        //判断店铺所在的地区是否是业务配置的地区范围内
        SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getDistrictId()));
        Boolean flag = yMProdService.checkProdArea(prodOpenApiDto.getProdId(), Long.valueOf(storeInfDto.getDistrictId()), sysAreaInfDto.getTreePath());
        if (!flag) {
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }
        return ResultDtoFactory.toAck("获取成功");
    }

    /**
     * 获取微信code
     *
     * @return
     */
    @RequestMapping(value = "/wechat/openid", method = RequestMethod.GET)
    public String getOpenId(@RequestParam String url) {
        String[] str = url.split("\\?");
        String urls = AppConfigUtil.getConfig("yunma.url") + "/jinfu-cloudCode/web/open-api/yunma/wechat/openidRes?url=" + str[0];
        if (str.length == 1) {
            urls = apiWeChatService.getAuthCodeUrl(urls, "ylfin");
        } else {
            urls = apiWeChatService.getAuthCodeUrl(urls, str[1].replace("&amp;", "&").replace("&#61;", "="));
        }
        return "redirect:" + urls;
    }

    /**
     * 获取微信openid
     *
     * @return
     */
    @RequestMapping(value = "/wechat/openidRes", method = RequestMethod.GET)
    public String getOpenIdRes(@RequestParam String url, @RequestParam String code, @RequestParam String state) {
        if ("ylfin".equals(state)) {
            return "redirect:" + url + "?openid=" + apiWeChatService.getAuthOpenid(code);
        }
        String states;
        try {
            states = URLEncoder.encode(state.replace("&amp;", "&").replace("&#61;", "="), "UTF-8").replace("%3D", "=").replace("%26", "&");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("转码失败", e);
            return null;
        }
        return "redirect:" + url + "?openid=" + apiWeChatService.getAuthOpenid(code) + "&" + states;
    }

    /**
     * 获取支付宝code
     *
     * @return
     */
    @RequestMapping(value = "/alipay/userid", method = RequestMethod.GET)
    public String getUserId(@RequestParam String url) {
        String[] str = url.split("\\?");
        String urls = AppConfigUtil.getConfig("yunma.url") + "/jinfu-cloudCode/web/open-api/yunma/alipay/useridRes?url=" + str[0];
        //String urls = "http://yltest.xylpay.com/ml-jinfu-cloudCode/web/open-api/yunma/alipay/useridRes?url=" + str[0];
        if (str.length == 1) {
            urls = apiAliPayService.getAuthCodeUrl(urls, "ylfin");
        } else {
            urls = apiAliPayService.getAuthCodeUrl(urls, str[1].replace("&amp;", "&").replace("&#61;", "="));
        }
        return "redirect:" + urls;
    }

    /**
     * 获取支付宝openid
     *
     * @return
     */
    @RequestMapping(value = "/alipay/useridRes", method = RequestMethod.GET)
    public String getUserIdRes(@RequestParam String url, @RequestParam String auth_code, @RequestParam String state) {
        if ("ylfin".equals(state)) {
            return "redirect:" + url + "?userid=" + apiAliPayService.getAuthUserid(auth_code);
        }
        String states;
        try {
            states = URLEncoder.encode(state.replace("&amp;", "&").replace("&#61;", "="), "UTF-8").replace("%3D", "=").replace("%26", "&");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("转码失败", e);
            return null;
        }
        String statesArr[] = states.split("=");
        if (statesArr.length == 2 && StringUtils.indexOf(states, "codeNo=") == 0) {
            YMMemberDto memberDto = yMMemberService.getMemberByQrCodeNo(statesArr[1]);
            if (memberDto != null) {
                StoreInfDto storeInfDto = storeService.findByStoreId(memberDto.getStoreId());
                if (storeInfDto != null) {
                    try {
                        states = states + "&memberName=" + URLEncoder.encode(storeInfDto.getStoreName(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        LOGGER.error("转码失败", e);
                        e.printStackTrace();
                    }
                }
            }
        }
        return "redirect:" + url + "?userid=" + apiAliPayService.getAuthUserid(auth_code) + "&" + states;
    }

    /**
     * 获取通道微信url(因为众码是用的众码的公众号)
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/wechat/openid/url", method = RequestMethod.GET)
    public ResultDto<Map<String, String>> getOpenIdUrl(@RequestParam String qrCodeNo, @RequestParam String url) {
        Map<String, String> resultMap = new HashMap<>();
        url = url.replace("&amp;", "&").replace("&#61;", "=");
        String oepnidUrl = AppConfigUtil.getConfig("yunma.url") + "/jinfu-cloudCode/web/open-api/yunma/wechat/openid?url=" + url;

        YMMemberDto yMMemberDto = yMMemberService.getMemberByQrCodeNo(qrCodeNo);
        if (yMMemberDto == null) {
            resultMap.put("url", oepnidUrl);
            return ResultDtoFactory.toAck("获取成功", resultMap);
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(yMMemberDto.getStoreId());
        if (storeInfDto == null) {
            resultMap.put("url", oepnidUrl);
            return ResultDtoFactory.toAck("获取成功", resultMap);
        } else {
            oepnidUrl = oepnidUrl + "%26memberName=" + storeInfDto.getStoreName();
        }
        SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getDistrictId()));
        if (sysAreaInfDto == null) {
            resultMap.put("url", oepnidUrl);
            return ResultDtoFactory.toAck("获取成功", resultMap);
        }
        //获取可支付通道
        List<MerchantSupportedChannelDetailDto> merchantSupportedChannelDtos = cloudCodeService.findMerchantChannelDetails(yMMemberDto.getMemberNo());//返回成功的通道
        MerchantSupportedChannelDetailDto merchantSupportedChannelDetailDto;
        if (CollectionUtils.isEmpty(merchantSupportedChannelDtos)) {
            resultMap.put("url", oepnidUrl);
            return ResultDtoFactory.toAck("获取成功", resultMap);
        } else if (merchantSupportedChannelDtos.size() == 1) {//返回一个通道
            merchantSupportedChannelDetailDto = merchantSupportedChannelDtos.get(0);
        } else {//返回多个通道
            List<String> channelList = new ArrayList<>();
            Map<String, MerchantSupportedChannelDetailDto> map = new HashMap<>();
            for (MerchantSupportedChannelDetailDto dto : merchantSupportedChannelDtos) {
                channelList.add(dto.getChannelProvider());
                map.put(dto.getChannelProvider(), dto);
            }
            List<YmPayChannelDto> list = ymPayChannelService.findByChannel(Long.valueOf(storeInfDto.getCityId()), sysAreaInfDto.getTreePath(), channelList);
            if (CollectionUtils.isEmpty(list)) {
                resultMap.put("url", oepnidUrl);
                return ResultDtoFactory.toAck("获取成功", resultMap);
            }
            merchantSupportedChannelDetailDto = map.get(list.get(0).getChannel().getCode());
            if (merchantSupportedChannelDetailDto == null) {//通道路由查询无结果
                merchantSupportedChannelDetailDto = merchantSupportedChannelDtos.get(0);
            }
        }
        if (EYmPayChannel.ZHONGMA.getCode().equals(merchantSupportedChannelDetailDto.getChannelProvider())) {
            //众码用众码的获取openid url
            oepnidUrl = "http://112.124.4.31/open-api/Openid/openid?URI=" + url + "&memberName=" + storeInfDto.getStoreName();
        }
        resultMap.put("url", oepnidUrl);
        return ResultDtoFactory.toAck("获取成功", resultMap);
    }

    /**
     * 获取通道支付宝url(因为平安走的是沙箱支付宝)
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/alipay/userid/url", method = RequestMethod.GET)
    public ResultDto<Map<String, String>> getUserIdUrl(@RequestParam String qrCodeNo, @RequestParam String url) {
        Map<String, String> resultMap = new HashMap<>();
        url = url.replace("&amp;", "&").replace("&#61;", "=");
        //String oepnidUrl = "http://112.124.4.31/open-api/Openid/userid?URI=" + url;
        String oepnidUrl = AppConfigUtil.getConfig("yunma.url") + "/jinfu-cloudCode/web/open-api/yunma/alipay/userid?url=" + url;

        if (AppConfigUtil.isProdEnv()) {//正式环境直接获取userid
            resultMap.put("url", oepnidUrl);
            return ResultDtoFactory.toAck("获取成功", resultMap);
        } else {
            YMMemberDto yMMemberDto = yMMemberService.getMemberByQrCodeNo(qrCodeNo);
            if (yMMemberDto == null) {
                resultMap.put("url", oepnidUrl);
                return ResultDtoFactory.toAck("获取成功", resultMap);
            }
            StoreInfDto storeInfDto = storeService.findByStoreId(yMMemberDto.getStoreId());
            if (storeInfDto == null) {
                resultMap.put("url", oepnidUrl);
                return ResultDtoFactory.toAck("获取成功", resultMap);
            } else {
                oepnidUrl = oepnidUrl + "%26memberName=" + storeInfDto.getStoreName();
            }
            SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getDistrictId()));
            if (sysAreaInfDto == null) {
                resultMap.put("url", oepnidUrl);
                return ResultDtoFactory.toAck("获取成功", resultMap);
            }
            //获取可支付通道
            List<MerchantSupportedChannelDetailDto> merchantSupportedChannelDtos = cloudCodeService.findMerchantChannelDetails(yMMemberDto.getMemberNo());//返回成功的通道
            MerchantSupportedChannelDetailDto merchantSupportedChannelDetailDto;
            if (CollectionUtils.isEmpty(merchantSupportedChannelDtos)) {
                resultMap.put("url", oepnidUrl);
                return ResultDtoFactory.toAck("获取成功", resultMap);
            } else if (merchantSupportedChannelDtos.size() == 1) {//返回一个通道
                merchantSupportedChannelDetailDto = merchantSupportedChannelDtos.get(0);
            } else {//返回多个通道
                List<String> channelList = new ArrayList<>();
                Map<String, MerchantSupportedChannelDetailDto> map = new HashMap<>();
                for (MerchantSupportedChannelDetailDto dto : merchantSupportedChannelDtos) {
                    channelList.add(dto.getChannelProvider());
                    map.put(dto.getChannelProvider(), dto);
                }
                List<YmPayChannelDto> list = ymPayChannelService.findByChannel(Long.valueOf(storeInfDto.getCityId()), sysAreaInfDto.getTreePath(), channelList);
                if (CollectionUtils.isEmpty(list)) {
                    resultMap.put("url", oepnidUrl);
                    return ResultDtoFactory.toAck("获取成功", resultMap);
                }
                merchantSupportedChannelDetailDto = map.get(list.get(0).getChannel().getCode());
                if (merchantSupportedChannelDetailDto == null) {//通道路由查询无结果
                    merchantSupportedChannelDetailDto = merchantSupportedChannelDtos.get(0);
                }
            }
            if (!EYmPayChannel.ZHONGMA.getCode().equals(merchantSupportedChannelDetailDto.getChannelProvider())) {
                //平安测试环境走沙箱环境
                oepnidUrl = url + "&memberName=" + storeInfDto.getStoreName() + "&userid=funfqs0733@sandbox.com";
            }
            resultMap.put("url", oepnidUrl);
            return ResultDtoFactory.toAck("获取成功", resultMap);
        }
    }


    /**
     * v2获取微信code
     *
     * @return
     */
    @RequestMapping(value = "/wechat/v2/openid", method = RequestMethod.GET)
    public String getV2OpenId(@RequestParam String json) {
        json = json.replace("\\&#34;", "\"");
        OpenidUrlDto openidUrlDto = JsonUtil.toObject(OpenidUrlDto.class, json);
        String urls = AppConfigUtil.getConfig("yunma.url") + "/jinfu-cloudCode/web/open-api/yunma/wechat/v2/openidRes?url=" + openidUrlDto.getUrl();
        //String urls = "http://yltest.xylpay.com/ml-jinfu-cloudCode/web/open-api/yunma/wechat/v2/openidRes?url=" + openidUrlDto.getUrl() + "&codeNo=" + openidUrlDto.getQrCodeNo();
        Map<String, String> map = new HashMap<>();
        map.put("qrCodeNo", openidUrlDto.getQrCodeNo());
        if (StringUtils.isNotEmpty(openidUrlDto.getCost())) {
            map.put("cost", openidUrlDto.getCost());
        }
        urls = apiWeChatService.getAuthCodeUrl(urls, JsonUtil.toJson(map));
        return "redirect:" + urls;
    }

    /**
     * v2获取微信openid
     *
     * @return
     */
    @RequestMapping(value = "/wechat/v2/openidRes", method = RequestMethod.GET)
    public String getV2OpenIdRes(@RequestParam String url, @RequestParam String code, @RequestParam String state) {
        state = state.replace("&#34;", "\"");
        OpenidUrlDto openidUrlDto = JsonUtil.toObject(OpenidUrlDto.class, state);
        String states = "codeNo=" + openidUrlDto.getQrCodeNo();
        if (StringUtils.isNotEmpty(openidUrlDto.getCost())) {
            states = states + "&cost=" + openidUrlDto.getCost();
        }
        if (StringUtils.isNotEmpty(openidUrlDto.getQrCodeNo())) {
            YMMemberDto yMMemberDto = yMMemberService.getMemberByQrCodeNo(openidUrlDto.getQrCodeNo());
            if (yMMemberDto != null) {
                StoreInfDto storeInfDto = storeService.findByStoreId(yMMemberDto.getStoreId());
                if (storeInfDto != null) {
                    states = states + "&memberName=" + storeInfDto.getStoreName();
                }
            }
        }
        return "redirect:" + url + "?openid=" + apiWeChatService.getAuthOpenid(code) + "&" + states;
    }

    /**
     * v2获取通道微信url(因为众码是用的众码的公众号)
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/wechat/v2/openid/url", method = RequestMethod.GET)
    public ResultDto<Map<String, String>> getV2OpenIdUrl(OpenidUrlDto openidUrlDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        Map<String, String> resultMap = new HashMap<>();
        String oepnidUrl = AppConfigUtil.getConfig("yunma.url") + "/jinfu-cloudCode/web/open-api/yunma/wechat/v2/openid?json=" + JsonUtil.toJson(openidUrlDto);

        YMMemberDto yMMemberDto = yMMemberService.getMemberByQrCodeNo(openidUrlDto.getQrCodeNo());
        if (yMMemberDto == null) {
            resultMap.put("url", oepnidUrl);
            return ResultDtoFactory.toAck("获取成功", resultMap);
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(yMMemberDto.getStoreId());
        if (storeInfDto == null) {
            resultMap.put("url", oepnidUrl);
            return ResultDtoFactory.toAck("获取成功", resultMap);
        }
        SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getDistrictId()));
        if (sysAreaInfDto == null) {
            resultMap.put("url", oepnidUrl);
            return ResultDtoFactory.toAck("获取成功", resultMap);
        }
        //获取可支付通道
        List<MerchantSupportedChannelDetailDto> merchantSupportedChannelDtos = cloudCodeService.findMerchantChannelDetails(yMMemberDto.getMemberNo());//返回成功的通道
        MerchantSupportedChannelDetailDto merchantSupportedChannelDetailDto;
        if (CollectionUtils.isEmpty(merchantSupportedChannelDtos)) {
            resultMap.put("url", oepnidUrl);
            return ResultDtoFactory.toAck("获取成功", resultMap);
        } else if (merchantSupportedChannelDtos.size() == 1) {//返回一个通道
            merchantSupportedChannelDetailDto = merchantSupportedChannelDtos.get(0);
        } else {//返回多个通道
            List<String> channelList = new ArrayList<>();
            Map<String, MerchantSupportedChannelDetailDto> map = new HashMap<>();
            for (MerchantSupportedChannelDetailDto dto : merchantSupportedChannelDtos) {
                channelList.add(dto.getChannelProvider());
                map.put(dto.getChannelProvider(), dto);
            }
            List<YmPayChannelDto> list = ymPayChannelService.findByChannel(Long.valueOf(storeInfDto.getCityId()), sysAreaInfDto.getTreePath(), channelList);
            if (CollectionUtils.isEmpty(list)) {
                resultMap.put("url", oepnidUrl);
                return ResultDtoFactory.toAck("获取成功", resultMap);
            }
            merchantSupportedChannelDetailDto = map.get(list.get(0).getChannel().getCode());
            if (merchantSupportedChannelDetailDto == null) {//通道路由查询无结果
                merchantSupportedChannelDetailDto = merchantSupportedChannelDtos.get(0);
            }
        }
        if (EYmPayChannel.ZHONGMA.getCode().equals(merchantSupportedChannelDetailDto.getChannelProvider())) {
            //众码用众码的获取openid url
            oepnidUrl = "http://112.124.4.31/open-api/Openid/openid?URI=" + openidUrlDto.getUrl() + "&memberName=" + storeInfDto.getStoreName();
            if (StringUtils.isNotEmpty(openidUrlDto.getCost())) {
                oepnidUrl = oepnidUrl + "&cost=" + openidUrlDto.getCost();
            }
        }
        resultMap.put("url", oepnidUrl);
        return ResultDtoFactory.toAck("获取成功", resultMap);
    }

    /**
     * v2获取支付宝code
     *
     * @return
     */
    @RequestMapping(value = "/alipay/v2/userid", method = RequestMethod.GET)
    public String getV2UserId(@RequestParam String json) {
        json = json.replace("\\&#34;", "\"");
        OpenidUrlDto openidUrlDto = JsonUtil.toObject(OpenidUrlDto.class, json);
        String urls = AppConfigUtil.getConfig("yunma.url") + "/jinfu-cloudCode/web/open-api/yunma/alipay/v2/useridRes?url=" + openidUrlDto.getUrl();
        //String urls = "http://yltest.xylpay.com/ml-jinfu-cloudCode/web/open-api/yunma/alipay/v2/useridRes?url=" + openidUrlDto.getUrl();
        String state = "codeNo=" + openidUrlDto.getQrCodeNo();
        if (StringUtils.isNotEmpty(openidUrlDto.getCost())) {
            state = state + "%26cost=" + openidUrlDto.getCost();
        }
        urls = apiAliPayService.getAuthCodeUrl(urls, state);
        return "redirect:" + urls;
    }

    /**
     * v2获取支付宝openid
     *
     * @return
     */
    @RequestMapping(value = "/alipay/v2/useridRes", method = RequestMethod.GET)
    public String getV2UserIdRes(@RequestParam String url, @RequestParam String auth_code, @RequestParam String state) {
        state = state.replace("&amp;", "&").replace("&#61;", "=");
        String[] strArr = state.split("&");
        Map<String, String> map = new HashMap<>();
        for (String str : Arrays.asList(strArr)) {
            String[] param = str.split("=");
            map.put(param[0], param[1]);
        }
        String qrCodeNo = map.get("codeNo");
        if (StringUtils.isNotEmpty(qrCodeNo)) {
            YMMemberDto yMMemberDto = yMMemberService.getMemberByQrCodeNo(qrCodeNo);
            if (yMMemberDto != null) {
                StoreInfDto storeInfDto = storeService.findByStoreId(yMMemberDto.getStoreId());
                if (storeInfDto != null) {
                    state = state + "%26memberName=" + storeInfDto.getStoreName();
                }
            }
        }
        return "redirect:" + url + "?userid=" + apiAliPayService.getAuthUserid(auth_code) + "&" + state;
    }

    /**
     * 获取通道支付宝url(因为平安走的是沙箱支付宝)
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/alipay/v2/userid/url", method = RequestMethod.GET)
    public ResultDto<Map<String, String>> getV2UserIdUrl(OpenidUrlDto openidUrlDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        Map<String, String> resultMap = new HashMap<>();
        String oepnidUrl = AppConfigUtil.getConfig("yunma.url") + "/jinfu-cloudCode/web/open-api/yunma/alipay/v2/userid?json=" + JsonUtil.toJson(openidUrlDto);

        if (AppConfigUtil.isProdEnv()) {//正式环境直接获取userid
            resultMap.put("url", oepnidUrl);
            return ResultDtoFactory.toAck("获取成功", resultMap);
        } else {
            YMMemberDto yMMemberDto = yMMemberService.getMemberByQrCodeNo(openidUrlDto.getQrCodeNo());
            if (yMMemberDto == null) {
                resultMap.put("url", oepnidUrl);
                return ResultDtoFactory.toAck("获取成功", resultMap);
            }
            StoreInfDto storeInfDto = storeService.findByStoreId(yMMemberDto.getStoreId());
            if (storeInfDto == null) {
                resultMap.put("url", oepnidUrl);
                return ResultDtoFactory.toAck("获取成功", resultMap);
            }
            SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getDistrictId()));
            if (sysAreaInfDto == null) {
                resultMap.put("url", oepnidUrl);
                return ResultDtoFactory.toAck("获取成功", resultMap);
            }
            //获取可支付通道
            List<MerchantSupportedChannelDetailDto> merchantSupportedChannelDtos = cloudCodeService.findMerchantChannelDetails(yMMemberDto.getMemberNo());//返回成功的通道
            MerchantSupportedChannelDetailDto merchantSupportedChannelDetailDto;
            if (CollectionUtils.isEmpty(merchantSupportedChannelDtos)) {
                resultMap.put("url", oepnidUrl);
                return ResultDtoFactory.toAck("获取成功", resultMap);
            } else if (merchantSupportedChannelDtos.size() == 1) {//返回一个通道
                merchantSupportedChannelDetailDto = merchantSupportedChannelDtos.get(0);
            } else {//返回多个通道
                List<String> channelList = new ArrayList<>();
                Map<String, MerchantSupportedChannelDetailDto> map = new HashMap<>();
                for (MerchantSupportedChannelDetailDto dto : merchantSupportedChannelDtos) {
                    channelList.add(dto.getChannelProvider());
                    map.put(dto.getChannelProvider(), dto);
                }
                List<YmPayChannelDto> list = ymPayChannelService.findByChannel(Long.valueOf(storeInfDto.getCityId()), sysAreaInfDto.getTreePath(), channelList);
                if (CollectionUtils.isEmpty(list)) {
                    resultMap.put("url", oepnidUrl);
                    return ResultDtoFactory.toAck("获取成功", resultMap);
                }
                merchantSupportedChannelDetailDto = map.get(list.get(0).getChannel().getCode());
                if (merchantSupportedChannelDetailDto == null) {//通道路由查询无结果
                    merchantSupportedChannelDetailDto = merchantSupportedChannelDtos.get(0);
                }
            }
            if (!EYmPayChannel.ZHONGMA.getCode().equals(merchantSupportedChannelDetailDto.getChannelProvider())) {
                //平安测试环境走沙箱环境
                oepnidUrl = oepnidUrl + "&userid=funfqs0733@sandbox.com";
            }
            resultMap.put("url", oepnidUrl);
            return ResultDtoFactory.toAck("获取成功", resultMap);
        }
    }

}
