package com.swufe.test;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

/*
* 1.实现用户自定义列表的显示
* 2.列表事件的单击处理，双击处理setOnItemClickListener(),implements AdapterView.OnItemClickListener
* */
public class RateListActivity2 extends ListActivity implements Runnable, AdapterView.OnItemClickListener{
    Handler handler;
    private ArrayList<HashMap<String, String>> listItems; // 存放文字、图片信息
    private SimpleAdapter listItemAdapter; // 适配器
    private int msgWhat = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initListView();
        this.setListAdapter(listItemAdapter);

        Thread t = new Thread(RateListActivity2.this); // 创建新线程
        getListView().setOnItemClickListener(RateListActivity2.this);//列表单击事件

        handler=new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == msgWhat) {
                    List<HashMap<String, String>> retList = (List<HashMap<String, String>>) msg.obj;
                    SimpleAdapter adapter = new SimpleAdapter(RateListActivity2.this, retList, // listItems数据源
                            R.layout.list_item, // ListItem的XML布局实现
                            new String[]{"ItemTitle", "ItemDetail"},
                            new int[]{R.id.itemTitle, R.id.itemDetail});
                    setListAdapter(adapter);
                    Log.i("handler", "reset list...");
                }
                super.handleMessage(msg);
            }
        };
        t.start(); // 开启线程
    }

    private void initListView() {
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate： " + i); // 标题文字
            map.put("ItemDetail", "detail" + i); // 详情描述
            listItems.add(map);
        }
        // 生成适配器的Item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(this, listItems, // listItems数据源
                R.layout.list_item, // ListItem的XML布局实现
                new String[] { "ItemTitle", "ItemDetail" },
                new int[] { R.id.itemTitle, R.id.itemDetail }
        );
    }


    @Override
    public void run() {
        Log.i("thread","run.....");
        boolean marker = false;
        List<HashMap<String, String>> rateList = new ArrayList<HashMap<String, String>>();

        try {
            Document doc = Jsoup.connect("http://www.usd-cny.com/icbc.htm").get();
            Elements tbs = doc.getElementsByClass("tableDataTable");
            Element table = tbs.get(0);
            Elements tds = table.getElementsByTag("td");
            for (int i = 6; i < tds.size(); i+=6) {
                Element td = tds.get(i);
                Element td2 = tds.get(i+3);
                String tdStr = td.text();
                String pStr = td2.text();

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("ItemTitle", tdStr);
                map.put("ItemDetail", pStr);

                rateList.add(map);
                Log.i("td",tdStr + "=>" + pStr);
            }
            marker = true;
        } catch (MalformedURLException e) {
            Log.e("www", e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("www", e.toString());
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage();
        msg.what = msgWhat;
        if(marker){
            msg.arg1 = 1;
        }else{
            msg.arg1 = 0;
        }

        msg.obj = rateList;
        handler.sendMessage(msg);

        Log.i("thread","sendMessage.....");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: parent=" + parent);
        Log.i(TAG, "onItemClick: view=" + view);
        Log.i(TAG, "onItemClick: position=" + position);
        Log.i(TAG, "onItemClick: id=" + id);

        //从ListView中获取选中数据
        HashMap<String,String> map = (HashMap<String, String>) getListView().getItemAtPosition(position);
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Log.i(TAG, "onItemClick: titleStr=" + titleStr);
        Log.i(TAG, "onItemClick: detailStr=" + detailStr);

        //从View中获取选中数据
        TextView title = (TextView) view.findViewById(R.id.itemTitle);
        TextView detail = (TextView) view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());
        Log.i(TAG, "onItemClick: title2=" + title2);
        Log.i(TAG, "onItemClick: detail2=" + detail2);

        //打开新的页面，传入参数
        Intent rateCalc = new Intent(this,RateCalcActivity.class);
        rateCalc.putExtra("title",titleStr);
        rateCalc.putExtra("rate",Float.parseFloat(detailStr));
        startActivity(rateCalc);
    }
}