package com.gy.gylibrary.okhttplib.interceptor.impl;

import com.gy.gylibrary.okhttplib.HttpInfo;
import com.gy.gylibrary.okhttplib.interceptor.ExceptionInterceptor;

/**
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2019/7/10
 * @Describe
 */
public class DefaultExceptionInterceptor implements ExceptionInterceptor {

    @Override
    public HttpInfo intercept(HttpInfo info){
        String result = info.getRetDetail();
        switch (info.getRetCode()){
            case HttpInfo.NonNetwork:
                info.setRetDetail("网络中断："+result);
                break;
            case HttpInfo.CheckURL:
                info.setRetDetail("网络地址错误["+info.getNetCode()+"]："+result);
                break;
            case HttpInfo.CheckNet:
                info.setRetDetail("请检查网络连接是否正常["+info.getNetCode()+"]："+result);
                break;
            case HttpInfo.ProtocolException:
                info.setRetDetail("协议类型错误["+info.getNetCode()+"]："+result);
                break;
            case HttpInfo.ConnectionTimeOut:
                info.setRetDetail("连接超时");
                break;
            case HttpInfo.WriteAndReadTimeOut:
                info.setRetDetail("读写超时");
                break;
            case HttpInfo.ConnectionInterruption:
                info.setRetDetail("连接中断："+result);
                break;
        }
        return info;
    }
}
