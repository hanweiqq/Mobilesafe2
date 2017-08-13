package com.heimad.mobilesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * 模板设计模式
 * 抽象类
 * Created by Administrator on 2017/8/6.
 */

public abstract class SetUpBaseActivity extends Activity {
    protected SharedPreferences sp;
    private GestureDetector detector;//1、定义一个手势识别器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        //1、初始化识别器
        detector = new GestureDetector(this, new MySimpleOnGeGestureListener());

    }


    public abstract void next_activity();

    public abstract void pre_activity();


    /**
     * 返回按钮事件
     */
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        pre_activity();
//    }

    /**
     * 另一种处理按键的方式，一般可以处理所有按键
     * keyCode点击的是哪个键
     * return true 处理了事件  false 代表没有处理
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            pre_activity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 上一步
     *
     * @param view
     */
    public void pre(View view) {
        pre_activity();
    }


    /**
     * 下一步
     *
     * @param view
     */
    public void next(View view) {
        System.out.println("——————————————————————");
        next_activity();
    }

    //4处理触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);//把手势识别注册在触摸事件上
        return super.onTouchEvent(event);
    }

    //3 处理手势事件
    private class MySimpleOnGeGestureListener extends GestureDetector.SimpleOnGestureListener {
        //猛动，滑动
        //(MotionEvent e1滑动之前按下的点
        //MotionEvent e2,滑动最后抬起的点
        //velocityX  x轴速率
        //velocityY Y轴速率

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float startX = e1.getRawX();
            float endX = e2.getRawX();
            float startY = e1.getY();
            float endY = e2.getY();
            if (Math.abs(endY - startY) < 100) {
                if (endX - startX > 0) {
                    pre_activity();
                } else if (startX - endX > 0) {
                    next_activity();
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
