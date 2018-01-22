import org.apache.commons.io.IOUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by KimLL on 2016/8/30.
 */
public class HttpUtilsTest {
    public static final String UTF8 = "utf-8";
    public static final String GBK = "gbk";
    public static final String GB2312 = "gb2312";
    public static final String ISO88591 = "ISO-8859-1";
    public static int READ_TIMEOUT = 30000;
    public static int CONNECT_TIMEOUT = 30000;

    /**
     * post请求数据
     *
     * @param connectURL
     * @param param
     * @param charset
     * @return
     */
    public static String doPost(String connectURL, String param, String charset) {
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOut = null;
        URL url = null;
        HttpURLConnection httpPost = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            url = new URL(connectURL);
            httpPost = (HttpURLConnection) url.openConnection();
            httpPost.setRequestMethod("POST");
            httpPost.setDoInput(true);
            httpPost.setDoOutput(true);
            httpPost.setUseCaches(false);
            httpPost.setConnectTimeout(CONNECT_TIMEOUT);
            httpPost.setReadTimeout(READ_TIMEOUT);
            httpPost.setRequestProperty("Content-Type", "application/json");
            httpPost.connect();
            System.out.println(param);
            out = httpPost.getOutputStream();
            out.write(param.getBytes(charset));
            out.flush();
            in = httpPost.getInputStream();
            byteArrayOut = new ByteArrayOutputStream();
            byte[] buf = new byte[512];
            int l = 0;
            while ((l = in.read(buf)) != -1) {
                byteArrayOut.write(buf, 0, l);
            }
            bytes = byteArrayOut.toByteArray();
            return new String(bytes, charset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(byteArrayOut);
            close(out);
            close(in);
            close(httpPost);
        }
        return null;
    }

    public static Map<String,String> doPostCookie(String connectURL, String param, String charset,String cookie) {
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOut = null;
        URL url = null;
        HttpURLConnection httpPost = null;
        OutputStream out = null;
        InputStream in = null;
        String responseCookie = "";
        try {
            url = new URL(connectURL);
            httpPost = (HttpURLConnection) url.openConnection();
            httpPost.setRequestMethod("POST");
            httpPost.setDoInput(true);
            httpPost.setDoOutput(true);
            //httpPost.setUseCaches(false);
            httpPost.setConnectTimeout(CONNECT_TIMEOUT);
            httpPost.setReadTimeout(READ_TIMEOUT);
            httpPost.setRequestProperty("Content-Type", "application/json");
            if(cookie != null) {
                httpPost.setRequestProperty("Cookie", cookie);// 给服务器送登录后的cookie
            }
            httpPost.connect();
            System.out.print(param);
            out = httpPost.getOutputStream();
            out.write(param.getBytes(charset));
            out.flush();
            in = httpPost.getInputStream();
            byteArrayOut = new ByteArrayOutputStream();
            byte[] buf = new byte[512];
            int l = 0;
            while ((l = in.read(buf)) != -1) {
                byteArrayOut.write(buf, 0, l);
            }
            bytes = byteArrayOut.toByteArray();
            responseCookie = httpPost.getHeaderField("Set-Cookie");// 取到所用的Cookie
            Map<String,String> map = new HashMap<>() ;
            map.put("body", new String(bytes, charset));
            map.put("cookie",responseCookie);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(byteArrayOut);
            close(out);
            close(in);
            close(httpPost);
        }
        return null;
    }

    public static String doPostSSL(String connectURL, Map<String, String> params, String charset) throws MalformedURLException, IOException, UnsupportedEncodingException {
        _ignoreSSL();
        return doPost(connectURL, params, charset);
    }

