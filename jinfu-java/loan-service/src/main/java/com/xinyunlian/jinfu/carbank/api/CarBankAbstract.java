package com.xinyunlian.jinfu.carbank.api;

import com.alibaba.fastjson.JSON;
import com.xinyunlian.jinfu.carbank.api.dto.request.CarBankRequest;
import com.xinyunlian.jinfu.carbank.api.dto.response.CarBankResponse;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.OkHttpUtil;
import com.xinyunlian.jinfu.common.util.ReflectionUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.security.Security;
import java.util.*;

/**
 * @author willwang
 */
public abstract class CarBankAbstract<T extends CarBankResponse> implements CarBankRequest{

    private static final String host = AppConfigUtil.getConfig("carbank.host");

    private static final String CALLER_CODE = AppConfigUtil.getConfig("carbank.caller.code");

    private static final String API_VERSION = AppConfigUtil.getConfig("carbank.api.version");

    private static final String SEC_KEY = AppConfigUtil.getConfig("carbank.sec.key");

    private String UID = "";

    private static final Logger LOGGER = LoggerFactory.getLogger(CarBankAbstract.class);

    protected <T> T execute(CarBankRequest<?> request){
        Class<?> requestClass = request.getClass();

        String requestUrl = host + this.getRequestUrl();

        T t = null;
        try {
            Map<String, Object> params = this.getMapByObject(request);
            Map<String, Object> sendParams = getSendParams(params);

            String rs = OkHttpUtil.get(requestUrl, sendParams, false);

            rs = AESCryptor.decrypt(rs, SEC_KEY);

            LOGGER.debug("车闪贷返回解码后的数据：{}", rs);

            Class<?> respClass = ReflectionUtil.getGenericParamClass(requestClass);
            t = JsonUtil.toObject(respClass, rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    private Map<String, Object> getMapByObject(Object obj){
        if(obj == null){
            return null;
        }

        Map<String, Object> map = new HashMap<>();

        Field[] fields = obj.getClass().getDeclaredFields();

        //final字段不参与参数传递
        for (Field field : fields) {
            if(Modifier.isFinal(field.getModifiers())){
                continue;
            }

            field.setAccessible(true);

            try {
                Object value = field.get(obj);
                if(value == null){
                    value = "";
                }
                map.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    private Map<String, Object> getSendParams(Map<String, Object> params){
        TreeMap<String, Object> sendParams = new TreeMap<>();
        sendParams.put("cid", CALLER_CODE + API_VERSION);
        sendParams.put("uid", UID);
        String q = JSON.toJSONString(params);
        q = AESCryptor.encrypt(q, SEC_KEY);//aes加密参数
        sendParams.put("q",q);
        String signStr = SignUtils.getSignStr(sendParams)+SEC_KEY;
        String sign = SignUtils.doMD5Sign(signStr);
        sendParams.put("sign",sign);

        return sendParams;
    }

}

class AESCryptor {

    private static final int KEY_BIT_SIZE = 128;

    private static final Charset CHAR_SET = Charset.forName("utf-8");

    //AES，简单分组，填充7
    private static final String ALGORITHM = "AES/ECB/PKCS7Padding";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 加密字符串。
     * @param target    原始字符串
     * @param key   密钥字符串
     * @return  加密结果字符串
     */
    public static String encrypt(String target, String key){
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, initKey(key));
            byte[] encryptResult = cipher.doFinal(target.getBytes(CHAR_SET));
            //兼容安卓环境的1.2codec
            String unsafeStr = new String(Base64.encodeBase64(encryptResult, false), CHAR_SET);
            return unsafeStr.replace('+','-').replace('/','_');
        } catch (Exception e) {
            throw new RuntimeException("敏感数据加密错误",e);
        }
    }

    /**
     * 解密字符串。
     * @param target    加密结果字符串
     * @param key   密钥字符串
     * @return  原始字符串
     */
    public static String decrypt(String target, String key){
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, initKey(key));
            String unsafeStr = target.replace('-','+').replace('_','/');
            byte[] decryptResult = cipher.doFinal(Base64.decodeBase64(unsafeStr.getBytes(CHAR_SET)));
            return new String(decryptResult, CHAR_SET);
        } catch (Exception e) {
            throw new RuntimeException("敏感数据解密错误",e);
        }
    }

    /**
     * 生成密钥字节数组，原始密钥字符串不足128位，补填0.
     * @param originalKey
     * @return
     */
    private static SecretKeySpec initKey(String originalKey){
        byte[] keys = originalKey.getBytes(CHAR_SET);

        byte[] bytes = new byte[KEY_BIT_SIZE / 8];
        for (int i = 0; i < bytes.length; i++) {
            if (keys.length > i) {
                bytes[i] = keys[i];
            }else{
                bytes[i] = 0;
            }
        }

        return new SecretKeySpec(bytes, "AES");
    }

}

class SignUtils {

    public static final String API_KEY = "aid";

    public static final String SIGN_KEY = "sign";

    public static String getSignStr(SortedMap<String, Object> signMap){
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = signMap.keySet().iterator();
        while(iterator.hasNext()) {

            String key = iterator.next();
            String value = (String)signMap.get(key);
            if(StringUtils.isNotEmpty(value)
                    && !key.equals(SIGN_KEY) && !key.equals(API_KEY)) {
                sb.append(key).append("=").append(value).append(";");
            }
        }
        return sb.substring(0, sb.length() - 1);
    }


    public static String doMD5Sign(String targetStr) {
        byte[] md5Result = DigestUtils.md5(targetStr.getBytes(Charsets.UTF_8));
        if (md5Result.length != 16) {
            throw new IllegalArgumentException("MD5加密结果字节数组错误");
        }
        Integer first = Math.abs(bytesToInt(md5Result, 0));
        Integer second = Math.abs(bytesToInt(md5Result, 4));
        Integer third = Math.abs(bytesToInt(md5Result, 8));
        Integer fourth = Math.abs(bytesToInt(md5Result, 12));
        return first.toString() + second.toString() + third.toString() + fourth.toString();
    }

    /**
     * 高位前，低位后，字节数组转INT
     * @param src
     * @param offset
     * @return
     */
    private static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) ( ((src[offset] & 0xFF)<<24)
                |((src[offset+1] & 0xFF)<<16)
                |((src[offset+2] & 0xFF)<<8)
                |(src[offset+3] & 0xFF));
        return value;
    }

}
