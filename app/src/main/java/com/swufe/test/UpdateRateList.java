package com.swufe.test;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.swufe.test.db.RateItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UpdateRateList extends ListActivity implements  Runnable{
    private String TAG = "UpdateRateList";
    Handler handler;
    Document doc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Thread t = new Thread(this);

        handler = new Handler() {//线程里的数据交给handle来处理
            public void handleMessage (Message msg){
                if(msg.what == 5){
                    List<String> list3 = ( List<String>)msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(UpdateRateList.this,android.R.layout.simple_list_item_1,list3);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
        t.start();

    }
    @Override
    public void run() {
        Message msg = handler.obtainMessage(5);
        //使用jsoup解析html数据
        String url = "http://www.usd-cny.com/bankofchina.htm";
        List<String> list2 = new ArrayList<>();
        try {
            doc = Jsoup.connect(url).get();
            Log.i(TAG, "run: " + doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table6 = tables.get(0);
//获取TD中的数据
            Elements tds = table6.getElementsByTag("td");
            for (int i = 0; i < tds.size(); i += 6) {
                Element td1 = tds.get(i);
                Element td2 = tds.get(i + 5);
                String str1 = td1.text();
                String val = td2.text();
                list2.add(str1 + "==>"+val);
                RateItem item = new RateItem();
                item.setId(i+1);
                item.setCurName(str1);
                item.setCurRate(val);
//                float v = 100f / Float.parseFloat(val);
//获取数据并返回……
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        msg.obj = list2;
        handler.sendMessage(msg);
    }
}
