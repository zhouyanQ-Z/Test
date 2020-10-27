package com.swufe.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements  Runnable {//多线程\

    private static  final  String TAG="MainActivity";
    URL url;
    float dollar;
    float euro;
    float won;
    float rmb;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg){

            //保存更新的日期
//            SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putFloat("dollar_rate",dollarRate);
//            editor.putFloat("euro_rate",euroRate);
//            editor.putFloat("won_rate",wonRate);
//            editor.putString("update_date",todayStr);
//            editor.apply();

            if(msg.what==5){
                String str=(String) msg.obj;
                Log.i("Tag","handleMessage: getMessage msg ="+str);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("rate", MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        //接收保存的汇率数据
        Thread t = new Thread(this);
        t.start();
        dollar = sp.getFloat("dollar_rate", 0.0f);
        euro = sp.getFloat("pound_rate", 0.0f);
        won = sp.getFloat("euro_rate", 0.0f);





        //设置汇率每天更新
//        String updateDate = sp.getString("update_date","");
//
////获取当前系统时间
//        Date today = Calendar.getInstance().getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        final String todayStr = sdf.format(today);
//        Log.i(TAG, "onCreate: sp updateDate=" + updateDate);
//        Log.i(TAG, "onCreate: todayStr=" + todayStr);
////然后进行日期判断，判断时间
//        if(!todayStr.equals(updateDate)){
//            Log.i(TAG, "onCreate: 需要更新");
//            //开启子线程
//            t = new Thread(this);
//            t.start();
//        }else{
//            Log.i(TAG, "onCreate: 不需要更新");
//        }





    }

    public void btn(View v){
        EditText editText= findViewById(R.id.editText);
        TextView textView  = findViewById(R.id.textView);
        dollar=0.1466f;
        euro=0.1256f;
        won=171.3488f;
        if(editText.getText().toString().isEmpty()){
            Toast.makeText(this,"请输入金额",Toast.LENGTH_LONG).show();
        }else{
            rmb = Float.parseFloat(editText.getText().toString());
            if(v.getId()==R.id.dollar){
                dollar =  dollar*rmb;
                textView.setText(""+dollar);
            }else if(v.getId()==R.id.euro){
                euro =  euro*rmb;
                textView.setText(""+euro);
            }else {
                won =  won*rmb;
                textView.setText(""+won);
            }
        }
    }
    public void open(View v){//Intent给另一页面传数据
        Intent config = new Intent(this,MainActivity2.class);
        config.putExtra("dollar_rate",dollar);
        config.putExtra("euro_rate",euro);
        config.putExtra("won_rate",won);
        Log.i(TAG,"onCreat:dollarRate:"+dollar);
        Log.i(TAG,"onCreat:euroRate:"+euro);
        Log.i(TAG,"onCreat:wonRate:"+won);
        startActivity(config);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data) {//bundle接受另一页面传回来的的数据
        if(requestCode==1&&resultCode==2){
            //用bundle接收数据
            Bundle bundle = data.getExtras();
            dollar = bundle.getFloat("key_dollar",0.1f);
            euro = bundle.getFloat("key_euro",0.1f);
            won = bundle.getFloat("key_won",0.1f);
            Log.i(TAG, "onActivityResult: dollarRate=" + dollar);
            Log.i(TAG, "onActivityResult: euroRate=" + euro);
            Log.i(TAG, "onActivityResult: wonRate=" + won);

            //用SharedPreferences接受数据
            //SharedPreferences sharedPreferences =getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            //PreferenceManager.getDefaultSharedPreferences(this);
            //dollar = sharedPreferences.getFloat("dollar_rate",0.0f);
            //euro = sharedPreferences.getFloat("euro_rate",0.0f);
            //won = sharedPreferences.getFloat("won_rate",0.0f);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    /*
    启用菜单项
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.first_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.menu_set){
            //时间处理代码

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void run() {//多线程实现
        Message msg = handler.obtainMessage(5);
        try {
            url = new URL("http://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            String html = inputStream2String(in);
            Log.i("TAG","run:html = "+html);
    //       Document doc = Jsoup.connect(URL).data().post();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        msg.obj = "Hello from run()";
        handler.sendMessage(msg);

    }

    private String inputStream2String(InputStream inputStream)
            throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }

    

}