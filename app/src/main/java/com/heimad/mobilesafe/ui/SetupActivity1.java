package com.heimad.mobilesafe.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/4.
 */

public class SetupActivity1 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(getApplicationContext());
        tv.setTextColor(Color.WHITE);
        tv.setText("这是手机设置向导页面");
        setContentView(tv);
    }
}
