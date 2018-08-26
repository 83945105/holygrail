package pub.avalon.holygrail.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Http工具
 *
 * @author 白超
 * @date 2018/1/29
 */
public class HttpUtil {

    private HttpUtil() {
    }

    private static final Log LOGGER = LogFactory.getLog(HttpUtil.class);
    private static final String HTTPS_PREFIX = "https://";

    /**
     * 判断是否是AJAX请求
     *
     * @param request 请求
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    /**
     * get
     *
     * @param host
     * @param path
     * @param headers
     * @param params
     * @return
     * @throws Exception
     */
    public static HttpResponse doGet(String host, String path,
                                     Map<String, String> headers,
                                     Map<String, String> params)
            throws Exception {
        HttpClient httpClient = wrapClient(host, path);
        HttpGet request = new HttpGet(buildUrl(host, path, params));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("http get uri:" + request.getURI());
        }
        return httpClient.execute(request);
    }

    /**
     * post form
     *
     * @param host
     * @param path
     * @param headers
     * @param params
     * @param bodys
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String host, String path,
                                      Map<String, String> headers,
                                      Map<String, String> params,
                                      Map<String, String> bodys)
            throws Exception {
        HttpClient httpClient = wrapClient(host, path);
        HttpPost request = new HttpPost(buildUrl(host, path, params));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        if (bodys != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<>();

            for (String key : bodys.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
            formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            request.setEntity(formEntity);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("http post uri:" + request.getURI());
        }
        return httpClient.execute(request);
    }

    /**
     * Post String
     *
     * @param host
     * @param path
     * @param headers
     * @param params
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String host, String path,
                                      Map<String, String> headers,
                                      Map<String, String> params,
                                      String body)
            throws Exception {
        HttpClient httpClient = wrapClient(host, path);
        HttpPost request = new HttpPost(buildUrl(host, path, params));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        if (StringUtils.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("http post uri:" + request.getURI());
        }
        return httpClient.execute(request);
    }

    /**
     * Post stream
     *
     * @param host
     * @param path
     * @param headers
     * @param params
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String host, String path,
                                      Map<String, String> headers,
                                      Map<String, String> params,
                                      byte[] body)
            throws Exception {
        HttpClient httpClient = wrapClient(host, path);
        HttpPost request = new HttpPost(buildUrl(host, path, params));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        if (body != null) {
            request.setEntity(new ByteArrayEntity(body));
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("http post uri:" + request.getURI());
        }
        return httpClient.execute(request);
    }

    /**
     * Put String
     *
     * @param host
     * @param path
     * @param headers
     * @param params
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPut(String host, String path,
                                     Map<String, String> headers,
                                     Map<String, String> params,
                                     String body)
            throws Exception {
        HttpClient httpClient = wrapClient(host, path);
        HttpPut request = new HttpPut(buildUrl(host, path, params));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        if (StringUtils.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("http put uri:" + request.getURI());
        }
        return httpClient.execute(request);
    }

    /**
     * Put stream
     *
     * @param host
     * @param path
     * @param headers
     * @param params
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPut(String host, String path,
                                     Map<String, String> headers,
                                     Map<String, String> params,
                                     byte[] body)
            throws Exception {
        HttpClient httpClient = wrapClient(host, path);
        HttpPut request = new HttpPut(buildUrl(host, path, params));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        if (body != null) {
            request.setEntity(new ByteArrayEntity(body));
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("http put uri:" + request.getURI());
        }
        return httpClient.execute(request);
    }

    /**
     * Delete
     *
     * @param host
     * @param path
     * @param headers
     * @param params
     * @return
     * @throws Exception
     */
    public static HttpResponse doDelete(String host, String path,
                                        Map<String, String> headers,
                                        Map<String, String> params)
            throws Exception {
        HttpClient httpClient = wrapClient(host, path);
        HttpDelete request = new HttpDelete(buildUrl(host, path, params));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("http delete uri:" + request.getURI());
        }
        return httpClient.execute(request);
    }

    /**
     * 构建请求的 url
     *
     * @param host
     * @param path
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String buildUrl(String host, String path, Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        if (!StringUtils.isBlank(host)) {
            sbUrl.append(host);
        }
        if (!StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }
        if (null != params) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : params.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }
        return sbUrl.toString();
    }

    /**
     * 获取 HttpClient
     *
     * @param host
     * @param path
     * @return
     */
    private static HttpClient wrapClient(String host, String path) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        if (host != null && host.startsWith(HTTPS_PREFIX)) {
            return sslClient();
        } else if (StringUtils.isBlank(host) && path != null && path.startsWith(HTTPS_PREFIX)) {
            return sslClient();
        }
        return httpClient;
    }

    /**
     * 在调用SSL之前需要重写验证方法，取消检测SSL
     * 创建ConnectionManager，添加Connection配置信息
     *
     * @return HttpClient 支持https
     */
    private static HttpClient sslClient() {
        try {
            // 在调用SSL之前需要重写验证方法，取消检测SSL
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                }
            };
            SSLContext ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
            ctx.init(null, new TrustManager[]{trustManager}, null);
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
            // 创建Registry
            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
                    .setExpectContinueEnabled(Boolean.TRUE).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", socketFactory).build();
            // 创建ConnectionManager，添加Connection配置信息
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(connectionManager)
                    .setDefaultRequestConfig(requestConfig).build();
            return closeableHttpClient;
        } catch (KeyManagementException | NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 将结果转换成JSONObject
     *
     * @param httpResponse
     * @return
     * @throws IOException
     */
    public static JSONObject getJSONObject(HttpResponse httpResponse) throws IOException {
        HttpEntity entity = httpResponse.getEntity();
        String resp = EntityUtils.toString(entity, "UTF-8");
        EntityUtils.consume(entity);
        return JSON.parseObject(resp);
    }

}
