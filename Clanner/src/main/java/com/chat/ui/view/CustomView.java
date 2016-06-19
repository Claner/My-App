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
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);  //取出宽度的确切数值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);  //取出宽度的测量模式

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);    //取出高度的确切数值
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);    //取出高度的测量模式

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 这是因为View的大小不仅由View本身控制，而且受父控件的影响，所以我们在确定View大小的时候最好使用系统提供的onSizeChanged回调函数。
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //创建一个画笔
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
        //设置颜色
        paint.setColor(getResources().getColor(android.R.color.holo_blue_light));
//        //绘制普通圆
//        canvas.drawCircle(0, 0, 100, paint);
        //设置空心Style
        paint.setStyle(Paint.Style.STROKE);
        //设置空心边框的宽度
        paint.setStrokeWidth(20);
        //绘制空心圆
        canvas.drawCircle(90, 90, 90, paint);
        canvas.drawCircle(getMeasuredWidth() - 90, 90, 90, paint);
        //绘制点
        canvas.drawPoint(90, 90, paint);
        canvas.drawPoint(getMeasuredWidth() - 90, 90, paint);
        //绘制点
        canvas.drawLine(90, 90, getMeasuredWidth() - 90, 90, paint);
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

//        /**
//         * 绘制文字：drawText(String text, float x, float y, Paint paint)
//         *text： 文本
//         * x： 文本origin的x坐标
//         * y： 文本baseline的y坐标
//         * paint： 绘制风格
//         */
//        //设置颜色
//        paint.setColor(getResources().getColor(android.R.color.holo_orange_dark));
//        paint.setTextSize(100);
//        //绘制文本
//        canvas.drawText("Welcome", 250, 100, paint);
    }

    /**
     * 自定义View
     *
     * view的坐标系
     *  getTop();       //获取子View左上角距父View顶部的距离
     *  getLeft();      //获取子View左上角距父View左侧的距离
     *  getBottom();    //获取子View右下角距父View顶部的距离
     *  getRight();     //获取子View右下角距父View左侧的距离
     *
     *  测量模式
     *  模式          二进制值        描述
     *  UNSPECIFIED	    00	        默认值，父控件没有给子view任何限制，子View可以设置为任意大小。
     *  EXACTLY	        01	        表示父控件已经确切的指定了子View的大小。
     *  AT_MOST	        10	        表示子View具体大小没有尺寸限制，但是存在上限，上限一般为父View大小。
     *
     *  Canvas简介
     *
     *  Canvas我们可以称之为画布，能够在上面绘制各种东西，是安卓平台2D图形绘制的基础，非常强大。
     *
     *  绘制颜色：
     *  drawColor, drawRGB, drawARGB	使用单一颜色填充整个画布
     *
     *  绘制基本形状：
     *  drawPoint, drawPoints, drawLine, drawLines, drawRect, drawRoundRect, drawOval, drawCircle, drawArc
     *  依次为 点、线、矩形、圆角矩形、椭圆、圆、圆弧
     *
     *  绘制图片：
     *  drawBitmap, drawPicture	绘制位图和图片
     *
     *  绘制文本：
     *  drawText, drawPosText, drawTextOnPath
     *  依次为 绘制文字、绘制文字时指定每个文字位置、根据路径绘制文字
     *
     *  绘制路径：
     *  drawPath    绘制路径，绘制贝塞尔曲线时也需要用到该函数
     *
     *  顶点操作：
     *  drawVertices, drawBitmapMesh
     *  通过对顶点操作可以使图像形变，drawVertices直接对画布作用、 drawBitmapMesh只对绘制的Bitmap作用
     *
     *  画布裁剪：
     *  clipPath, clipRect	设置画布的显示区域
     *
     *  画布快照：
     *  save, restore, saveLayerXxx, restoreToCount, getSaveCount
     *  依次为 保存当前状态、 回滚到上一次保存的状态、 保存图层状态、 回滚到指定状态、 获取保存次数
     *
     *  画布变换：
     *  translate, scale, rotate, skew	依次为 位移、缩放、 旋转、倾斜
     *
     *  Matrix(矩阵)
     *  getMatrix, setMatrix, concat
     *  实际画布的位移，缩放等操作的都是图像矩阵Matrix，只不过Matrix比较难以理解和使用，故封装了一些常用的方法。
     */
}
