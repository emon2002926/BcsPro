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
import com.gdalamin.bcs_pro.modelClass.modelForLecture;
import com.gdalamin.bcs_pro.modelClass.modelForExam;

public class myadapter2 extends RecyclerView.Adapter<myadapter2.myviewholder>
{
    modelForExam data[];

    public myadapter2(modelForExam[] data2) {
        this.data = data2;
    }

    private Context mContext;


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_slide,parent,false);

         return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, final int position) {


        String examDitals = data[position].getDailyExam();

        if (examDitals !=null){
            holder.t1.setText(examDitals);

        }


    }

    @Override
    public int getItemCount() {
        return data.length;
    }
    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView t1,t2,t3,t4;

        RelativeLayout relativeLayout;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            t1=itemView.findViewById(R.id.exam);
            t2=itemView.findViewById(R.id.q1);




        }
    }

    private void selectedOption(RelativeLayout selectedOptionLayout , ImageView selectedOptionIcon) {


        selectedOptionIcon.setImageResource(R.drawable.chack);
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_selected_option);

    }




}
