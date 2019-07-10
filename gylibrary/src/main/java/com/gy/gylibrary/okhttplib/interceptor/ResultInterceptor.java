package com.gy.gylibrary.okhttplib.interceptor;

import com.gy.gylibrary.okhttplib.HttpInfo;

/**
 * 请求结果拦截器
 * @author 高炎
 */
public interface ResultInterceptor {

    HttpInfo intercept(HttpInfo info) throws Exception;

}
