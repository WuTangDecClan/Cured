package com.example.cured;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.MyViewHolder>{

    Context context;
    ArrayList<MyDiary> myDiary;

    public DiaryAdapter(Context context, ArrayList<MyDiary> p) {
        context = context;
        myDiary = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_main , viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.titlediary.setText(myDiary.get(i).getTitlediary());
        myViewHolder.descdiary.setText(myDiary.get(i).getTitlediary());
        myViewHolder.datediary.setText(myDiary.get(i).getTitlediary());
    }

    @Override
    public int getItemCount() {
        return myDiary.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titlediary, descdiary, datediary;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titlediary = (TextView) itemView.findViewById(R.id.titlediary);
            descdiary = (TextView) itemView.findViewById(R.id.descdiary);
            datediary = (TextView) itemView.findViewById(R.id.datediary);
        }
    }
}
