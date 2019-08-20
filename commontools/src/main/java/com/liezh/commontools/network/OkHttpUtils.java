package com.liezh.commontools.network;

import com.liezh.commontools.config.PropertiesUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {

    private static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private static final MediaType XMARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    private static volatile OkHttpClient singleton = new OkHttpClient();

    private OkHttpUtils() {

    }

    /**
     * 单例创建okhttp客户端
     * @return
     */
    public static OkHttpClient getInstance() {
        if (singleton == null) {
            synchronized (singleton) {
                if (singleton == null) {
                    singleton = new OkHttpClient();
                }
            }
        }
        return singleton;
    }

    /**
     * post 请求
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String post(String url, MediaType mediaType, String json) throws IOException {
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = getInstance().newCall(request).execute()) {
            return response.body().string();
        }
    }


    /**
     * post 请求，发送json参数
     * @param json
     * @return
     * @throws IOException
     */
    public static String post(String json) throws IOException {
        return post(PropertiesUtil.getStringValue("server_url"), JSON, json);
    }

    /**
     * 不带参数get请求
     * @param url
     * @return
     * @throws IOException
     */
    public static String get(String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        try (Response response = getInstance().newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * 带参get请求
     * @param url
     * @param argMap
     * @return
     * @throws IOException
     */
    public static String get(String url, Map<String, Object> argMap) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        // 构建参数列表
        if (argMap != null && argMap.size() > 0) {
            sb.append("?");
            Set<String> keys = argMap.keySet();
            for (String k : keys) {
                sb.append(k).append(argMap.get(k));
                sb.append("&");
            }
            sb.deleteCharAt(sb.length());
        }
        return get(sb.toString());
    }


    public static void main(String[] args) throws IOException {
        String str = OkHttpUtils.get("https://www.jianshu.com/p/da4a806e599b");
        System.out.println(str);
    }

}
