package com.example.cured;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class NewDiaryActivity extends AppCompatActivity {

    TextView titlepage, addtitle, adddesc, adddate;
    EditText diary_title, diary_desc, diary_date;
    Button btnSaveEntry, btnCancel;
    DatabaseReference reference;
    Integer diaryNum = new Random().nextInt();
    String diary_key = Integer.toString(diaryNum);
    String diary_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_diary);

        titlepage = findViewById(R.id.titlepage);

        addtitle = findViewById(R.id.addtitle);
        adddesc = findViewById(R.id.adddesc);
        adddate = findViewById(R.id.adddate);

        diary_title = findViewById(R.id.diary_title);
        diary_desc = findViewById(R.id.diary_desc);
        diary_date = findViewById(R.id.diary_date);

        btnSaveEntry = findViewById(R.id.btnSaveEntry);
        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(NewDiaryActivity.this, DiaryActivity.class);
                startActivity(a);
            }
        });

        btnSaveEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insert data to database
                reference = FirebaseDatabase.getInstance().getReference().child("DiaryApp").
                        child("Diary" + diaryNum);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        dataSnapshot.getRef().child("diary_title").setValue(diary_title.getText().toString());
                        dataSnapshot.getRef().child("diary_desc").setValue(diary_desc.getText().toString());
                        dataSnapshot.getRef().child("diary_date").setValue(diary_date.getText().toString());
                        dataSnapshot.getRef().child("diary_key").setValue(diary_key);
                        dataSnapshot.getRef().child("diary_uid").setValue(diary_uid);

                        Intent a = new Intent(NewDiaryActivity.this,DiaryActivity.class);
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
        diary_title.setTypeface(MMedium);

        adddesc.setTypeface(MLight);
        diary_desc.setTypeface(MMedium);

        adddate.setTypeface(MLight);
        diary_date.setTypeface(MMedium);

        btnSaveEntry.setTypeface(MMedium);
        btnCancel.setTypeface(MLight);

    }
}