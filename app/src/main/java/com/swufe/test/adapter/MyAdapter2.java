package com.swufe.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.swufe.test.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter2 extends ArrayAdapter {

    private static final String TAG = "MyAdapter";

    public MyAdapter2(Context context, int resource, ArrayList<HashMap<String,String>> list) {
        super(context, resource, list);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Map<String,String> map = (Map<String, String>) getItem(position);
        TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
        TextView detail = (TextView) itemView.findViewById(R.id.itemDetail);

        title.setText("Title:" + map.get("ItemTitle"));
        detail.setText("detail:" + map.get("ItemDetail"));

        return itemView;

/*
*
* 通过构造方法传入数据list，此处为了兼容List数据，List里还是使用Map数据集合，最主要的方法就
* 是getView，为列表提供显示所需要的视图，通过加载布局文件构造View并填充相应的数据，之后修改Activity
* 页面，使用自定义的MyAdapter
*MyAdapter myAdapter = new MyAdapter(this,R.layout.list_item,listItems);
*this.setListAdapter(myAdapter);
*通常情况自定义的Adapter中使用的数据类型为自定义实体List<Object>
* */
    }
}
