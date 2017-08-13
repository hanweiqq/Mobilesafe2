package com.heimad.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import com.heimad.mobilesafe.ui.SettingView;

/**
 * Created by Administrator on 2017/8/6.
 */

public class Setup2Activity extends SetUpBaseActivity {
    private SettingView sv_bing;
    //获取当前手机的sim卡信息
    private TelephonyManager manager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        manager= (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        sv_bing = findViewById(R.id.sv_bing);
        //TextUtils.isEmpty来判断是否为 null ""
        if(TextUtils.isEmpty(sp.getString("sim", ""))){
            sv_bing.setChecked(false);
        }else {
            sv_bing.setChecked(true);//空指针异常   一个空的对象，调用一个方法，才会报，
            //是因为sv_bing在其下方定义的，所以提到上方就好
        }
        //sv_bing = findViewById(R.id.sv_bing);
        sv_bing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = sp.edit();
                if(sv_bing.isChecked()){//解绑
                    sv_bing.setChecked(false);
                    edit.putString("sim","");
                }else {
                    sv_bing.setChecked(true);
                    //manager.getLine1Number();//获取电话号码  由于有双卡双电手机，所以有line 1/2 nuber方法但是在中国可能获取不到电话号码
                    String simSerialNumber = manager.getSimSerialNumber();//获取sim卡的串号，相当于sim卡的身份证号
                    edit.putString("sim",simSerialNumber);
                }
                edit.commit();
            }
        });
    }

    @Override
    public void next_activity() {
        Intent intent = new Intent(getApplicationContext(),Setup3Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.tran_next_enter,R.anim.tran_next_exit);
    }

    @Override
    public void pre_activity() {
        Intent intent = new Intent(getApplicationContext(),Setup1Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.tran_pro_enter,R.anim.tran_pro_exit);
    }
}
