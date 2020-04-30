package com.example.cured;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,15);
        calendar.set(Calendar.MINUTE,58);
        calendar.set(Calendar.SECOND,0);

        //if alredy past
        if(calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.DATE,1);
        }

        //check
        Toast.makeText(getApplicationContext(),"alarm set",Toast.LENGTH_SHORT).show();

        //save in Preference
        SharedPreferences.Editor editor = getSharedPreferences("daily alarm",MODE_PRIVATE).edit();
        editor.putLong("nextNotifyTime",(long)calendar.getTimeInMillis());
        editor.apply();

        diaryNotification(calendar);


    }

    void diaryNotification(Calendar calendar){
        Boolean dailyNotify = true;

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this,Reboot.class);
        Intent aIntent = new Intent(this,Alarm.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this,0,aIntent,0);
        AlarmManager aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //if everyday set
        if(dailyNotify){
            if(aManager!=null){
                aManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pIntent);

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    aManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pIntent);
                }
            }

            //after boot
            pm.setComponentEnabledSetting(receiver,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
        }
    }
}
