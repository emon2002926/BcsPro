package com.gdalamin.bcs_pro.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gdalamin.bcs_pro.Activity.ActivityPdfViwer;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;

public class AdapterForCourse extends RecyclerView.Adapter<AdapterForCourse.myviewholder>
{

    ModelForLectureAndAllQuestion data[];


    private Context mContext;



    public AdapterForCourse(ModelForLectureAndAllQuestion[] data) {
        this.data = data;
    }



    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_all_course,parent,false);


         return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, final int position) {



        String courseImageName = data[position].getCourse();

        Glide.with(holder.imgCourse1.getContext())
                .load("http://emon.searchwizy.com/api2/image/" + courseImageName)
                .into(holder.imgCourse1);





    }

    @Override
    public int getItemCount() {
        return data.length;
    }



    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView imgCourse1,imgCourse2;

        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            imgCourse1 = itemView.findViewById(R.id.imgCourse1);

        }
    }




}
