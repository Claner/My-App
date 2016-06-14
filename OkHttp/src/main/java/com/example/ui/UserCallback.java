package com.example.ui;

import com.example.utils.callback.Callback;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Clanner on 2016/5/10.
 */
public abstract class UserCallback extends Callback<User> {
    @Override
    public User parseNetworkResponse(Response response) throws IOException {
        String string = response.body().string();
        User user = new Gson().fromJson(string, User.class);
        return user;
    }
}
