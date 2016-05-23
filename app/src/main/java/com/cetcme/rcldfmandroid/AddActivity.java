package com.cetcme.rcldfmandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private Adapter adapter;
    private List<Map<String,Object>> datalist = new LinkedList<>();

    private String[] deviceList = {"监管仪编号", "打卡器编号"};
    private String[] picList = {"姓名", "手机号"};
    private String[] ownerList = {"姓名", "手机号"};
    private String[] operatorList = {"姓名", "手机号", "安装时间", "安装地点"};
    private String[] shipInfoList = {"渔船编号", "船名", "船长", "上甲板长度", "船宽", "船深", "材质", "伏休期起始时间", "伏休期结束时间", "建成时间", "制卡时间"};
    private String[] titleList = {"设备", "负责人", "船东", "安装人员", "渔船身份标签"};
    private int[] titleNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addData();
        titleNum = new int[]{0,
                deviceList.length + 1,
                deviceList.length + picList.length + 2,
                deviceList.length + picList.length + ownerList.length + 3,
                deviceList.length + picList.length + ownerList.length + operatorList.length + 4,
        };

        simpleAdapter = new SimpleAdapter(getApplicationContext(),datalist,
                R.layout.list_view_cell,new String[]{"name"},new int[]{R.id.textViewInCell});

        listView = (ListView) findViewById(R.id.listViewInAddActivity);
        adapter = new Adapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Main", "ListItem: " + i + ":" + "name: " + datalist.get(i).get("name") + "; data: " + datalist.get(i).get("data"));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem add = menu.add(0,0,0,"Add");
        add.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        add.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.i("Main", "clicked");
                Log.i("Main", datalist.toString());
                return false;
            }
        });
        return true;
    }

    private void addData() {

        for (int i=0; i<deviceList.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name",deviceList[i]);
            datalist.add(map);
        }
        for (int i=0; i<picList.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name",picList[i]);
            datalist.add(map);
        }
        for (int i=0; i<ownerList.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name",ownerList[i]);
            datalist.add(map);
        }
        for (int i=0; i<operatorList.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name",operatorList[i]);
            datalist.add(map);
        }
        for (int i=0; i<shipInfoList.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name",shipInfoList[i]);
            datalist.add(map);
        }

    }

    class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datalist.size() + titleList.length;
        }

        @Override
        public Object getItem(int i) {
            return datalist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View titleView = LayoutInflater.from(getApplication()).inflate(R.layout.list_view_title, null);
            View contentView = view = LayoutInflater.from(getApplication()).inflate(R.layout.list_view_cell, null);
            TextView textView = (TextView) contentView.findViewById(R.id.textViewInCell);
            EditText editText = (EditText) contentView.findViewById(R.id.editTextInCell);
            TextView titleTextView = (TextView) titleView.findViewById(R.id.titleTextView);


            if (i == 0 || i == 3 || i == 6 ||
                    i == 9 || i == 14 ) {
                String title = "";
                if (i == titleNum[0]) {
                    title = titleList[0];
                } else if (i == titleNum[1]) {
                    title = titleList[1];
                } else if (i == titleNum[2]) {
                    title = titleList[2];
                } else if (i == titleNum[3]) {
                    title = titleList[3];
                } else if (i == titleNum[4]) {
                    title = titleList[4];
                }

                view = titleView;
                titleTextView.setText(title);
            } else {

                String name = "";
                String data = "";

                if (i<3) {
                    name = (String) datalist.get(i-1).get("name");
                    data = (String) datalist.get(i-1).get("data");
                } else if (3<i && i<6) {
                    name = (String) datalist.get(i-2).get("name");
                    data = (String) datalist.get(i-2).get("data");
                } else if (6<i && i<9) {
                    name = (String) datalist.get(i-3).get("name");
                    data = (String) datalist.get(i-3).get("data");
                } else if (9<i && i<14) {
                    name = (String) datalist.get(i-4).get("name");
                    data = (String) datalist.get(i-4).get("data");
                } else if (14<i) {
                    name = (String) datalist.get(i-5).get("name");
                    data = (String) datalist.get(i-5).get("data");
                }

                view = contentView;
                textView.setText(name);
                editText.setText(data);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (i<titleNum[1]) {
                            datalist.get(i - 1).put("data",editable.toString());
                        } else if (titleNum[1]<i && i<titleNum[2]) {
                            datalist.get(i - 2).put("data",editable.toString());
                        } else if (titleNum[2]<i && i<titleNum[3]) {
                            datalist.get(i - 3).put("data",editable.toString());
                        } else if (titleNum[3]<i && i<titleNum[4]) {
                            datalist.get(i - 4).put("data",editable.toString());
                        } else if (titleNum[4]<i) {
                            datalist.get(i - 5).put("data",editable.toString());
                        }

                    }
                });
            }


            return view;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }
}
