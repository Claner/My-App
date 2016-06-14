package com.example.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.ui.fragment.BaseFragment;

/**
 * Created by Clanner on 2016/5/23.
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

    /**
     * 1.两个必须实现的抽象方法，获取布局文件Layout的resource ID，获取布局文件中Fragment的ID
     *
     * 2.添加fragment：开启一个事物,替换了当前layout容器中的由getFragmentContentId()标识的fragment。
     * 通过调用 addToBackStack(String tag), replace事务被保存到back stack, 因此用户可以回退事务,
     * 并通过按下BACK按键带回前一个fragment，如果没有调用 addToBackStack(String tag), 那么当事务提交后,
     * 那个fragment会被销毁,并且用户不能导航回到它。其中参数tag将作为本次加入BackStack的Transaction的标志。
     * commitAllowingStateLoss()，这种提交是允许发生异常时状态值丢失的情况下也能正常提交事物
     *
     *3.移除fragment：与addToBackStack()相对应的接口方法是popBackStack()，调用该方法后会将事务操作插入到FragmentManager的操作队列，
     * 轮询到该事务时开始执行。这里进行了一下判断，获取回退栈中所有事务数量，大于1的时候，执行回退操作，
     * 等于1的时候，代表当前Activity只剩下一个Fragment，直接finish()当前Activity即可
     *
     * 4.监听返回键的返回事件，当事务数量等于1的时候，直接finish()
     */
}
