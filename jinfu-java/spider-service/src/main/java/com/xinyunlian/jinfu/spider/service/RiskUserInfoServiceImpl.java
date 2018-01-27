package com.xinyunlian.jinfu.spider.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.mq.sender.QueueSender;
import com.xinyunlian.jinfu.spider.dao.RiskUserInfoDao;
import com.xinyunlian.jinfu.spider.dao.RiskUserOrderDao;
import com.xinyunlian.jinfu.spider.dto.*;
import com.xinyunlian.jinfu.spider.dto.req.AuthLoginDto;
import com.xinyunlian.jinfu.spider.dto.resp.RiskUserInfoDto;
import com.xinyunlian.jinfu.spider.entity.RiskUserInfoPo;
import com.xinyunlian.jinfu.spider.entity.RiskUserOrderPo;
import com.xinyunlian.jinfu.spider.enums.EDataType;
import com.xinyunlian.jinfu.spider.preprocess.ParamProcessService;
import com.xinyunlian.jinfu.spider.processor.ProcessorFactory;
import com.xinyunlian.jinfu.spider.util.Crawler;
import com.xinyunlian.jinfu.spider.util.SocketSpider;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.UniformRandomBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by menglei on 2016年11月07日.
 */
@Service
public class RiskUserInfoServiceImpl implements RiskUserInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RiskUserInfoService.class);
    @Autowired
    private RiskUserInfoDao riskUserInfoDao;
    @Autowired
    private RiskUserOrderDao riskUserOrderDao;
    @Autowired
    private CrawlerStepService crawlerStepService;
    @Autowired
    private RedisCacheManager redisCacheManager;
    @Autowired
    private EnterpriseInfoService enterpriseInfoService;
    @Autowired
    private QueueSender queueSender;
    @Autowired
    private SocketCrawlerStepService socketCrawlerStepService;

    private static void logStep(CrawlerStepDto step) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(JsonUtil.toJson(step));
        }
    }

    public static List<String> treatTime(List<RiskUserOrderPo> orderList) {
        List<String> resArray = new ArrayList<>();
        List<Double> allAmount = new ArrayList<>();//每个月的金额
        List<String> allDate = new ArrayList<>();//所有月份

        List<String> date = new ArrayList<>();//数据中的日期
        List<String> salary = new ArrayList<>();//数据中的金额
        List<String> needDate = new ArrayList<>();//需要显示的日期
        //获取系统当前时间
        Calendar calendar = Calendar.getInstance();
        int thisyear = calendar.get(Calendar.YEAR);
        int thismonth = calendar.get(Calendar.MONTH) + 1;
        //将最近12月份的日期存在数组中
        for (int i = 0; i < 12; i++) {
            //判断月份是否低于1月份
            if (thismonth != 0) {
                //判断月份,统一格式
                if (thismonth < 10) {//小于10月份,在前面添0
                    needDate.add(thisyear + "年" + "0" + thismonth + "月");
                } else {
                    //大于10月份
                    needDate.add(thisyear + "年" + thismonth + "月");
                }
            } else {
                thismonth = 12;
                thisyear -= 1;
                needDate.add(thisyear + "年" + thismonth + "月");//添加上一年12月份
            }
            thismonth--;//添加上一个月的日期
        }

        for (RiskUserOrderPo riskUserOrderPo : orderList) {
            String dateString = riskUserOrderPo.getOrderTime();
            // compatible with yyyyMMdd format
            if (!dateString.contains("-")) {
                dateString = dateString.substring(0, 4).concat("-").concat(dateString.substring(4, 6)).concat("-").concat(dateString.substring(6, 8));
            }
            date.add(dateString);
            salary.add(riskUserOrderPo.getTotalPrice());
        }
        //将arrayList中的日删除
        for (int i = 0; i < date.size(); i++) {
            //将日期分割
            String year = date.get(i).split("\\-")[0];//年
            String month = date.get(i).split("\\-")[1];//月
            //修改arraylist中的数据
            date.set(i, year + "年" + month + "月");
        }
        //判断月份是否相同,若相同，则将金额相加
        for (int i = 0; i < date.size() - 1; i++) {
            Double amount = Double.parseDouble(salary.get(i));//初始化金额
            allDate.add(date.get(i));
            allAmount.add(amount);
            for (int j = i + 1; j < date.size(); j++) {
                if (date.get(i).equals(date.get(j))) {
                    //如果月份相同
                    amount += Double.parseDouble(salary.get(j));
                    allAmount.set(i, amount);
                    date.remove(j);
                    salary.remove(j);
                    j--;
                }
            }
        }
        //boolean is
        //输出最后结果
        for (int i = 0; i < 12; i++) {
            Double needMoney = 0.00;//需要显示的金额
            for (int j = 0; j < allDate.size(); j++) {
                //判断显示月份和实际有记录的月份是否相同
                if (needDate.get(i).equals(allDate.get(j))) {
                    needMoney = allAmount.get(j);
                    break;
                    //System.out.println(needDate.get(i)+":"+allAmount.get(j));
                }
            }
            resArray.add(needDate.get(i) + ":" + AmtUtils.formatAmt2TwoDecimal(BigDecimal.valueOf(needMoney)) + "元");
            //将金额赋值
            //System.out.println(needDate.get(i)+":"+needMoney+"元");
        }
        return resArray;
    }

    public static String downloadPage(CloseableHttpClient hc, String url) {
        String location = null;
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(createConfig(5000, false));
            response = hc.execute(httpGet);
            StatusLine status = response.getStatusLine();
            int statusCode = status.getStatusCode();
            if (statusCode == HttpStatus.SC_MOVED_TEMPORARILY ||
                    statusCode == HttpStatus.SC_SEE_OTHER ||
                    statusCode == HttpStatus.SC_TEMPORARY_REDIRECT) {
                Header h = response.getFirstHeader("Location");
                location = h.getValue();
                System.out.println("head loction is :" + location);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return location;
    }

    private static RequestConfig createConfig(int timeout, boolean redirectsEnabled) {
        return RequestConfig.custom()
                .setSocketTimeout(timeout)
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setRedirectsEnabled(redirectsEnabled)
                .build();
    }

    private String processParams(String aParams, Map<String, String> context) {
        Pattern pattern = Pattern.compile("\\|.*?\\|");
        Matcher matcher = pattern.matcher(aParams);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String group = matcher.group();
            String key = group.substring(1, group.length() - 1);
            String[] splitKey = key.split(":");
            if (splitKey.length > 1) {
                List<String> textList = new ArrayList<>();
                for (int i = 1; i < splitKey.length; i++) {
                    String textKey = splitKey[i];
                    String value = textKey;
                    if (textKey.startsWith("$")) {
                        value = StringUtils.defaultIfEmpty(context.get(textKey.substring(1)), "");
                    }
                    ;
                    textList.add(value);
                }
                ParamProcessService paramProcessService = ApplicationContextUtil.getBean(splitKey[0], ParamProcessService.class);
                String encryptedValue = paramProcessService.process(textList.toArray(new String[0]));
                matcher.appendReplacement(sb, encryptedValue);
            } else {
                String value = StringUtils.defaultIfEmpty(context.get(key), "");
                matcher.appendReplacement(sb, value);
            }
        }
        matcher.appendTail(sb);
        String result = sb.toString();
        return result;
    }

    public boolean socketAuthLogin(SocketCrawlerStepDto socketStep, AuthLoginDto authLoginDto) {
        boolean authed = false;
        String result = null;
        SocketSpider socketSpider = new SocketSpider(socketStep.getConfig());
        String code;
        if (socketStep.getConfig().isAuthCode()) {
            code = getAuthCode(authLoginDto);
            if (null != code) {
                result = socketSpider.spider(authLoginDto.getUsername(), code, true);
            }
        } else {
            result = socketSpider.spider(authLoginDto.getUsername(), authLoginDto.getPassword(), true);
        }
        JSONObject json = JSONObject.parseObject(result);
        if (json != null)
            authed = json.getBoolean("login");


        RiskUserInfoPo userInfoPo = riskUserInfoDao.findByUserId(authLoginDto.getUserId());
        if (null == userInfoPo) {
            userInfoPo = new RiskUserInfoPo();
        }
        userInfoPo.setUsername(authLoginDto.getUsername());
        userInfoPo.setPassword(authLoginDto.getPassword());
        userInfoPo.setUserId(authLoginDto.getUserId());
        userInfoPo.setProvinceId(authLoginDto.getProvinceId());
        userInfoPo.setCityId(authLoginDto.getCityId());
        userInfoPo.setAreaId(authLoginDto.getAreaId());
        userInfoPo.setLastOperationStatus(authed ? "S" : "F");
        riskUserInfoDao.save(userInfoPo);


        return authed;
    }

    public boolean authLogin(Crawler crawler, AuthLoginDto authLoginDto, Map<String, String> context) throws BizServiceException {
        Boolean isLoggedIn = Boolean.FALSE;

        if (StringUtils.isEmpty(authLoginDto.getUserId()) || StringUtils.isEmpty(authLoginDto.getUsername())
                || StringUtils.isEmpty(authLoginDto.getPassword())) {
            throw new BizServiceException(EErrorCode.CRAWLER_NO_AUTHORIZATION_INFORMATION,
                    JsonUtil.toJson(authLoginDto));
        }
        LOGGER.info("UserID[{}] is attempt to login in with username[{}]",
                authLoginDto.getUserId(), authLoginDto.getUsername());
        try {
            /** 登录配置  */
            List<CrawlerStepDto> loginSteps = crawlerStepService.getStepByAreaAndDataType(
                    authLoginDto.getProvinceId(),
                    authLoginDto.getCityId(),
                    authLoginDto.getAreaId(), EDataType.LOGIN);
            if (CollectionUtils.isEmpty(loginSteps)) {
                LOGGER.warn("该爬取{}的配置不存在", EDataType.LOGIN.getText());
            } else {
                Boolean result = Boolean.TRUE;
                for (CrawlerStepDto crawlerStepDto : loginSteps) {
                    logStep(crawlerStepDto);
                    String processedParamString = processParams(crawlerStepDto.getParams(), context);
                    HttpContent content = crawler.getPage(crawlerStepDto.getUrl(),
                            JsonUtil.toMap(processedParamString), crawlerStepDto.getMethod());
                    String processResult = ProcessorFactory.getProcessor(crawlerStepDto.getProcessor()).process(content.getData(),
                            content.getCharset(), crawlerStepDto.getProcessorConfig(), context, crawler);
                    result = Boolean.parseBoolean(processResult);
                    if (!result) {
                        break;
                    }
                }
                isLoggedIn = result;
                LOGGER.info("UserID[{}] logged in with username[{}]?{}",
                        authLoginDto.getUserId(), authLoginDto.getUsername(), isLoggedIn);
            }
        } catch (Exception e) {
            // rethrow exception
            throw new BizServiceException(EErrorCode.CRAWLER_LOGIN_FAILURE, "Login failed", e);
        }

        RiskUserInfoPo userInfoPo = riskUserInfoDao.findByUserId(authLoginDto.getUserId());
        if (null == userInfoPo) {
            userInfoPo = new RiskUserInfoPo();
        }
        userInfoPo.setUsername(authLoginDto.getUsername());
        userInfoPo.setPassword(authLoginDto.getPassword());
        userInfoPo.setUserId(authLoginDto.getUserId());
        userInfoPo.setProvinceId(authLoginDto.getProvinceId());
        userInfoPo.setCityId(authLoginDto.getCityId());
        userInfoPo.setAreaId(authLoginDto.getAreaId());
        userInfoPo.setLastOperationStatus(isLoggedIn ? "S" : "F");
        riskUserInfoDao.save(userInfoPo);

        return isLoggedIn.booleanValue();
    }

    @Override
    @Transactional
    public boolean authLogin(AuthLoginDto authLoginDto) throws BizServiceException {
        Boolean authed;
        SocketCrawlerStepDto socketStep = socketCrawlerStepService.findStepByArea(authLoginDto.getProvinceId(), authLoginDto.getCityId(), authLoginDto.getAreaId());
        if (socketStep != null) {
            authed = socketAuthLogin(socketStep, authLoginDto);
        } else {
            String proxyIp = redisCacheManager.getCache(CacheType.APPLICATIONAL).get(
                    "Spider.ProxyIp", String.class
            );
            String proxyPort = redisCacheManager.getCache(CacheType.APPLICATIONAL).get(
                    "Spider.ProxyPort", String.class
            );
            String proxyUser = redisCacheManager.getCache(CacheType.APPLICATIONAL).get(
                    "Spider.ProxyUser", String.class
            );
            String proxyPass = redisCacheManager.getCache(CacheType.APPLICATIONAL).get(
                    "Spider.ProxyPass", String.class
            );
            Crawler crawler = new Crawler(proxyIp, proxyPort, proxyUser, proxyPass);
            Map<String, String> context = new HashedMap();
            context.put("username", authLoginDto.getUsername());
            context.put("password", authLoginDto.getPassword());
            authed = authLogin(crawler, authLoginDto, context);
        }

        if (canSpider(authLoginDto)) {
            return authed;
        } else {
            return Boolean.TRUE;
        }
    }

    @Override
    @Transactional
    public void spiderUserInfo(String userId) throws BizServiceException {
        RiskUserInfoPo riskUserInfoPo = riskUserInfoDao.findByUserId(userId);

        if (riskUserInfoPo == null) {
            throw new BizServiceException(EErrorCode.CRAWLER_NO_AUTHORIZATION_INFORMATION);
        }

        AuthLoginDto authLoginDto = ConverterService.convert(riskUserInfoPo, AuthLoginDto.class);

        Map<String, String> context = new HashedMap();
        context.put("username", authLoginDto.getUsername());
        context.put("password", authLoginDto.getPassword());

        riskUserInfoPo.setLastOperationStatus("F");
        RetryTemplate retryTemplate = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(1);
        UniformRandomBackOffPolicy backOffPolicy = new UniformRandomBackOffPolicy();
        backOffPolicy.setMinBackOffPeriod(10000L);
        backOffPolicy.setMaxBackOffPeriod(30000L);
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        final RetryCallback<AllInfo, Exception> retryCallback = new RetryCallback<AllInfo, Exception>() {
            @Override
            public AllInfo doWithRetry(RetryContext retryContext) throws Exception {
                SocketCrawlerStepDto socketCrawlerStepDto = socketCrawlerStepService.findStepByArea(riskUserInfoPo.getProvinceId(),
                        riskUserInfoPo.getCityId(),
                        riskUserInfoPo.getAreaId());
                if (socketCrawlerStepDto != null) {
                    SocketSpider socketSpider = new SocketSpider(socketCrawlerStepDto.getConfig());
                    String result = null;
                    String code;
                    if (socketCrawlerStepDto.getConfig().isAuthCode()) {
                        code = getAuthCode(authLoginDto);
                        if (null != code) {
                            result = socketSpider.spider(authLoginDto.getUsername(), code);
                        }
                    } else {
                        result = socketSpider.spider(authLoginDto.getUsername(), authLoginDto.getPassword());
                    }
                    JSONObject json = JSONObject.parseObject(result);
                    Boolean isLoggedIn = json.getBoolean("login");

                    if (!isLoggedIn) {
                        throw new BizServiceException(EErrorCode.CRAWLER_LOGIN_FAILURE);
                    }

                    AllInfo allInfo = new AllInfo();
                    allInfo.setUserInfo(JSONObject.toJavaObject(json.getJSONObject("userInfo"), UserInfo.class));
                    allInfo.setOrderList(JSONObject.parseArray(json.getJSONArray("orderInfo").toJSONString(), OrderInfo.class));
                    return allInfo;
                } else {
                    String proxyIp = redisCacheManager.getCache(CacheType.APPLICATIONAL).get(
                            "Spider.ProxyIp", String.class
                    );
                    String proxyPort = redisCacheManager.getCache(CacheType.APPLICATIONAL).get(
                            "Spider.ProxyPort", String.class
                    );
                    String proxyUser = redisCacheManager.getCache(CacheType.APPLICATIONAL).get(
                            "Spider.ProxyUser", String.class
                    );
                    String proxyPass = redisCacheManager.getCache(CacheType.APPLICATIONAL).get(
                            "Spider.ProxyPass", String.class
                    );
                    Crawler crawler = new Crawler(proxyIp, proxyPort, proxyUser, proxyPass);

                    Boolean isLoggedIn = authLogin(crawler, authLoginDto, context);

                    if (!isLoggedIn) {
                        throw new BizServiceException(EErrorCode.CRAWLER_LOGIN_FAILURE);
                    }

                    UserInfo userInfo = new UserInfo();
                    List<OrderInfo> orderInfoList = null;
                    LOGGER.info("UserID[{}] is attempt to grab user information", riskUserInfoPo.getUserId());
                    List<CrawlerStepDto> userInfoSteps = crawlerStepService.getStepByAreaAndDataType(
                            riskUserInfoPo.getProvinceId(),
                            riskUserInfoPo.getCityId(),
                            riskUserInfoPo.getAreaId(),
                            EDataType.USERINFO);

                    if (CollectionUtils.isEmpty(userInfoSteps)) {
                        throw new BizServiceException(EErrorCode.CRAWLER_NO_STEP.setArgs(EDataType.USERINFO.getText()));
                    }

                    Map<String, String> userInfoMap = new HashedMap();
                    for (CrawlerStepDto crawlerStepDto : userInfoSteps) {
                        logStep(crawlerStepDto);
                        HttpContent content = crawler.getPage(processParams(crawlerStepDto.getUrl(), context),
                                JsonUtil.toMap(processParams(crawlerStepDto.getParams(), context)),
                                crawlerStepDto.getMethod());
                        String processResult = ProcessorFactory.getProcessor(crawlerStepDto.getProcessor()).process(content.getData(),
                                content.getCharset(), crawlerStepDto.getProcessorConfig(), context, crawler);
                        userInfoMap.putAll(JsonUtil.toMap(processResult));
                    }
                    userInfo = JSON.parseObject(JsonUtil.toJson(userInfoMap), UserInfo.class);
                    LOGGER.info("UserID[{}] grabbed user information:{}", riskUserInfoPo.getUserId(), JsonUtil.toJson(userInfo));

                    LOGGER.info("UserID[{}] is attempt to grab order information", riskUserInfoPo.getUserId());
                    List<CrawlerStepDto> orderInfoSteps = crawlerStepService.getStepByAreaAndDataType(
                            riskUserInfoPo.getProvinceId(),
                            riskUserInfoPo.getCityId(),
                            riskUserInfoPo.getAreaId(),
                            EDataType.ORDERS);

                    if (CollectionUtils.isEmpty(orderInfoSteps)) {
                        throw new BizServiceException(EErrorCode.CRAWLER_NO_STEP.setArgs(EDataType.USERINFO.getText()));
                    }
                    orderInfoList = new ArrayList<>();
                    for (CrawlerStepDto crawlerStepDto : orderInfoSteps) {
                        logStep(crawlerStepDto);
                        HttpContent content = crawler.getPage(processParams(crawlerStepDto.getUrl(), context),
                                JsonUtil.toMap(processParams(crawlerStepDto.getParams(), context)),
                                crawlerStepDto.getMethod());
                        String processResult = ProcessorFactory.getProcessor(crawlerStepDto.getProcessor()).process(content.getData(),
                                content.getCharset(), crawlerStepDto.getProcessorConfig(), context, crawler);
                        orderInfoList.addAll(JsonUtil.toObject(List.class, OrderInfo.class, processResult));
                    }
                    LOGGER.info("UserID[{}] grabbed order information:{}", riskUserInfoPo.getUserId(), JsonUtil.toJson(orderInfoList));
                    //}
                    AllInfo allInfo = new AllInfo();
                    allInfo.setUserInfo(userInfo);
                    allInfo.setOrderList(orderInfoList);
                    return allInfo;
                }
            }
        };

        AllInfo allInfo = null;
        try {
            allInfo = retryTemplate.execute(retryCallback);
            riskUserInfoPo.setLastOperationStatus("S");
        } catch (BizServiceException bizException) {
            // rethrow business exception
            throw bizException;
        } catch (Exception e) {
            // throw wrapped exception
            throw new BizServiceException(EErrorCode.CRAWLER_CLIMB_FAILURE, "爬取失败", e);
        } finally {
            // save data
            if (allInfo != null) {
                UserInfo userInfo = allInfo.getUserInfo();
                ConverterService.convert(userInfo, riskUserInfoPo);
                riskUserInfoPo.setUserId(userId);
                riskUserInfoPo.setUsername(authLoginDto.getUsername());
                riskUserInfoPo.setPassword(authLoginDto.getPassword());
                riskUserInfoPo.setAreaId(authLoginDto.getAreaId());
                riskUserInfoPo.setProvinceId(authLoginDto.getProvinceId());
                riskUserInfoPo.setCityId(authLoginDto.getCityId());
            }
            riskUserInfoDao.save(riskUserInfoPo);

            if (allInfo != null) {
                List<OrderInfo> orderInfoList = allInfo.getOrderList();
                if (CollectionUtils.isNotEmpty(orderInfoList)) {
                    // 删除历史数据
                    riskUserOrderDao.deleteByRiskUserId(riskUserInfoPo.getId());
                    List<RiskUserOrderPo> orderList = new ArrayList<RiskUserOrderPo>();
                    //List<RiskUserOrderPo> orderList = riskUserOrderDao.findByRiskUserId(riskUserInfoPo.getId());
                    Map<String, RiskUserOrderPo> existedOrderMap = new HashMap<>();
                    /*for (RiskUserOrderPo riskUserOrderPo : orderList) {
                        existedOrderMap.put(riskUserOrderPo.getOrderNo(), riskUserOrderPo);
                    }*/
                    for (OrderInfo orderInfo : orderInfoList) {
                      /*  if (existedOrderMap.containsKey(orderInfo.getOrderNo())) {
                            RiskUserOrderPo existedOrder = existedOrderMap.get(orderInfo.getOrderNo());
                            ConverterService.convert(orderInfo, existedOrder);
                        } else {*/
                        if (!existedOrderMap.containsKey(orderInfo.getOrderNo())) {
                            RiskUserOrderPo newOrder = ConverterService.convert(orderInfo, RiskUserOrderPo.class);
                            newOrder.setRiskUserId(riskUserInfoPo.getId());
                            orderList.add(newOrder);
                            existedOrderMap.put(orderInfo.getOrderNo(), newOrder);
                        }
                        //existedOrderMap.put(newOrder.getOrderNo(), newOrder);
                        // }
                    }
                    // 新增数据
                    riskUserOrderDao.save(orderList);
                }
            }
            if (null != riskUserInfoPo.getCustName()) {
                enterpriseInfoService.crawlerEnterpriseInfo(riskUserInfoPo.getCustName(), riskUserInfoPo.getUserId());
                queueSender.send("SPIDER_COMPLETED_EVENT", riskUserInfoPo.getUserId());
            }
        }

    }

    /**
     * 获取用户信息及订单信息
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public RiskUserInfoDto getUserInfo(String userId) {
        RiskUserInfoPo riskUserInfoPo = riskUserInfoDao.findByUserId(userId);
        RiskUserInfoDto riskUserInfoDto = ConverterService.convert(riskUserInfoPo, RiskUserInfoDto.class);
        if (Objects.nonNull(riskUserInfoPo)) {
            SocketCrawlerStepDto socketCrawlerStepDto = socketCrawlerStepService.findStepByArea(riskUserInfoPo.getProvinceId(),
                    riskUserInfoPo.getCityId(),
                    riskUserInfoPo.getAreaId());
            if (socketCrawlerStepDto != null) {
                riskUserInfoDto.setWelcomeUrl(socketCrawlerStepDto.getUrl());
            } else {
                List<CrawlerStepDto> welcomeSteps = crawlerStepService.getStepByAreaAndDataType(riskUserInfoPo.getProvinceId(),
                        riskUserInfoPo.getCityId(),
                        riskUserInfoPo.getAreaId(),
                        EDataType.WELCOME);
                if (welcomeSteps.size() > 0) {
                    riskUserInfoDto.setWelcomeUrl(welcomeSteps.get(0).getUrl());
                }
            }

            List<RiskUserOrderPo> orderList = riskUserOrderDao.findByRiskUserId(riskUserInfoPo.getId());
            if (orderList != null && orderList.size() > 0) {
                List<String> list = treatTime(orderList);
                riskUserInfoDto.setOrderList(list);
            }
        }
        return riskUserInfoDto;
    }

    @Override
    public RiskUserInfoDto findByUserId(String userId) {
        RiskUserInfoPo riskUserInfoPo = riskUserInfoDao.findByUserId(userId);
        RiskUserInfoDto riskUserInfoDto = ConverterService.convert(riskUserInfoPo, RiskUserInfoDto.class);

        if (riskUserInfoDto != null) {
            List<RiskUserOrderPo> orderList = riskUserOrderDao.findByRiskUserId(riskUserInfoPo.getId());
            if (orderList != null && orderList.size() > 0) {
                List<String> list = treatTime(orderList);
                riskUserInfoDto.setOrderList(list);
            }
        }

        return riskUserInfoDto;
    }

    @Override
    public AuthLoginDto getAuthLoginData(String userId) {
        AuthLoginDto authLoginDto = new AuthLoginDto();
        RiskUserInfoPo riskUserInfoPo = riskUserInfoDao.findByUserId(userId);
        authLoginDto.setUserId(userId);
        authLoginDto.setUsername(riskUserInfoPo.getUsername());
        authLoginDto.setPassword(riskUserInfoPo.getPassword());
        authLoginDto.setProvinceId(riskUserInfoPo.getProvinceId());
        authLoginDto.setCityId(riskUserInfoPo.getCityId());
        authLoginDto.setAreaId(riskUserInfoPo.getAreaId());
        return authLoginDto;

    }

    @Override
    public List<AuthLoginDto> getFailedUserList() {
        Specification<RiskUserInfoPo> specification = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            expressions.add(cb.equal(root.get("lastOperationStatus"), "F"));
            return predicate;
        };

        List<RiskUserInfoPo> userInfoPos = riskUserInfoDao.findAll(specification);
        List<AuthLoginDto> authLoginDtos = new ArrayList<>(userInfoPos.size());
        userInfoPos.forEach(riskUserInfoPo -> {
            AuthLoginDto authLoginDto = new AuthLoginDto();
            authLoginDto.setUserId(riskUserInfoPo.getUserId());
            authLoginDto.setUsername(riskUserInfoPo.getUsername());
            authLoginDto.setPassword(riskUserInfoPo.getPassword());
            authLoginDto.setProvinceId(riskUserInfoPo.getProvinceId());
            authLoginDto.setCityId(riskUserInfoPo.getCityId());
            authLoginDto.setAreaId(riskUserInfoPo.getAreaId());
            authLoginDtos.add(authLoginDto);
        });
        return authLoginDtos;
    }

    @Override
    public BigDecimal getYearlyOrderAmout(String userId) {
        final BigDecimal[] totalAmount = {BigDecimal.ZERO};
        RiskUserInfoPo riskUserInfoPo = riskUserInfoDao.findByUserId(userId);
        if (Objects.nonNull(riskUserInfoPo)) {
            List<RiskUserOrderPo> orderList = riskUserOrderDao.findByRiskUserId(riskUserInfoPo.getId());
            if (CollectionUtils.isNotEmpty(orderList)) {
                CollectionUtils.filter(orderList, object -> {
                    RiskUserOrderPo po = (RiskUserOrderPo) object;
                    String orderDate = po.getOrderTime();
                    if (!orderDate.contains("-")) {
                        orderDate = orderDate.substring(0, 4).concat("-").concat(orderDate.substring(4, 6)).concat("-").concat(orderDate.substring(6, 8));
                    }
                    if (DateHelper.betweenDays(DateHelper.getDate(orderDate), new Date()) > 360) {
                        return false;
                    } else {
                        return true;
                    }
                });

                orderList.forEach(po -> {
                    totalAmount[0] = totalAmount[0].add(new BigDecimal(po.getTotalPrice()));
                });

            }
        }
        return totalAmount[0];
    }

    @Override
    public Double getOrderAmtForThisMonth(String userId) {
        Double orderAmt = null;
        RiskUserInfoPo riskUserInfoPo = riskUserInfoDao.findByUserId(userId);
        if (Objects.nonNull(riskUserInfoPo)) {
            List<RiskUserOrderPo> orderList = riskUserOrderDao.findByRiskUserId(riskUserInfoPo.getId());
            List<String> monthlyOrderLines = treatTime(orderList);
            orderAmt = Double.parseDouble(StringUtils.removeEnd(monthlyOrderLines.get(0).split(":")[1], "元").replaceAll(",", ""));
        }
        return orderAmt;
    }

    @Override
    public Double getGrowthRate(String userId) {
        Double rate = null;
        RiskUserInfoPo riskUserInfoPo = riskUserInfoDao.findByUserId(userId);
        if (Objects.nonNull(riskUserInfoPo)) {
            List<RiskUserOrderPo> orderList = riskUserOrderDao.findByRiskUserId(riskUserInfoPo.getId());
            List<String> monthlyOrderLines = treatTime(orderList);
            if (monthlyOrderLines.size() > 7) {
                Double amt0 = Double.parseDouble(StringUtils.removeEnd(monthlyOrderLines.get(0).split(":")[1], "元").replaceAll(",", ""));
                Double amt1 = Double.parseDouble(StringUtils.removeEnd(monthlyOrderLines.get(1).split(":")[1], "元").replaceAll(",", ""));
                Double amt2 = Double.parseDouble(StringUtils.removeEnd(monthlyOrderLines.get(2).split(":")[1], "元").replaceAll(",", ""));
                Double amt3 = Double.parseDouble(StringUtils.removeEnd(monthlyOrderLines.get(3).split(":")[1], "元").replaceAll(",", ""));
                Double amt4 = Double.parseDouble(StringUtils.removeEnd(monthlyOrderLines.get(4).split(":")[1], "元").replaceAll(",", ""));
                Double amt5 = Double.parseDouble(StringUtils.removeEnd(monthlyOrderLines.get(5).split(":")[1], "元").replaceAll(",", ""));
                Double amt6 = Double.parseDouble(StringUtils.removeEnd(monthlyOrderLines.get(6).split(":")[1], "元").replaceAll(",", ""));
                Double amtQ1 = 0d;
                Double amtQ2 = 0d;
                if (amt6 > 0) {
                    amtQ1 = amt1 + amt2 + amt3;
                    amtQ2 = amt4 + amt5 + amt6;
                } else if (amt4 + amt5 > 0) {
                    amtQ1 = amt1 + amt2;
                    amtQ2 = amt3 + amt4;
                }

                if (amtQ2 > 0) {
                    rate = amtQ1 / amtQ2 * 100;
                }

            }
        }
        return rate;
    }

    @Override
    public Boolean canSpider(AuthLoginDto authLoginDto) {

        String ignoreCity = AppConfigUtil.getConfig("spider.ignore.city");

        String[] cityIds = ignoreCity.split(",");
        for (String cityId : cityIds) {
            if (StringUtils.equals(cityId, "" + authLoginDto.getCityId())) {
                return false;
            }
        }

        SocketCrawlerStepDto socketCrawlerStepDto = socketCrawlerStepService.findStepByArea(authLoginDto.getProvinceId(),
                authLoginDto.getCityId(),
                authLoginDto.getAreaId());
        if (Objects.isNull(socketCrawlerStepDto)) {
            List<CrawlerStepDto> orderSteps = crawlerStepService.getStepByAreaAndDataType(
                    authLoginDto.getProvinceId(),
                    authLoginDto.getCityId(),
                    authLoginDto.getAreaId(), EDataType.ORDERS);
            return CollectionUtils.isNotEmpty(orderSteps);
        } else
            return true;
    }

    @Override
    public Boolean isAuthed(String userId) {
        RiskUserInfoPo userInfoPo = riskUserInfoDao.findByUserId(userId);
        if (Objects.isNull(userInfoPo)) {
            return Boolean.FALSE;
        }
        Boolean authed = StringUtils.equals(userInfoPo.getLastOperationStatus(), "S");
        AuthLoginDto authLoginDto = new AuthLoginDto();
        authLoginDto.setUserId(userInfoPo.getUserId());
        authLoginDto.setAreaId(userInfoPo.getAreaId());
        authLoginDto.setCityId(userInfoPo.getCityId());
        authLoginDto.setPassword(userInfoPo.getPassword());
        authLoginDto.setProvinceId(userInfoPo.getProvinceId());
        authLoginDto.setUsername(userInfoPo.getUsername());
        if (canSpider(authLoginDto)) {
            return authed;
        } else {
            return Boolean.TRUE;
        }
    }

    private String getAuthCode(AuthLoginDto authLoginDto) {
        CloseableHttpClient client = HttpClients.createDefault();
        String code = null;
        List<CrawlerStepDto> loginSteps = crawlerStepService.getStepByAreaAndDataType(
                authLoginDto.getProvinceId(),
                authLoginDto.getCityId(),
                authLoginDto.getAreaId(), EDataType.LOGIN);
        String processedParamString = null;
        Map<String, String> context = new HashedMap();
        context.put("username", authLoginDto.getUsername());
        context.put("password", authLoginDto.getPassword());
        StringBuffer loginUrl = null;
        Boolean result = Boolean.FALSE;
        CloseableHttpResponse response = null;
        String codeResult = null;
        try {
            for (CrawlerStepDto csd : loginSteps) {
                if (csd.getOrder() == 1) {
                    loginUrl = new StringBuffer(csd.getUrl());
                    processedParamString = processParams(csd.getParams(), context);
                    context = JsonUtil.toMap(processedParamString);
                    for (Map.Entry entry : context.entrySet()) {
                        if ("j_username".equals(entry.getKey())) {
                            loginUrl.append("?j_username=").append(entry.getValue());
                        } else {
                            loginUrl.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                        }
                    }
                    // 登录
                    HttpGet httpGet = new HttpGet(loginUrl.toString());
                    httpGet.setConfig(createConfig(5000, false));
                    response = client.execute(httpGet);
                    String responseXml = EntityUtils.toString(response.getEntity());
                    String processResult = ProcessorFactory.getProcessor(csd.getProcessor()).process(responseXml.getBytes(),
                            Charset.defaultCharset(), csd.getProcessorConfig(), context, null);
                    result = Boolean.parseBoolean(processResult);
                } else {
                    // 重定向
                    loginUrl = new StringBuffer(csd.getUrl());
                    loginUrl.append(authLoginDto.getUsername());
                    String loc = downloadPage(client, loginUrl.toString());
                    if (!StringUtil.isBlank(loc)) {
                        codeResult = downloadPage(client, loc);
                    }
                    if (!StringUtil.isBlank(codeResult)) {
                        code = codeResult.split("\\?")[1].split("=")[1];
                    }
                }
                if (!result) {
                    break;
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return code;
    }
}
