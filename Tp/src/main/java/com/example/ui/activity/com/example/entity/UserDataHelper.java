package com.example.ui.activity.com.example.entity;

/**
 * Created by Clanner on 2016/5/23.
 */
public class UserDataHelper {

    /**
     * code : 20000
     * response : {"phone":"18219118686","name":"bsvc","head_path":"","background_path":"","is_approve":"2"}
     */

    private int code;
    /**
     * phone : 18219118686
     * name : bsvc
     * head_path :
     * background_path :
     * is_approve : 2
     */

    private ResponseBean response;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        private String phone;
        private String name;
        private String head_path;
        private String background_path;
        private String is_approve;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHead_path() {
            return head_path;
        }

        public void setHead_path(String head_path) {
            this.head_path = head_path;
        }

        public String getBackground_path() {
            return background_path;
        }

        public void setBackground_path(String background_path) {
            this.background_path = background_path;
        }

        public String getIs_approve() {
            return is_approve;
        }

        public void setIs_approve(String is_approve) {
            this.is_approve = is_approve;
        }
    }
}
