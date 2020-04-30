package com.example.cured;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;


public class Alarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){

        int hour=0,minute=0;
        String dosage,title;

        hour = intent.getIntExtra("hour",0);
        minute = intent.getIntExtra("minute",0);
        dosage = intent.getStringExtra("dosage");
        title = intent.getStringExtra("title");
        Log.e("receive",hour+title+dosage+minute);


        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent nIntent = new Intent(context,MainActivity.class);

        nIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pIntent = PendingIntent.getActivity(context,0,nIntent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"default");

        if(Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O){
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);

            String cName = "Daily Alarm";
            String desc = "Alarming";
            int importance = NotificationManager.IMPORTANCE_HIGH;//sound + message

            NotificationChannel channel= new NotificationChannel("default",cName,importance);
            channel.setDescription(desc);

            if(notificationManager!=null){
                notificationManager.createNotificationChannel(channel);
            }else builder.setSmallIcon(R.mipmap.ic_launcher);

            builder.setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setTicker("{timeto}")
                    .setContentTitle(title)
                    .setContentText(dosage)
                    .setContentInfo("Info")
                    .setContentIntent(pIntent);


            if(notificationManager!=null){
                //run notification
                notificationManager.notify(1234,builder.build());

                Calendar nextNotifyTime = Calendar.getInstance();

                //set tomorrow
                nextNotifyTime.add(Calendar.DATE,1);

                //save in Preference
                SharedPreferences.Editor editor = context.getSharedPreferences("daily alarm",Context.MODE_PRIVATE).edit();
                editor.putLong("nextNotifyTime",nextNotifyTime.getTimeInMillis());
                editor.apply();

                Toast.makeText(context.getApplicationContext(),hour+":"+minute+" time to"+title,Toast.LENGTH_SHORT).show();
            }
        }




    }

}
