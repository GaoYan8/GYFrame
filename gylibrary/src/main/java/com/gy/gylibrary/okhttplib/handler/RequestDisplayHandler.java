package com.gy.gylibrary.okhttplib.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Activity请求数据，处理LoadingDialog 显示逻辑
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2019/7/10
 * @Describe
 */
public class RequestDisplayHandler extends Handler {

    private CallBack callBack;

    public interface CallBack{
        void handleMessage(Message msg);
    }

    public RequestDisplayHandler(CallBack callBack, Looper looper){
        super(looper);
        this.callBack = callBack;
    }

    @Override
    public void handleMessage(Message msg) {
        if(null != callBack){
            callBack.handleMessage(msg);
        }
    }
}
