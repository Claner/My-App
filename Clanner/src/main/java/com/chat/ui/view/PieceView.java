package com.chat.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.chat.ui.activity.R;

/**
 * Created by Clanner on 2016/6/17.
 */
public class PieceView extends View {

    private Bitmap iconBitmap;
    private String text = "";
    //这个方法是转变为标准尺寸的一个函数
    private int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());

    //内存中绘图
    private Canvas canvas;
    private Bitmap bitmap;
    private Paint paint;

    //icon和text的位置
    private Rect iconRect;
    private Rect textRect;
    private Paint textPaint;

    public PieceView(Context context) {
        this(context, null);
    }

    public PieceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 获取自定义属性的值
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public PieceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //遍历attrs里的值
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.pieceView);
        int n = array.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.pieceView_piece_icon:
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) array.getDrawable(attr);
                    iconBitmap = bitmapDrawable.getBitmap();
                    break;
                case R.styleable.pieceView_piece_text:
                    text = array.getString(attr);
                    break;
                case R.styleable.pieceView_piece_text_size:
                    textSize = (int) array.getDimension(attr, TypedValue.applyDimension
                            (TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
            }
        }
        array.recycle();

        //初始化
        textRect = new Rect();
        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setColor(0Xff555555);
        //测量文字的范围
        textPaint.getTextBounds(text, 0, text.length(), textRect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * icon的边长
         * view的宽度-leftpadding=rightpadding
         * view的高度 - toppadding-bottompadding=textRect.height
         */
        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight()
                , getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - textRect.height());
        int left = getMeasuredWidth() / 2 - iconWidth / 2;
        int top = getMeasuredHeight() / 2 - (textRect.height() + iconWidth / 2);
        iconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        textPaint.setColor(0xFF45C01A);
        int x = getMeasuredWidth() / 2 - textRect.width() / 2;
        int y = iconRect.bottom + textRect.height();
        canvas.drawBitmap(iconBitmap, null, iconRect, null);
        canvas.drawText(text, x, y, textPaint);
    }
}
