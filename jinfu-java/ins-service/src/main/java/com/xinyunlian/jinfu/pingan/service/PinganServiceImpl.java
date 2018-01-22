package com.xinyunlian.jinfu.pingan.service;

import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.pingan.dto.PolicyDto;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Created by DongFC on 2016-09-01.
 */
@Service
public class PinganServiceImpl implements PinganService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PinganServiceImpl.class);

    @Value("${max.encrypt.block}")
    private int maxEncryptBlock;

    @Value("${max.decrypt.block}")
    private int maxDecryptBlock;

    @Value("${certificate.name}")
    private String certificateName;

    @Value("${key.pass}")
    private String keyPass;

    @Value("${certificate.format}")
    private String certificateFormat;

    @Override
    public String aesEncrypt(PolicyDto policyDto) throws BizServiceException{

        if (policyDto != null){

            StringBuilder stringBuilder = new StringBuilder();

            if (!StringUtils.isEmpty(policyDto.getStoreName())){
                stringBuilder.append(policyDto.getStoreName());
            }
            stringBuilder.append("^");
            if (!StringUtils.isEmpty(policyDto.getTobaccoCertificateNo())){
                stringBuilder.append(policyDto.getTobaccoCertificateNo());
            }
            stringBuilder.append("^");
            if (!StringUtils.isEmpty(policyDto.getAssuranceOrderNo())){
                stringBuilder.append(policyDto.getAssuranceOrderNo());
            }
            stringBuilder.append("^");
            if (!StringUtils.isEmpty(policyDto.getContactName())){
                stringBuilder.append(policyDto.getContactName());
            }
            stringBuilder.append("^");
            if (!StringUtils.isEmpty(policyDto.getContactMobile())){
                stringBuilder.append(policyDto.getContactMobile());
            }
            stringBuilder.append("^");
            if (!StringUtils.isEmpty(policyDto.getProvinceCnName())){
                stringBuilder.append(policyDto.getProvinceCnName());
            }
            stringBuilder.append("^");
            if (!StringUtils.isEmpty(policyDto.getProvinceCode())){
                stringBuilder.append(policyDto.getProvinceCode());
            }
            stringBuilder.append("^");
            if (!StringUtils.isEmpty(policyDto.getCityCnName())){
                stringBuilder.append(policyDto.getCityCnName());
            }
            stringBuilder.append("^");
            if (!StringUtils.isEmpty(policyDto.getCityCode())){
                stringBuilder.append(policyDto.getCityCode());
            }
            stringBuilder.append("^");
            if (!StringUtils.isEmpty(policyDto.getCountyCnName())){
                stringBuilder.append(policyDto.getCountyCnName());
            }
            stringBuilder.append("^");
            if (!StringUtils.isEmpty(policyDto.getCountyCode())){
                stringBuilder.append(policyDto.getCountyCode());
            }
            stringBuilder.append("^");
            if (!StringUtils.isEmpty(policyDto.getAddress())){
                stringBuilder.append(policyDto.getAddress());
            }

            String content = stringBuilder.toString();
            LOGGER.debug("加密前：" + content);
            try {
                HashMap keyMap = connect("classpath:ins_pingan/" + certificateName);
                if (!CollectionUtils.isEmpty(keyMap) && keyMap.containsKey("pubkey")){
                    LOGGER.debug("加密密钥和解密密钥：" + keyMap.get("pubkey"));
                    String encrypt = aesEncrypt(content, (PublicKey)keyMap.get("pubkey"));
                    String result = encrypt.replace("+","%2B");
                    LOGGER.debug("加密后+转换为%2B：" + encrypt.replace("+","%2B"));
                    return result;
                }
            } catch (Exception e) {
                LOGGER.error("加密失败", e);
                throw new BizServiceException(EErrorCode.INS_CERTIFICIATE_NOT_FOUND);
            }
        }

        return null;

    }

    /**
     * 证书加载
     */
    private HashMap connect(String datafile) throws Exception{
        HashMap keyMap = new HashMap();
        FileInputStream keystoreInstream = new FileInputStream(ResourceUtils.getFile(datafile));
        try {
            KeyStore ks = KeyStore.getInstance(certificateFormat);
            // 加载pfx文件
            ks.load(keystoreInstream, keyPass.toCharArray());

            Enumeration enumas = ks.aliases();
            String keyAlias = null;
            if (enumas.hasMoreElements()) {
                keyAlias = (String)enumas.nextElement();
                LOGGER.debug("alias=[" + keyAlias + "]");
            }
            LOGGER.debug("is key entry=" + ks.isKeyEntry(keyAlias));
            PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, keyPass.toCharArray());
            Certificate cert = ks.getCertificate(keyAlias);
            PublicKey pubkey = cert.getPublicKey();
            LOGGER.debug("cert class = " + cert.getClass().getName());
            LOGGER.debug("cert = " + cert);
            LOGGER.debug("public key = " + pubkey);
            LOGGER.debug("private key = " + prikey);
            keyMap.put("prikey", prikey);
            keyMap.put("pubkey", pubkey);
            return keyMap;
        } catch (Exception e) {
            LOGGER.error("证书获取密钥异常", e);
        } finally {
            keystoreInstream.close();
        }
        return null;

    }

    /**
     * base 64 encode
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    private String base64Encode(byte[] bytes){
        return Base64.encodeBase64String(bytes);
    }

    /**
     * base 64 decode
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    private byte[] base64Decode(String base64Code) throws Exception{
        return StringUtils.isEmpty(base64Code) ? null : Base64.decodeBase64(base64Code);
    }

    /**
     * RSA加密
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    private byte[] aesEncryptToBytes(String content, PublicKey encryptKey) throws Exception {

        Cipher cipher = Cipher.getInstance(encryptKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, encryptKey);
        int inputLen = content.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;

        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxEncryptBlock) {
                cache = cipher.doFinal(content.getBytes("utf-8"), offSet, maxEncryptBlock);
            } else {
                cache = cipher.doFinal(content.getBytes("utf-8"), offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxEncryptBlock;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * AES加密为base 64 code
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    private String aesEncrypt(String content, PublicKey encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * RSA解密
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey 解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    private String aesDecryptByBytes(byte[] encryptBytes, PrivateKey decryptKey) throws Exception {

        Cipher cipher = Cipher.getInstance(decryptKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, decryptKey);
        int inputLen = encryptBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;

        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxDecryptBlock) {
                cache = cipher.doFinal(encryptBytes, offSet, maxDecryptBlock);
            } else {
                cache = cipher.doFinal(encryptBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxDecryptBlock;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData,"utf-8");
    }

    /**
     * 将base 64 code AES解密
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    private String aesDecrypt(String encryptStr, PrivateKey decryptKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

}

