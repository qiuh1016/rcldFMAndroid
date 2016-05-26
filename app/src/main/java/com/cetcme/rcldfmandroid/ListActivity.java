package com.cetcme.rcldfmandroid;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>>  dataList;

    private SQLiteDatabase db;
    private String db_name = "info.db";
    private DbHelper helper = new DbHelper(this, db_name, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setTitle("设备列表");

        //db
        db = helper.getReadableDatabase();
        getData();

        listView = (ListView) findViewById(R.id.listViewInListActivity);
        simpleAdapter = new SimpleAdapter(getApplicationContext(),dataList,R.layout.device_list_cell,
                new String[]{"name", "age"}, new int[]{R.id.textViewInDeviceListCell, R.id.textView2InDeviceListCell});
        listView.setAdapter(simpleAdapter);


    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private void getData() {
        dataList = new LinkedList<>();
        Map<String,Object> map = new HashMap<>();
//        Cursor cursor = db.rawQuery("SELECT * FROM User_Table WHERE age = ?",new String[]{"28"});
        Cursor cursor = db.rawQuery("select * from User_Table", null); //获取所有数据
        while (cursor.moveToNext())
        {
            String str = "name:"+cursor.getString(0) + " age:"+cursor.getInt(1);
            Log.i("str:", str);
            map.put("name", cursor.getString(0));
            map.put("age", cursor.getInt(1));
            dataList.add(map);
        }
        cursor.close();


    }

}
