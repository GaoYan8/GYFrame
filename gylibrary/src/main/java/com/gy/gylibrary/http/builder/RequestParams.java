package com.gy.gylibrary.http.builder;

import android.text.TextUtils;


import com.gy.gylibrary.http.constant.HTTP;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xiaowu on 2016/6/17 0017.
 */
public class RequestParams {
    /**
     * 默认字符编码
     */
    private String charset = HTTP.UTF_8;
    /**
     * header
     */
    private Map<String, String> headers;

    /**
     * 发送请求字符串
     */
    private List<Map<String, String>> queryStringParams;

    /**
     * 发送请求体
     */
    private List<Map<String, String>> bodyParams;

    /**
     * 发送的文件
     */
    private Map<String, List<Map<String, File>>> fileParams;

    public RequestParams() {

    }

    public RequestParams(String charset) {
        if (!TextUtils.isEmpty(charset)) {
            this.charset = charset;
        }
    }

    public String getCharset() {
        return charset;
    }

    /**
     * Adds a header to this message. The header will be appended to the end of the list.
     *
     * @param name
     * @param value
     */
    public void addHeader(String name, String value) {
        if (this.headers == null) {
            this.headers = new HashMap<String, String>();
        }
        this.headers.put(name, value);
    }

    /**
     * Adds all the headers to this message.
     *
     * @param headers
     */
    public void addHeader(Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            this.addHeader(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Overwrites the first header with the same name.
     * The new header will be appended to the end of the list, if no header with the given name can be found.
     *
     * @param header
     */
    public void setHeader(Map<String, String> header) {
        if (this.headers == null) {
            this.headers = new HashMap<String, String>();
        }
        this.headers = header;
    }

    /**
     * Overwrites the first header with the same name.
     * The new header will be appended to the end of the list, if no header with the given name can be found.
     *
     * @param name
     * @param value
     */
    public void setHeader(String name, String value) {
        if (this.headers == null) {
            this.headers = new HashMap<String, String>();
        }
        this.headers.clear();
        this.headers.put(name, value);
    }

    /**
     * 得到params中的headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addQueryStringParameter(String name, String value) {
        if (queryStringParams == null) {
            queryStringParams = new ArrayList<Map<String, String>>();
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put(name, value);
        queryStringParams.add(map);
    }

    public void addQueryStringParameter(Map<String, String> map) {
        if (queryStringParams == null) {
            queryStringParams = new ArrayList<Map<String, String>>();
        }
        queryStringParams.add(map);
    }

    public void addQueryStringParameter(List<Map<String, String>> list) {
        if (queryStringParams == null) {
            queryStringParams = new ArrayList<Map<String, String>>();
        }
        if (list != null && list.size() > 0) {
            queryStringParams.addAll(list);
        }
    }

    public List<Map<String, String>> getQueryStringParams() {
        return queryStringParams;
    }

    public void addBodyParameter(String name, String value) {
        if (bodyParams == null) {
            bodyParams = new ArrayList<Map<String, String>>();
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put(name, value);
        bodyParams.add(map);
    }

    public void addBodyParameter(Map<String, String> map) {
        if (bodyParams == null) {
            bodyParams = new ArrayList<Map<String, String>>();
        }
        bodyParams.add(map);
    }

    public void addBodyParameter(List<Map<String, String>> list) {
        if (bodyParams == null) {
            bodyParams = new ArrayList<Map<String, String>>();
        }
        if (list != null && list.size() > 0) {
            bodyParams.addAll(list);
        }
    }

    public List<Map<String, String>> getBodyParams() {
        return bodyParams;
    }

    public void addFile(String name, String filename, File file) {
        if (fileParams == null) {
            fileParams = new HashMap<String, List<Map<String, File>>>();
        }
        if (fileParams.containsKey(name)) {
            Map<String, File> map = new HashMap<String, File>();
            map.put(filename, file);
            fileParams.get(name).add(map);
        } else {
            List<Map<String, File>> list = new ArrayList<Map<String, File>>();
            Map<String, File> map = new HashMap<String, File>();
            map.put(filename, file);
            list.add(map);
            fileParams.put(name, list);
        }
    }

    public void addFile(String name, Map<String, File> map) {
        if (fileParams == null) {
            fileParams = new HashMap<String, List<Map<String, File>>>();
        }
        if (fileParams.containsKey(name)) {
            fileParams.get(name).add(map);
        } else {
            List<Map<String, File>> list = new ArrayList<Map<String, File>>();
            list.add(map);
            fileParams.put(name, list);
        }
    }

    public void addFile(String name, List<Map<String, File>> list) {
        if (fileParams == null) {
            fileParams = new HashMap<String, List<Map<String, File>>>();
        }
        if (fileParams.containsKey(name)) {
            if (list != null && list.size() > 0) {
                fileParams.get(name).addAll(list);
            }
        } else {
            if (list != null && list.size() > 0) {
                fileParams.put(name, list);
            }
        }
    }

    public Map<String, List<Map<String, File>>> getFileParams() {
        return fileParams;
    }

}
