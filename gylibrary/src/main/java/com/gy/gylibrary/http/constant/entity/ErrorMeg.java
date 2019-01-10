package com.gy.gylibrary.http.constant.entity;

import java.io.Serializable;

/**
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2018/9/26
 * @Describe
 */
public class ErrorMeg implements Serializable {

    public ErrorMeg() {

    }

    public ErrorMeg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    private int code;
    private String msg;
    private int status;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
