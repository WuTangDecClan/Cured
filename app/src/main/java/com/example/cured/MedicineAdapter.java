package com.example.cured;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MyViewHolder>{
    Context context;
    ArrayList<MyMedicine> myMedicine;
    int h,m;
    String t;

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

        myViewHolder.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    StringTokenizer st = new StringTokenizer((String) myViewHolder.medicine_time.getText(),"::");
                    h = Integer.parseInt(st.nextToken());
                    m = Integer.parseInt(st.nextToken());
                    t=Integer.toString(h)+" : "+Integer.toString(m);

                    Log.e("time",t);
                }
                else{

                }
            }
        });
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
        Switch switch1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            medicine_title = (TextView) itemView.findViewById(R.id.medicine_title);
            medicine_dose = (TextView) itemView.findViewById(R.id.medicine_dose);
            medicine_time = (TextView) itemView.findViewById(R.id.medicine_time);
            switch1 = (Switch) itemView.findViewById(R.id.switch1);
        }
    }
}
