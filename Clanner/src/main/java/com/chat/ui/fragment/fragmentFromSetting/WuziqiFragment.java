package com.chat.ui.fragment.fragmentFromSetting;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

import com.chat.ui.activity.R;
import com.chat.ui.fragment.BaseFragment;
import com.chat.ui.view.PieceView;
import com.chat.ui.view.WuziqiPanel;

/**
 * Created by Clanner on 2016/6/17.
 */
public class WuziqiFragment extends BaseFragment implements WuziqiPanel.OnWhichListener {

    private WuziqiPanel wuziqi;
    //    private TextView white;
//    private TextView black;
    private PieceView pvWhite;
    private PieceView pvBlack;

    public static WuziqiFragment newInstance() {
        return new WuziqiFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_viewdemo;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //设置ActionBar
        setHasOptionsMenu(true);
        ActionBar actionBar = getHoldingActivity().getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("五子棋");

        wuziqi = (WuziqiPanel) view.findViewById(R.id.wuziqi);
        wuziqi.setOnWhichListener(this);
//        white = (TextView) view.findViewById(R.id.tv_white);
//        black = (TextView) view.findViewById(R.id.tv_black);
        pvWhite = (PieceView) view.findViewById(R.id.pv_white);
        pvBlack = (PieceView) view.findViewById(R.id.pv_black);
        view.findViewById(R.id.btn_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wuziqi.start();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                removeFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 用接口回调的方式监听轮到谁下子
     * @param isWhite
     */
    @Override
    public void onWhich(boolean isWhite) {
        boolean flag = isWhite;
        if (flag == true) {
            pvWhite.setVisibility(View.VISIBLE);
            pvBlack.setVisibility(View.GONE);
//            white.setVisibility(View.VISIBLE);
//            black.setVisibility(View.GONE);
        } else {
            pvWhite.setVisibility(View.GONE);
            pvBlack.setVisibility(View.VISIBLE);
//            white.setVisibility(View.GONE);
//            black.setVisibility(View.VISIBLE);
        }
    }
}
