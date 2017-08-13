package com.heimad.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;


/**
 * 手机防盗界面
 * Created by Administrator on 2017/8/4.
 */

public class LostFindActivity extends Activity {
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("config", MODE_PRIVATE);
//        TextView tv = new TextView(getApplicationContext());
//        tv.setTextColor(Color.WHITE);
//        tv.setText("这是手机防盗页面");
        //如果用户第一次进入手机防盗页面，则让用户跳转到设置向导
        boolean b = sp.getBoolean("first", true);
        if (b) {
            //第一次进入
            Intent intent = new Intent(getApplicationContext(), Setup1Activity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_lostfind);
        }
    }

    /**
     * 重新进入设置向导
     * @param view
     */
    public void resetup(View view) {
        Intent intent = new Intent(getApplicationContext(), Setup1Activity.class);
        startActivity(intent);
        finish();

    }
}
