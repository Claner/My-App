package com.chat.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chat.ui.fragment.BaseFragment;
import com.chat.ui.fragment.FragmentFour;
import com.chat.ui.fragment.FragmentOne;
import com.chat.ui.fragment.FragmentThree;
import com.chat.ui.fragment.FragmentTwo;
import com.chat.ui.view.MyView;
import com.chat.utils.DoubleClickExitHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    private String[] mTitles = new String[]{"First", "Second", "Third", "Fourth"};
    private FragmentPagerAdapter mAdapter;
    private DoubleClickExitHelper doubleClickExitHelper;

    private List<MyView> mTabIndicators = new ArrayList<MyView>();

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //接收广播，若收到的信号为finish则finish当前Activity
            if (intent.getAction().equals("finish")) {
                Log.e("Hello","收到终止Activity的广播");
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        initView();
        if (null != getIntent()) {
            handleIntent(getIntent());
        }
        //避免重复添加Fragment
        if (null == getSupportFragmentManager().getFragments()) {
            BaseFragment firstFragment = getFirstFragment();
            if (null != firstFragment) {
                addFragment(firstFragment);
            }
        }
        initData();
        mViewPager.setAdapter(mAdapter);
        initEvent();
        doubleClickExitHelper = new DoubleClickExitHelper(this);

        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("finish");
        registerReceiver(broadcastReceiver, filter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Toast.makeText(MainActivity.this, "添加", Toast.LENGTH_SHORT).show();
            case R.id.group_chat:
                Toast.makeText(MainActivity.this, "群聊", Toast.LENGTH_SHORT).show();
                break;
            case R.id.money:
                Toast.makeText(MainActivity.this, "钱包", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化Fragment
     */
    private void initData() {

        for (String title : mTitles) {
            if (title.equals("First")) {
                mTabs.add(FragmentOne.newInstance());
            } else if (title.equals("Second")) {
                mTabs.add(FragmentTwo.newInstance());
            } else if (title.equals("Third")) {
                mTabs.add(FragmentThree.newInstance());
            } else if (title.equals("Fourth")) {
                mTabs.add(FragmentFour.newInstance());
            }
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }
        };
    }

    /**
     * 初始化所有事件
     */
    private void initEvent() {
        mViewPager.addOnPageChangeListener(this);
    }

    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        MyView one = (MyView) findViewById(R.id.indicator_one);
        mTabIndicators.add(one);
        MyView two = (MyView) findViewById(R.id.indicator_two);
        mTabIndicators.add(two);
        MyView three = (MyView) findViewById(R.id.indicator_three);
        mTabIndicators.add(three);
        MyView four = (MyView) findViewById(R.id.indicator_four);
        mTabIndicators.add(four);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

        one.setIconAlpha(1.0f);
    }

    @Override
    public void onClick(View view) {
        clickTab(view);
    }

    /**
     * 点击Tab按钮
     *
     * @param v
     */
    private void clickTab(View v) {
        resetOtherTabs();

        switch (v.getId()) {
            case R.id.indicator_one:
                mTabIndicators.get(0).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.indicator_two:
                mTabIndicators.get(1).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.indicator_three:
                mTabIndicators.get(2).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.indicator_four:
                mTabIndicators.get(3).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(3, false);
                break;
        }
    }

    /**
     * 重置其他的TabIndicator的颜色
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicators.size(); i++) {
            mTabIndicators.get(i).setIconAlpha(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Log.e("TAG", "position = " + position + " ,positionOffset =  "
        // + positionOffset);
        if (positionOffset > 0) {
            MyView left = mTabIndicators.get(position);
            MyView right = mTabIndicators.get(position + 1);
            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            return doubleClickExitHelper.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
