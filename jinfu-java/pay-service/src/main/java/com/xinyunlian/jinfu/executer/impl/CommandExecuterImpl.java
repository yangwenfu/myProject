package com.xinyunlian.jinfu.executer.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.OkHttpUtil;
import com.xinyunlian.jinfu.common.util.ReflectionUtil;
import com.xinyunlian.jinfu.domain.CommandRequest;
import com.xinyunlian.jinfu.domain.CommandResponse;
import com.xinyunlian.jinfu.domain.SignatureIgnore;
import com.xinyunlian.jinfu.domain.req.CommonCommandRequest;
import com.xinyunlian.jinfu.executer.AbstractCommandExecuter;
import com.xylpay.gateway.client.enums.CharsetTypeEnum;
import com.xylpay.gateway.client.service.ClientSignatureService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cong on 2016/5/29.
 */
@Component
public class CommandExecuterImpl extends AbstractCommandExecuter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandExecuterImpl.class);

    @Override
    public boolean isSupportedRequestType(Class<?> reqClass) {
        return CommonCommandRequest.class.isAssignableFrom(reqClass);
    }

    @Override
    protected String marshal(CommandRequest<?> request) {
        CommonCommandRequest req = (CommonCommandRequest) request;
        String rawSign = getSignatureMessage(req);
        try {
            req.setSignMsg(ClientSignatureService.genSignByRSA(rawSign, CharsetTypeEnum.UTF8));
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
        CommonCommandRequest req = (CommonCommandRequest) request;
        String requestJson = JsonUtil.toJson(request);
        try {
            return OkHttpUtil.postJson(req.getExecuteUrl(), requestJson, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    protected String getSignatureMessage(CommandRequest<?> request) {
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
                        return fieldName + "=" + valueStr;
                    } catch (IllegalAccessException e) {
                        LOGGER.warn("failed to access field:{}", field.getName());
                        return fieldName + "=" + StringUtils.EMPTY;
                    }
                })
                .collect(Collectors.toList());
        return Joiner.on("&").join(separators);
    }

}
