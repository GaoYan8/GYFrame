package com.gy.gylibrary.okhttplib.interceptor;

import com.gy.gylibrary.okhttplib.HttpInfo;

/**
 * 请求链路异常（非业务逻辑）拦截器
 * @author 高炎
 */
public interface ExceptionInterceptor {

    HttpInfo intercept(HttpInfo info) throws Exception;

}
