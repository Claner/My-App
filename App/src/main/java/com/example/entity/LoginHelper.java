package com.example.entity;

/**
 * Created by Clanner on 2016/5/19.
 */
public class LoginHelper {
    /**
     * code : 20000
     * response : {"name":"monster","head_path":"Public/user_head/student/18219111772.jpg","role":"1"}
     */

    private int code;
    /**
     * name : monster
     * head_path : Public/user_head/student/18219111772.jpg
     * role : 1
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
        private String name;
        private String head_path;
        private String role;

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

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
