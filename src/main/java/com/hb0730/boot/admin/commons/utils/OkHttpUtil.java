package com.hb0730.boot.admin.commons.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The enum Http method.
 */
enum HttpMethod {
    /**
     * Get http method.
     */
    GET,
    /**
     * Post http method.
     */
    POST,
    /**
     * Delete http method.
     */
    DELETE,
    /**
     * Put http method.
     */
    PUT,
    /**
     * Patch http method.
     */
    PATCH
}

/**
 * OkHttpUtil
 * http请求工具类
 *
 * @author qiaojinfeng3
 * @date 2019 /5/28 14:13
 */
@Slf4j
public class OkHttpUtil {
    /**
     * okHttpClientThreadLocal
     */
    // private static final ThreadLocal<OkHttpClient> OKHTTP_CLIENT_THREADLOCAL = new ThreadLocal<>();
    private static final MediaType FORM_CONTENT_TYPE = MediaType.get("application/x-www-form-urlencoded");
    private static final MediaType JSON_CONTENT_TYPE = MediaType.get("application/json");

    /**
     * Get response.
     *
     * @param url    the url
     * @param params the params
     * @return the response
     * @throws IOException the io exception
     */
    public static Optional<Response> get(String url, Map<String, String> params) throws IOException {
        OkHttpClient client = getClient();
        // requestBuilder
        Request.Builder requestBuilder = getRequestBuilder(HttpMethod.GET, url, params, null);
        return getResponse(client, requestBuilder);
    }

    /**
     * Get optional.
     *
     * @param url     the url
     * @param params  the params
     * @param headers the headers
     * @return the optional
     * @throws IOException the io exception
     */
    public static Optional<Response> get(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        OkHttpClient client = getClient();
        // requestBuilder
        Request.Builder requestBuilder = getRequestBuilder(HttpMethod.GET, url, params, headers);
        return getResponse(client, requestBuilder);
    }

    /**
     * Get optional.
     *
     * @param <T>     the type parameter
     * @param url     the url
     * @param t       the t
     * @param headers the headers
     * @return the optional
     * @throws IOException the io exception
     */
    public static <T> Optional<Response> get(String url, T t, Map<String, String> headers) throws IOException {
        OkHttpClient client = getClient();
        // requestBuilder
        Request.Builder requestBuilder = getRequestBuilder(HttpMethod.GET, null, url, t, headers);
        return getResponse(client, requestBuilder);
    }

    /**
     * Gets async.
     *
     * @param <T>      the type parameter
     * @param url      the url
     * @param t        the t
     * @param headers  the headers
     * @param callback the callback
     */
    public static <T> void getAsync(String url, T t, Map<String, String> headers, Callback callback) {
        OkHttpClient client = getClient();
        // requestBuilder
        Request.Builder requestBuilder = getRequestBuilder(HttpMethod.GET, null, url, t, headers);
        getResponseAsync(client, requestBuilder, callback);
    }

    /**
     * Post form body optional.
     *
     * @param url    the url
     * @param params the params
     * @return the optional
     * @throws IOException the io exception
     */
    public static Optional<Response> postFormBody(String url, Map<String, String> params) throws IOException {
        OkHttpClient client = getClient();
        // requestBuilder
        Request.Builder requestBuilder = getRequestBuilder(HttpMethod.POST, url, params, null);
        return getResponse(client, requestBuilder);
    }

    /**
     * Post form body optional.
     *
     * @param url     the url
     * @param params  the params
     * @param headers the headers
     * @return the optional
     * @throws IOException the io exception
     */
    public static Optional<Response> postFormBody(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        OkHttpClient client = getClient();
        // requestBuilder
        Request.Builder requestBuilder = getRequestBuilder(HttpMethod.POST, url, params, headers);
        return getResponse(client, requestBuilder);
    }

