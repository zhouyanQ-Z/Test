package com.swufe.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity {
    private static  final  String TAG="MainActivity2";
    float dollar2;
    float euro2;
    float won2;
    EditText text1;
    EditText text2;
    EditText text3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        dollar2 = intent.getFloatExtra("dollar_rate",0.0f);
        euro2 = intent.getFloatExtra("euro_rate",0.0f);
        won2 = intent.getFloatExtra("won_rate",0.0f);
        Log.i(TAG,"onCreat:dollar2:"+dollar2);
        Log.i(TAG,"onCreat:euro2:"+euro2);
        Log.i(TAG,"onCreat:won2:"+won2);

        text1 = findViewById(R.id.dollar_rate);
        text2 = findViewById(R.id.euro_rate);
        text3 = findViewById(R.id.won_rate);
        text1.setText(""+dollar2);
        text2.setText(""+euro2);
        text3.setText(""+won2);
    }
    /*
     *bundle打包传递数据
     */
    public void save(View v){ //给之前传数据的页面再把数据传回去
        float newDollar_rate=Float.parseFloat(text1.getText().toString());
        float newEuro_rate=Float.parseFloat(text2.getText().toString());
        float newWon_rate=Float.parseFloat(text3.getText().toString());

        Bundle bundle = new Bundle();
        Intent iten = getIntent();
        bundle.putFloat("key_dollar",newDollar_rate);
        bundle.putFloat("key_euro",newEuro_rate);
        bundle.putFloat("key_won",newWon_rate);
        iten.putExtras(bundle);
        setResult(2,iten);//设置 resultCode及带回的数据

        //返回调用页面
        finish();

        //SharedPreferences保存数据
//        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putFloat("dollar_rate", dollar2);
//        editor.putFloat("pound_rate", euro2);
//        editor.putFloat("euro_rate", won2);
//        editor.apply();
        //editor.commit();
        //finish();
    }

}