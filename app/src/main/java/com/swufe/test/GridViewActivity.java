package com.swufe.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        GridView listView = findViewById(R.id.gridlist);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("item" + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.nodata));
        listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }
}