package com.example.cured;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class MainActivity extends AppCompatActivity implements callAlarm{
    TextView titlepage, subtitlepage, endpage;
    Button btnAddNew, btnCancel;

    DatabaseReference reference;
    RecyclerView medicine_intakes;
    ArrayList<MyMedicine> list;
    MedicineAdapter medicineAdapter;
    Navigation navigation;


    ListView listview = null;

    private static final String TAG = "MainActivity";

    @Override
    public void call(int hour, int minute, String title, String dosage,String key){
        Intent i = new Intent(MainActivity.this, NotificationActivity.class);
        i.putExtra("hour",hour);
        i.putExtra("minute",minute);
        i.putExtra("title",title);
        i.putExtra("dosage",dosage);
        i.putExtra("key",key);
        i.putExtra("cancel",false);
        Log.e("iIntent",hour+title+dosage+minute+key);

        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicines_with_nav);

        titlepage = findViewById(R.id.titlepage);
        subtitlepage = findViewById(R.id.subtitlepage);

        btnAddNew = findViewById(R.id.btnAddNew);

        final String[] items = {"Medicines", "Calendar", "Diary", "Instructions", "LogOut"};
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list, items);

        listview = (ListView) findViewById(R.id.drawerList);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        navigation = new Navigation(this, listview, drawer);
        navigation.setN();

        // import font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");

        // customize font
        titlepage.setTypeface(MMedium);
        subtitlepage.setTypeface(MLight);

        btnAddNew.setTypeface(MLight);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, NewMedicineActivity.class);
                startActivity(a);
            }
        });

        // working with data
        medicine_intakes = findViewById(R.id.medicine_intakes);
        medicine_intakes.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyMedicine>();

        // get data from firebase
        reference = FirebaseDatabase.getInstance().getReference().child("CuredApp");
        medicineAdapter = new MedicineAdapter(MainActivity.this, list, this);
        final callAlarm a = this;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // set code to retrieve data and replace layout
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    MyMedicine p = dataSnapshot1.getValue(MyMedicine.class);
                    list.add(p);
                }
                medicineAdapter = new MedicineAdapter(MainActivity.this, list, a);
                medicine_intakes.setAdapter(medicineAdapter);
                medicineAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // set code to show an error
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}