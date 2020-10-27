package com.swufe.test;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class AlarmService extends Service {
    /**
     * 每一天更新一次数据
     */
    private String TAG = "AlarmService";
    private static final int ONE_Miniute=24*60*60*1000;
    private static final int PENDING_REQUEST=0;
    //Handler handler;
    Document doc;
    public AlarmService() {

    }
    /**
     * 调用Service都会执行到该方法
     */
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        //这里模拟后台操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("wj","循环执行了，哈哈."+ System.currentTimeMillis());

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
                     //获取数据并返回……
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Gson gson = new Gson();
                String data = gson.toJson(list2);
                SharedPreferences sp = getSharedPreferences("rate", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("get_rate", data);
                editor.commit();

            }
        }).start();

        //通过AlarmManager定时启动广播
        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        //long triggerAtTime= SystemClock.elapsedRealtime()+ONE_Miniute;//从开机到现在的毫秒书（手机睡眠(sleep)的时间也包括在内
        Intent i=new Intent(this, AlarmReceiver.class);
        PendingIntent pIntent=PendingIntent.getBroadcast(this,PENDING_REQUEST,i,PENDING_REQUEST);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,getTimesmorning(),ONE_Miniute,pIntent);
       // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 10000, pi);
        //时间到时，执行PendingIntent，只执行一次
       //AlarmManager.RTC_WAKEUP休眠时会运行，如果是AlarmManager.RTC,在休眠时不会运行
       //am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 10000, pi);
       //如果需要重复执行，使用上面一行的setRepeating方法，倒数第二参数为间隔时间,单位为毫秒

        return super.onStartCommand(intent, flags, startId);
    }




    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static int getTimesmorning(){//获取当天0点
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis()/1000);
    }

    //获得当天24点时间
    public static int getTimesnight(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis()/1000);
    }

}

