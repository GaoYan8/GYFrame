package com.gy.gylibrary.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.gy.gylibrary.http.builder.RequestParams;
import com.gy.gylibrary.http.okhttp.OkHttpUtils;
import com.gy.gylibrary.http.okhttp.builder.GetBuilder;
import com.gy.gylibrary.http.okhttp.builder.PostFormBuilder;
import com.gy.gylibrary.http.okhttp.callback.Callback;
import com.gy.gylibrary.http.okhttp.cookie.CookieJarImpl;
import com.gy.gylibrary.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.CookieJar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpManager {
    /**
     * 单例模式，实例化HttpManager对象
     */
    private volatile static HttpManager singleton;

    private HttpManager() {

    }

    public static HttpManager getInstance() {
        if (singleton == null) {
            synchronized (HttpManager.class) {
                if (singleton == null) {
                    singleton = new HttpManager();
                }
            }
        }
        return singleton;
    }

    /**
     * @param url
     * @param callBack
     * @desciption 发送默认时间，不带参数和后缀的get请求
     */
    public void requestGet(String url, Callback callBack) {
        requestGet(url, null, callBack);
    }

    /**
     * @param url
     * @param params
     * @param callBack
     * @description 发送默认超时时间，不带后缀url的get请求
     */
    public void requestGet(String url, RequestParams params, Callback callBack) {
        requestGet(url, params, callBack, null);
    }

    /**
     * @param url
     * @param callBack
     * @param urlExtra
     * @description 发送默认超时时间，不带参数的get请求
     */
    public void requestGet(String url, Callback callBack, String urlExtra) {
        requestGet(url, null, callBack, urlExtra);
    }


    /**
     * @param url
     * @param params
     * @param callBack
     * @param urlExtra
     * @description 发送默认超时时间的get请求
     */
    public void requestGet(String url, RequestParams params, Callback callBack, String urlExtra) {
        requestGet(url, params, callBack, 0, 0, 0, urlExtra);
    }

    /**
     * @param url
     * @param params
     * @param callBack
     * @param connTimeOut
     * @param readTimeOut
     * @param writeTimeOut
     * @param urlExtra
     * @description 发送get请求
     */
    public void requestGet(String url, RequestParams params, Callback callBack, long connTimeOut, long readTimeOut, long writeTimeOut, String urlExtra) {
        if (params == null) {
            params = new RequestParams();
        }
        // 将params中的queryString拼接到url中
        if (params.getQueryStringParams() != null && params.getQueryStringParams().size() != 0) {
            StringBuilder sb = new StringBuilder();
            for (Map<String, String> map : params.getQueryStringParams()) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                }
            }
            if (!url.contains("?")) {
                sb.deleteCharAt(0);
                sb.insert(0, "?");
            }
            url = url + sb;
        }
        // 附属的url字符串拼接到url中
        if (!TextUtils.isEmpty(urlExtra)) {
            url = url + urlExtra;
        }
        // 构建get请求的builder
        GetBuilder builder = OkHttpUtils.getInstance().get().url(url);
        // 添加header
        if (params.getHeaders() != null) {
            builder = builder.headers(params.getHeaders());
        }
        // 构建RequestCall
        RequestCall call = builder.build();
        // 设置超时时间
        if (connTimeOut > 0) {
            call = call.connTimeOut(connTimeOut);
        }
        if (readTimeOut > 0) {
            call = call.readTimeOut(readTimeOut);
        }
        if (writeTimeOut > 0) {
            call = call.writeTimeOut(writeTimeOut);
        }
        // 发起请求，通过回调方法返回值
        call.execute(callBack);
    }

    /**
     * @param url
     * @param callBack
     * @description 发送默认超时时间，没有参数的post请求
     */
    public void requestPost(String url, Callback callBack) {
        requestPost(url, null, callBack);
    }

    /**
     * @param url
     * @param params
     * @param callBack
     * @description 发送默认超时时间的post请求
     */
    public void requestPost(String url, RequestParams params, Callback callBack) {
        requestPost(url, params, callBack, 0, 0, 0);
    }

    /**
     * @param url
     * @param params
     * @param callBack
     * @param connTimeOut
     * @param readTimeOut
     * @param writeTimeOut
     * @description 发送post请求
     */
    public void requestPost(String url, RequestParams params, Callback callBack, long connTimeOut, long readTimeOut, long writeTimeOut) {
        if (params == null) {
            params = new RequestParams();
        }
        PostFormBuilder builder = OkHttpUtils.getInstance().post().url(url);
        // 添加header
        if (params.getHeaders() != null) {
            builder = builder.headers(params.getHeaders());
        }
        if (params.getBodyParams() != null && params.getBodyParams().size() != 0) {
            for (Map<String, String> map : params.getBodyParams()) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    builder = builder.addParams(entry.getKey(), entry.getValue());
                }
            }
        }
        if (params.getFileParams() != null) {
            for (Map.Entry<String, List<Map<String, File>>> entry : params.getFileParams().entrySet()) {
                for (Map<String, File> map : entry.getValue()) {
                    for (Map.Entry<String, File> entry2 : map.entrySet()) {
                        builder = builder.addFile(entry.getKey(), entry2.getKey(), entry2.getValue());
                    }
                }
            }
        }
        // 构建RequestCall
        RequestCall call = builder.build();
        // 设置超时时间
        if (connTimeOut > 0) {
            call = call.connTimeOut(connTimeOut);
        }
        if (readTimeOut > 0) {
            call = call.readTimeOut(readTimeOut);
        }
        if (writeTimeOut > 0) {
            call = call.writeTimeOut(writeTimeOut);
        }
        // 发起请求，通过回调方法返回值
        call.execute(callBack);
    }


    //使用DELETE方式向服务器提交数据并获取返回提示数据
    public static void requestDelete(String url, okhttp3.Callback callback) {
        //JSONObject这里是要提交的数据部分
        Request request = new Request.Builder().url(url).delete().build();
        new OkHttpClient().newCall(request).enqueue(callback);
    }

    //使用PUT方式向服务器提交数据并获取返回提示数据
    public static void requestPUT(String url, RequestBody requestBody, okhttp3.Callback callback) {
        //JSONObject这里是要提交的数据部分
        Request request = new Request.Builder().url(url).put(requestBody).build();
        new OkHttpClient().newCall(request).enqueue(callback);
    }

    /**
     * @param url
     * @param json
     * @param callback
     * @description 发送json字符串
     */
    public void postJsonString(String url, String json, Callback callback) {
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(json)
                .build()
                .execute(callback);
    }

    /**
     * @param url
     * @param obj
     * @param callback
     * @description 发送json对象
     */
    public void postJsonObject(String url, Object obj, Callback callback) {
        String str = new Gson().toJson(obj);
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(str)
                .build()
                .execute(callback);
    }

    /**
     * @description 清除缓存
     */
    public void clearSession() {
        CookieJar cookieJar = OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        if (cookieJar instanceof CookieJarImpl) {
            ((CookieJarImpl) cookieJar).getCookieStore().removeAll();
        }
    }
}
