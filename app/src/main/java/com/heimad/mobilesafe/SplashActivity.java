package com.heimad.mobilesafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.heimad.mobilesafe.utils.StreamUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;

public class SplashActivity extends AppCompatActivity {

    private static final int MSG_SHOW_UPDATE = 10;
    private static final int MSG_ENTER_HOME = 20;
    private static final int MES_IO_ERROR = 30;
    private static final int MES_JSON_ERROR = 40;
    private static final int MSG_NET_ERROR = 50;
    //@ViewInject(R.id.tv_splash_version)
    private TextView tv_splash_version;
    //@ViewInject(R.id.tv_splash_process)
    private TextView tv_splash_process;

    private String apkCode;
    private String des;
    private String apkurl;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SHOW_UPDATE:
                    showUpdateDialog();
                    break;
                case MSG_ENTER_HOME:
                    enterHome();
                    break;
                case MES_IO_ERROR:
                    Toast.makeText(getApplicationContext(), "错误号：" + MES_IO_ERROR, Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case MES_JSON_ERROR:
                    enterHome();
                    Toast.makeText(getApplicationContext(), "错误号：" + MES_JSON_ERROR, Toast.LENGTH_SHORT).show();
                    break;
                case MSG_NET_ERROR:
                    Toast.makeText(getApplicationContext(),"请检查网络连接",Toast.LENGTH_SHORT).show();
                default:
                    break;

            }
        }
    };

    /**
     * 进入home界面
     */
    private void enterHome() {

        //开启下一个activity
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ViewUtils.inject(this);
        tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
        tv_splash_process = (TextView) findViewById(R.id.tv_splash_process);
        //动态显示版本号
        tv_splash_version.setText(getVersionName());

        checkupdate();
    }

    /**
     * 检查服务器版本
     */
    private void checkupdate() {
        //访问网络  比较耗时的操作在android 4.0以后都不能再主线程中操作
        new Thread() {
            @Override
            public void run() {
                ;
                super.run();
                long startTime = System.currentTimeMillis();
                Message message = Message.obtain();
                try {
                    URL url = new URL(getResources().getString(R.string.server_url));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    //conn.setConnectTimeout();设置读取超时时间
                    int code = conn.getResponseCode();
                    if (200 == code) {
                        //解析服务器数据，获取服务器的版本号
                        //System.out.println("获取服务器数据");

                        InputStream inputStream = conn.getInputStream();//返回服务器输入流
//最新的版本
                        String json = StreamUtils.parserInputStream(inputStream);
                        //System.out.println("____________str"+json);
                        //最新版本的下载地址

                        //新版本的描述信息 json xml

                        //解析json
                        JSONObject jsonObject = new JSONObject(json);

                        apkCode = jsonObject.getString("code");
                        des = jsonObject.getString("des");
                        apkurl = jsonObject.getString("apkurl");

                        //System.out.println(apkCode+"..."+des+"..."+apkurl);
                        if (getVersionName().equals(apkCode)) {
                            //版本号一致,正常打开
                            message.what = MSG_ENTER_HOME;

                        } else {
                            //版本号不一致，弹出升级对话框
                            //子线程中不能直接更改界面showUpdateDialog();
                            //new Message（）；
                            message.what = MSG_SHOW_UPDATE;
                        }
                    }else{
                        message.what = MSG_NET_ERROR;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    message.what = MES_IO_ERROR;
                } catch (JSONException e) {
                    e.printStackTrace();
                    message.what = MES_JSON_ERROR;
                } finally {
                    long endTime = System.currentTimeMillis();
                    long dTime = endTime - startTime;
                    if (dTime < 2000) {
                        try {
                            Thread.sleep(2000 - dTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    /**
     * 弹出升级对话框
     */
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);//设置对话框的图标
        builder.setTitle("发现新版本：" + apkCode);
        builder.setMessage(des);
        builder.setCancelable(false);//不可以直接取消
        //如果单击确定，则下载新的版本
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                //下载新的版本
                download();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                enterHome();
            }
        });//如果点击事件设置为空，则默认取消dialog
        //builder.create().show();
        builder.show();

    }

    /**
     * 下载新版本
     */
    private void download() {
        HttpUtils httpUtils = new HttpUtils();
        //            参数1  下载链接   参数2  下载的目的地                  参数3     下完完成后的回调方法
        httpUtils.download(apkurl, "/mnt/sdcard/mobilesafe2.0.apk", new RequestCallBack<File>() {
            //下载成功
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                //安装
                //调用系统安装程序的应用来安装其他程序
                installApk();

            }

            //下载进度
            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                tv_splash_process.setVisibility(TextView.VISIBLE);
                tv_splash_process.setText(current + "/" + total);
            }

            //下载失败
            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getApplicationContext(), "下载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 安装应用 打开系统的packageInstallerActivity
     */
    private void installApk() {
//          <activity android:name=".PackageInstallerActivity"
//        android:configChanges="orientation|keyboardHidden"
//        android:theme="@style/TallTitleBarTheme">
//            <intent-filter>
//                <action android:name="android.intent.action.VIEW" />
//                <category android:name="android.intent.category.DEFAULT" />
//                <data android:scheme="content" />
//                <data android:scheme="file" />
//                <data android:mimeType="application/vnd.android.package-archive" />
//            </intent-filter>
//        </activity>
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/mobilesafe2.0.apk")),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public String getVersionName() {
        return BuildConfig.VERSION_NAME;
//        //PackageManager pm = getPackageManager();
//        try {
//            //获取当前程序的包名
//            //pm.getPackageInfo("com.project.mobilesafe",0);
//            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
//            return packageInfo.versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        //String versionName = BuildConfig.VERSION_NAME;
        // return null;
    }
}
