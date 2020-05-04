package com.example.cured;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;

public class Navigation  {
    Context context;
    ListView listview;
    DrawerLayout drawer;

    public Navigation(Context c, ListView l,DrawerLayout d){
        context = c;
        listview = l;
        drawer = d;
    }


   public void setN(){

        final String[] items = {"Main", "Medicines", "Diary","Log Out", "Instructions"};
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.list, items);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new ListView.OnItemClickListener() {

           @Override
           public void onItemClick(AdapterView parent, View v, int position, long id) {

               switch (position) {
                   case 0: // Main
                       Intent d = new Intent(context, MainActivity.class);
                       context.startActivity(d);
                       break;
                   case 1: // Medicines
                       Intent a = new Intent(context, MedicineDisplay.class);
                       context.startActivity(a);
                       break;
                   case 2: // Diary
                       Intent b = new Intent(context, DiaryActivity.class);
                       context.startActivity(b);
                       break;
                   case 3: //Logout
                       new AlertDialog.Builder(context)
                               .setTitle("logout").setMessage("Do you want to logout?")
                               .setPositiveButton("LogOut", new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int whichButton) {
                                       FirebaseAuth.getInstance().signOut();
                                       Intent i = new Intent(context, LoginActivity.class);
                                       i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                       context.startActivity(i);
                                   }
                               })
                               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int whichButton) {

                                   }
                               })
                               .show();
                       break;
                   case 4:
                       Intent m = new Intent(context, InstructionActivity.class);
                       context.startActivity(m);
                       break;
               }
               drawer.closeDrawer(Gravity.LEFT);
           }
        });
   }


}
