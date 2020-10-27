package com.swufe.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity3 extends AppCompatActivity  {
    private static  final  String TAG="MainActivity3";
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

//        ListView listView = findViewById(R.id.mylist);
//        String[] data = {"one","two","three","four"};
//
//        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
//        listView.setAdapter(adapter);
        listView = findViewById(R.id.mylist);
        ArrayList listItems = new ArrayList<HashMap<String,String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate： " + i); // 标题文字
            map.put("ItemDetail", "detail" + i); // 详情描述
            listItems.add(map);
        }
        MyAdapter myAdapter = new MyAdapter(this, R.layout.list_item, listItems);
        listView.setAdapter(myAdapter);

//        for(int i = 0;i < 10 ;i++){
//            HashMap<String,String> map = new  HashMap<String,String>();
//            map.put("ItemTitle","Rate:  "+i);//标题文字
//            map.put("ItemDetail","detail"+i);//详情描述
//            listItems.add(map);
//        }
//        //生成适配器的Item和动态数组对应的元素
//        SimpleAdapter listItemDapter = new SimpleAdapter(this,listItems,R.layout.list_main,
//                new String[]{"ItemTitle","ItemDetail"},new int[]{R.id.itemTitle,R.id.itemDetail});
//        //listView.setAdapter(listItemDapter);


    }


//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//       // Object itemAtPosition = getListView().getItemAtPosition(position);
//        Object itemAtPosition = listView.getItemAtPosition(position);
//        HashMap<String,String> map = (HashMap<String, String>) itemAtPosition;
//        String titleStr = map.get("ItemTitle");
//        String detailStr = map.get("ItemDetail");
//        Log.i(TAG, "onItemClick: titleStr=" + titleStr);
//        Log.i(TAG, "onItemClick: detailStr=" + detailStr);
//        TextView title = (TextView) view.findViewById(R.id.itemTitle);
//        TextView detail = (TextView) view.findViewById(R.id.itemDetail);
//        String title2 = String.valueOf(title.getText());
//        String detail2 = String.valueOf(detail.getText());
//        Log.i(TAG, "onItemClick: title2=" + title2);
//        Log.i(TAG, "onItemClick: detail2=" + detail2);
//    }

}