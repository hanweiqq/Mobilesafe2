package com.heimad.mobilesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heimad.mobilesafe.R;

/**
 * Created by Administrator on 2017/8/3.
 */

public class SttingView extends RelativeLayout {
    public SttingView(Context context) {
        super(context);
        init();
    }


    public SttingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SttingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 控件初始化
     */
    private void init() {
//        TextView tv = new TextView(getContext());
//        tv.setText("我是自定义控件");

        View view = View.inflate(getContext(), R.layout.setting_view,this);
        //在相对布局上，添加一个控件view
       // this.addView(view);
    }

}
