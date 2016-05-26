package com.cetcme.rcldfmandroid;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.LENGTH_SHORT;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView welcomeTextView;
    private Button addButton;
    private Button listButton;

    private JSONObject myShipInfoJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
        addButton = (Button) findViewById(R.id.addButton);
        listButton = (Button) findViewById(R.id.listButton);

        addButton.setOnClickListener(this);
        listButton.setOnClickListener(this);

        //set button size
        Display display = getWindowManager().getDefaultDisplay();
        Point pointSize = new Point();
        display.getSize(pointSize);
        int buttonSize = pointSize.x * 14 / 44;
        int horizontalMargin = buttonSize / 3;
        int verticalMargin = pointSize.y * 7 / 100;

        RelativeLayout.LayoutParams welcomeTextParams = (RelativeLayout.LayoutParams) welcomeTextView.getLayoutParams();
        welcomeTextParams.bottomMargin = verticalMargin * 4 / 5;
        welcomeTextView.setLayoutParams(welcomeTextParams);

        RelativeLayout.LayoutParams myShipButtonParams = (RelativeLayout.LayoutParams) addButton.getLayoutParams();
        myShipButtonParams.height = buttonSize;
        myShipButtonParams.width = buttonSize;
        addButton.setLayoutParams(myShipButtonParams);

        RelativeLayout.LayoutParams routeButtonParams = (RelativeLayout.LayoutParams) listButton.getLayoutParams();
        routeButtonParams.height = buttonSize;
        routeButtonParams.width = buttonSize;
        routeButtonParams.topMargin = verticalMargin;
        listButton.setLayoutParams(routeButtonParams);

        //获取上一个Activity的数据
//        Bundle bundle = this.getIntent().getExtras();
//        String str = bundle.getString("myShipInfo");
//        try {
//            myShipInfoJSON = new JSONObject(str);
//            JSONArray data = myShipInfoJSON.getJSONArray("data");
//            JSONObject data0 = data.getJSONObject(0);
//            String ownerName = data0.getString("ownerName");
//            if (ownerName.isEmpty()) {
//                welcomeTextView.setText("欢迎使用本软件!");
//            } else {
//                welcomeTextView.setText(ownerName + "，欢迎使用本软件!");
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            welcomeTextView.setText("欢迎使用本软件!");
//        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem qrcode = menu.add(0,0,0,"二维码");
        qrcode.setIcon(R.drawable.qrcodeicon);
        qrcode.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        qrcode.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //TODO: QRCODE
                Toast.makeText(getApplicationContext(),"二维码扫描，待开发",LENGTH_SHORT).show();
                return false;
            }
        });

        MenuItem setting = menu.add(0, 0, 0, "退出");
        setting.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        setting.setIcon(R.drawable.user);
        setting.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                logoutDialog();
                return false;
            }
        });

        //TODO: 修改密码
//        MenuItem changePassword = menu.add(0, 0, 0, "修改密码");
//        changePassword.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//        changePassword.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Toast.makeText(getApplicationContext(),"修改密码",LENGTH_SHORT).show();
//                return false;
//            }
//        });

        return true;

    }

    public void onBackPressed() {
        //返回手机桌面
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }

    private void logout() {
        Intent loginIntent = new Intent();
        loginIntent.setClass(getApplicationContext(), LoginActivity.class);
        startActivity(loginIntent);
        overridePendingTransition(R.anim.push_right_in_no_alpha, R.anim.push_right_out_no_alpha);
        finish();
    }

    private void logoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(IndexActivity.this);
//        builder.setIcon(R.mipmap.ic_launcher);
        builder.setIcon(android.R.drawable.ic_menu_myplaces);
        builder.setMessage("是否继续?");
        builder.setTitle("即将退出登录");
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final KProgressHUD kProgressHUD = KProgressHUD.create(IndexActivity.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("退出中")
                        .setAnimationSpeed(1)
                        .setDimAmount(0.3f)
                        .setSize(110, 110)
                        .show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        kProgressHUD.dismiss();
                        logout();
                    }
                },1000);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    private void finishDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(IndexActivity.this);
//        builder.setIcon(R.mipmap.ic_launcher);
        builder.setIcon(android.R.drawable.ic_delete);

        builder.setMessage("是否继续?");
        builder.setTitle("即将关闭程序");
        builder.setNeutralButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setPositiveButton("最小化", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //返回手机桌面
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.addButton:
//                Bundle bundle = new Bundle();
//                bundle.putString("myShipInfo", myShipInfoJSON.toString());
                Intent addIntent = new Intent();
                addIntent.setClass(getApplicationContext(), AddActivity.class);
//                myShipIntent.putExtras(bundle);
                startActivity(addIntent);
                overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
                break;
            case R.id.listButton:
                Intent listIntent = new Intent();
                listIntent.setClass(getApplicationContext(), ListActivity.class);
                startActivity(listIntent);
                overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
                break;


        }
    }

    private void showAntiThiefDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(IndexActivity.this);
        builder.setMessage("当前位置已超出您设置的防盗范围");
        builder.setTitle("注意");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences antiThief = getSharedPreferences("antiThief", 0);
                SharedPreferences.Editor edit = antiThief.edit();
                edit.putBoolean("notification", false);
                edit.putBoolean("antiThiefIsOpen", false);
                edit.apply();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onDestroy() {
//        antiThiefService.stopDetection();
//        unbindService(serviceConnection);
//        unregisterReceiver(antiThiefReceiver);
        super.onDestroy();
    }

}
