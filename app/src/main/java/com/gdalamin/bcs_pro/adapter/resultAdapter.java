package com.gdalamin.bcs_pro.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gdalamin.bcs_pro.Activity.TestResult;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.modelClass.ExamResult;

public class resultAdapter extends RecyclerView.Adapter<resultAdapter.myviewholder>
{
    ExamResult examResults[];

    public resultAdapter(ExamResult[] data) {
        this.examResults = data;
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


        holder.total.setText(examResults[position].getTotal());
        holder.correct.setText(examResults[position].getCorrect());
        holder.wrong.setText(examResults[position].getWrong());
        holder.marks.setText(examResults[position].getMark());
        holder.date.setText(examResults[position].getDate());



        holder.cvResultLayout.setOnClickListener(view -> {



            view.getContext().startActivity(new Intent(view.getContext(), TestResult.class));
        });


    }

    @Override
    public int getItemCount() {
        return examResults.length;
    }
    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView total,correct,wrong,marks,date;
        CardView cvResultLayout;


        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            cvResultLayout = itemView.findViewById(R.id.resultLayoutCV);

            total = itemView.findViewById(R.id.totalTv);
            correct = itemView.findViewById(R.id.correctTv);
            wrong =itemView.findViewById(R.id.wrongTv);
            marks = itemView.findViewById(R.id.marksTv);
            date = itemView.findViewById(R.id.tvDate);


        }
    }






}
