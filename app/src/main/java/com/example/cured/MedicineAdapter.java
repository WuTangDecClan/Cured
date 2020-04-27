package com.example.cured;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MyViewHolder>{
    Context context;
    ArrayList<MyMedicine> myMedicine;

    public MedicineAdapter(Context c, ArrayList<MyMedicine> p) {
        context = c;
        myMedicine = p;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.medicine_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.medicine_title.setText(myMedicine.get(i).getMedicine_title());
        //myViewHolder.medicine_dose.setText(myMedicine.get(i).getMedicine_dose());
        myViewHolder.medicine_time.setText(myMedicine.get(i).getMedicine_time());

//        final String getMedicine_title = myMedicine.get(i).getMedicine_title();
//        final String getMedicine_dose = myMedicine.get(i).getMedicine_dose();
//        final String getMedicine_time = myMedicine.get(i).getMedicine_time();
//        final String getMedicine_key = myMedicine.get(i).getMedicine_key();
//
//        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent aa = new Intent(context,NewMedicineActivity.class);
//                aa.putExtra("medicine_title", getMedicine_title);
//                aa.putExtra("medicine_dose", getMedicine_dose);
//                aa.putExtra("medicine_time", getMedicine_time);
//                aa.putExtra("medicine_key", getMedicine_key);
//                context.startActivity(aa);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return myMedicine.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView medicine_title, medicine_dose, medicine_time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            medicine_title = (TextView) itemView.findViewById(R.id.medicine_title);
            medicine_dose = (TextView) itemView.findViewById(R.id.medicine_dose);
            medicine_time = (TextView) itemView.findViewById(R.id.medicine_time);
        }
    }
}
