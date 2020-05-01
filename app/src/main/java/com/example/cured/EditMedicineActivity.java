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

public class EditMedicineActivity extends AppCompatActivity {

    EditText Medicine_title, Medicine_dose, Medicine_time;
    Button btnSaveChanges, btnDelete;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_medicine);


        Medicine_title = findViewById(R.id.medicine_title);
        Medicine_dose = findViewById(R.id.medicine_dose);
        Medicine_time = findViewById(R.id.medicine_time);

        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnDelete = findViewById(R.id.btnDelete);

        //get value from previous page
        Medicine_title.setText(getIntent().getStringExtra("medicine_title"));
        Medicine_dose.setText(getIntent().getStringExtra("medicine_desc"));
        Medicine_time.setText(getIntent().getStringExtra("medicine_time"));

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
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        dataSnapshot.getRef().child("medicine_title").setValue(Medicine_title.getText().toString());
                        dataSnapshot.getRef().child("medicine_dose").setValue(Medicine_dose.getText().toString());
                        dataSnapshot.getRef().child("medicine_time").setValue(Medicine_time.getText().toString());
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