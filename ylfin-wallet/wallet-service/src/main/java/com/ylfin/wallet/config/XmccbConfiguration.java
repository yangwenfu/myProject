package com.ylfin.wallet.config;

import com.ylfin.wallet.provider.xmccb.XmccbApiProvider;
import com.ylfin.wallet.provider.xmccb.XmccbProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(XmccbProperties.class)
public class XmccbConfiguration {

    private static final String TEXT_JSON_CHARSET_UTF_8 = "text/json;charset=UTF-8";

    @Autowired
    private XmccbProperties xmccbProperties;

    @Bean
    public XmccbApiProvider xmccbApiProvider(RestOperations restOperations) {
        XmccbApiProvider xmccbApiProvider = new XmccbApiProvider(xmccbProperties);
        xmccbApiProvider.setRestOperations(restOperations);
        return xmccbApiProvider;
    }

    @Bean
    public RestTemplateCustomizer xmccbRestTemplateCustomizer() {
        return new RestTemplateCustomizer() {
            @Override
            public void customize(RestTemplate restTemplate) {
                List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
                for (HttpMessageConverter<?> messageConverter : messageConverters) {
                    if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
                        MappingJackson2HttpMessageConverter converter = (MappingJackson2HttpMessageConverter) messageConverter;
                        List<MediaType> mediaTypes = converter.getSupportedMediaTypes();
                        for (MediaType mediaType : mediaTypes) {
                            if (TEXT_JSON_CHARSET_UTF_8.equals(mediaType.toString())) {
                                break;
                            }
                        }
                        ArrayList<MediaType> list = new ArrayList<>(converter.getSupportedMediaTypes());
                        list.add(MediaType.valueOf("text/json;charset=UTF-8"));
                        converter.setSupportedMediaTypes(list);
                        break;
                    }
                }
            }
        };
    }
}
