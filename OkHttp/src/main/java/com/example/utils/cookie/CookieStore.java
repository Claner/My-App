package com.example.utils.cookie;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by Clanner on 2016/5/8.
 */
public interface CookieStore {

    public void add(HttpUrl uri, List<Cookie> cookies);

    public List<Cookie> get(HttpUrl uri);

    public List<Cookie> getCookies();

    public boolean remove(HttpUrl uri,Cookie cookie);

    public boolean removeAll();
}
