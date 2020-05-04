package com.example.cured;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class NewMedicineActivity  extends AppCompatActivity{
    TextView titlepage, addtitle, adddose, addtime;
    EditText medicine_title, medicine_dose;
    TimePicker picker;
    Button btnSaveIntake, btnCancel;
    DatabaseReference reference;
    Integer medicineNum = new Random().nextInt();
    String medicine_key = Integer.toString(medicineNum);
    String medicine_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String medicine_time="00:00";
    Integer hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_medicine);

        titlepage = findViewById(R.id.titlepage);

        addtitle = findViewById(R.id.addtitle);
        adddose = findViewById(R.id.adddose);
        addtime = findViewById(R.id.addtime);

        medicine_title = findViewById(R.id.medicine_title);
        medicine_dose = findViewById(R.id.medicine_dose);
        picker = findViewById(R.id.timePicker);
        picker.setIs24HourView(true);


        btnSaveIntake = findViewById(R.id.btnSaveIntake);
        btnCancel = findViewById(R.id.btnCancel);

        btnSaveIntake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String h,m;

                hour = picker.getHour();
                minute = picker.getMinute();
                if(hour<10){
                    h="0"+hour;
                }else h=Integer.toString(hour);
                if(minute<10){
                    m="0"+minute;
                }else m=Integer.toString(minute);
                medicine_time = h+":"+m;

                // insert data to database
                reference = FirebaseDatabase.getInstance().getReference().child("CuredApp").
                        child("Medicine" + medicineNum);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        dataSnapshot.getRef().child("medicine_title").setValue(medicine_title.getText().toString());
                        dataSnapshot.getRef().child("medicine_dose").setValue(medicine_dose.getText().toString());
                        dataSnapshot.getRef().child("medicine_time").setValue(medicine_time);
                        dataSnapshot.getRef().child("medicine_key").setValue(medicine_key);
                        dataSnapshot.getRef().child("medicine_uid").setValue(medicine_uid);

                        Intent a = new Intent(NewMedicineActivity.this,MainActivity.class);
                        startActivity(a);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        // import font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

        // customize font
        titlepage.setTypeface(MMedium);

        addtitle.setTypeface(MLight);
        medicine_title.setTypeface(MMedium);

        adddose.setTypeface(MLight);
        medicine_dose.setTypeface(MMedium);

        addtime.setTypeface(MLight);
        //medicine_time.setTypeface(MMedium);

        btnSaveIntake.setTypeface(MMedium);
        btnCancel.setTypeface(MLight);

    }
}
