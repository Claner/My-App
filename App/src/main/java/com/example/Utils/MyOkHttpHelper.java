package com.example.Utils;

import android.os.Handler;
import android.util.Log;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by Clanner on 2016/5/9.
 */
public class MyOkHttpHelper {
    private static MyOkHttpHelper helper;
    private OkHttpClient client;
    private Handler handler;

    private MyOkHttpHelper() {
        client = new OkHttpClient();
        client.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        handler = new Handler();
    }

    public static MyOkHttpHelper getHelper() {
        if (helper == null) {
            synchronized (MyOkHttpHelper.class) {
                if (helper == null) {
                    helper = new MyOkHttpHelper();
                }
            }
        }
        return helper;
    }

    /**
     * 对外公布的方法
     */
    public static void Get(String url, ResultCallback callback) {
        getHelper().getRequest(url, callback);
    }

    public static Response Get(String url) throws IOException {
        return getHelper().getRequest(url);
    }

    public static String GetAsString(String url) throws IOException {
        return getHelper().getRequestAsString(url);
    }

    /***********************************
     * ***************************************************************************/

    private void getRequest(String url, final ResultCallback callback) {
        Log.d("Hello", "异步的get请求");
        final Request request = new Request.Builder().url(url).build();
        deliveryResult(request, callback);
    }

    private Response getRequest(String url) throws IOException {
        Log.d("Hello", "同步的get请求");
        final Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        Response response = call.execute();
        return response;
    }

    private String getRequestAsString(String url) throws IOException {
        Response response = getRequest(url);
        return response.body().string();
    }

    /**
     * 处理结果
     */
    private void deliveryResult(final Request request, final ResultCallback callback) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("Hello", "onFailure");
                failure(request, e, callback);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String string = response.body().string();
                if (callback.mType == String.class) {
                    success(string, callback);
                }
            }
        });
    }

    /**
     * 请求失败后调用ResultCallback的onError接口
     */
    private void failure(final Request request, final Exception e, final ResultCallback callback) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onError(request, e);
                }
            }
        });
    }

    /**
     * 请求成功后调用ResultCallback的onResponse接口
     *
     * @param object
     * @param callback
     */
    private void success(final Object object, final ResultCallback callback) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(object);
                }
            }
        });
    }

    /**
     * ResultCallbac类
     *
     * @param <T>
     */
    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
            Log.d("Hello", "创建一个ResultCallback");
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);
    }
}
