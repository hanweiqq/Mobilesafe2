package com.heimad.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.heimad.mobilesafe.ui.SettingActivity;

/**
 *
 * Created by Administrator on 2017/8/1.
 */

public class HomeActivity extends Activity {

    private GridView gv_home;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
                switch (i){
                    case 8:
                        Intent intent8 = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent8);
                }
            }
        });


    }
    private class HomeAdapter extends BaseAdapter{

        /**
         * 一共有九个控件
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

        int[] imageId = {R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app,
                            R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,
                            R.drawable.sysoptimize,R.drawable.atools,R.drawable.settings};
        String[] names = {"手机通讯","通讯卫士","管理软件","进程管理","流量统计",
                "手机杀毒","清理缓存","高级工具","设置中心",};
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
