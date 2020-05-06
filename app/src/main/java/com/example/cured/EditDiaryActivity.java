package com.example.cured;

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

    EditText Diary_title, Diary_desc, Diary_date;
    Button btnSaveChanges, btnDelete;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_diary);


        Diary_title = findViewById(R.id.diary_title);
        Diary_desc = findViewById(R.id.diary_desc);
        Diary_date = findViewById(R.id.diary_date);

        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnDelete = findViewById(R.id.btnDelete);

        //get value from previous page
        Diary_title.setText(getIntent().getStringExtra("diary_title"));
        Diary_desc.setText(getIntent().getStringExtra("diary_desc"));
        Diary_date.setText(getIntent().getStringExtra("diary_date"));

        final String Diary_keykey = getIntent().getStringExtra("diary_key");

        reference = FirebaseDatabase.getInstance().getReference().child("DiaryApp").
                child("Diary" + Diary_keykey);

        //delete item
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent a = new Intent(EditDiaryActivity.this,DiaryActivity.class);
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
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        dataSnapshot.getRef().child("diary_title").setValue(Diary_title.getText().toString());
                        dataSnapshot.getRef().child("diary_desc").setValue(Diary_desc.getText().toString());
                        dataSnapshot.getRef().child("diary_date").setValue(Diary_date.getText().toString());
                        dataSnapshot.getRef().child("diary_key").setValue(Diary_keykey);

                        Intent a = new Intent(EditDiaryActivity.this,DiaryActivity.class);
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