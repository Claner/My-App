package com.chat.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.chat.MyApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Clanner on 2016/6/15.
 * 获取验证码倒计时按钮
 */
public class TimeButton extends Button implements View.OnClickListener {

    private final String TIME = "time";
    private final String CTIME = "ctime";
    Map<String, Long> map = new HashMap<String, Long>();
    private long timeLenght = 60 * 1000;  //倒计时长度，默认为60秒
    private String textAfter = "秒重新获取";
    private String textBefore = "点击获取验证码";
    private OnClickListener listener;
    private Timer timer;
    private TimerTask timerTask;
    private Long time;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TimeButton.this.setText(time / 1000 + textAfter);
            time -= 1000;
            if (time < 0) {
                TimeButton.this.setEnabled(true);
                TimeButton.this.setText(textBefore);
                clearTimer();
            }
        }
    };

    public TimeButton(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    private void initTimer() {
        time = timeLenght;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0x01);
            }
        };
    }

    private void clearTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (l instanceof TimeButton) {
            super.setOnClickListener(l);
        } else {
            this.listener = l;
        }
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
        initTimer();
        this.setText(time / 1000 + textAfter);
        this.setEnabled(false);
        timer.schedule(timerTask, 0, 1000);
    }

    /**
     * 和activity的onCreate()方法同步
     *
     * @param bundle
     */
    public void onCreate(Bundle bundle) {
        if (MyApplication.map == null) return;
        //这里表示没有上次未完成的计时
        if (MyApplication.map.size() <= 0) return;
        long time = System.currentTimeMillis() - MyApplication.map.get(CTIME) - MyApplication.map.get(TIME);
        MyApplication.map.clear();
        if (time > 0) {
            return;
        } else {
            initTimer();
            this.time = Math.abs(time);
            timer.schedule(timerTask, 0, 1000);
            this.setText(time + textAfter);
            this.setEnabled(false);
        }
    }

    /**
     * 和activity的onDestroy()方法同步
     */
    public void onDestroy() {
        if (MyApplication.map == null) MyApplication.map = new HashMap<String, Long>();
        MyApplication.map.put(TIME, time);
        MyApplication.map.put(CTIME, System.currentTimeMillis());
        clearTimer();
    }

    /**
     * 设置计时时现实的文本
     * @param text
     * @return
     */
    public TimeButton setTextAfter(String text) {
        this.textAfter = text;
        return this;
    }

    /**
     * 设置点击之前的文本
     * @param text
     * @return
     */
    public TimeButton setTextBefore(String text) {
        this.textBefore = text;
        return this;
    }

    /**
     * 设置倒计时长度
     * @param lenght
     * @return
     */
    public TimeButton setTimeLenght(long lenght) {
        this.timeLenght = lenght;
        return this;
    }
}
