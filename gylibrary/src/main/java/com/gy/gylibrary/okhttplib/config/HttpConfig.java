package com.gy.gylibrary.okhttplib.config;

import com.gy.gylibrary.okhttplib.annotation.CacheType;

/**
 * 初始化网络参数对象
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2019/7/10
 * @Describe
 */
public class HttpConfig {




    /**
     * 连接超时时间 默认60秒
     */
    private int connectTimeout = 60;

    /**
     * 写超时时间
     */
    private int writeTimeout = 60;

    /**
     * 读超时时间
     */
    private int readTimeout = 60;

    /**
     * 缓存空间大小
     */
    private int maxCacheSize = 10 * 1024 * 1024;

    /**
     * 缓存类型
     */
    private @CacheType
    int cacheType = CacheType.FORCE_NETWORK;

    /**
     * 设置请求日志标识
     */
    private String httpLogTAG = "HttpLog";

    /**
     * Gzip压缩，需要服务端支持
     */
    private boolean isGzip = false;

    /**
     * 显示请求日志
     */
    private boolean showHttpLog = true;

    /**
     * 显示Activity销毁日志
     */
    private boolean showLifecycleLog = true;

    /**
     * 失败后不自动重连
     */
    private boolean retryOnConnectionFailure = false;

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getMaxCacheSize() {
        return maxCacheSize;
    }

    public void setMaxCacheSize(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }

    public int getCacheType() {
        return cacheType;
    }

    public void setCacheType(int cacheType) {
        this.cacheType = cacheType;
    }

    public String getHttpLogTAG() {
        return httpLogTAG;
    }

    public void setHttpLogTAG(String httpLogTAG) {
        this.httpLogTAG = httpLogTAG;
    }

    public boolean isGzip() {
        return isGzip;
    }

    public void setGzip(boolean gzip) {
        isGzip = gzip;
    }

    public boolean isShowHttpLog() {
        return showHttpLog;
    }

    public void setShowHttpLog(boolean showHttpLog) {
        this.showHttpLog = showHttpLog;
    }

    public boolean isShowLifecycleLog() {
        return showLifecycleLog;
    }

    public void setShowLifecycleLog(boolean showLifecycleLog) {
        this.showLifecycleLog = showLifecycleLog;
    }

    public boolean isRetryOnConnectionFailure() {
        return retryOnConnectionFailure;
    }

    public void setRetryOnConnectionFailure(boolean retryOnConnectionFailure) {
        this.retryOnConnectionFailure = retryOnConnectionFailure;
    }

}
