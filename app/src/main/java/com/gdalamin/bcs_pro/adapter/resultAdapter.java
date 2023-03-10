package com.gdalamin.bcs_pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.modelClass.model;
import com.gdalamin.bcs_pro.modelClass.resultModel;

public class resultAdapter extends RecyclerView.Adapter<resultAdapter.myviewholder>
{
    resultModel data[];

    public resultAdapter(resultModel[] data2) {
        this.data = data2;
    }

    private Context mContext;


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.result,parent,false);

         return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, final int position) {


        holder.total.setText(data[position].getTotal());
        holder.correct.setText(data[position].getCorrect());
        holder.wrong.setText(data[position].getWrong());
        holder.marks.setText(data[position].getMark());
        holder.date.setText(data[position].getDate());


    }

    @Override
    public int getItemCount() {
        return data.length;
    }
    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView total,correct,wrong,marks,date;


        public myviewholder(@NonNull View itemView)
        {
            super(itemView);


            total = itemView.findViewById(R.id.totalTv);
            correct = itemView.findViewById(R.id.correctTv);
            wrong =itemView.findViewById(R.id.wrongTv);
            marks = itemView.findViewById(R.id.marksTv);
            date = itemView.findViewById(R.id.tvDate);


        }
    }






}
