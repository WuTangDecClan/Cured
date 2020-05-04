package com.example.cured;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.MyViewHolder>{

    Context context;
    ArrayList<MyDiary> myDiary;

    public DiaryAdapter(Context c, ArrayList<MyDiary> p) {
        context = c;
        myDiary = p;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_diary,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.diary_title.setText(myDiary.get(i).getDiary_title());
        myViewHolder.diary_desc.setText(myDiary.get(i).getDiary_desc());
        myViewHolder.diary_date.setText(myDiary.get(i).getDiary_date());

        final String getDiary_title = myDiary.get(i).getDiary_title();
        final String getDiary_desc = myDiary.get(i).getDiary_desc();
        final String getDiary_date = myDiary.get(i).getDiary_date();
        final String getDiary_key = myDiary.get(i).getDiary_key();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa = new Intent(context, EditDiaryActivity.class);
                aa.putExtra("diary_title", getDiary_title);
                aa.putExtra("diary_desc", getDiary_desc);
                aa.putExtra("diary_date", getDiary_date);
                aa.putExtra("diary_key", getDiary_key);
                context.startActivity(aa);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myDiary.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView diary_title, diary_desc, diary_date, todo_key;
        Switch switch1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            diary_title = (TextView) itemView.findViewById(R.id.diary_title);
            diary_desc = (TextView) itemView.findViewById(R.id.diary_desc);
            diary_date = (TextView) itemView.findViewById(R.id.diary_date);
            switch1 = (Switch) itemView.findViewById(R.id.switch1);
        }
    }

}