package com.example.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ui.activity.BaseActivity;

/**
 * Created by Clanner on 2016/5/23.
 */
public abstract class BaseFragment extends Fragment {

    //Something different
    protected BaseActivity mActivity;

    protected abstract void initView(View view, Bundle savedInstanceState);

    //获取fragment布局文件ID
    protected abstract int getLayoutId();

    //获取宿主Activity
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    //添加fragment
    protected void addFragment(BaseFragment fragment) {
        if (null != fragment) {
            getHoldingActivity().addFragment(fragment);
        }
    }

    //移除fragment
    protected void removeFragment() {
        getHoldingActivity().removeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);
        return view;
    }

    /**
     * 在APP运行在后台的时候，系统资源紧张的时候会导致后台的Activity被销毁，可能会带来一些问题，
     * 其中之一就是Fragment调用getActivity()的地方却返回null，报了空指针异常。
     * 解决办法就是在Fragment基类里设置一个Activity mActivity的全局变量，在onAttach(Activity activity)里赋值，
     * 使用mActivity代替getActivity()。
     */
}
