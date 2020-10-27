package com.swufe.test.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.swufe.test.R;

public class HomeFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return inflater.inflate(R.layout.frame_home,container);
    }

    @NonNull
    @Override
    public void onActivityCreated(Bundle saveInstanceState){
    super.onActivityCreated(saveInstanceState);
        TextView tv = getView().findViewById(R.id.homeTextView1);
        tv.setText("这是主页面");
    }
}
