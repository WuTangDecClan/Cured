package com.example.cured;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DiaryActivity extends AppCompatActivity {
    TextView titlepage, subtitlepage, endpage;
    Button btnAddDiary;

    DatabaseReference reference;
    RecyclerView diary_entries;
    ArrayList<MyDiary> list;
    DiaryAdapter diaryAdapter;

    ListView listview = null;
    Navigation navigation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_with_nbar);
        listview = (ListView) findViewById(R.id.drawerList);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        navigation = new Navigation(this,listview,drawer);
        navigation.setN();

        titlepage = findViewById(R.id.titlepage);
        subtitlepage = findViewById(R.id.subtitlepage);
        endpage = findViewById(R.id.endpage);

        btnAddDiary = findViewById(R.id.btnAddDiary);

        // import font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

        // customize font
        titlepage.setTypeface(MMedium);
        subtitlepage.setTypeface(MLight);
        endpage.setTypeface(MLight);

        btnAddDiary.setTypeface(MLight);

        //Button to add diary starts new activity
        btnAddDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(DiaryActivity.this,NewDiaryActivity.class);
                startActivity(a);
            }
        });


        // working with data
        diary_entries = findViewById(R.id.diary_entries);
        diary_entries.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyDiary>();

        // get data from firebase
        reference = FirebaseDatabase.getInstance().getReference().child("DiaryApp");
        diaryAdapter = new DiaryAdapter(DiaryActivity.this, list);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // set code to retrieve data and replace layout
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    MyDiary p = dataSnapshot1.getValue(MyDiary.class);
                    list.add(p);
                }
                diaryAdapter = new DiaryAdapter(DiaryActivity.this, list);
                diary_entries.setAdapter(diaryAdapter);
                diaryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // set code to show an error
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}