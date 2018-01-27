package com.xinyunlian.jinfu;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

public class TestAd {
    private static final String DEFAULT_CHARSET = "UTF-8";
    static final String timestamp = new Timestamp(System.currentTimeMillis()) + "";
    //post请求接口
    public static String post(String url, Map<String, Object> params) {
        HttpClient httpclient = new DefaultHttpClient();
        String body = null;
        HttpPost post = postForm(url, params);
        body = invoke((DefaultHttpClient) httpclient, post);
        httpclient.getConnectionManager().shutdown();
        return body;
    }
    //get请求接口
    public static String get(String url, Map<String, Object> params) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String body = null;
        String getUrl = initParams(url, params);
        HttpGet get = new HttpGet(getUrl);
        String sign = createSign(params);
        get.setHeader("sign", sign);
        get.setHeader("timestamp", timestamp);
        body = invoke(httpclient, get);
        httpclient.getConnectionManager().shutdown();

        return body;
    }

    private static String invoke(DefaultHttpClient httpclient, HttpUriRequest httpost) {
        HttpResponse response = sendRequest(httpclient, httpost);
        String body = paseResponse(response);
        return body;
    }

    private static String paseResponse(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        String charset = EntityUtils.getContentCharSet(entity);
        String body = null;
        try {
            body = EntityUtils.toString(entity, "UTF-8");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    private static HttpResponse sendRequest(DefaultHttpClient httpclient, HttpUriRequest httpost) {
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static HttpPost postForm(String url, Map<String, Object> params) {
        HttpPost httpost = new HttpPost(url);
        String sign = createSign(params);
//      httpost.addHeader("debug", "debug"); //开启只有在测试接口中有效
        httpost.addHeader("sign", sign);
        httpost.addHeader("timestamp", timestamp);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            String[] arr = (params.get(key).toString()).replaceAll("[\\[\\]]", "").split(",");
            for (String value : arr) {
                nvps.add(new BasicNameValuePair(key, value.trim()));
            }
        }

        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return httpost;
    }

    /**
     * 请求参数格式化
     */
    private static String initParams(String url, Map<String, Object> params) {
        if (null == params || params.isEmpty()) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        if (url.indexOf("?") == -1) {
            sb.append("?");
        } else {
            sb.append("&");
        }
        boolean first = true;
        for (Entry<String, Object> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            String key = entry.getKey();
            String value = entry.getValue().toString();
            sb.append(key).append("=");
            if (!StringUtils.isEmpty(value)) {
                try {
                    sb.append(URLEncoder.encode(value, DEFAULT_CHARSET));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();

                }
            }
        }
        return sb.toString();
    }

    private static String createSign(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (params != null) {
            for (Entry<String, Object> entry : params.entrySet()) {

                map.put(entry.getKey(), getParameterValue(entry.getValue()));
            }
        }
        map.put("timestamp", timestamp);
        map.put("key", "e48e2efcdd0ab7676ef034889d8de0f5");//需替换成测试的key
        StringBuffer sb = new StringBuffer();
        Collection<String> keyset = map.keySet();

        List<String> list = new ArrayList<String>(keyset);
        Collections.sort(list);
        sb.append(list.get(0) + "=" + map.get(list.get(0)));
        for (int i = 1; i < list.size(); i++) {
            sb.append("&" + list.get(i) + "=" + map.get(list.get(i)));
        }
        System.out.println("sign=" + sb.toString());
        return TestAd.encodingMD5(sb.toString()).toUpperCase();
    }

    private static String encodingMD5(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(content.getBytes(Charset.forName("UTF-8")));
            return GetHashString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String GetHashString(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString();
    }

    private static String getParameterValue(Object v) {
        String result = "";
        if (v == null) {
            result = null;
        } else if (v instanceof String[]) {
            String[] strArr = (String[]) v;
            if (strArr.length > 0) {
                result = strArr[0];
            } else {
                result = null;
            }
        } else if (v instanceof String) {
            result = (String) v;
        } else {
            result = v.toString();
        }

        return result;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        testAd();
    }

    //测试广告接口方法
    public static void testAd() throws UnsupportedEncodingException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("isv_code", "ISV14933525360006");//需要替换成测试的isv_code
//        params.put("key", "ISV14933525360006");//需要替换成测试的isv_code
        params.put("store_name", URLEncoder.encode("小s磊火锅店", "UTF-8"));
        params.put("province", URLEncoder.encode("江苏", "UTF-8"));
        params.put("city", URLEncoder.encode("南京市", "UTF-8"));
        params.put("address", "2015050700000010");
        params.put("paytime", "2017-04-20 19:00:00");
        params.put("amount", 1000);
        params.put("order_no", "201705220001111111");
        params.put("pay_channel", "wxpay");
        params.put("mchid", "102810000022200030");
        params.put("appid", "wx100011000000000");
        params.put("type", "isvapi");
        params.put("return_type", "json");
        params.put("ad_show_type", "push");
        String str = TestAd.post("http://isvapitest.51wanquan.com"+ "/api/openad/getad", params);
        System.out.println(str);
    }
}