package com.ylfin.wallet.provider.shangqi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.ylfin.wallet.provider.shangqi.req.PaymentReq;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ShangqiApi {

    private static final Logger logger = LoggerFactory.getLogger(ShangqiApi.class);

    private static final Set<String> EXCLUDE_FIELDS;

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private ShangqiProperties shangqiProperties;

    static {
        EXCLUDE_FIELDS = new HashSet<>();
        EXCLUDE_FIELDS.add("sign");
        EXCLUDE_FIELDS.add("secret");
        EXCLUDE_FIELDS.add("apiGateway");
    }

    private RestOperations restOperations;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void setShangqiProperties(ShangqiProperties shangqiProperties) {
        this.shangqiProperties = shangqiProperties;
    }

    public void setRestOperations(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String paymentReq(PaymentReq paymentReq) {
        Map<String, String> map = objectMapper.convertValue(paymentReq, Map.class);
        map.put("createTime", DateTime.now().toString("yyyyMMddHHmmss"));
        map.put("agentCode", shangqiProperties.getAgentCode());
        map.put("intMhtCode", shangqiProperties.getIntMhtCode());
//        map.put("backEndUrl", Base64Utils.encodeToString(shangqiProperties.getBackEndUrl().getBytes(UTF_8)));
//        map.put("frontEndUrl", Base64Utils.encodeToString(shangqiProperties.getFrontEndUrl().getBytes(UTF_8)));
        TreeMap<String, String> sortedMap = new TreeMap<>(map);
        LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap();
        StringBuilder sb = new StringBuilder(shangqiProperties.getSecret());
        System.out.println("Map<String, String> map = new TreeMap();");
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!EXCLUDE_FIELDS.contains(key) && StringUtils.hasText(value)) {
                sb.append(key).append(value);
                System.out.println(String.format("map.put(\"%s\", \"%s\");", key, value));
                try {
                    String encodeStr = URLEncoder.encode(value, UTF_8.name());
                    multiValueMap.add(key, encodeStr);
                } catch (UnsupportedEncodingException e) {
                    // ignore
                }
            }
        }
        sb.append(shangqiProperties.getSecret());
//        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '&') {
//            sb.deleteCharAt(sb.length() - 1);
//        }
        String stringToSign = sb.toString();
        logger.info("String to sign: {}", stringToSign);
        String sign = DigestUtils.md5DigestAsHex(stringToSign.getBytes(UTF_8)).toUpperCase();
        multiValueMap.put("sign", Lists.newArrayList(sign));
        for (Map.Entry<String, List<String>> entry : multiValueMap.entrySet()) {
            System.out.println(String.format("<input type=\"text\" name=\"%s\" value=\"%s\">", entry.getKey(), entry.getValue().get(0)));
        }
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().uri(shangqiProperties.getApiGateway())
                .path("/ncOrder/create").queryParams(multiValueMap);
        return builder.build(false).toUriString();
    }


}
