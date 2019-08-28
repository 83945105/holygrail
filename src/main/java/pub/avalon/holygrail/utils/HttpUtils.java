package pub.avalon.holygrail.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @author baichao
 */
public final class HttpUtils {

    private final static HttpUtils HTTP_UTILS = new HttpUtils();

    private static final String HTTPS_PREFIX = "https://";

    private HttpClient defaultHttpClient = HttpClientBuilder
            .create()
            .build();

    private HttpClient defaultHttpsClient = sslClient();

    private HttpUtils() {
    }

    public static HttpUtils getInstance() {
        return HTTP_UTILS;
    }

    public static HttpUtils newInstance() {
        return new HttpUtils();
    }

    public static HttpUtils newInstance(HttpClient defaultHttpClient) {
        return new HttpUtils().setDefaultHttpClient(defaultHttpClient);
    }

    private HttpResponse execute(HttpUriRequest httpUriRequest) throws IOException {
        String path = httpUriRequest.getURI().getPath();
        if (path.startsWith(HTTPS_PREFIX)) {
            return defaultHttpsClient.execute(httpUriRequest);
        }
        return defaultHttpClient.execute(httpUriRequest);
    }

    public HttpResponse doGet(URI uri, Header[] headers, RequestConfig requestConfig) throws IOException {
        HttpGet httpGet = new HttpGet(uri);
        if (headers != null) {
            httpGet.setHeaders(headers);
        }
        if (requestConfig != null) {
            httpGet.setConfig(requestConfig);
        }
        return execute(httpGet);
    }

    public HttpResponse doGet(String url, Collection<UrlParameter> urlParameters, Header[] headers, RequestConfig requestConfig) throws IOException {
        return doGet(URI.create(assembleUrlParameters(url, urlParameters)), headers, requestConfig);
    }

    public HttpResponse doGet(String url, Collection<UrlParameter> urlParameters, Header[] headers) throws IOException {
        return doGet(url, urlParameters, headers, null);
    }

    public HttpResponse doGet(String url, Collection<UrlParameter> urlParameters) throws IOException {
        return doGet(url, urlParameters, null, null);
    }

    public HttpResponse doGet(String url, Map<String, String> urlParameterMap, Header[] headers, RequestConfig requestConfig) throws IOException {
        return doGet(url, convertUrlParameters(urlParameterMap), headers, requestConfig);
    }

    public HttpResponse doGet(String url, Map<String, String> urlParameterMap, Header[] headers) throws IOException {
        return doGet(url, urlParameterMap, headers, null);
    }

    public HttpResponse doGet(String url, Map<String, String> urlParameterMap) throws IOException {
        return doGet(url, urlParameterMap, null, null);
    }

    public HttpResponse doGet(String url) throws IOException {
        return doGet(url, Collections.emptyList(), null, null);
    }

