package com.Wuziqi.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.Wuziqi.ui.view.WuziqiPanel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int whiteFirst = 1;
    private final int blackFirst = 2;
    private WuziqiPanel wuziqiPanel;
    private Button btn_white;
    private Button btn_black;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_restart).setOnClickListener(this);
        btn_white = (Button) findViewById(R.id.btn_white_first);
        btn_white.setOnClickListener(this);
        btn_black = (Button) findViewById(R.id.btn_black_first);
        btn_black.setOnClickListener(this);
        wuziqiPanel = (WuziqiPanel) findViewById(R.id.wuziqi);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_restart:
                wuziqiPanel.start();
                break;
            case R.id.btn_white_first:
                wuziqiPanel.setWhichFirst(whiteFirst);
                btn_white.setEnabled(false);
                btn_white.setClickable(false);
                btn_black.setEnabled(true);
                btn_black.setClickable(true);
                break;
            case R.id.btn_black_first:
                wuziqiPanel.setWhichFirst(blackFirst);
                btn_black.setEnabled(false);
                btn_black.setClickable(false);
                btn_white.setEnabled(true);
                btn_white.setClickable(true);
                break;
        }
    }
}
