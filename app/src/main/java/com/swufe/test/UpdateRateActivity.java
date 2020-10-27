package com.swufe.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class UpdateRateActivity extends ListActivity {
    private String TAG = "UpdateRateActivity";

    Handler handler;
    Document doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_update_rate);
        Intent intent = new Intent(this, AlarmService.class);
        startService(intent);

    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences preferences=getSharedPreferences("rate", Activity.MODE_PRIVATE);
        String listJson = preferences.getString("get_rate", "");
        Gson gson = new Gson();
        List<String> list = gson.fromJson(listJson, new TypeToken<List<String>>() {}.getType()); //将json字符串转换成List集合
        ListAdapter adapter = new ArrayAdapter<String>(UpdateRateActivity.this,android.R.layout.simple_list_item_1,list);
        setListAdapter(adapter);

    }


}