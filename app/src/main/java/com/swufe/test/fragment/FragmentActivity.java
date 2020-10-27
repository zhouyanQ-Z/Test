package com.swufe.test.fragment;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.swufe.test.R;

public class FragmentActivity extends androidx.fragment.app.FragmentActivity {
    private Fragment mFragments[];
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbtHome,rbtFunc,rbtSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        mFragments = new Fragment[3];
        fragmentManager = getSupportFragmentManager();
        mFragments[0] = fragmentManager.findFragmentById(R.id.fragment_main);
        mFragments[1] = fragmentManager.findFragmentById(R.id.fragment_func);
        mFragments[2] = fragmentManager.findFragmentById(R.id.fragment_setting);
        fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
        fragmentTransaction.show(mFragments[0]).commit();

        //切换控制
        rbtHome = findViewById(R.id.radioHome);
        rbtFunc = findViewById(R.id.radioFunc);
        rbtSetting = findViewById(R.id.radioSetting);
        initView();
        radioGroup = findViewById(R.id.bottomGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i(" radioGroup ","checkedIid="+checkedId);
                fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
                switch (checkedId){
                    case  R.id.radioHome:
                        fragmentTransaction.show(mFragments[0]).commit();
                        break;
                    case  R.id.radioFunc:
                        fragmentTransaction.show(mFragments[1]).commit();
                        break;
                    case  R.id.radioSetting:
                        fragmentTransaction.show(mFragments[2]).commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    //设置底部大小
    private void initView() {
        Drawable dhome = ContextCompat.getDrawable(this, R.drawable.selector_home);
        dhome.setBounds(0, 0, 80, 80);
        rbtHome.setCompoundDrawables(null, dhome, null, null);

        Drawable dfunc = ContextCompat.getDrawable(this, R.drawable.selector_func);
        dfunc.setBounds(0, 0, 80, 80);
        rbtFunc.setCompoundDrawables(null, dfunc, null, null);

        Drawable dsetting = ContextCompat.getDrawable(this, R.drawable.selector_setting);
        dsetting.setBounds(0, 0, 80, 80);
        rbtSetting.setCompoundDrawables(null, dsetting, null, null);
    }

}