package com.gy.gylibrary.okhttplib.callback;

import com.gy.gylibrary.okhttplib.HttpInfo;

/**
 * 进度回调抽象类
 * @author 高炎
 */
public abstract class ProgressCallbackAbs {

    public abstract void onResponseMain(String filePath, HttpInfo info);

    public abstract void onResponseSync(String filePath, HttpInfo info);

    public abstract void onProgressAsync(int percent, long bytesWritten, long contentLength, boolean done);

    public abstract void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done);

}
