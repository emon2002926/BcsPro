package com.gdalamin.bcs_pro.adapter;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gdalamin.bcs_pro.Activity.QuestionListActivity;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.api.SharedPreferencesManagerAppLogic;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;

import java.io.UnsupportedEncodingException;

public class TestmyadapterForAllbcs extends RecyclerView.Adapter<TestmyadapterForAllbcs.myviewholder> {
    ModelForLectureAndAllQuestion data[];

    public TestmyadapterForAllbcs(ModelForLectureAndAllQuestion[] data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bcs_question_bank, parent, false);

        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, @SuppressLint("RecyclerView") final int position) {


        /////////       This will open Older Bcs Question

        SharedPreferencesManagerAppLogic preferencesManager = new SharedPreferencesManagerAppLogic(holder.bcsYearName.getContext());

        Resources resources = holder.tvSubject.getContext().getResources();
        String amount = resources.getText(R.string.amountOfQuestion)+" :"+data[position].getTotalQuestion();
        holder.numOfQuestion.setText(amount);



        String subjectName = convertToUTF8(data[position].getSubjects());
        holder.tvPosition.setText(String.valueOf(position+1)+")");
        holder.tvSubject.setText(subjectName);
        holder.cardView1.setVisibility(View.GONE);
        holder.subjectLayout.setVisibility(View.VISIBLE);


        holder.bcsYearName.setText(data[position].getText());

        holder.cardView1.setVisibility(View.VISIBLE);
        holder.subjectLayout.setVisibility(View.GONE);
        holder.cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String subjectName = data[position].getText();

                preferencesManager.saveString("bcsYearName",subjectName);

                preferencesManager.saveInt("subCode",4);

                Intent intent = new Intent(view.getContext(), QuestionListActivity.class);
                intent.putExtra("titleText",subjectName);
                view.getContext().startActivity(intent);

            }
        });

    }
    private String convertToUTF8(String inputString) {
        try {
            return new String(inputString.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }





    @Override
    public int getItemCount() {
        return data.length;
    }



    static class myviewholder extends RecyclerView.ViewHolder {
        TextView bcsYearName, numOfQuestion,tvSubject,tvPosition;
        LinearLayout cardView1,subjectLayout;



        public myviewholder(@NonNull View itemView) {
            super(itemView);

            bcsYearName = itemView.findViewById(R.id.questionBatch);
            numOfQuestion = itemView.findViewById(R.id.numOFQuestion);
            cardView1 = itemView.findViewById(R.id.layout1);
            subjectLayout = itemView.findViewById(R.id.layout2);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvPosition = itemView.findViewById(R.id.tvPosition);
        }


    }
}