    /**
     * Post form body optional.
     *
     * @param <T>     the type parameter
     * @param url     the url
     * @param t       the t
     * @param headers the headers
     * @return the optional
     * @throws IOException the io exception
     */
    public static <T> Optional<Response> postFormBody(String url, T t, Map<String, String> headers) throws IOException {
        OkHttpClient client = getClient();
        // requestBuilder
        Request.Builder requestBuilder = getRequestBuilder(HttpMethod.POST, url, t, headers);
        return getResponse(client, requestBuilder);
    }

    /**
     * Post json optional.
     *
     * @param <T>     the type parameter
     * @param url     the url
     * @param t       the t
     * @param headers the headers
     * @return the optional
     * @throws IOException the io exception
     */
    public static <T> Optional<Response> postJson(String url, T t, Map<String, String> headers) throws IOException {
        OkHttpClient client = getClient();
        // requestBuilder
        Request.Builder requestBuilder = getRequestBuilder(HttpMethod.POST, JSON_CONTENT_TYPE, url, t, headers);
        return getResponse(client, requestBuilder);
    }

    /**
     * Post json async.
     *
     * @param <T>      the type parameter
     * @param url      the url
     * @param t        the t
     * @param headers  the headers
     * @param callback the callback
     * @throws IOException the io exception
     */
    public static <T> void postJsonAsync(String url, T t, Map<String, String> headers, Callback callback) {
        OkHttpClient client = getClient();
        // requestBuilder
        Request.Builder requestBuilder = getRequestBuilder(HttpMethod.POST, JSON_CONTENT_TYPE, url, t, headers);
        getResponseAsync(client, requestBuilder, callback);
    }

    /**
     * Gets response.
     *
     * @param client         the client
     * @param requestBuilder the request builder
     * @return the response
     */
    private static Optional<Response> getResponse(OkHttpClient client, Request.Builder requestBuilder) throws IOException {
        try {
            Response response = client.newCall(requestBuilder.build()).execute();
            return Optional.of(response);
        } finally {
            // 必须回收自定义的ThreadLocal变量,尤其在线程池场景下,线程经常会被复用,如果不清理自定义的 ThreadLocal变量,可能会影响后续业务逻辑和造成内存泄露等问题,尽量在代理中使用try-finally块进行回收
            // OKHTTP_CLIENT_THREADLOCAL.remove();
        }
    }

    private static void getResponseAsync(OkHttpClient client, Request.Builder requestBuilder, Callback responseCallback) {
        client.newCall(requestBuilder.build()).enqueue(responseCallback);
    }

    /**
     * Gets request builder.
     *
     * @param httpMethod the http method
     * @param url        the url
     * @param params     the params
     * @param headers    the headers
     * @return the request builder
     */
    private static Request.Builder getRequestBuilder(HttpMethod httpMethod, String url, Map<String, String> params, Map<String, String> headers) {
        // requestBuilder
        Request.Builder requestBuilder = new Request.Builder();
        // params封装
        if (httpMethod == HttpMethod.GET) {
            StringBuilder stringBuilder = new StringBuilder(url);
            if (null != params && !params.isEmpty()) {
                final String mark = "?";
                final String and = "&";
                final String eq = "=";
                stringBuilder.append(mark);
                // 拼接url params
                params.forEach((k, v) -> stringBuilder.append(k)
                        .append(eq)
                        .append(v)
                        .append(and));
                url = stringBuilder.substring(0, stringBuilder.length() - 1);
            }
            requestBuilder.url(url).get();
        } else if (httpMethod == HttpMethod.POST) {
            FormBody.Builder formBuilder = new FormBody.Builder();
            if (null != params && !params.isEmpty()) {
                params.forEach(formBuilder::add);
            }
            requestBuilder.url(url).post(formBuilder.build());
        }
        // heads add
        if (null != headers && !headers.isEmpty()) {
            headers.forEach(requestBuilder::addHeader);
        }
        return requestBuilder;
    }

