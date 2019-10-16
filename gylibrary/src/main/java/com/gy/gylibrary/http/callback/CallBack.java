package com.gy.gylibrary.http.callback;

import com.gy.gylibrary.http.constant.entity.ErrorMeg;

import java.lang.reflect.ParameterizedType;

/**
 * Created by xiaowu on 2016/6/17 0017.
 */
public abstract class CallBack<T> {
    private Class<T> clazz;

    public CallBack() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<T>) type.getActualTypeArguments()[0];
    }

    public Class<T> getDataClass() {
        return clazz;
    }

    /**
     * 成功获取的回调
     */
    public abstract void onSuccess(T data);

    /**
     * 失败的回调
     */
    public abstract void onFailure(Exception e, ErrorMeg errorMeg);
}
