package com.example.cured;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EditDiaryActivity extends AppCompatActivity {

    EditText titleDiary, dateDiary, descDiary;
    Button btnSaveUpdate, btnDelete;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_diary);

        titleDiary = findViewById(R.id.diary_title);
        descDiary = findViewById(R.id.diary_desc);
        dateDiary = findViewById(R.id.diary_date);

        btnSaveUpdate = findViewById(R.id.btnSaveChanges);
        btnDelete = findViewById(R.id.btnDelete);

        //get a value from previous page
        titleDiary.setText(getIntent().getStringExtra("diary_title"));
        descDiary.setText(getIntent().getStringExtra("diary_desc"));
        dateDiary.setText(getIntent().getStringExtra("diary_date"));

        final String keykeyDiary = getIntent().getStringExtra("diary_key");

        reference = FirebaseDatabase.getInstance().getReference().child("DiaryApp").
                child("Diary" + keykeyDiary);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent a = new Intent(EditDiaryActivity.this, DiaryActivity.class);
                            startActivity(a);
                        } else {
                            Toast.makeText(getApplicationContext(), "Not working!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //make an event for button
        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("diary_title").setValue(titleDiary.getText().toString());
                        dataSnapshot.getRef().child("diary_desc").setValue(descDiary.getText().toString());
                        dataSnapshot.getRef().child("diary_date").setValue(dateDiary.getText().toString());
                        dataSnapshot.getRef().child("diary_key").setValue(keykeyDiary);

                        Intent a = new Intent(EditDiaryActivity.this,DiaryActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}