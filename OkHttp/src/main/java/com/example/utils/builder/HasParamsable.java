package com.example.utils.builder;

import java.util.Map;

/**
 * Created by Clanner on 2016/5/9.
 */
public interface HasParamsable {
    public abstract OkHttpRequestBuilder params(Map<String,String> params);

    public abstract OkHttpRequestBuilder addParams(String key,String val);
}
