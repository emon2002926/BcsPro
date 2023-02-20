package com.gdalamin.bcs_pro.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gdalamin.bcs_pro.Activity.ActivityExam;
import com.gdalamin.bcs_pro.Activity.AllBcsQuestionActivity;
import com.gdalamin.bcs_pro.Activity.McqTestActivity;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.modelClass.model;
import com.gdalamin.bcs_pro.modelClass.modelForLecture;
import com.gdalamin.bcs_pro.modelClass.modelForExam;

public class myadapter2 extends RecyclerView.Adapter<myadapter2.myviewholder>
{
    modelForExam data[];

    public myadapter2(modelForExam[] data2) {
        this.data = data2;
    }




    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_slide,parent,false);

         return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, final int position) {



        SharedPreferences sharedPreferences = holder.cardView.getContext().getSharedPreferences("totalQuestion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String examDitals = data[position].getDailyExam();




        holder.examDate.setText(examDitals);
        holder.t2.setText(data[position].getDetails());


        holder.cardView.setOnClickListener(view -> {

            editor.putInt("examQuestionNum",data[position].getTotalQc());
            editor.commit();
            Intent intent = new Intent(view.getContext(), ActivityExam.class);
            view.getContext().startActivity(intent);

        });



    }

    @Override
    public int getItemCount() {
        return data.length;
    }
    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView examDate,t2;
        CardView cardView;


        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            examDate=itemView.findViewById(R.id.exam);
            t2=itemView.findViewById(R.id.q1);
            cardView = itemView.findViewById(R.id.card);

        }
    }



}
