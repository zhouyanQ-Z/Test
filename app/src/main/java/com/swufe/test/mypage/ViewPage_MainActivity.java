package com.swufe.test.mypage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.swufe.test.R;

public class ViewPage_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpage_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        MyPageAdapter pageAdapter = new MyPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);

        //添加头部tab
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

}