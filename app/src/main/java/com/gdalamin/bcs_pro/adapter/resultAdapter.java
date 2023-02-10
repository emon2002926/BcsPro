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

public class resultAdapter extends RecyclerView.Adapter<resultAdapter.myviewholder>
{
    model data[];

    public resultAdapter(model[] data2) {
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



    }

    @Override
    public int getItemCount() {
        return data.length;
    }
    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView total,correct,wrong,marks;

        RelativeLayout relativeLayout;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);


            total = itemView.findViewById(R.id.totalTv);
            correct = itemView.findViewById(R.id.correctTv);
            wrong =itemView.findViewById(R.id.wrongTv);
            marks = itemView.findViewById(R.id.marksTv);


        }
    }






}
