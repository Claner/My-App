package com.example.utils.builder;

import android.util.Log;

import com.example.utils.request.GetRequest;
import com.example.utils.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Clanner on 2016/5/9.
 */
public class GetBuilder extends OkHttpRequestBuilder implements HasParamsable {

    @Override
    public RequestCall build() {
        if (params != null) {
            url = appendParams(url, params);
        }
        Log.d("Hello","build");
        return new GetRequest(url, tag, params, headers).build();
    }

    protected String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public GetBuilder url(String url) {
        Log.d("Hello","传入一个url");
        this.url = url;
        return this;
    }

    @Override
    public GetBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public GetBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public GetBuilder addParams(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
            params.put(key, val);
        }
        return this;
    }

    @Override
    public GetBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public GetBuilder addHeader(String key, String val) {
        if (this.headers == null) {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return this;
    }


}
