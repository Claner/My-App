package com.chat.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.chat.ui.activity.R;

/**
 * Created by Clanner on 2016/5/26.
 */
public class FragmentOne extends BaseFragment implements View.OnClickListener {

    private ImageView testImageView;

    public static FragmentOne newInstance() {
        return new FragmentOne();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        testImageView = (ImageView) view.findViewById(R.id.test_image);
        setButtonListener(view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    private void setButtonListener(View view) {
        view.findViewById(R.id.btn_alpha).setOnClickListener(this);
        view.findViewById(R.id.btn_scale).setOnClickListener(this);
        view.findViewById(R.id.btn_translate).setOnClickListener(this);
        view.findViewById(R.id.btn_rotate).setOnClickListener(this);
        view.findViewById(R.id.btn_combination1).setOnClickListener(this);
        view.findViewById(R.id.btn_combination2).setOnClickListener(this);
        view.findViewById(R.id.btn_flash).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Animation animation;
        switch (view.getId()) {
            case R.id.btn_alpha:
                animation = AnimationUtils.loadAnimation(getHoldingActivity(), R.anim.alpha);
                testImageView.startAnimation(animation);
                break;
            case R.id.btn_scale:
                animation = AnimationUtils.loadAnimation(getHoldingActivity(), R.anim.scale);
                testImageView.startAnimation(animation);
                break;
            case R.id.btn_translate:
                animation = AnimationUtils.loadAnimation(getHoldingActivity(), R.anim.translate);
                testImageView.startAnimation(animation);
                break;
            case R.id.btn_rotate:
                animation = AnimationUtils.loadAnimation(getHoldingActivity(), R.anim.rotate);
                testImageView.startAnimation(animation);
                break;
            case R.id.btn_combination1:
                comninationAnimation();
                break;
            case R.id.btn_combination2:
                animation = AnimationUtils.loadAnimation(getHoldingActivity(), R.anim.combination);
                testImageView.startAnimation(animation);
                break;
            case R.id.btn_flash:
                flashAnimation();
                break;
        }
    }

    /**
     * 组合动画
     */
    private void comninationAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getHoldingActivity(), R.anim.alpha);
        testImageView.startAnimation(animation);
        final Animation animation2 = AnimationUtils.loadAnimation(getHoldingActivity(), R.anim.rotate);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                testImageView.startAnimation(animation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /**
     * 闪烁动画
     */
    private void flashAnimation() {
        //动态加载动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(100);
        alphaAnimation.setRepeatCount(10);
        //倒序重复REVERSE  正序重复RESTART
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        testImageView.startAnimation(alphaAnimation);
    }

}
