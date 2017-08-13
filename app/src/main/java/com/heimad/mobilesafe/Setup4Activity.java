package com.heimad.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/8/6.
 */

public class Setup4Activity extends SetUpBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);

    }

    @Override
    public void next_activity() {
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("first",false);
        edit.commit();
        Intent intent = new Intent(getApplicationContext(),LostFindActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.tran_next_enter,R.anim.tran_next_exit);
    }

    @Override
    public void pre_activity() {
        Intent intent = new Intent(getApplicationContext(),Setup3Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.tran_pro_enter,R.anim.tran_pro_exit);
    }
}
