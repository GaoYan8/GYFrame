package com.gy.gylibrary.okhttplib.interceptor.impl;

import com.gy.gylibrary.okhttplib.HttpInfo;
import com.gy.gylibrary.okhttplib.interceptor.ResultInterceptor;

/**
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2019/7/10
 * @Describe
 */
public class DefaultResultInterceptor implements ResultInterceptor {
    @Override
    public HttpInfo intercept(HttpInfo info)  {
        return info;
    }
}
