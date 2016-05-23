package com.cetcme.rcldfmandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private Adapter adapter;
    private List<Map<String,Object>> datalist = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addData();


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

    private void addData() {
        String[] nameList = {"姓名", "手机号", "地址", "邮编"};
        for (int i=0; i<nameList.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name",nameList[i]);
            datalist.add(map);
        }

    }

    class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datalist.size();
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
            String name = (String) datalist.get(i).get("name");
            String data = (String) datalist.get(i).get("data");
            view = LayoutInflater.from(getApplication()).inflate(R.layout.list_view_cell, null);
            TextView textView = (TextView) view.findViewById(R.id.textViewInCell);
            EditText editText = (EditText) view.findViewById(R.id.editTextInCell);
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
                    datalist.get(i).put("data",editable.toString());
                }
            });
            return view;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }
}
