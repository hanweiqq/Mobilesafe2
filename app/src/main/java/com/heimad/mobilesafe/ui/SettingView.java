package com.heimad.mobilesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heimad.mobilesafe.R;
import com.lidroid.xutils.db.annotation.Check;

/**
 * Created by Administrator on 2017/8/3.
 */

public class SettingView extends RelativeLayout {

    private TextView tv_title;
    private TextView tv_des;
    private CheckBox cb;
    private String title;
    private String des_on;
    private String des_off;

    public SettingView(Context context) {
        super(context);
        init();
    }

    /**
     * 在布局文件中使用控件
     *
     * @param context
     * @param attrs
     */
    public SettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
//        System.out.println("************************"+attrs.getAttributeValue(0));
//        System.out.println(attrs.getAttributeValue(1));
//        System.out.println(attrs.getAttributeValue(2));
//        System.out.println(attrs.getAttributeValue(3));
//        System.out.println(attrs.getAttributeValue(4));
//        System.out.println(attrs.getAttributeValue(5));

        title = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "title");
        des_on = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "des_on");
        des_off = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "des_off");

        setTitle(title);
        if (cb.isChecked()) {
            setDes(des_on);
        } else {
            setDes(des_off);
        }

    }


    public SettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    /**
     * 控件初始化
     */
    private void init() {
//        TextView tv = new TextView(getContext());
//        tv.setText("我是自定义控件");

        View view = View.inflate(getContext(), R.layout.setting_view, this);
        //在相对布局上，添加一个控件view
        // this.addView(view);

        //暴露方法

        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_des = (TextView) view.findViewById(R.id.tv_des);
        cb = view.findViewById(R.id.cb);
    }

    /**
     * 修改标题
     */
    public void setTitle(String title) {
        tv_title.setText(title);
    }

    /**
     * 修改描述信息
     */
    public void setDes(String des) {
        tv_des.setText(des);
    }

    /**
     * 判断是否被选中
     */
    public boolean isChecked() {
        return cb.isChecked();
    }

    /**
     * 修改选中状态
     */
    public void setChecked(boolean isChecked) {
        cb.setChecked(isChecked);

        setTitle(title);
        if (cb.isChecked()) {
            setDes(des_on);
        } else {
            setDes(des_off);
        }
    }


}
