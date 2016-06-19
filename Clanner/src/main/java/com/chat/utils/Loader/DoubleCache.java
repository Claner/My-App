package com.chat.utils.Loader;

import android.graphics.Bitmap;

/**
 * Created by Clanner on 2016/6/19.
 * 双缓存DoubleCache类
 */
public class DoubleCache implements ImageCache {

    ImageCache mMemoryCache = new MemoryCache();
    ImageCache mDiskCache = new DiskCache();

    //先从内存缓存中获取图片，如果没有再从sd卡中获取
    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap == null) bitmap = mDiskCache.get(url);
        return bitmap;
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
        mDiskCache.put(url, bitmap);
    }
}
