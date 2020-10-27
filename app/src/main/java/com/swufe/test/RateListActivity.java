package com.swufe.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class RateListActivity  extends ListActivity {

    private String TAG = "RateListActivity";
    private UpdateHandler handler;
    Document doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new UpdateHandler(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
//
//                while (count < 100 || isFinishing()) {
//                    count++;
//                    Message msg = handler.obtainMessage();
//                    msg.obj = String.valueOf(count);
//                    handler.sendMessage(msg);
//                    SystemClock.sleep(1000);

//                }
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
//                float v = 100f / Float.parseFloat(val);
                    //获取数据并返回……
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                msg.obj = list2;
                handler.sendMessage(msg);
                try {
                    Thread.sleep(24*60*60*1000);//休眠1天
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        //setContentView(R.layout.activity_rate_list);
//        List<String> list1 = new ArrayList<>();
//        for(int i = 1;i < 100;i++){
//            list1.add("item"+i);
//        }
//        String[] list_date = {"one","two","three","four"};
//        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_date);
//        setListAdapter(adapter);


//        Thread t = new Thread(this);
//
//        handler = new Handler() {//线程里的数据交给handle来处理
//            public void handleMessage (Message msg){
//                if(msg.what == 5){
//                    List<String> list3 = ( List<String>)msg.obj;
//                    ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,list3);
//                    setListAdapter(adapter);
//                }
//                super.handleMessage(msg);
//            }
//        };
//        t.start();


    }
    private class UpdateHandler extends Handler {
        private WeakReference<RateListActivity> reference;
        //弱引用
        public UpdateHandler(RateListActivity activity) {
            this.reference = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            if (reference != null && reference.get() != null) {
                //RateListActivity activity = reference.get();
                if(msg.what == 5){
                    List<String> list3 = ( List<String>)msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,list3);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        Log.i("tag", "destory");
    }

//
//    @Override
//    public void run() {
//        Message msg = handler.obtainMessage(5);
//        //使用jsoup解析html数据
//        String url = "http://www.usd-cny.com/bankofchina.htm";
//        List<String> list2 = new ArrayList<>();
//        try {
//            doc = Jsoup.connect(url).get();
//            Log.i(TAG, "run: " + doc.title());
//            Elements tables = doc.getElementsByTag("table");
//            Element table6 = tables.get(0);
////获取TD中的数据
//            Elements tds = table6.getElementsByTag("td");
//            for (int i = 0; i < tds.size(); i += 6) {
//                Element td1 = tds.get(i);
//                Element td2 = tds.get(i + 5);
//                String str1 = td1.text();
//                String val = td2.text();
//                list2.add(str1 + "==>"+val);
////                float v = 100f / Float.parseFloat(val);
////获取数据并返回……
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        msg.obj = list2;
//        handler.sendMessage(msg);
//
//    }
}