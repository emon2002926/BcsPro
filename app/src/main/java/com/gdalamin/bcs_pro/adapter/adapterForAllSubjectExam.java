package com.gdalamin.bcs_pro.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gdalamin.bcs_pro.Activity.ActivityExam;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.api.SharedPreferencesManagerAppLogic;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.nio.charset.StandardCharsets;

public class adapterForAllSubjectExam extends RecyclerView.Adapter<adapterForAllSubjectExam.myviewholder> {
    ModelForLectureAndAllQuestion[] data;
    private int lastPosition = -1;
    public adapterForAllSubjectExam(ModelForLectureAndAllQuestion[] data) {
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

            SharedPreferencesManagerAppLogic preferencesManager = new SharedPreferencesManagerAppLogic(holder.bcsYearName.getContext());

//            int subCode = preferencesManager.getInt("subCode");
//            int LOGIC = preferencesManager.getInt("logic");


            setAnimation(holder.tvSubject.getContext(),holder.itemView,position);

                String subjectName = convertToUTF8(data[position].getSubjects());

//                holder.tvPosition.setText(position + 1 +")");

                holder.tvSubject.setText(subjectName);
                holder.cardView1.setVisibility(View.GONE);
                holder.subjectLayout.setVisibility(View.VISIBLE);

                    holder.subjectLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //  Show a bottom sheet dialog to allow the user to submit the num of question  and time
                            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDailogTheme);
                            View bottomSheetView = LayoutInflater.from(view.getContext()).inflate(R.layout.subject_based_exam_submition
                                    ,bottomSheetDialog.findViewById(R.id.bottomSheetContainer));

                            bottomSheetDialog.setContentView(bottomSheetView);
                            bottomSheetDialog.show();

                            TextView tvShowSubject = bottomSheetView.findViewById(R.id.tvSubjectName);
                            tvShowSubject.setText(subjectName);

                            EditText edTime = bottomSheetView.findViewById(R.id.edTime);
                            EditText edNumOfQuestion = bottomSheetView.findViewById(R.id.edNumOfQuestion);
                            edNumOfQuestion.setContentDescription("প্রশ্ন");
                            edTime.setContentDescription("সময়");

                            bottomSheetView.findViewById(R.id.btnSubmit).setOnClickListener(submitView -> {
                                String time = edTime.getText().toString().trim();
                                String NUM_OF_QUESTION= edNumOfQuestion.getText().toString().trim();

                                int NUM_OF_QUESTION2 = Integer.parseInt(NUM_OF_QUESTION);

                                int time2 = Integer.parseInt(time);

                                if (time.isEmpty()){
                                    edTime.setError("please enter time");
                                    edTime.requestFocus();
                                } else if (time2 >50) {
                                    edTime.setError("সর্বাধিক ৫০ মিনিট");
                                    edTime.requestFocus();

                                } else if (NUM_OF_QUESTION.isEmpty()) {
                                    edNumOfQuestion.setError("please enter the amount of question");
                                    edNumOfQuestion.requestFocus();
                                }else if (NUM_OF_QUESTION2 >100) {
                                    edNumOfQuestion.setError("সর্বাধিক ১০০টি প্রশ্ন");
                                    edNumOfQuestion.requestFocus();
                                }
                                else {

                                    preferencesManager.saveString("subjectCode",convertToUTF8(data[position].getSubjectCode()));
                                    preferencesManager.saveInt("examQuestionNum", Integer.parseInt(NUM_OF_QUESTION));

                                    preferencesManager.saveInt("time",Integer.parseInt(time));
                                    preferencesManager.saveInt("LogicForExam",2);


                                    preferencesManager.saveString("subjectPosition","2");
                                    Intent intent = new Intent(view.getContext(),ActivityExam.class);
                                    intent.putExtra("titleText",subjectName);

                                    bottomSheetDialog.dismiss();

                                    view.getContext().startActivity(intent);

                                }


                            });

                            bottomSheetView.findViewById(R.id.btnCancal).setOnClickListener(cancelView -> {
                                bottomSheetDialog.dismiss();
                            });


                        }
                    });





    }
    private String convertToUTF8(String inputString) {
        return new String(inputString.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
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
