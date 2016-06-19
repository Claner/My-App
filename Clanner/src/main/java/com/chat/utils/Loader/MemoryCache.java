package com.chat.utils.Loader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Clanner on 2016/6/19.
 * 内存缓存类
 */
public class MemoryCache implements ImageCache{

    private LruCache<String ,Bitmap> mMemeryCche;

    public MemoryCache(){
        //初始化Lru缓存
    }

    @Override
    public Bitmap get(String url) {
        return mMemeryCche.get(url);
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        mMemeryCche.put(url,bitmap);
    }
}
