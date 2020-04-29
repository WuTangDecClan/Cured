package com.example.cured;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.ListFragment;

public class Activity extends AppCompatActivity {
    ListView listview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        final String[] items = {"add", "delete", "update", "personal_info"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);

        listview = (ListView) findViewById(R.id.drawerList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                //TextView contentTextview = (TextView) findViewById(R.id.drawer_content);

                switch (position) {
                    case 0: // add
                        Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_LONG).show();
                        break;
                    case 1: // delete
                        Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_LONG).show();
                        break;
                    case 2: // update
                        Toast.makeText(getApplicationContext(), "update", Toast.LENGTH_LONG).show();
                        break;
                    case 3: // personal_info
                        Toast.makeText(getApplicationContext(), "personal_info", Toast.LENGTH_LONG).show();
                        break;
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
    }
}
