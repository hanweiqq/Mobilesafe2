package com.heimad.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/8/6.
 */

public class Setup3Activity extends SetUpBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
    }

    @Override
    public void next_activity() {
        Intent intent = new Intent(getApplicationContext(),Setup4Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.tran_next_enter,R.anim.tran_next_exit);
    }

    @Override
    public void pre_activity() {
        Intent intent = new Intent(getApplicationContext(),Setup2Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.tran_pro_enter,R.anim.tran_pro_exit);
    }
}