    public HttpResponse doPost(URI uri, HttpEntity httpEntity, Header[] headers, RequestConfig requestConfig) throws IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (httpEntity != null) {
            httpPost.setEntity(httpEntity);
        }
        if (headers != null) {
            httpPost.setHeaders(headers);
        }
        if (requestConfig != null) {
            httpPost.setConfig(requestConfig);
        }
        return execute(httpPost);
    }

    public HttpResponse doPost(String url, Collection<UrlParameter> urlParameters, HttpEntity httpEntity, Header[] headers, RequestConfig requestConfig) throws IOException {
        return doPost(URI.create(assembleUrlParameters(url, urlParameters)), httpEntity, headers, requestConfig);
    }

    public HttpResponse doPost(String url, Map<String, String> urlParameterMap, HttpEntity httpEntity, Header[] headers, RequestConfig requestConfig) throws IOException {
        return doPost(url, convertUrlParameters(urlParameterMap), httpEntity, headers, requestConfig);
    }

    public HttpResponse doPost(String url, HttpEntity httpEntity, Header[] headers, RequestConfig requestConfig) throws IOException {
        return doPost(url, Collections.emptyList(), httpEntity, headers, requestConfig);
    }

    public HttpResponse doPost(String url, HttpEntity httpEntity, Header[] headers) throws IOException {
        return doPost(url, Collections.emptyList(), httpEntity, headers, null);
    }

    public HttpResponse doPost(String url, HttpEntity httpEntity) throws IOException {
        return doPost(url, Collections.emptyList(), httpEntity, null, null);
    }

    public HttpResponse doPost(String url) throws IOException {
        return doPost(url, Collections.emptyList(), null, null, null);
    }

    public HttpResponse post(String url, Map<String, String> parameters) throws IOException {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairs, StandardCharsets.UTF_8);
        formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
        return doPost(url, formEntity);
    }

    public HttpResponse doPut(URI uri, HttpEntity httpEntity, Header[] headers, RequestConfig requestConfig) throws IOException {
        HttpPut httpPut = new HttpPut(uri);
        if (httpEntity != null) {
            httpPut.setEntity(httpEntity);
        }
        if (headers != null) {
            httpPut.setHeaders(headers);
        }
        if (requestConfig != null) {
            httpPut.setConfig(requestConfig);
        }
        return execute(httpPut);
    }

    public HttpResponse doPut(String url, Collection<UrlParameter> urlParameters, HttpEntity httpEntity, Header[] headers, RequestConfig requestConfig) throws IOException {
        return doPut(URI.create(assembleUrlParameters(url, urlParameters)), httpEntity, headers, requestConfig);
    }

    public HttpResponse doPut(String url, Map<String, String> urlParameterMap, HttpEntity httpEntity, Header[] headers, RequestConfig requestConfig) throws IOException {
        return doPut(url, convertUrlParameters(urlParameterMap), httpEntity, headers, requestConfig);
    }

    public HttpResponse doPut(String url, HttpEntity httpEntity, Header[] headers, RequestConfig requestConfig) throws IOException {
        return doPut(url, Collections.emptyList(), httpEntity, headers, requestConfig);
    }

    public HttpResponse doPut(String url, HttpEntity httpEntity, Header[] headers) throws IOException {
        return doPut(url, Collections.emptyList(), httpEntity, headers, null);
    }

    public HttpResponse doPut(String url, HttpEntity httpEntity) throws IOException {
        return doPut(url, Collections.emptyList(), httpEntity, null, null);
    }

    public HttpResponse doPut(String url) throws IOException {
        return doPut(url, Collections.emptyList(), null, null, null);
    }

    public HttpResponse doDelete(URI uri, Header[] headers, RequestConfig requestConfig) throws IOException {
        HttpDelete httpDelete = new HttpDelete(uri);
        if (headers != null) {
            httpDelete.setHeaders(headers);
        }
        if (requestConfig != null) {
            httpDelete.setConfig(requestConfig);
        }
        return execute(httpDelete);
    }

    public HttpResponse doDelete(String url, Collection<UrlParameter> urlParameters, Header[] headers, RequestConfig requestConfig) throws IOException {
        return doDelete(URI.create(assembleUrlParameters(url, urlParameters)), headers, requestConfig);
    }

    public HttpResponse doDelete(String url, Collection<UrlParameter> urlParameters, Header[] headers) throws IOException {
        return doDelete(url, urlParameters, headers, null);
    }

    public HttpResponse doDelete(String url, Collection<UrlParameter> urlParameters) throws IOException {
        return doDelete(url, urlParameters, null, null);
    }

    public HttpResponse doDelete(String url, Map<String, String> urlParameterMap, Header[] headers, RequestConfig requestConfig) throws IOException {
        return doDelete(url, convertUrlParameters(urlParameterMap), headers, requestConfig);
    }

    public HttpResponse doDelete(String url, Map<String, String> urlParameterMap, Header[] headers) throws IOException {
        return doDelete(url, urlParameterMap, headers, null);
    }

    public HttpResponse doDelete(String url, Map<String, String> urlParameterMap) throws IOException {
        return doDelete(url, urlParameterMap, null, null);
    }

    public HttpResponse doDelete(String url) throws IOException {
        return doDelete(url, Collections.emptyList(), null, null);
    }

    public HttpUtils setDefaultHttpClient(HttpClient defaultHttpClient) {
        this.defaultHttpClient = defaultHttpClient;
        this.defaultHttpsClient = defaultHttpClient;
        return this;
    }

    public static Collection<UrlParameter> convertUrlParameters(Map<String, String> urlParameterMap) {
        if (urlParameterMap == null || urlParameterMap.size() == 0) {
            return Collections.emptyList();
        }
        List<UrlParameter> urlParameters = new ArrayList<>(urlParameterMap.size());
        for (Map.Entry<String, String> entry : urlParameterMap.entrySet()) {
            urlParameters.add(new UrlParameter(entry.getKey(), entry.getValue()));
        }
        return urlParameters;
    }

    public static String assembleUrlParameters(String url, Collection<UrlParameter> urlParameters) throws UnsupportedEncodingException {
        if (url == null || url.length() == 0) {
            return url;
        }
        if (urlParameters == null || urlParameters.size() == 0) {
            return url;
        }
        StringBuilder sb = new StringBuilder();
        for (UrlParameter urlParameter : urlParameters) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            if (urlParameter.getKey() == null || urlParameter.getKey().length() == 0) {
                continue;
            }
            if (urlParameter.getValue() == null || urlParameter.getValue().length() == 0) {
                sb.append(urlParameter.getKey()).append("=");
                continue;
            }
            sb.append(urlParameter.getKey()).append("=").append(URLEncoder.encode(urlParameter.getValue(), StandardCharsets.UTF_8.name()));
        }
        return sb.insert(0, "?").insert(0, url).toString();
    }

    private static HttpClient sslClient() {
        try {
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
            SSLContext sslContext = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
                    .setExpectContinueEnabled(Boolean.TRUE).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Collections.singletonList(AuthSchemes.BASIC)).build();
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", socketFactory).build();
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            return HttpClients.custom().setConnectionManager(connectionManager)
                    .setDefaultRequestConfig(requestConfig).build();
        } catch (KeyManagementException | NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public final static class UrlParameter {

        private String key;

        private String value;

        public UrlParameter() {
        }

        public UrlParameter(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public final static class HttpConfig {

        private Map<String, String> urlParameters;

        private Map<String, String> headers;

        public Map<String, String> getUrlParameters() {
            return urlParameters;
        }

        public HttpConfig setUrlParameters(Map<String, String> urlParameters) {
            this.urlParameters = urlParameters;
            return this;
        }

        public HttpConfig addUrlParameter(String key, String value) {
            if (urlParameters == null) {
                urlParameters = new LinkedHashMap<>();
            }
            urlParameters.put(key, value);
            return this;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public HttpConfig setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public HttpConfig addHeader(String key, String value) {
            if (headers == null) {
                headers = new LinkedHashMap<>();
            }
            headers.put(key, value);
            return this;
        }
    }
}