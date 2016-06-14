package com.example.utils.builder;

import com.example.utils.OkHttpUtils;
import com.example.utils.request.OtherRequest;
import com.example.utils.request.RequestCall;

/**
 * Created by Clanner on 2016/5/9.
 */
public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers).build();
    }
}
