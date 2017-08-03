package com.heimad.mobilesafe.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ViewUtils;
import android.view.View;

import com.heimad.mobilesafe.R;
import com.lidroid.xutils.view.annotation.ViewInject;

import static android.content.SharedPreferences.*;

/**
 * Created by Administrator on 2017/8/1.
 */

public class SettingActivity extends Activity {
    @ViewInject(R.id.sv_update)
    private SettingView sv_update;

    private SharedPreferences sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sp=getSharedPreferences("config",MODE_PRIVATE);

        com.lidroid.xutils.ViewUtils.inject(this);

        //数据回显
        boolean update = sp.getBoolean("update", true);
        if(update){
            sv_update.setDes("自动更新开启");
            sv_update.setChecked(true);
        }else {
            sv_update.setDes("自动更新关闭");
            sv_update.setChecked(false);
        }
        sv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editor edit  = sp.edit();
                if(sv_update.isChecked()){
                    sv_update.setChecked(false);
                    sv_update.setDes("自动更新关闭");
                    edit.putBoolean("update",false);
                }else {
                    sv_update.setChecked(true);
                    sv_update.setDes("自动更新开启");
                    edit.putBoolean("update",true);
                }
                edit.commit();
            }
        });
    }

}
