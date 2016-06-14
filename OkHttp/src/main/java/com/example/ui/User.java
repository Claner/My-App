package com.example.ui;

/**
 * Created by Clanner on 2016/5/10.
 */
public class User {

    public String username;
    public String password;

    public User() {
        // TODO Auto-generated constructor stub
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
