package com.android.tuto.ch9.intentservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class EarthquakeAlarmReceiver extends BroadcastReceiver {

    public static final String ACTION_REFRESH_QUAKE_ALARM = "com.android.tuto.ch9.ACTION_REFRESH_QUAKE_ALARM";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startIntent = new Intent(context, EarthquakeUpdateService.class);
        startIntent.putExtra(EarthquakeUpdateService.EXTRA_KEY_INPUT, "From Alarm Receiver");
        context.startService(startIntent);
    }

}
