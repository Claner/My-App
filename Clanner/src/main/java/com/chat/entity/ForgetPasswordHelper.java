package com.chat.entity;

/**
 * Created by Clanner on 2016/5/25.
 */
public class ForgetPasswordHelper {

    /**
     * code : 20000
     * response : 发送短信成功，请注意查收！
     */

    private int code;
    private String response;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
