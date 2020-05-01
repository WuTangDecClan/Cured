package com.example.cured;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Reboot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){

        if(Objects.equals(intent.getAction(),"android.intent.action.BOOT_COMPLETED")){
            //device boot complete, reset the alarm
            Intent aIntent = new Intent(context,Alarm.class);
            PendingIntent pIntent = PendingIntent.getBroadcast(context,0,aIntent,0);

            AlarmManager aManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            SharedPreferences sharedPreferences = context.getSharedPreferences("daily alarm",Context.MODE_PRIVATE);
            Map<String, ?> allD = sharedPreferences.getAll();
                Set<String> set = allD.keySet();
                for(String s : set){
                    Log.e("alarm",allD.get(s).getClass().getSimpleName()+":"+allD.get(s).toString());
                }

            Calendar current = Calendar.getInstance();
            Calendar nextNotifyTime = new GregorianCalendar();
            nextNotifyTime.setTimeInMillis(sharedPreferences.getLong("nextNotifyTime",0));

            if(current.after(nextNotifyTime)){
                nextNotifyTime.add(Calendar.DATE,1);
            }

            if(aManager!=null){
                aManager.setRepeating(AlarmManager.RTC_WAKEUP,nextNotifyTime.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pIntent);
            }
        }
    }

}
