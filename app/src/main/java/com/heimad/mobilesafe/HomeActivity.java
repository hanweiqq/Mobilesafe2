package com.heimad.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.heimad.mobilesafe.ui.SettingActivity;
import com.heimad.mobilesafe.utils.MD5Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/8/1.
 */

public class HomeActivity extends Activity {

    private GridView gv_home;
    private AlertDialog dialog;
    private SharedPreferences sp;
    private AlertDialog.Builder builder;
    private View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        //System.out.println("跳转到homeActivity界面");
        gv_home = findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter());
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //adapterView GridView
            //view 每个条目的布局
            //i 点击条目的位置
            //l
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0://点击手机防盗
                        if (TextUtils.isEmpty(sp.getString("password", ""))) {
                            //弹出设置密码的对话框
                            showSetupDialog();
                        } else {
                            showEnterDialog();
                        }

                        break;
                    case 8:
                        Intent intent8 = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent8);
                        break;
                }
            }
        });


    }



    /**
     * 打开输入密码对话框
     */
    private void showEnterDialog() {

        builder = new AlertDialog.Builder(this);
        view = View.inflate(getApplicationContext(), R.layout.dialog_enter, null);
        //加载控件
        final EditText et_password = view.findViewById(R.id.et_password);
        Button btn_ok = view.findViewById(R.id.bt_ok);
        Button btn_cancel = view.findViewById(R.id.bt_cancel);

        //取消按钮的点击事件
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消点击事件  ，隐藏dialog
                dialog.dismiss();
            }
        });

        //确定按钮的点击事件
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = et_password.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                //获取到配置文件的密码
                String sp_password = sp.getString("password", "");
                if (MD5Utils.digest(password).equals(sp_password)) {
                    Toast.makeText(getApplicationContext(), "密码正确", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "密码错误请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setView(view);//设置dialog显示的view对象

        dialog = builder.create();
        dialog.show();
    }

    /**
     * 弹出设置密码的对话框
     */
    private void showSetupDialog() {
        builder = new AlertDialog.Builder(this);
        view = View.inflate(getApplicationContext(), R.layout.dialog_setup, null);
        //加载控件
        final EditText et_password = view.findViewById(R.id.et_password);
        final EditText et_password_confirm = view.findViewById(R.id.et_password_confirm);
        Button btn_ok = view.findViewById(R.id.bt_ok);
        Button btn_cancel = view.findViewById(R.id.bt_cancel);

        //取消按钮的点击事件
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消点击事件  ，隐藏dialog
                dialog.dismiss();
            }
        });

        //确定按钮的点击事件
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = et_password.getText().toString().trim();
                String password_confirm = et_password_confirm.getText().toString().trim();
                if (!TextUtils.isEmpty(password)) {

                    //一定要让passrod在前  password_confirm在后，因为系统只判断password不为空，
                    //如果password_confirm在前，则会报空指针异常
                    if (password.equals(password_confirm)) {//equals比较内容，==比较地址
                        //密码设置成功
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("password", MD5Utils.digest(password));
                        edit.commit();
                        dialog.dismiss();

                    } else {
                        Toast.makeText(getApplicationContext(), "两次密码不同", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //提示用户密码框为空
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setView(view);//设置dialog显示的view对象

        dialog = builder.create();
        dialog.show();
    }

    private class HomeAdapter extends BaseAdapter {

        /**
         * 一共有九个控件
         *
         * @return
         */
        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        int[] imageId = {R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app,
                R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan,
                R.drawable.sysoptimize, R.drawable.atools, R.drawable.settings};
        String[] names = {"手机防盗", "通讯卫士", "管理软件", "进程管理", "流量统计",
                "手机杀毒", "清理缓存", "高级工具", "设置中心",};

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
//            TextView textView = new TextView(getApplicationContext());
//            textView.setText("我是第"+i+"个条目");
            View item_view = View.inflate(getApplicationContext(), R.layout.item_gridview, null);
            ImageView iv_icon = item_view.findViewById(R.id.iv_Item_icon);
            TextView tv_name = item_view.findViewById(R.id.tv_Item_name);

            iv_icon.setImageResource(imageId[i]);
            tv_name.setText(names[i]);

            return item_view;
        }
    }
}
