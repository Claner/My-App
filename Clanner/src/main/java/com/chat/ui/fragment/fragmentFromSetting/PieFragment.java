package com.chat.ui.fragment.fragmentFromSetting;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chat.entity.PieData;
import com.chat.ui.activity.R;
import com.chat.ui.fragment.BaseFragment;
import com.chat.ui.view.PieView;

import java.util.ArrayList;

/**
 * Created by Clanner on 2016/6/18.
 */
public class PieFragment extends BaseFragment implements View.OnClickListener {

    private PieView pie;
    private ArrayList<PieData> pieDatas = new ArrayList<PieData>();

    //控件
    private EditText etValue1;
    private EditText etValue2;
    private EditText etValue3;
    private EditText etValue4;
    private EditText etValue5;
    private EditText etValue6;
    private Button btn_draw;
    private Button btn_clear;

    private PieData p1;
    private PieData p2;
    private PieData p3;
    private PieData p4;
    private PieData p5;
    private PieData p6;
    private String value1;
    private String value2;
    private String value3;
    private String value4;
    private String value5;
    private String value6;
    private float number1 = 0;
    private float number2 = 0;
    private float number3 = 0;
    private float number4 = 0;
    private float number5 = 0;
    private float number6 = 0;
    private boolean isClear = false;


    public static PieFragment newInstance() {
        return new PieFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pie;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //设置ActionBar
        setHasOptionsMenu(true);
        ActionBar actionBar = getHoldingActivity().getSupportActionBar();
        actionBar.setTitle("饼状图");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setLayoutView(view);
    }

    private void setLayoutView(View view) {
        pie = (PieView) view.findViewById(R.id.pie);
        etValue1 = (EditText) view.findViewById(R.id.et_value1);
        etValue2 = (EditText) view.findViewById(R.id.et_value2);
        etValue3 = (EditText) view.findViewById(R.id.et_value3);
        etValue4 = (EditText) view.findViewById(R.id.et_value4);
        etValue5 = (EditText) view.findViewById(R.id.et_value5);
        etValue6 = (EditText) view.findViewById(R.id.et_value6);
        btn_draw = (Button) view.findViewById(R.id.btn_draw);
        btn_clear = (Button) view.findViewById(R.id.btn_clear);
        btn_draw.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_draw:
                setValue();
                break;
            case R.id.btn_clear:
                clear();
                break;
        }
    }

    /**
     * 设置数值
     */
    private void setValue() {
        value1 = etValue1.getText().toString().trim();
        value2 = etValue2.getText().toString().trim();
        value3 = etValue3.getText().toString().trim();
        value4 = etValue4.getText().toString().trim();
        value5 = etValue5.getText().toString().trim();
        value6 = etValue6.getText().toString().trim();
        if (TextUtils.isEmpty(value1) && TextUtils.isEmpty(value2) && TextUtils.isEmpty(value3) && TextUtils.isEmpty(value3)
                && TextUtils.isEmpty(value4) && TextUtils.isEmpty(value5) && TextUtils.isEmpty(value6)) {
            Toast.makeText(getHoldingActivity().getApplicationContext(), "请至少输入一个值", Toast.LENGTH_SHORT).show();
            isClear = true;
        }else {
            isClear = false;
        }
        try {
            number1 = Float.valueOf(value1);
            number2 = Float.valueOf(value2);
            number3 = Float.valueOf(value3);
            number4 = Float.valueOf(value4);
            number5 = Float.valueOf(value5);
            number6 = Float.valueOf(value6);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        p1 = new PieData("Clanner");
        p2 = new PieData("Cafe");
        p3 = new PieData("GuanXin");
        p4 = new PieData("LLL");
        p5 = new PieData("hdkfj");
        p6 = new PieData("jrhajkh");
        p1.setValue(number1);
        p2.setValue(number2);
        p3.setValue(number3);
        p4.setValue(number4);
        p5.setValue(number5);
        p6.setValue(number6);
        pieDatas.clear();
        pieDatas.add(p1);
        pieDatas.add(p2);
        pieDatas.add(p3);
        pieDatas.add(p4);
        pieDatas.add(p5);
        pieDatas.add(p6);
        draw();
    }

    /**
     * 根据数值绘制饼状图
     */
    private void draw() {
        if (isClear) return;
        pie.setData(pieDatas);
    }

    /**
     * 清空内容
     */
    private void clear() {
        etValue1.setText("");
        etValue2.setText("");
        etValue3.setText("");
        etValue4.setText("");
        etValue5.setText("");
        etValue6.setText("");
        pieDatas.clear();
        pie.setData(null);
        isClear = true;
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
}