    /**
     * Gets request builder.
     *
     * @param <T>        the type parameter
     * @param httpMethod the http method
     * @param url        the url
     * @param params     the params
     * @param headers    the headers
     * @return the request builder
     */
    private static <T> Request.Builder getRequestBuilder(HttpMethod httpMethod, String url, T params, Map<String, String> headers) {
        return getRequestBuilder(httpMethod, null, url, params, headers);
    }

    /**
     * Get request builder request . builder.
     *
     * @param <T>        the type parameter
     * @param httpMethod the http method
     * @param mediaType  the media type
     * @param url        the url
     * @param params     the params
     * @param headers    the headers
     * @return the request . builder
     */
    private static <T> Request.Builder getRequestBuilder(HttpMethod httpMethod, MediaType mediaType, String url, T params, Map<String, String> headers) {
        // requestBuilder
        Request.Builder requestBuilder = new Request.Builder();
        // params封装
        if (httpMethod == HttpMethod.GET) {
            if (null != params) {
                StringBuilder stringBuilder = new StringBuilder(url);
                final String mark = "?";
                final String and = "&";
                final String eq = "=";
                stringBuilder.append(mark);
                // 拼接url params
                String jsonString = JSON.toJSONString(params);
                JSONObject jsonObject = JSON.parseObject(jsonString);
                Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
                for (Map.Entry<String, Object> stringObjectEntry : entries) {
                    String key = stringObjectEntry.getKey();
                    Object value = stringObjectEntry.getValue();
                    stringBuilder.append(key)
                            .append(eq)
                            .append(value)
                            .append(and);
                }
                url = stringBuilder.substring(0, stringBuilder.length() - 1);
                requestBuilder.url(url).get();
            } else {
                requestBuilder.url(url).get();
            }
        } else if (httpMethod == HttpMethod.POST) {
            if (null != params) {
                if (null == mediaType || mediaType.equals(FORM_CONTENT_TYPE)) {
                    FormBody.Builder formBuilder = new FormBody.Builder();
                    // 拼接url params
                    String jsonString = JSON.toJSONString(params);
                    JSONObject jsonObject = JSON.parseObject(jsonString);
                    Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
                    for (Map.Entry<String, Object> stringObjectEntry : entries) {
                        String key = stringObjectEntry.getKey();
                        Object value = stringObjectEntry.getValue();
                        if (null != value) {
                            formBuilder.add(key, value.toString());
                        }
                    }
                    // setting requestBuilder
                    requestBuilder.url(url).post(formBuilder.build());
                } else if (mediaType.equals(JSON_CONTENT_TYPE)) {
                    byte[] bytes = JSON.toJSONBytes(params);
                    RequestBody requestBody = RequestBody.create(JSON_CONTENT_TYPE, bytes);
                    // setting requestBuilder
                    requestBuilder.url(url).post(requestBody);
                }
            } else {
                requestBuilder.url(url);
            }
        }
        // heads add
        if (null != headers && !headers.isEmpty()) {
            headers.forEach(requestBuilder::addHeader);
        }
        return requestBuilder;
    }

    /**
     * 获取OkHttpClient实例
     *
     * @return OkHttpClient
     */
    public static OkHttpClient getClient() {
        return OkHttpClientHolder.OK_HTTP_CLIENT;
    }

    private static final class OkHttpClientHolder {
        private static final Dispatcher DISPATCHER = new Dispatcher(
                new ThreadPoolExecutor(10, Integer.MAX_VALUE, 5 * 60, TimeUnit.SECONDS,
                        new SynchronousQueue<>(), Util.threadFactory("Private Number OkHttp Dispatcher", false),
                        new ThreadPoolExecutor.CallerRunsPolicy())
        );
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .dispatcher(DISPATCHER)
                //读取超时
                .readTimeout(30, TimeUnit.SECONDS)
                //连接超时
                .connectTimeout(10, TimeUnit.SECONDS)
                //写入超时
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }
}

