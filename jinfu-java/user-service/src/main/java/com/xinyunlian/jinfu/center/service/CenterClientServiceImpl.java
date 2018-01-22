package com.xinyunlian.jinfu.center.service;

import com.google.common.hash.Hashing;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by King on 2017/5/11.
 */
@Service
public class CenterClientServiceImpl implements CenterClientService{
    @Value("${member.center.domain}")
    private String MEMBER_CENTER_DOMAIN;
    @Value("${member.center.client.id}")
    private String MEMBER_CENTER_CLIENT_ID;
    @Value("${member.center.secret}")
    private String MEMBER_CENTER_SECRET;
 /*   private String MEMBER_CENTER_DOMAIN = "http://192.168.100.122:9999";
    private String MEMBER_CENTER_CLIENT_ID = "sparklr";
    private String MEMBER_CENTER_SECRET = "secret";*/

    private ClientCredentialsResourceDetails getResource(){
        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();

        resource.setAccessTokenUri(MEMBER_CENTER_DOMAIN + "/oauth/token");
        resource.setClientId(MEMBER_CENTER_CLIENT_ID);
        resource.setClientSecret(MEMBER_CENTER_SECRET);
        resource.setId("oauth2-resource");
        resource.setScope(Arrays.asList("trust", "read","write"));
        return resource;
    }

    private OAuth2AccessToken getAccessToken( ClientCredentialsResourceDetails resource){
        ClientCredentialsAccessTokenProvider provider = new ClientCredentialsAccessTokenProvider();
        OAuth2AccessToken accessToken = provider.obtainAccessToken(resource, new DefaultAccessTokenRequest());
        return  accessToken;
    }

    private OAuth2RestTemplate getRestTemplate(ClientCredentialsResourceDetails resource,OAuth2AccessToken accessToken){
        OAuth2RestTemplate template = new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext(accessToken));
        HttpHeaders httpHeaders = new HttpHeaders();

        List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
        basicNameValuePairs.add(new BasicNameValuePair("appSecret",MEMBER_CENTER_SECRET));
        Date now = new Date();
        final String nowString = String.valueOf(now.getTime());
        basicNameValuePairs.add(new BasicNameValuePair("sendTime",nowString));

        Collections.sort(basicNameValuePairs, new Comparator<BasicNameValuePair>() {
            public int compare(BasicNameValuePair o1, BasicNameValuePair o2) {
                return o1.getName().compareTo(o2.getName());
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == null) {
                    return false;
                }
                return String.valueOf(this).equals(String.valueOf(obj));
            }
        });

        final String str = URLEncodedUtils.format(basicNameValuePairs, "utf-8");

        template.setInterceptors(Arrays.<ClientHttpRequestInterceptor>asList(new ClientHttpRequestInterceptor(){
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                    throws IOException {
                request.getHeaders().set("Content-type", "application/json;charset=UTF-8");
                request.getHeaders().add("sign", Hashing.md5().hashBytes(str.getBytes()).toString());
                request.getHeaders().add("sendTime",String.valueOf(nowString));
                //request.getHeaders().add("api_visit_properties","userUUID,url");
                return execution.execute(request, body);
            }
        }));

        return template;
    }

    @Override
    public String get(String url){
        HttpHeaders httpHeaders = new HttpHeaders();
        ClientCredentialsResourceDetails resource = this.getResource();
        OAuth2AccessToken accessToken = this.getAccessToken(resource);
        OAuth2RestTemplate template = this.getRestTemplate(resource,accessToken);
        String ret = template.getForObject(MEMBER_CENTER_DOMAIN + "/oauth/api/" +
                "memberCeter/1.0/" + url, String.class, new HttpEntity<String>(httpHeaders));
        //"memberCeter/1.0/store/5380e338-87cb-4d38-bbda-058952dbd708", String.class, new HttpEntity<String>(httpHeaders));
        return ret;
    }

    @Override
    public String post(String url, String data) {
        ClientCredentialsResourceDetails resource = this.getResource();
        OAuth2AccessToken accessToken = this.getAccessToken(resource);
        OAuth2RestTemplate template = this.getRestTemplate(resource,accessToken);

        HttpEntity<byte[]> formEntity = new HttpEntity<>(data.getBytes(StandardCharsets.UTF_8));
        String ret = template.postForObject(MEMBER_CENTER_DOMAIN + "/oauth/api/" +
                "memberCeter/1.0/" + url,formEntity, String.class);
        return ret;
    }

    @Override
    public String put(String url, String data) {
        ClientCredentialsResourceDetails resource = this.getResource();
        OAuth2AccessToken accessToken = this.getAccessToken(resource);
        OAuth2RestTemplate template = this.getRestTemplate(resource,accessToken);

        HttpEntity<byte[]> formEntity = new HttpEntity<>(data.getBytes(StandardCharsets.UTF_8));
        ResponseEntity<String> resultEntity = template.exchange(MEMBER_CENTER_DOMAIN + "/oauth/api/" +
                        "memberCeter/1.0/"  + url, HttpMethod.PUT,
                formEntity, String.class);

        return resultEntity.getBody();
    }

    public static void main(String[] args) {
        CenterClientServiceImpl centerClientService = new CenterClientServiceImpl();
        //System.out.println(centerClientService.get("user/111/paper/1"));
        //System.out.println(centerClientService.get("store/5380e338-87cb-4d38-bbda-058952dbd708"));
       /* centerClientService.put("store/5380e338-87cb-4d38-bbda-058952dbd708",
                centerClientService.get("store/5380e338-87cb-4d38-bbda-058952dbd708"));*/
        /*System.out.println(centerClientService.post("user","{\"username\":\"13805844444\",\"mobile\":\"13805844444\"}"));*/
        //System.out.println(centerClientService.post("store","{\"userUUID\":\"f34dd30c-fab2-49be-822b-88f693b4a61d\",\"storeName\":\"店铺名称002\",\"province\":\"浙江省\",\"city\":\"湖州市\",\"area\":\"市辖区\",\"street\":\"街道\",\"storeAddress\":\"店铺地址\",\"areaGbCode\":330501,\"source\":\"金服\",\"tobaccoLicence\":\"13223\"}"));
        System.out.println(centerClientService.put("store/5380e338-87cb-4d38-bbda-058952dbd708","{\"userUUID\":\"f34dd30c-fab2-49be-822b-88f693b4a61d\",\"storeName\":\"店铺名称002\",\"province\":\"浙江省\",\"city\":\"湖州市\",\"area\":\"市辖区\",\"street\":\"街道\",\"storeAddress\":\"店铺地址\",\"areaGbCode\":330501,\"tobaccoLicence\":\"123\"}"));
    }
}
