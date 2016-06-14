package com.example.utils.callback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Clanner on 2016/5/8.
 */
public abstract class Callback<T> {

    public static Callback CALLBACK_DEFAULT = new Callback() {
        @Override
        public Object parseNetworkResponse(Response response) throws Exception {
            return null;
        }

        @Override
        public void onError(Call call, Exception e) {

        }

        @Override
        public void onResponse(Object response) {

        }
    };

    /**
     * UI Thread
     *
     * @param request
     */
    public void onBefore(Request request) {

    }

    /**
     * UI Thread
     *
     * @param
     */
    public void onAfter() {

    }

    /**
     * UI Thread
     *
     * @param progress
     */
    public void inProgress(float progress) {

    }

    /**
     * Thread Pool Thread
     *
     * @param response
     * @return
     * @throws Exception
     */
    public abstract T parseNetworkResponse(Response response) throws Exception;

    public abstract void onError(Call call, Exception e);

    public abstract void onResponse(T response);
}
