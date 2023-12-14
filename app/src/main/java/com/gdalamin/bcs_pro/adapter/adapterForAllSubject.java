package com.gdalamin.bcs_pro.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gdalamin.bcs_pro.Activity.QuestionListActivity;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.ViewModel.SharedViewModel;
import com.gdalamin.bcs_pro.api.SharedPreferencesManagerAppLogic;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;

import java.io.UnsupportedEncodingException;

public class adapterForAllSubject extends RecyclerView.Adapter<adapterForAllSubject.myviewholder> {
    ModelForLectureAndAllQuestion data[];
    private int lastPosition = -1;
    Context context;
    private SharedViewModel mViewModel;


    public adapterForAllSubject(ModelForLectureAndAllQuestion[] data,SharedViewModel viewModel) {
        this.sharedViewModel = viewModel;
        this.data = data;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bcs_question_bank, parent, false);

        return new myviewholder(view);
    }

    private SharedViewModel sharedViewModel;

    // Constructor to receive SharedViewModel instance





    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, @SuppressLint("RecyclerView") final int position) {

            SharedPreferencesManagerAppLogic preferencesManager = new SharedPreferencesManagerAppLogic(holder.bcsYearName.getContext());

            int subCode = preferencesManager.getInt("subCode");
            int LOGIC = preferencesManager.getInt("logic");



        setAnimation(holder.tvSubject.getContext(),holder.itemView,position);

                String subjectName = convertToUTF8(data[position].getSubjects());
                holder.tvPosition.setText(String.valueOf(position+1)+")");
                holder.tvSubject.setText(subjectName);
                holder.cardView1.setVisibility(View.GONE);
                holder.subjectLayout.setVisibility(View.VISIBLE);
                    holder.subjectLayout.setOnClickListener(view -> {
                        int LOGIC_FOR_ALL_SUBJECT_EXAM =0;
                        preferencesManager.saveInt("LogicForExam",LOGIC_FOR_ALL_SUBJECT_EXAM);
                        preferencesManager.saveString("subjectCode",convertToUTF8(data[position].getSubjectCode()));

                        holder.bindData(subjectName);
                        sharedViewModel.setTitleText(subjectName);

                        Intent intent = new Intent(view.getContext(), QuestionListActivity.class);
//                        intent.putExtra("titleText",subjectName);
                        view.getContext().startActivity(intent);
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
        public void bindData(String data) {
            // Bind data to your ViewHolder views
            // Example: titleTextView.setText(data);
        }


    }
    public void setAnimation(Context ctx, View viewToAnimate, int position){
        if (position>lastPosition){
            Animation slideIn = AnimationUtils.loadAnimation(ctx, android.R.anim.fade_in);
            viewToAnimate.setAnimation(slideIn);
            lastPosition = position;
        }

    }
}
