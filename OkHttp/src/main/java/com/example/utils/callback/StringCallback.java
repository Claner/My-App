package com.example.utils.callback;

import okhttp3.Response;

/**
 * Created by Clanner on 2016/5/10.
 */
public abstract class StringCallback extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response) throws Exception {
        return response.body().string();
    }
}
