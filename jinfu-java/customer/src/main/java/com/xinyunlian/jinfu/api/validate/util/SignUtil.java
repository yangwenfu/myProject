package com.xinyunlian.jinfu.api.validate.util;

import com.xinyunlian.jinfu.common.util.EncryptUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by JL on 2016/9/7.
 */
public class SignUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(SignUtil.class);


    /**
     * Description: 生成随机数
     *
     * @return
     */
    public static String CreateNoncestr() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < 16; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }

    /**
     * Description: 创建签名
     *
     * @param parameters
     * @param apiKey
     * @return
     */
    public static String createSign(SortedMap<String, String> parameters, String apiKey) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (String k : parameters.keySet()) {
            String v = parameters.get(k);
            if (StringUtils.isNotBlank(k) && StringUtils.isNotBlank(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        sb.append("key=" + apiKey);
        LOGGER.debug("签名参数：{}", sb);
        String sign = EncryptUtil.encryptMd5(sb.toString()).toUpperCase();
        LOGGER.debug("签名成功：{}", sign);
        return sign;

    }


    /**
     * 创建对象签名
     *
     * @param target
     * @param apiKey
     * @return
     * @throws Exception
     */
    public static String createSign(Object target, String apiKey) throws Exception {
        Field[] fields = target.getClass().getDeclaredFields();
        SortedMap<String, String> sortedMap = new TreeMap<>();
        for (Field f : fields
                ) {
            if (f.getType().toString().equals("class java.lang.String")) {
                String fieldName = f.getName();
                String value = getFieldValue(fieldName, target);
                if (StringUtils.isNotBlank(value)) {
                    sortedMap.put(fieldName, value);
                }
            }
        }
        return SignUtil.createSign(sortedMap, apiKey);
    }


    private static String getFieldValue(String fieldName, Object o) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + fieldName.substring(1);
        Method method = o.getClass().getMethod(getter);
        return (String) method.invoke(o);
    }

}
