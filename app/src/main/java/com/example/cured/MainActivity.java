package com.example.cured;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

//import com.facebook.AccessToken;


public class MainActivity extends AppCompatActivity implements callAlarm {
    TextView titlepage, subtitlepage, endpage;
    Button btnAddNew;

    DatabaseReference reference;
    RecyclerView medicine_intakes;
    ArrayList<MyMedicine> list;
    MedicineAdapter medicineAdapter;
    Navigation navigation;

    private FirebaseAuth mAuth;

    private static final String TAG = "MainActivity";

    private CalendarView mCalendarView;
    ListView listview = null;

    @Override
    public void call(int hour, int minute, String title, String dosage, String key) {
        Intent i = new Intent(MainActivity.this, NotificationActivity.class);
        i.putExtra("hour", hour);
        i.putExtra("minute", minute);
        i.putExtra("title", title);
        i.putExtra("dosage", dosage);
        i.putExtra("key", key);
        i.putExtra("cancel", false);
        Log.e("iIntent", hour + title + dosage + minute + key);

        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity);

//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//        handleFacebookAccessToken(accessToken);
//
//        if (FirebaseAuth.getInstance().getCurrentUser() == null && !isLoggedIn) { // If there are no current user
//         Go to Login view
//              startLoginActivity();
//        }

        final String[] items = {"Add", "Edit", "Diary", "Main", "LogOut"};
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list, items);

        listview = (ListView) findViewById(R.id.drawerList);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        navigation = new Navigation(this, listview, drawer);
        navigation.setN();


        //setContentView(R.layout.activity_design);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + month + "/" + year;
                Log.d(TAG, "date is :" + date);
            }
        });


        //if (FirebaseAuth.getInstance().getCurrentUser() == null) { // If there are no current user
        // Go to Login view or Sign up view or some other view..
        //FirebaseAuth.getInstance().signOut(); // This is logout method
        //}


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

        //set the visuals on click

        LinearLayout medicine = (LinearLayout )findViewById(R.id.medicine);
        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MedicineDisplay.class);
                startActivity(intent );
            }
        });

        LinearLayout diary = (LinearLayout )findViewById(R.id.diary);
        diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DiaryActivity.class);
                startActivity(intent );
            }
        });
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}


//    private void handleFacebookAccessToken(AccessToken token) {
//        Log.d(TAG, "handleFacebookAccessToken:" + token);
//
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                        }
//                    }
//                });
//    }


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