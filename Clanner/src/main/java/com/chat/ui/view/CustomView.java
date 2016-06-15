package com.chat.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Clanner on 2016/6/2.
 */
public class CustomView extends View {
    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();

        /**
         * 绘制圆形：drawCircle(float cx, float cy, float radius, Paint paint)
         * cx： 圆心的x坐标
         * cy： 圆心的y坐标
         * radius： 圆的半径
         * paint： 绘制风格
         */
        //去锯齿
        paint.setAntiAlias(true);
//        //设置颜色
//        paint.setColor(getResources().getColor(android.R.color.holo_blue_light));
//        //绘制普通圆
//        canvas.drawCircle(200, 200, 100, paint);
//        //设置空心Style
//        paint.setStyle(Paint.Style.STROKE);
//        //设置空心边框的宽度
//        paint.setStrokeWidth(20);
//        //绘制空心圆
//        canvas.drawCircle(200, 500, 90, paint);
//
//        /**
//         * 绘制矩形：
//         * drawRect(float left, float top, float right, float bottom, Paint paint) / drawRect(RectF rect, Paint paint)
//         * left： 矩形left的x坐标
//         * top： 矩形top的y坐标
//         * right： 矩形right的x坐标
//         * bottom： 矩形bottom的y坐标
//         * paint： 绘制风格
//         */
//        //设置颜色
//        paint.setColor(getResources().getColor(android.R.color.holo_blue_light));
//        //绘制正方形
//        canvas.drawRect(100, 100, 300, 300, paint);
//        //上面代码等同于
//        //RectF rel=new RectF(100,100,300,300);
//        //canvas.drawRect(rel, paint);
//
//        //设置空心Style
//        paint.setStyle(Paint.Style.STROKE);
//        //设置空心边框的宽度
//        paint.setStrokeWidth(20);
//        //绘制空心矩形
//        canvas.drawRect(100, 400, 600, 800, paint);
//
//        /**
//         * 绘制圆角矩形：
//         * drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, Paint paint)
//         * / drawRoundRect(RectF rect, float rx, float ry, Paint paint)
//         * left： 图形left的x坐标
//         * top： 图形top的y坐标
//         * right： 图形right的x坐标
//         * bottom： 图形bottom的y坐标
//         * rx： x方向的圆角半径
//         * ry： y方向的圆角半径
//         * paint > 绘制风格
//         */
//        //设置颜色
//        paint.setColor(getResources().getColor(android.R.color.holo_blue_light));
//        //绘制圆角矩形
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            canvas.drawRoundRect(100, 100, 300, 300, 30, 30, paint);
//        }
//        //上面代码等同于
//        //RectF rel=new RectF(100,100,300,300);
//        //canvas.drawRoundRect(rel,30,30,paint);
//
//        //设置空心Style
//        paint.setStyle(Paint.Style.STROKE);
//        //设置空心边框的宽度
//        paint.setStrokeWidth(20);
//        //绘制空心圆角矩形
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            canvas.drawRoundRect(100, 400, 600, 800, 30, 30, paint);
//        }
//
//        /**
//         * 绘制椭圆：drawOval(float left, float top, float right, float bottom, Paint paint)
//         * left： 图形left的x坐标
//         * top： 图形top的y坐标
//         * right： 图形right的x坐标
//         * bottom： 图形bottom的y坐标
//         * paint： 绘制风格
//         */
//        //设置颜色
//        paint.setColor(getResources().getColor(android.R.color.holo_orange_dark));
//        //绘制椭圆
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            canvas.drawOval(100, 100, 500, 300, paint);
//        }
//        //设置空心Style
//        paint.setStyle(Paint.Style.STROKE);
//        //设置空心边框的宽度
//        paint.setStrokeWidth(20);
//        //绘制空心椭圆
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            canvas.drawOval(100, 400, 600, 800, paint);
//        }
//
//        /**
//         * 绘制弧：drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
//         * oval： 指定圆弧的外轮廓矩形区域
//         * startAngle： 圆弧起始角度，单位为度
//         * sweepAngle： 圆弧扫过的角度，顺时针方向，单位为度
//         * useCenter： 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形
//         * paint： 绘制风格
//         */
//        //设置颜色
//        paint.setColor(getResources().getColor(android.R.color.holo_orange_dark));
//        RectF rel = new RectF(100, 100, 300, 300);
//        //实心圆弧
//        canvas.drawArc(rel, 0, 270, false, paint);
//        //实心圆弧 将圆心包含在内
//        RectF rel2 = new RectF(100, 400, 300, 600);
//        canvas.drawArc(rel2, 0, 270, true, paint);
//        //设置空心Style
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(20);
//        RectF rel3 = new RectF(100, 700, 300, 900);
//        canvas.drawArc(rel3, 0, 270, false, paint);
//        RectF rel4 = new RectF(100, 1000, 300, 1200);
//        canvas.drawArc(rel4, 0, 270, true, paint);

        /**
         * 绘制文字：drawText(String text, float x, float y, Paint paint)
         *text： 文本
         * x： 文本origin的x坐标
         * y： 文本baseline的y坐标
         * paint： 绘制风格
         */
        //设置颜色
        paint.setColor(getResources().getColor(android.R.color.holo_orange_dark));
        paint.setTextSize(100);
        //绘制文本
        canvas.drawText("Welcome", 80, 150, paint);
    }
}
