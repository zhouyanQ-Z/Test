package com.swufe.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class RateClickActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private TextView text_tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_click);
        listView = findViewById(R.id.no_list);
        text_tip = findViewById(R.id.nodata);

        listView.setEmptyView(findViewById(R.id.nodata));//空数据显示

        //列表数据的删除
        /*
         *当列表adapter为ArrayAdapter时，删除数据的方式：adapter.remove(listView.getItemPosition(position));
         */
        //当前案例以实现单击列表后删除数据，首先要添加事件监听
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("tag","position"+position);
        Log.i("tag","position"+listView);
        //adapter.remove(listView.getItemPosition(position));//事件添加删除语句
        //adapter.notifyDataChanged();//通过adapter删除数据时，notifyDataChanged()自动调用，不需要加上
    }
}