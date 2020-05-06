package com.example.cured;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.StringTokenizer;

public class EditMedicineActivity extends AppCompatActivity {

    EditText Medicine_title, Medicine_dose;
    TimePicker picker;
    String Medicine_time;
    Button btnSaveChanges, btnDelete;
    DatabaseReference reference;
    int hour,minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_medicine);


        Medicine_title = findViewById(R.id.medicine_title);
        Medicine_dose = findViewById(R.id.medicine_dose);
        picker = findViewById(R.id.timePicker);
        picker.setIs24HourView(true);

        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnDelete = findViewById(R.id.btnDelete);

        //get value from previous page
        Medicine_title.setText(getIntent().getStringExtra("medicine_title"));
        Medicine_dose.setText(getIntent().getStringExtra("medicine_dose"));
        String getMedicine_time = getIntent().getStringExtra("medicine_time");

        StringTokenizer st = new StringTokenizer(getMedicine_time,"::");
        hour = Integer.parseInt(st.nextToken());
        minute = Integer.parseInt(st.nextToken());
        if (Build.VERSION.SDK_INT >= 23 ){
            picker.setHour(hour);
            picker.setMinute(minute);
        }
        else{
            picker.setCurrentHour(hour);
            picker.setCurrentMinute(minute);
        }


        final String Medicine_keykey = getIntent().getStringExtra("medicine_key");

        reference = FirebaseDatabase.getInstance().getReference().child("CuredApp").
                child("Medicine" + Medicine_keykey);

        //delete item
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent a = new Intent(EditMedicineActivity.this,MainActivity.class);
                            startActivity(a);
                        } else {
                            Toast.makeText(getApplicationContext(), "Failure!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //make an event for button
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
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
                Medicine_time = h+":"+m;

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        dataSnapshot.getRef().child("medicine_title").setValue(Medicine_title.getText().toString());
                        dataSnapshot.getRef().child("medicine_dose").setValue(Medicine_dose.getText().toString());
                        dataSnapshot.getRef().child("medicine_time").setValue(Medicine_time);
                        dataSnapshot.getRef().child("medicine_key").setValue(Medicine_keykey);

                        Intent a = new Intent(EditMedicineActivity.this,MainActivity.class);
                        startActivity(a);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}