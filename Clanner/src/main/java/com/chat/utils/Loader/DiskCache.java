package com.chat.utils.Loader;

import android.graphics.Bitmap;

/**
 * Created by Clanner on 2016/6/19.
 * sd卡缓存DiskCache类
 */
public class DiskCache implements ImageCache{
    @Override
    public Bitmap get(String url) {
        return null;    //从本地文件中获取该图片
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        //将Bitmap写入文件中
    }
}
