package com.chat.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.chat.ui.fragment.BaseFragment;

/**
 * Created by Clanner on 2016/5/24.
 */
public abstract class BaseActivity extends AppCompatActivity {

    //布局文件ID
    protected abstract int getContentViewId();

    //布局中Fragment的ID
    protected abstract int getFragmentContentId();

    //添加fragment
    public void addFragment(BaseFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    //移除fragment
    public void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    //返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

//    /**
//     * 关闭Activity的广播，放在自定义的基类中，让其他的Activity继承这个Activity就行
//     */
//    protected BroadcastReceiver finishAppReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            finish();
//        }
//    };
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // 在当前的activity中注册广播
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("net.loonggg.exitapp");
//        this.registerReceiver(this.finishAppReceiver, filter);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        this.unregisterReceiver(this.finishAppReceiver);
//    }
//
//    /**
//     * 退出应用程序的方法，发送退出程序的广播
//     */
//    public void exitApp() {
//        Intent intent = new Intent();
//        intent.setAction("exitApp");
//        this.sendBroadcast(intent);
//    }

}
