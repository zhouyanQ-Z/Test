package com.swufe.test.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.swufe.test.R;

public class FuncFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return inflater.inflate(R.layout.frame_func,container);
    }

    @NonNull
    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
        TextView tv = getView().findViewById(R.id.homeTextView2);
        tv.setText("这是功能页面");
    }
}
