package com.gy.gyframe.bean.base;



/**
 * 返回消息公共类
 *
 * @author Man
 */
public class MessagesEntity extends BaseEntity {
    private boolean success;

    private Integer status;
    /** 响应的操作信息. */

    private String msg;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
