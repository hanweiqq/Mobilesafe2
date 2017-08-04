package com.heimad.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.heimad.mobilesafe.ui.SetupActivity1;

/**
 * 手机防盗界面
 * Created by Administrator on 2017/8/4.
 */

public class LostFindActivity extends Activity {
    private SharedPreferences sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp= getSharedPreferences("config",MODE_PRIVATE);
        TextView tv = new TextView(getApplicationContext());
        tv.setTextColor(Color.WHITE);
        tv.setText("这是手机防盗页面");
        //如果用户第一次进入手机防盗页面，则让用户跳转到设置向导
        boolean b =sp.getBoolean("first",true);
        if(b){
            //第一次进入
            Intent intent = new Intent(getApplicationContext(), SetupActivity1.class);
            startActivity(intent);
            finish();
        }
        setContentView(tv);
    }
}
