package com.chat.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.chat.ui.activity.R;
import com.chat.utils.CheckGameOverHelper;

import java.util.ArrayList;

/**
 * Created by Clanner on 2016/6/13.
 */
public class WuziqiPanel extends View {

    private static final String INSTANCE = "instance";
    private static final String INSTANCE_GAME_OVER = "instance_game_over";
    private static final String INSTANCE_WHITE_ARRAY = "instance_white_array";
    private static final String INSTANCE_BLACK_ARRAY = "instnce_black_array";
    private final int NO_ONE_WIN = 0;
    private final int WHITE_WIN = 1;
    private final int BLACK_WIN = 2;
    private final int whiteFirst = 1;
    private final int blackFirst = 2;
    public OnWhichListener listener;
    private ArrayList<Point> mWhiteArray = new ArrayList<Point>();
    private ArrayList<Point> mBlackArray = new ArrayList<Point>();
    //棋盘的宽度
    private int mPanelWidth;
    //每一行的高度,务必写成float
    private float mLineHeight;
    private int MAX_LINE = 10;
    private Paint mPaint = new Paint();
    //白色的棋子
    private Bitmap whitePiece;
    //黑色的棋子
    private Bitmap blackPiece;
    private float radioPieceOfLineHeight = 3 * 1.0f / 4;
    //白棋先手，当前轮到白棋
    private boolean isWhite = true;
    private boolean isGameOver;
    private CheckGameOverHelper helper;

    public WuziqiPanel(Context context, AttributeSet attrs) {
        super(context, attrs);

        setBackgroundColor(getResources().getColor(R.color.wuziqipanel_color));
        init();
    }

    private void init() {
        mPaint.setColor(0x88000000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);

        whitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        blackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heightSize);
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPanelWidth = w;
        mLineHeight = mPanelWidth * 1.0f / MAX_LINE;
        //棋子的宽高
        int pieceWidth = (int) (mLineHeight * radioPieceOfLineHeight);
        whitePiece = Bitmap.createScaledBitmap(whitePiece, pieceWidth, pieceWidth, false);
        blackPiece = Bitmap.createScaledBitmap(blackPiece, pieceWidth, pieceWidth, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isGameOver) return false;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point p = getValidPoint(x, y);
            if (mWhiteArray.contains(p) || mBlackArray.contains(p)) {
                return false;
            }
            if (isWhite) {
                mWhiteArray.add(p);
            } else {
                mBlackArray.add(p);
            }
            invalidate();
            isWhite = !isWhite;
        }
        return true;
    }

    private Point getValidPoint(int x, int y) {
        return new Point((int) (x / mLineHeight), (int) (y / mLineHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBoard(canvas);
        drawPiece(canvas);
        initEvent();
//        helper = new CheckGameOverHelper(mWhiteArray, mBlackArray);//每一次检查是否为游戏结束状态都要new一个helper对象，浪费资源
        if (helper != null) {
            helper.setDatas(mWhiteArray, mBlackArray);
        } else {
            helper = new CheckGameOverHelper(mWhiteArray, mBlackArray);
        }
        if (helper.checkGamoOver()) {
            isGameOver = true;
            if (helper.isWhiteOrBlackWin() == WHITE_WIN) {
                Toast.makeText(getContext(), "白棋胜利", Toast.LENGTH_SHORT).show();
            } else if (helper.isWhiteOrBlackWin() == BLACK_WIN) {
                Toast.makeText(getContext(), "黑棋胜利", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 绘制棋子
     *
     * @param canvas
     */
    private void drawPiece(Canvas canvas) {
        //白子
        for (int i = 0, n = mWhiteArray.size(); i < n; i++) {
            Point whitePoint = mWhiteArray.get(i);
            canvas.drawBitmap(whitePiece,
                    (whitePoint.x + (1 - radioPieceOfLineHeight) / 2) * mLineHeight,
                    (whitePoint.y + (1 - radioPieceOfLineHeight) / 2) * mLineHeight, null);
        }
        //黑子
        for (int i = 0, n = mBlackArray.size(); i < n; i++) {
            Point blackPoint = mBlackArray.get(i);
            canvas.drawBitmap(blackPiece,
                    (blackPoint.x + (1 - radioPieceOfLineHeight) / 2) * mLineHeight,
                    (blackPoint.y + (1 - radioPieceOfLineHeight) / 2) * mLineHeight, null);
        }
    }

    /**
     * 绘制棋盘
     *
     * @param canvas
     */
    private void drawBoard(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHeight;

        //绘制横线
        for (int i = 0; i < MAX_LINE; i++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);

            int y = (int) ((0.5 + i) * lineHeight);
            /**
             * 参数说明
             * 1：开始的横坐标
             * 2：开始的纵坐标
             * 3：结束的横坐标
             * 4：结束的纵坐标
             */
            canvas.drawLine(startX, y, endX, y, mPaint);
            //canvas.drawLine(y, startX, y, endX, mPaint);
        }

        //绘制竖线
        for (int i = 0; i < MAX_LINE; i++) {
            int startY = (int) (lineHeight / 2);
            int endY = (int) (w - lineHeight / 2);
            int x = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(x, startY, x, endY, mPaint);
        }
    }

    public void start() {
        mWhiteArray.clear();
        mBlackArray.clear();
        isGameOver = false;
        invalidate();
    }

    public boolean getIsWhite() {
        return isWhite;
    }

    /**
     * 注：
     * 存储与恢复需要为自定义View添加id
     *
     * @return
     */
    @Override

    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAME_OVER, isGameOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY, mWhiteArray);
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY, mBlackArray);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            isGameOver = bundle.getBoolean(INSTANCE_GAME_OVER);
            mWhiteArray = bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
            mBlackArray = bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void setWhichFirst(int which) {
        if (which == whiteFirst) {
            isWhite = true;
        } else if (which == blackFirst) {
            isWhite = false;
        }
    }

    public void setOnWhichListener(OnWhichListener listener) {
        this.listener = listener;
    }

    private void initEvent(){
        if (listener!=null){
            listener.onWhich(getIsWhite());
        }
    }

    public interface OnWhichListener {
        void onWhich(boolean isWhite);
    }
}
