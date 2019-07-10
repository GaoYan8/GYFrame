package com.gy.gylibrary.okhttplib.callback;

import com.gy.gylibrary.okhttplib.HttpInfo;

import java.io.IOException;

/**
 * 异步请求回调接口
 * @author 高炎
 * 该接口已过时，请使用com.gy.gylibrary.okhttplib.callback.Callback
 */
@Deprecated
public interface CallbackOk  extends BaseCallback{
    /**
     * 该回调方法已切换到UI线程
     */
    void onResponse(HttpInfo info) throws IOException;
}
