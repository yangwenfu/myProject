package com.xinyunlian.jinfu.api.validate.util;

import com.xinyunlian.jinfu.common.util.EncryptUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
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
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            Random rd = new Random();
            sb.append(Character.toString(chars.charAt(rd.nextInt(chars.length() - 1))));
        }
        return sb.toString();
    }

    /**
     * Description: 创建签名
     *
     * @param parameters
     * @param apiKey
     * @return
     */
    public static String createSign(SortedMap<String, String> parameters, String apiKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> en : parameters.entrySet()) {
            String v = en.getValue();
            if (StringUtils.isNotBlank(en.getKey()) && StringUtils.isNotBlank(v) && !"sign".equals(en.getKey()) && !"key".equals(en.getKey())) {
                sb.append(en.getKey()).append("=").append(v).append("&");
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
    public static String createSign(Object target, String apiKey) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, UnsupportedEncodingException, NoSuchAlgorithmException {
        Field[] fields = target.getClass().getDeclaredFields();
        SortedMap<String, String> sortedMap = new TreeMap<>();
        for (Field f : fields) {
            if (!"serialVersionUID".equals(f.getName()) && !"interface java.util.List".equals(f.getType().toString())) {
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
        String value = null;
        if (method.invoke(o) != null) {
            value = String.valueOf(method.invoke(o));
        }
        return value;
    }

}
