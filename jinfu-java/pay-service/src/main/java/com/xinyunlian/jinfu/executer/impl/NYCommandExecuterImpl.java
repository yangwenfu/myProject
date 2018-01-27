package com.xinyunlian.jinfu.executer.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;
import com.ny.lib.NyHttpClient;
import com.ny.lib.NySignatureData;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.OkHttpUtil;
import com.xinyunlian.jinfu.common.util.ReflectionUtil;
import com.xinyunlian.jinfu.domain.CommandRequest;
import com.xinyunlian.jinfu.domain.CommandResponse;
import com.xinyunlian.jinfu.domain.SignatureIgnore;
import com.xinyunlian.jinfu.domain.req.CommonCommandRequest;
import com.xinyunlian.jinfu.domain.req.NYRealAuthRequest;
import com.xinyunlian.jinfu.executer.AbstractCommandExecuter;
import com.xylpay.gateway.client.enums.CharsetTypeEnum;
import com.xylpay.gateway.client.service.ClientSignatureService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by cong on 2016/5/29.
 */
@Component
@Configuration
public class NYCommandExecuterImpl extends AbstractCommandExecuter {
    @Value(value = "${ny.isdev}")
    private Boolean isDev;

    public Boolean getDev() {
        return isDev;
    }

    private String mock(NYRealAuthRequest request){
        if(request.getCardNo().endsWith("0"))
            return "{\"sign\":\"6BA3487BCCAA8F895E3E15694B0EB7FFD5C50E12F8F657DF9CD06A74611C3CC69A0277ACCDEA24A7828146A5958B9B6CD02362694D378670BDBC8A06E80C8EAFCFF84D53774B51AB1CA7FC7E125B94CFB2A5E59FDAD9888C2F71E80DC9691BBC738C26B7B2078568D4A984C7E13DF65AF7F3A920675C57725E2FCB02CD812D0D\",\"respCode\":\"0000\",\"result\":\"\",\"respMsg\":\"仿真渠道模拟交易成功\"}";
        else
            return "{\"sign\":\"7929012CD8BACF5DDA052DEE990698718E14E11767D3EC59E6B988880BF2F511C0BB086E23EBE5C2D920D6630F9E41DB4F63E8437118549BE4787615FA5EBC8D8124025459B61C468265B97B8FC26AD6B9FB5A1C022494D9A2E799857BE42507F238F09CA972FE7BB7CD5F132FD2FBD2E1DC90A656805C970E34AD6EA2938A4E\",\"respCode\":\"2319\",\"result\":\"\",\"respMsg\":\" 仿真渠道模拟无效卡号\"}";
    }

    public void setDev(Boolean dev) {
        isDev = dev;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(NYCommandExecuterImpl.class);

    @Override
    public boolean isSupportedRequestType(Class<?> reqClass) {
        return NYRealAuthRequest.class.isAssignableFrom(reqClass);
    }

    @Override
    protected String marshal(CommandRequest<?> request) {
        NYRealAuthRequest req = (NYRealAuthRequest) request;
        String rawSign = getSignatureMessage(req);
        try {
            NySignatureData signatureData = new NySignatureData();
            req.setSign(signatureData.sign(rawSign, AppConfigUtil.getConfig("ny.jinfu.prikey")));
            //req.setSignMsg(ClientSignatureService.genSignByRSA(rawSign, CharsetTypeEnum.UTF8));
        } catch (Exception e) {
            LOGGER.error("failed to gen sign", e);
        }
        return JsonUtil.toJson(req);
    }

    @Override
    protected CommandResponse unmarshal(Class<?> respClass, String responseMsg) {
        return JsonUtil.toObject(respClass, responseMsg);
    }

    @Override
    protected String send(CommandRequest<?> request) {
        NYRealAuthRequest req = (NYRealAuthRequest) request;
        if(isDev){
            return mock(req);
        }
        Map<String, String> formData = getFormData(req);
        try {
            NyHttpClient hc = new NyHttpClient(req.getExecuteUrl(), 30000, 30000);
            hc.send(formData, "UTF-8");
            return hc.getResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Map<String, String> getFormData(NYRealAuthRequest request) {
        Map<String, String> formData = new HashMap<>();
        ReflectionUtil.getFieldsIncludingSuperClasses(request.getClass())
                .stream()
                .filter(field -> null != field.getAnnotation(JsonProperty.class))
                .forEach(field -> {
                    JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                    String fieldName = jsonProperty == null ? field.getName() : jsonProperty.value();
                    try {
                        field.setAccessible(true);
                        Object value = field.get(request);
                        String valueStr = null == value ? StringUtils.EMPTY : value.toString();
                        formData.put(fieldName, valueStr);
                    } catch (IllegalAccessException e) {
                        LOGGER.warn("failed to access field:{}", field.getName());
                        formData.put(fieldName, StringUtils.EMPTY);;
                    }
                });
        return formData;
    }

    protected String getSignatureMessage(NYRealAuthRequest request) {
        List<String> separators = ReflectionUtil.getFieldsIncludingSuperClasses(request.getClass())
                .stream()
                .filter(field -> null == field.getAnnotation(SignatureIgnore.class))
                .map(field -> {
                    JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                    String fieldName = jsonProperty == null ? field.getName() : jsonProperty.value();
                    try {
                        field.setAccessible(true);
                        Object value = field.get(request);
                        String valueStr = null == value ? StringUtils.EMPTY : value.toString();
                        return valueStr;
                    } catch (IllegalAccessException e) {
                        LOGGER.warn("failed to access field:{}", field.getName());
                        return StringUtils.EMPTY;
                    }
                })
                .collect(Collectors.toList());
        return Joiner.on("").join(separators);
    }

}
