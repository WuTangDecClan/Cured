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
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        int hour=0,minute=0;
        String dosage,title,key;
        Boolean cancel=false;

        hour=getIntent().getIntExtra("hour",0);
        minute = getIntent().getIntExtra("minute",0);
        dosage=getIntent().getStringExtra("dosage");
        title = getIntent().getStringExtra("title");
        key = getIntent().getStringExtra("key");
        cancel = getIntent().getBooleanExtra("cancel",false);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);

        //if alredy past
        if(calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.DATE,1);
        }

        //check
        Toast.makeText(getApplicationContext(),hour+":"+minute+" alarm set",Toast.LENGTH_SHORT).show();

        if(!cancel){
            //save in Preference
            SharedPreferences.Editor editor = getSharedPreferences("daily alarm",MODE_PRIVATE).edit();
            editor.putLong(key,(long)calendar.getTimeInMillis());
            editor.apply();
        }
        else{
            SharedPreferences.Editor editor = getSharedPreferences("daily alarm",MODE_PRIVATE).edit();
            editor.remove(key);
            editor.apply();

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("daily alarm",Context.MODE_PRIVATE);
            Map<String, ?> allD = sharedPreferences.getAll();
            Set<String> set = allD.keySet();
            for(String s : set){
                Log.e("alarm",allD.get(s).getClass().getSimpleName()+":"+allD.get(s).toString());
            }
        }



        diaryNotification(calendar,hour,minute,title,dosage,key,cancel);

        Intent home = new Intent(this,MainActivity.class);
        startActivity(home);


    }

    void diaryNotification(Calendar calendar,int hour, int minute, String title, String dosage,String key,Boolean cancel){
        Boolean dailyNotify = true;
        int k;

        k = Integer.parseInt(key);

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this,Reboot.class);
        Intent aIntent = new Intent(this,Alarm.class);
        aIntent.putExtra("hour",hour);
        aIntent.putExtra("minute",minute);
        aIntent.putExtra("title",title);
        aIntent.putExtra("dosage",dosage);
        aIntent.putExtra("key",k);
        Log.e("aIntent",hour+title+dosage);
        PendingIntent pIntent = PendingIntent.getBroadcast(this,k,aIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //if everyday set
        if(dailyNotify){
            if(aManager!=null){
                aManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pIntent);
                if(cancel){
                    aManager.cancel(pIntent);
                    Intent ni = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(ni);
                }
                else{
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                        aManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pIntent);
                    }
                }


            }

            //after boot
            pm.setComponentEnabledSetting(receiver,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
        }
    }
}
