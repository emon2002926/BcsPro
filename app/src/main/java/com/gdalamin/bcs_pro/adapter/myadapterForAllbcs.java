package com.gdalamin.bcs_pro.adapter;


import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gdalamin.bcs_pro.Activity.ActivityExam;
import com.gdalamin.bcs_pro.Activity.QuestionListActivity;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.api.SharedPreferencesManager;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;
import com.gdalamin.bcs_pro.modelClass.QuestionList;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
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
    public void onBindViewHolder(@NonNull final myviewholder holder, final int position) {


            SharedPreferences sharedPreferences = holder.t1.getContext().getSharedPreferences("totalQuestion", MODE_PRIVATE);
            SharedPreferencesManager preferencesManager = new SharedPreferencesManager(holder.t1.getContext());

            int LOGIC = sharedPreferences.getInt("logic", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            int subCode = sharedPreferences.getInt("subCode",0);


            String SUBJECT_CODE = String.valueOf(position+1);

            if (LOGIC == 2){

                String subjectName = data[position].getSubjects();
                holder.tvPosition.setText(String.valueOf(position+1)+")");
                holder.tvSubject.setText(convertToUTF8(subjectName));
                holder.cardView1.setVisibility(View.GONE);
                holder.cardView2.setVisibility(View.VISIBLE);


                if (subCode ==3){
                    holder.cardView2.setOnClickListener(view -> {

                        int NUM_OF_QUESTION =200;
                        editor.putInt("examQuestionNum", NUM_OF_QUESTION);

                        editor.putString("subjectPosition",SUBJECT_CODE);
                        editor.commit();
                        view.getContext().startActivity(new Intent(view.getContext(),QuestionListActivity.class));
                    });


                }else if (subCode == 2){


                    holder.cardView2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //  Show a bottom sheet dialog to allow the user to submit the answers
                            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDailogTheme);
                            View bottomSheetView = LayoutInflater.from(view.getContext()).inflate(R.layout.subject_based_exam_submition, (LinearLayout) bottomSheetDialog.findViewById(R.id.bottomSheetContainer));


                            bottomSheetDialog.setContentView(bottomSheetView);
                            bottomSheetDialog.show();

                            TextView tvShowSubject = bottomSheetView.findViewById(R.id.tvSubjectName);
                            tvShowSubject.setText(subjectName);

                            EditText edTime = bottomSheetView.findViewById(R.id.edTime);
                            EditText edNumOfQuestion = bottomSheetView.findViewById(R.id.edNumOfQuestion);


                            editor.putString("subjectPosition",SUBJECT_CODE);



                            bottomSheetView.findViewById(R.id.btnSubmit).setOnClickListener(submitView -> {



                                String time = edTime.getText().toString().trim();
                                String NUM_OF_QUESTION= edNumOfQuestion.getText().toString().trim();


                                if (time.isEmpty()){
                                    edTime.setError("please enter time");
                                    edTime.requestFocus();
                                    return;
                                } else if (NUM_OF_QUESTION.isEmpty()) {
                                    edNumOfQuestion.setError("please enter the amount of question");
                                    edNumOfQuestion.requestFocus();
                                    return;
                                }else {

                                    preferencesManager.saveInt("examQuestionNum", Integer.valueOf(NUM_OF_QUESTION));
//                                    editor.putInt("examQuestionNum", Integer.valueOf(NUM_OF_QUESTION));
                                    editor.putInt("time",Integer.valueOf(time));
                                    editor.putInt("LogicForExam",2);
                                    editor.commit();
                                    navigateTo(view.getContext());
                                }


                            });

                            bottomSheetView.findViewById(R.id.btnCancal).setOnClickListener(cancelView -> {
                                bottomSheetDialog.dismiss();
                            });


                        }
                    });

                }



                
            } else if (LOGIC ==1) {


                holder.t1.setText(data[position].getText());
                holder.cardView1.setVisibility(View.VISIBLE);
                holder.cardView2.setVisibility(View.GONE);
                holder.cardView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String subjectName = data[position].getText();
                        Toast.makeText(view.getContext(),subjectName,Toast.LENGTH_SHORT).show();
                        editor.putString("bcsYearName",subjectName);
                        editor.commit();

                        Intent intent = new Intent(view.getContext(), QuestionListActivity.class);
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

    public void  navigateTo(Context context){

        context.startActivity(new Intent(context.getApplicationContext(),ActivityExam.class));

    }



    @Override
    public int getItemCount() {
        return data.length;
    }



    static class myviewholder extends RecyclerView.ViewHolder {
        TextView t1, t2,tvSubject,tvPosition;
        CardView cardView1, cardView2;


        public myviewholder(@NonNull View itemView) {
            super(itemView);

            t1 = itemView.findViewById(R.id.questionBatch);
            t2 = itemView.findViewById(R.id.numOFQuestion);
            cardView1 = itemView.findViewById(R.id.layout1);
            cardView2 = itemView.findViewById(R.id.layout2);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvPosition = itemView.findViewById(R.id.tvPosition);
        }


    }
}
