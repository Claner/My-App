package com.Wuziqi.utils;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clanner on 2016/6/13.
 * 检查是否游戏结束的工具类
 */
public class CheckGameOverHelper {

    private final int MAX_COUNT_IN_LINE = 5;
    private final int NO_ONE_WIN = 0;
    private final int WHITE_WIN = 1;
    private final int BLACK_WIN = 2;
    private ArrayList<Point> mWhiteArray;
    private ArrayList<Point> mBlackArray;
    private boolean whiteWin = false;
    private boolean blackWin = false;

    public CheckGameOverHelper() {

    }

    public CheckGameOverHelper(ArrayList<Point> mWhiteArray, ArrayList<Point> mBlackArray) {
        this.mWhiteArray = mWhiteArray;
        this.mBlackArray = mBlackArray;
    }

    public void setDatas(ArrayList<Point> mWhiteArray, ArrayList<Point> mBlackArray) {
        this.mWhiteArray = mWhiteArray;
        this.mBlackArray = mBlackArray;
    }

    public int isWhiteOrBlackWin() {
        if (whiteWin) {
            return WHITE_WIN;
        } else if (blackWin) {
            return BLACK_WIN;
        } else {
            return NO_ONE_WIN;
        }
    }

    /**
     * 判断游戏是否结束
     */
    public boolean checkGamoOver() {
        whiteWin = checkFiveInLine(mWhiteArray);
        blackWin = checkFiveInLine(mBlackArray);
        if (whiteWin || blackWin) return true;
        return false;
    }

    /**
     * 判断是否五子连线
     *
     * @param points
     * @return
     */
    private boolean checkFiveInLine(List<Point> points) {
        for (Point p : points) {
            int x = p.x;
            int y = p.y;
            boolean win = checkHorizontal(x, y, points);
            if (win) return true;
            win = checkVertical(x, y, points);
            if (win) return true;
            win = checkLeftDiagonal(x, y, points);
            if (win) return true;
            win = checkRightDiagonal(x, y, points);
            if (win) return true;
        }
        return false;
    }

    /**
     * 判断x，y位置的棋子是否横向有相邻的五个一致
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean checkHorizontal(int x, int y, List<Point> points) {
        int count = 1;
        //左
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y))) {
                count++;
            } else {
                break;
            }
        }
        //右
        if (count == MAX_COUNT_IN_LINE) return true;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x + i, y))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) return true;
        return false;
    }

    /**
     * 判断x，y位置的棋子是否垂直有相邻的五个一致
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean checkVertical(int x, int y, List<Point> points) {
        int count = 1;
        //上
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x, y - i))) {
                count++;
            } else {
                break;
            }
        }
        //下
        if (count == MAX_COUNT_IN_LINE) return true;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) return true;
        return false;
    }

    /**
     * 判断x，y位置的棋子是否左斜有相邻的五个一致
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean checkLeftDiagonal(int x, int y, List<Point> points) {
        int count = 1;
        //左上
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y + i))) {
                count++;
            } else {
                break;
            }
        }
        //左下
        if (count == MAX_COUNT_IN_LINE) return true;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x + i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) return true;
        return false;
    }

    /**
     * 判断x，y位置的棋子是否右斜有相邻的五个一致
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean checkRightDiagonal(int x, int y, List<Point> points) {
        int count = 1;
        //右上
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        //右下
        if (count == MAX_COUNT_IN_LINE) return true;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x + i, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) return true;
        return false;
    }

}
