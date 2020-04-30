package com.example.cured;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Diary extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView ourdiary;
    ArrayList<MyDiary> list;
    DiaryAdapter diaryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        ourdiary = findViewById(R.id.ourdiary);
        ourdiary.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyDiary>();

        //get data from firebase
        reference = FirebaseDatabase.getInstance().getReference().child("DiaryApp");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //set code
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    MyDiary p = dataSnapshot1.getValue(MyDiary.class);
                    list.add(p);
                }
                diaryAdapter = new DiaryAdapter(Diary.this, list);
                ourdiary.setAdapter(diaryAdapter);
                diaryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
