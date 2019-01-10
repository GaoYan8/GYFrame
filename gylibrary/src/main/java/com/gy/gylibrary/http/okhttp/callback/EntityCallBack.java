package com.gy.gylibrary.http.okhttp.callback;

import android.util.Log;

import com.google.gson.Gson;
import com.zarltech.zarlspmis.frame.http.okhttp.utils.GenericUtil;

import okhttp3.Response;

/**
 * Created by Josn on 2016/7/21.
 */
public abstract class EntityCallBack<T> extends Callback<T> {

    protected Class<T> mClazz;

    public EntityCallBack() {
        this.mClazz = GenericUtil.getSuperGenericClass(this.getClass());
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        String str = response.body().string();
        Log.e("EntityCallBack","网路请求数据："+str);
        return new Gson().fromJson(str, mClazz);
    }
}
