package com.example.cured;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    TextView titlepage, subtitlepage, endpage;
    Button btnAddNew;

    DatabaseReference reference;
    RecyclerView medicine_intakes;
    ArrayList<MyMedicine> list;
    MedicineAdapter medicineAdapter;

    private static final String TAG = "MainActivity";

    private CalendarView mCalendarView;
    ListView listview = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity);
        final String[] items = {"add", "delete", "update", "main", "LogOut","My Medicine"};
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list, items);

        listview = (ListView) findViewById(R.id.drawerList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                switch (position) {
                    case 0: // add
                        Intent a = new Intent(MainActivity.this, NewMedicineActivity.class);
                        startActivity(a);
                        break;
                    case 1: // delete
                        Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_LONG).show();
                        break;
                    case 2: // update
                        Toast.makeText(getApplicationContext(), "update", Toast.LENGTH_LONG).show();
                        break;
                    case 3: // main
                        Intent d = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(d);
                        break;
                    case 4:
                        new AlertDialog.Builder(MainActivity.this/* 해당 액티비티를 가르킴 */)
                                .setTitle("logout").setMessage("Do you want to logout?")
                                .setPositiveButton("LogOut", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivity(i);
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                })
                                .show();
                        break;
                    case 5:
                        Intent e = new Intent(MainActivity.this, MyMedicine.class);
                        startActivity(e);
                        break;
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });

        //setContentView(R.layout.activity_design);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + month + "/" + year;
                Log.d(TAG, "date is :" + date);
            }
        });
        if (FirebaseAuth.getInstance().getCurrentUser() == null) { // If there are no current user
            // Go to Login view or Sign up view or some other view..
            //FirebaseAuth.getInstance().signOut(); // This is logout method
        }


        // import font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

        btnAddNew = findViewById(R.id.btnAddNew);


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
        reference = FirebaseDatabase.getInstance().getReference().child("medicines");
        medicineAdapter = new MedicineAdapter(MainActivity.this, list);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // set code to retrieve data and replace layout
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    MyMedicine p = dataSnapshot1.getValue(MyMedicine.class);
                    list.add(p);
                }
                medicineAdapter = new MedicineAdapter(MainActivity.this, list);
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

// SPLASH SCREEN CODE
//    private static int SPLASH_SCREEN = 5000;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_main);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(MainActivity.this, Design.class);
//                startActivity(intent);
//                finish();
//            }
//        },SPLASH_SCREEN);
//    }