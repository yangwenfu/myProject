package com.xinyunlian.jinfu.spider.util;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.spider.dto.HttpContent;
import com.xinyunlian.jinfu.spider.enums.EHttpMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;

/**
 * Created by bright on 2016/12/26.
 */
public class Crawler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Crawler.class);

    private static final String FAKED_UA = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0)";

    private Executor executor;

    public Crawler(String proxyIp, String proxyPort, String proxyUser, String proxyPass) {
        HttpHost proxy = null;
        try{
            proxy = new HttpHost(proxyIp, Integer.parseInt(proxyPort));
        }catch (Exception e){
        }
        HttpClientBuilder builder = HttpClientBuilder.create().setUserAgent(FAKED_UA);
        if(Objects.nonNull(proxy)){
            builder.setProxy(proxy);
            if(StringUtils.isNotEmpty(proxyUser)){
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                Credentials credentials = new UsernamePasswordCredentials(proxyUser, proxyPass);
                AuthScope authScope = new AuthScope(proxy.getHostName(), proxy.getPort());
                credentialsProvider.setCredentials(authScope, credentials);
                builder.setDefaultCredentialsProvider(credentialsProvider);
            }
        }
        CloseableHttpClient httpClient = builder.build();
        executor = Executor.newInstance(httpClient);
    }

    public HttpContent getPage(String aUrl, Map<String, String> parameter, EHttpMethod method) throws Exception {
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("{} page {} with {}", method.getCode(), aUrl, JsonUtil.toJson(parameter));
        }
        Request request = null;

        Form form = Form.form();
        if (parameter != null) {
            parameter.forEach((key, value) -> {
                form.add(key, value);
            });
        }

        switch (method) {
            case GET:
                URI uri = new URIBuilder(aUrl).addParameters(form.build()).build();
                request = Request.Get(uri);
                break;
            case POST:
                request = Request.Post(aUrl);
                request.bodyForm(form.build());
                break;
            default:
                LOGGER.error("Method[{}] not support", method.getCode());
                throw new IllegalArgumentException("Method not support");
        }
        Content content = executor.execute(request.useExpectContinue()).returnContent();
        Charset charset = content.getType().getCharset();
        if(Objects.isNull(charset)){
            charset = Charset.defaultCharset();
        }
        return new HttpContent(charset, content.asBytes());
    }

}
