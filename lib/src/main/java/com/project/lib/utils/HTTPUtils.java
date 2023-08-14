package com.project.lib.utils;

import org.apache.http.*;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


public class HTTPUtils {
    private static final Logger logger = LoggerFactory.getLogger(HTTPUtils.class);

    public static final int	   SC_OK 						= HttpServletResponse.SC_OK;
    public static final int	   SC_INTERNAL_SERVER_ERROR		= HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

    public static final String POST 						= "POST";
    public static final String GET	 						= "GET";
    public static final String PUT	 						= "PUT";
    public static final String DELETE 						= "DELETE";

    public static final String CONTENT_TYPE 				= "Content-Type";
    public static final String AUTHORIZATION 				= "Authorization";

    public static final String HOST 						= "Host";

    public static final int    DEFAULT_TIMEOUT              = 30000;
    public static final int    DEFAULT_SOCKTIMEOUT          = 10000;

    /* */
    public static Map<String, List<String>> getHeaderMapWithHeaders(Header[] headers) {
        Map<String,List<String>> headerMap = new HashMap<String,List<String>>();
        if(headers != null) {
            for(int idx = 0; idx < headers.length; idx++) {
                Header header = headers[idx];

                String name  = header.getName();
                String value = header.getValue();

                if(name != null && !name.isEmpty() && value != null) {
                    if(headerMap.containsKey(name)) {
                        headerMap.get(name).add(value);
                    } else {
                        List<String> valueList = new ArrayList<String>();
                        valueList.add(value);

                        /* case-insensitive, so we change the name with lowercase to protect confusion */
                        headerMap.put(name.toLowerCase(), valueList);
                    }
                }
            }
        }
        return Collections.unmodifiableMap(headerMap);
    }

    /* */
    public static Map<String, List<String>> getRequestHeaderMap(HttpRequest request) {
        return getHeaderMapWithHeaders(request.getAllHeaders());
    }


    /* */
    public static Map<String, List<String>> getResponseHeaderMap(HttpResponse response) {
        return getHeaderMapWithHeaders(response.getAllHeaders());
    }

