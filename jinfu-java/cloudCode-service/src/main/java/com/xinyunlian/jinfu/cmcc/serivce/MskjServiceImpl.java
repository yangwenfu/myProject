package com.xinyunlian.jinfu.cmcc.serivce;

import com.xinyunlian.jinfu.cmcc.service.MskjService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.EncryptUtil;
import com.xinyunlian.jinfu.common.util.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 慕尚科技
 * Created by menglei on 2016年11月20日.
 */
@Service
public class MskjServiceImpl implements MskjService {

    @Value("${cmcc.mskj.url}")
    private String URL;

    @Value("${cmcc.mskj.merid}")
    private String MERID;

    @Value("${cmcc.mskj.key}")
    private String KEY;

    @Value("${cmcc.mskj.version}")
    private String VERSION;

    private static final Logger LOGGER = LoggerFactory.getLogger(MskjServiceImpl.class);

    /**
     * 查询手机号电子券余额
     *
     * @param mobile
     * @return
     * @throws BizServiceException
     */
    @Override
    public Map<String, String> getQueryBalance(String mobile) throws BizServiceException {
        String result = null;
        try {
            String queryString = "merId=" + MERID + "&phoneNo=" + mobile + "&version=" + VERSION;
            result = HttpUtil.doPost(URL + "/mer/queryBalance", null, getSign(queryString));
            result = URLDecoder.decode(result, "UTF-8");
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取失败", e);
        }
        return resultToMap(result);
    }

    /**
     * 下单,发短信
     *
     * @param mobile
     * @return
     * @throws BizServiceException
     */
    @Override
    public Map<String, String> getDirectPay(String orderId, String mobile, String amount, String storeId, String storeName, String storeAddress) throws BizServiceException {
        String result = null;
        try {
            String queryString = "merId=" + MERID + "&posId=" + storeId + "&posName=" + storeName + "&posAddress=" + storeAddress + "&orderDate=" + formatDate(new Date()) + "&orderId=" + orderId + "&phoneNo=" + mobile + "&amount=" + formatAmount(amount) + "&payType=TK" + "&version=" + VERSION;
            result = HttpUtil.doPost(URL + "/mer/directPay", null, getSign(queryString));
            result = URLDecoder.decode(result, "UTF-8");
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取失败", e);
        }
        return resultToMap(result);
    }

    /**
     * 下单支付确认
     *
     * @param orderId
     * @param tradeNo
     * @param amount
     * @param passwd
     * @return
     * @throws BizServiceException
     */
    @Override
    public Map<String, String> getDirectConf(String orderId, String tradeNo, String amount, String passwd, String storeId) throws BizServiceException {
        String result = null;
        try {
            String queryString = "merId=" + MERID + "&posId=" + storeId + "&orderDate=" + formatDate(new Date()) + "&orderId=" + orderId + "&tradeNo=" + tradeNo + "&amount=" + formatAmount(amount) + "&passwd=" + passwd + "&version=" + VERSION;
            result = HttpUtil.doPost(URL + "/mer/directConf", null, getSign(queryString));
            result = URLDecoder.decode(result, "UTF-8");
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取失败", e);
        }
        return resultToMap(result);
    }

    /**
     * 电子券兑换比例
     *
     * @param amount
     * @return
     * @throws BizServiceException
     */
    @Override
    public Map<String, String> getQueryVouchers(String amount) throws BizServiceException {
        String result = null;
        try {
            String queryString = "merId=" + MERID + "&amount=" + formatAmount(amount) + "&version=" + VERSION;
            result = HttpUtil.doPost(URL + "/mer/queryVouchers", null, getSign(queryString));
            result = URLDecoder.decode(result, "UTF-8");
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取失败", e);
        }
        return resultToMap(result);
    }

    /**
     * 查询订单状态
     *
     * @param orderId
     * @param tradeNo
     * @return
     * @throws BizServiceException
     */
    @Override
    public Map<String, String> getQuery(String orderId, String tradeNo) throws BizServiceException {
        String result = null;
        try {
            String queryString = "merId=" + MERID + "&orderDate=" + formatDate(new Date()) + "&orderId=" + orderId + "&tradeNo=" + tradeNo + "&version=" + VERSION;
            result = HttpUtil.doPost(URL + "/mer/query", null, getSign(queryString));
            result = URLDecoder.decode(result, "UTF-8");
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取失败", e);
        }
        return resultToMap(result);
    }

    /**
     * 签名
     *
     * @param queryString
     * @return
     */
    public String getSign(String queryString) {
        String sign;
        try {
            sign = EncryptUtil.encryptMd5(queryString + KEY);
        } catch (Exception e) {
            LOGGER.error("签名失败", e);
            throw new BizServiceException();
        }
        return queryString + "&sign=" + sign;
    }

    /**
     * 接口返回结果转map
     *
     * @param result
     * @return
     */
    public static Map<String, String> resultToMap(String result) {
        Map<String, String> params = new HashMap<>();
        String[] paramsList = result.split("&");
        for (String str : Arrays.asList(paramsList)) {
            String[] param = str.split("=");
            params.put(param[0], param[1]);
        }
        return params;
    }

    /**
     * 获取当天时间 格式：20161118
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return formatDate(date, "yyyyMMdd");
    }

    public static String formatAmount(String amount) {
        return amount.replaceAll("\\.", StringUtils.EMPTY);
    }

    public static String formatDate(Date date, String format) {
        if (date == null) return null;
        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        return dateformat.format(date);
    }

}
