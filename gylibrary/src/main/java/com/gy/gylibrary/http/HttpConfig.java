package com.gy.gylibrary.http;

/**
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2018/9/26
 * @Describe
 */
public class HttpConfig {

    public static long connectTimeout = 1000 * 30l;//连接超时时间 单位:毫秒
    public static long writeTimeout = 1000 * 30l;//写入超时时间 单位:毫秒
    public static long readTimeout = 1000 * 30l;//读取超时时间 单位:毫秒



    public static String OK_HTTP_TAG= "OK_HTTP";
}
