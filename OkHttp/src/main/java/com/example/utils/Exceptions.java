package com.example.utils;

/**
 * Created by Clanner on 2016/5/8.
 */
public class Exceptions {

    public static void illegalArgument(String msg, Object... params) {
        throw new IllegalArgumentException(String.format(msg, params));
    }

}
