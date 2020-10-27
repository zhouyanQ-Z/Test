package com.swufe.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //循环启动Service
        Intent i = new Intent(context, AlarmService.class);
        context.startService(i);
    }
}
