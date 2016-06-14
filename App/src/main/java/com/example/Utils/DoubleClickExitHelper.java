package com.example.Utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * Created by Clanner on 2016/5/8.
 * 双击退出
 */
public class DoubleClickExitHelper {
    private final Activity mActivity;
    private boolean isOnKeyBacking;
    private Handler mHandler;
    private Toast mBackToast;
    private Runnable onBackTimeRunnable = new Runnable() {

        /**
         * 过了三秒后还没有再点击一次退出就执行下面方法
         * 并清空Toast内容
         */
        @Override
        public void run() {
            Log.d("Hello","请再按一次退出");
            isOnKeyBacking = false;
            if (mBackToast != null) {
                //若Toast不为空则清空内容
                mBackToast.cancel();
            }
        }
    };

    public DoubleClickExitHelper(Activity activity) {
        mActivity = activity;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /**
         * 注释掉的代码似乎没什么用
         */
//        if (keyCode != KeyEvent.KEYCODE_BACK) {
//            Log.d("Hello","没有按退出");
//            return false;
//        }
        if (isOnKeyBacking) {
            Log.d("Hello","你又点击了一次退出，程序将终止");
            mHandler.removeCallbacks(onBackTimeRunnable);
            if (mBackToast != null) {
                mBackToast.cancel();
            }
            //退出程序
            mActivity.finish();
            return true;
        } else {
            Log.d("Hello","你已经按了一次退出");
            isOnKeyBacking = true;
            if (mBackToast == null) {
                mBackToast = Toast.makeText(mActivity, "再按一次退出", Toast.LENGTH_SHORT);
            }
            mBackToast.show();
            mHandler.postDelayed(onBackTimeRunnable, 3000);
            return true;
        }
    }
}
