package com.heimad.mobilesafe.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/1.
 */

public class FocusedTextView extends android.support.v7.widget.AppCompatTextView {
    //在代码中使用控件
    public FocusedTextView(Context context) {
        super(context);
    }

    //在布局文件中  使用空间
    public FocusedTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //使用自定义样式
    public FocusedTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //判断控件是否获取到焦点
    @Override
    public boolean isFocused() {
        return true;//欺骗系统
    }
}
