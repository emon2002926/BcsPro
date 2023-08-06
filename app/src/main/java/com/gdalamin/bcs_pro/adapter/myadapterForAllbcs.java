package com.gdalamin.bcs_pro.adapter;


import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gdalamin.bcs_pro.Activity.ActivityExam;
import com.gdalamin.bcs_pro.Activity.QuestionListActivity;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.api.SharedPreferencesManagerAppLogic;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.UnsupportedEncodingException;

public class myadapterForAllbcs extends RecyclerView.Adapter<myadapterForAllbcs.myviewholder> {
    ModelForLectureAndAllQuestion data[];

    int tolatExamQuestion =0;
    public myadapterForAllbcs(ModelForLectureAndAllQuestion[] data) {
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


//            SharedPreferences sharedPreferences = holder.bcsYearName.getContext().getSharedPreferences("totalQuestion", MODE_PRIVATE);

            SharedPreferencesManagerAppLogic preferencesManager = new SharedPreferencesManagerAppLogic(holder.bcsYearName.getContext());

//            SharedPreferences.Editor editor = sharedPreferences.edit();


            int subCode = preferencesManager.getInt("subCode");
            int LOGIC = preferencesManager.getInt("logic");


            String SUBJECT_CODE = String.valueOf(position+1);

            holder.numOfQuestion.setText("Total :"+data[position].getTotalQuestion());


            if (LOGIC == 2){


                String subjectName = convertToUTF8(data[position].getSubjects());
                holder.tvPosition.setText(String.valueOf(position+1)+")");
                holder.tvSubject.setText(subjectName);
                holder.cardView1.setVisibility(View.GONE);
                holder.cardView2.setVisibility(View.VISIBLE);



                if (subCode ==3){
                    holder.cardView2.setOnClickListener(view -> {
                        int LOGIC_FOR_ALL_SUBJECT_EXAM =0;

                        preferencesManager.saveInt("LogicForExam",LOGIC_FOR_ALL_SUBJECT_EXAM);
                        preferencesManager.saveString("subjectPosition",SUBJECT_CODE);
                        Intent intent = new Intent(view.getContext(), QuestionListActivity.class);
                        intent.putExtra("titleText",subjectName);
                        view.getContext().startActivity(intent);
                    });


                }else if (subCode == 2){

                    ///This will open Subject based Exam
                    holder.cardView2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //  Show a bottom sheet dialog to allow the user to submit the num of question  and time
                            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDailogTheme);
                            View bottomSheetView = LayoutInflater.from(view.getContext()).inflate(R.layout.subject_based_exam_submition, (LinearLayout) bottomSheetDialog.findViewById(R.id.bottomSheetContainer));

                            bottomSheetDialog.setContentView(bottomSheetView);
                            bottomSheetDialog.show();

                            TextView tvShowSubject = bottomSheetView.findViewById(R.id.tvSubjectName);
                            tvShowSubject.setText(subjectName);

                            EditText edTime = bottomSheetView.findViewById(R.id.edTime);
                            EditText edNumOfQuestion = bottomSheetView.findViewById(R.id.edNumOfQuestion);


                            bottomSheetView.findViewById(R.id.btnSubmit).setOnClickListener(submitView -> {


                                String time = edTime.getText().toString().trim();
                                String NUM_OF_QUESTION= edNumOfQuestion.getText().toString().trim();

                                int NUM_OF_QUESTION2 = Integer.parseInt(NUM_OF_QUESTION);

                                int time2 = Integer.parseInt(time);

                                if (time.isEmpty()){
                                    edTime.setError("please enter time");
                                    edTime.requestFocus();
                                    return;
                                } else if (time2 >18) {
                                    edTime.setError("Maximum time is 18 minutes");
                                    edTime.requestFocus();
                                    return;

                                } else if (NUM_OF_QUESTION.isEmpty()) {
                                    edNumOfQuestion.setError("please enter the amount of question");
                                    edNumOfQuestion.requestFocus();
                                    return;
                                }else if (NUM_OF_QUESTION2 >50) {
                                    edNumOfQuestion.setError("Maximum question is 50");
                                    edNumOfQuestion.requestFocus();
                                    return;
                                }
                                else {

                                    preferencesManager.saveString("subjectPosition",SUBJECT_CODE);
                                    preferencesManager.saveInt("examQuestionNum", Integer.parseInt(NUM_OF_QUESTION));

                                    preferencesManager.saveInt("time",Integer.parseInt(time));
                                    preferencesManager.saveInt("LogicForExam",2);


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



                
            }
            else if (LOGIC ==1) {

/////////       This will open Older Bcs Question
                holder.bcsYearName.setText(data[position].getText());
                holder.cardView1.setVisibility(View.VISIBLE);
                holder.cardView2.setVisibility(View.GONE);
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
        LinearLayout cardView1,cardView2;



        public myviewholder(@NonNull View itemView) {
            super(itemView);

            bcsYearName = itemView.findViewById(R.id.questionBatch);
            numOfQuestion = itemView.findViewById(R.id.numOFQuestion);
            cardView1 = itemView.findViewById(R.id.layout1);
            cardView2 = itemView.findViewById(R.id.layout2);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvPosition = itemView.findViewById(R.id.tvPosition);
        }


    }
}
