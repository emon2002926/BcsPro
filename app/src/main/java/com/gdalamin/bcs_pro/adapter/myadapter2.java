package com.gdalamin.bcs_pro.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gdalamin.bcs_pro.Activity.ActivityExam;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.api.SharedPreferencesManager;
import com.gdalamin.bcs_pro.modelClass.modelForExam;

import java.io.UnsupportedEncodingException;

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



        SharedPreferencesManager preferencesManager = new SharedPreferencesManager(holder.cardView.getContext());

        String examDitals = data[position].getDailyExam();
        String getDetails = data[position].getDetails();



        holder.TvExamDate.setText(examDitals);



        holder.TvExamDetails.setText(convertToUTF8(getDetails));

        holder.cardView.setOnClickListener(view -> {

            int LOGIC_FOR_ALL_SUBJECT_EXAM = data[position].getTotalQc();


            preferencesManager.saveInt("examQuestionNum",data[position].getTotalQc());
            preferencesManager.saveInt("LogicForExam",LOGIC_FOR_ALL_SUBJECT_EXAM);


            Intent intent = new Intent(view.getContext(), ActivityExam.class);
            intent.putExtra("titleText","Daily Model Test");
            view.getContext().startActivity(intent);

        });



    }

    @Override
    public int getItemCount() {
        return data.length;
    }
    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView TvExamDate,TvExamDetails;
        CardView cardView;


        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            TvExamDate=itemView.findViewById(R.id.exam);
            TvExamDetails=itemView.findViewById(R.id.q1);
            cardView = itemView.findViewById(R.id.card);

        }
    }
    private String convertToUTF8(String inputString) {
        try {
            return new String(inputString.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }




}