    /**
     * post请求数据
     *
     * @param connectURL
     * @param
     * @param charset
     * @return
     */
    public static String doPost(String connectURL, Map<String, String> params, String charset) {
        String param = "";
        if (params != null && !params.isEmpty()) {
            StringBuffer paramBuf = new StringBuffer();
            for (Iterator<String> it = params.keySet().iterator(); it.hasNext();) {
                String key = it.next();
                String value = params.get(key);
                paramBuf.append("&").append(key).append("=").append(value);
            }
            param = paramBuf.substring(1);
        }
        System.out.println("post url:" + connectURL);
        System.out.println("post data:" + param);
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOut = null;
        URL url = null;
        HttpURLConnection httpPost = null;
        OutputStream out = null;
        BufferedInputStream in = null;
        try {
            url = new URL(connectURL);
            httpPost = (HttpURLConnection) url.openConnection();
            httpPost.setRequestMethod("POST");
            httpPost.setDoInput(true);
            httpPost.setDoOutput(true);
            httpPost.setUseCaches(false);
            httpPost.setConnectTimeout(CONNECT_TIMEOUT);
            httpPost.setReadTimeout(READ_TIMEOUT);
            httpPost.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpPost.connect();
            out = httpPost.getOutputStream();
            out.write(param.getBytes(charset));
            out.flush();

            try {
                in = new BufferedInputStream(httpPost.getInputStream());
            } catch (IOException e) {
                in = new BufferedInputStream(httpPost.getErrorStream());
            }

            byteArrayOut = new ByteArrayOutputStream();
            byte[] buf = new byte[512];
            int l = 0;
            while ((l = in.read(buf)) != -1) {
                byteArrayOut.write(buf, 0, l);
            }
            bytes = byteArrayOut.toByteArray();
            return new String(bytes, charset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(byteArrayOut);
            close(in);
            close(out);
            close(httpPost);
        }
        return null;
    }

    /**
     * Get请求数据
     *
     * @param connectURL
     * @param
     * @param charset
     * @return
     */
    public static String doGet(String connectURL, String charset) {
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOut = null;
        URL url = null;
        HttpURLConnection httpGet = null;
        InputStream in = null;
        try {
            url = new URL(connectURL);
            httpGet = (HttpURLConnection) url.openConnection();
            httpGet.setConnectTimeout(CONNECT_TIMEOUT);
            httpGet.setReadTimeout(READ_TIMEOUT);
            httpGet.connect();
            in = httpGet.getInputStream();
            byteArrayOut = new ByteArrayOutputStream();
            byte[] buf = new byte[512];
            int l = 0;
            while ((l = in.read(buf)) != -1) {
                byteArrayOut.write(buf, 0, l);
            }
            bytes = byteArrayOut.toByteArray();
            return bytes != null ? new String(bytes, charset) : null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(byteArrayOut);
            close(in);
            close(httpGet);
        }
        return null;
    }

    private static void close(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
                stream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void close(HttpURLConnection httpConn){
        if(httpConn != null){
            httpConn.disconnect();
        }
    }

    private static HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String s, SSLSession sslsession) {
            return true;
        }
    };

    /**
     * 忽略SSL
     */
    private static void _ignoreSSL() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            } };

            // Install the all-trusting trust manager

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
        } catch (KeyManagementException ex) {
            Logger.getLogger(HttpUtilsTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HttpUtilsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int postBody(String urlPath, String json) throws Exception {
        HttpURLConnection urlConnection = null;
        try{
            // Configure and open a connection to the site you will send the request
            URL url = new URL(urlPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            // 设置doOutput属性为true表示将使用此urlConnection写入数据
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("accept", "application/json");
            urlConnection.setRequestProperty("connection", "Keep-Alive");
            // 定义待写入数据的内容类型，我们设置为application/x-www-form-urlencoded类型
            urlConnection.setRequestProperty("content-type", "application/json");
            // 得到请求的输出流对象
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            // 把数据写入请求的Body
            out.write(json);
            out.flush();
            out.close();

            // 从服务器读取响应
            InputStream inputStream = urlConnection.getInputStream();
            String encoding = urlConnection.getContentEncoding();
            String body = IOUtils.toString(inputStream, encoding);
            if(urlConnection.getResponseCode()==200){
                return 200;
            }else{
                System.out.println(urlConnection.getErrorStream().toString());
                throw new Exception(body);
            }
        }catch(IOException e){

            throw e;
        }
    }
}
