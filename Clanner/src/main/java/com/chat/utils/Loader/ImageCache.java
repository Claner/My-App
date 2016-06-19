package com.chat.utils.Loader;

import android.graphics.Bitmap;

/**
 * Created by Clanner on 2016/6/19.
 * 图片缓存接口
 */
public interface ImageCache {
    public Bitmap get(String url);

    public void put(String url, Bitmap bitmap);
}