    /* */
    private static final HttpClientConnectionManager newConnectionManager() {
        @SuppressWarnings("deprecation")
        SSLContext sslContext = SSLContexts.createSystemDefault();

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslContext))
                .build();

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        /* [CHECK POINT] HTTP CONNECTION POOL */
//        cm.setMaxTotal(2000);
//        cm.setDefaultMaxPerRoute(500);
        cm.setMaxTotal(2000);
        cm.setDefaultMaxPerRoute(500);

        /* */
        logger.info("[*] remote http connection settings, maxTotal="+cm.getMaxTotal()+", defaultMaxPerRoute="+cm.getDefaultMaxPerRoute());

        return cm;
    }

    /* */
    public static final CloseableHttpClient newClient() {
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setConnectionManager(newConnectionManager());
        builder.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));

        /*
         * SocketConfig.custom()
	        		.setSndBufSize(sndBufSize)
	        		.setRcvBufSize(rcvBufSize)
	        		.setSoTimeout(10000)
	        		.setSoKeepAlive(true)
	        		.setTcpNoDelay(true)
	        		.setSoReuseAddress(true)
	        		.build()
         */
        SocketConfig.Builder socketConfigBuilder = SocketConfig.copy(SocketConfig.DEFAULT);
        socketConfigBuilder.setSoTimeout(10000);
        builder.setDefaultSocketConfig(socketConfigBuilder.build());

        builder.setRedirectStrategy(new RedirectStrategy() {
            @Override
            public boolean isRedirected(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws ProtocolException {
                return false;
            }

            @Override
            public HttpUriRequest getRedirect(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws ProtocolException {
                return null;
            }
        });

        return builder.build();
    }

    /* */
    public interface OnHttpResponseListener {
        public void onResponse(HttpResponse response) throws UnsupportedOperationException, IOException, Exception;
    }

    /* */
    private static void setRequestConfig(HttpRequestBase request, int socketTimeout, int connectTimeout) {
        final RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .build();
        request.setConfig(requestConfig);
    }

    /* */
    private static void setRequestUri(HttpRequestBase request, Map<String,Object> queryParamMap, String charset) throws URISyntaxException {
        /* */
        if (queryParamMap != null && !queryParamMap.isEmpty()) {
            URIBuilder newBuilder = new URIBuilder(request.getURI());
            if (charset != null) {
                newBuilder.setCharset(Charset.forName(charset));
            }

            System.out.println("[*]"+newBuilder.getQueryParams());

            List<NameValuePair> params = newBuilder.getQueryParams();

            for(Map.Entry<String,Object> entry : queryParamMap.entrySet()) {
                Object value = entry.getValue();
                NameValuePair nv = new BasicNameValuePair(entry.getKey(),value.toString());
                params.add(nv);
            }

            newBuilder.setParameters(params);

            /* */
            request.setURI(newBuilder.build());
        }
    }

    /* */
    private static void setRequestHeaders(HttpRequestBase request, Map<String,Object> headerMap, String contentType) {
        if (headerMap != null) {
            for(Map.Entry<String, Object> entry : headerMap.entrySet()) {
                request.setHeader(entry.getKey(),entry.getValue().toString());
            }
        }
        if (contentType != null) {
            request.setHeader(CONTENT_TYPE, contentType);
        }
    }

    /*
     * [CHECK POINT] HTTP CONNECTION POOL
     */
    private static final AtomicReference<CloseableHttpClient> HTTPCLIENT = new AtomicReference<CloseableHttpClient>(newClient());
    /* */
    private static void excuteHttpClient(HttpUriRequest request, OnHttpResponseListener listener) throws Exception {
        /*
         * [CHECK POINT] HTTP CONNECTION POOL
         *
         * final CloseableHttpClient httpclient = newClient();
         */
        final CloseableHttpClient httpclient = HTTPCLIENT.get();
        CloseableHttpResponse response = null;

        try {
            /* */
            response = httpclient.execute(request);
            if (listener != null) {
                listener.onResponse(response);
            }
        } catch(Exception e) {
            request.abort();
            throw e;
        } finally {
            try { if (response != null) response.close(); } catch(Exception e) { /* ignore */ }

            /*
             * [CHECK POINT] HTTP CONNECTION POOL
             * try { httpclient.close(); } catch(Exception e) { }
             */
            try { httpclient.getConnectionManager().closeExpiredConnections(); } catch(Exception e) { e.printStackTrace();/* ignore */ }
        }
    }


    /* */
    public static final void get(String uri,
                                 Map<String,Object> queryParamMap,
                                 String charset,
                                 Map<String,Object> headerMap,
                                 int socketTimeout, int connectTimeout,
                                 OnHttpResponseListener listener) throws Exception {


        /* */
        final HttpGet httpget = new HttpGet(uri);
        /* */
        setRequestConfig(httpget,socketTimeout,connectTimeout);
        setRequestUri(httpget, queryParamMap, charset);
        setRequestHeaders(httpget, headerMap, null);

        /* */
        excuteHttpClient(httpget,listener);
    }

    /* */
    public static final void post(String uri,
                                  byte[] contentBytes,
                                  String charset,
                                  Map<String,Object> queryParamMap,
                                  String contentType,
                                  Map<String,Object> headerMap,
                                  int socketTimeout, int connectTimeout,
                                  OnHttpResponseListener listener) throws Exception {

        final HttpPost httppost = new HttpPost(uri);

        setRequestConfig(httppost,socketTimeout,connectTimeout);
        setRequestUri(httppost, queryParamMap, charset);
        setRequestHeaders(httppost, headerMap, contentType);

        /* */
        if (contentBytes != null && contentBytes.length > 0) {
            httppost.setEntity(new ByteArrayEntity(contentBytes));
        }

        /* */
        excuteHttpClient(httppost,listener);
    }

    /* */
    public static final void postMultiForm(String uri,
                                           Map<String,Object> paramMap,
                                           String charset,
                                           Map<String,Object> queryParamMap,
                                           String contentType,
                                           Map<String,Object> headerMap,
                                           int socketTimeout, int connectTimeout,
                                           OnHttpResponseListener listener) throws Exception {

        final HttpPost httppost = new HttpPost(uri);
        setRequestConfig(httppost,socketTimeout,connectTimeout);
        setRequestUri(httppost, queryParamMap, charset);
        setRequestHeaders(httppost, headerMap, contentType);


        if (paramMap != null && !paramMap.isEmpty()) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            for(Map.Entry<String,Object> entry : paramMap.entrySet()) {
                Object value = entry.getValue();
                NameValuePair nv = new BasicNameValuePair(entry.getKey(),value.toString());
                nvps.add(nv);
            }

            /* */
            if (!nvps.isEmpty()) {
                if (charset != null) {
                    httppost.setEntity(new UrlEncodedFormEntity(nvps, charset));
                } else {
                    httppost.setEntity(new UrlEncodedFormEntity(nvps));
                }
            }
        }

        /* */
        excuteHttpClient(httppost,listener);
    }

    /* */
    public static final void put(String uri,
                                 byte[] contentBytes,
                                 String charset,
                                 Map<String,Object> queryParamMap,
                                 String contentType,
                                 Map<String,Object> headerMap,
                                 int socketTimeout, int connectTimeout,
                                 OnHttpResponseListener listener) throws Exception {

        final HttpPut httpput = new HttpPut(uri);
        setRequestConfig(httpput,socketTimeout,connectTimeout);
        setRequestUri(httpput, queryParamMap, charset);
        setRequestHeaders(httpput, headerMap, contentType);

        /* */
        if (contentBytes != null && contentBytes.length > 0) {
            httpput.setEntity(new ByteArrayEntity(contentBytes));
        }

        /* */
        excuteHttpClient(httpput,listener);
    }


    /* */
    public static final void delete(String uri,
                                    Map<String,Object> queryParamMap,
                                    String charset,
                                    Map<String,Object> headerMap,
                                    int socketTimeout, int connectTimeout,
                                    OnHttpResponseListener listener) throws Exception {


        /* */
        final HttpDelete httpdelete = new HttpDelete(uri);
        /* */
        setRequestConfig(httpdelete,socketTimeout,connectTimeout);
        setRequestUri(httpdelete, queryParamMap, charset);
        setRequestHeaders(httpdelete, headerMap, null);

        /* */
        excuteHttpClient(httpdelete,listener);
    }
}
