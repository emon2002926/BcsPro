package com.gdalamin.bcs_pro.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gdalamin.bcs_pro.Activity.TestResult;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.fragment.DashBordFragment;
import com.gdalamin.bcs_pro.modelClass.ExamResult;

public class resultAdapter extends RecyclerView.Adapter<resultAdapter.myviewholder> {

    ExamResult examResults[];
    private int lastPosition = -1;

    public resultAdapter(ExamResult[] data) {
        this.examResults = data;
    }

    public static final String ACTION_TOTAL_QUESTIONS_CHANGED = "com.gdalamin.bcs_pro.fragment.DashBordFragment";


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_layout, parent, false);

        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, final int position) {



        int totalQuestions = 0;

        int wrongAnswer = 0;
        int correctAnswer = 0;
        int notAnswred = 0;


        for (int i = 0; i < examResults.length; i++) {
            totalQuestions += Integer.parseInt(examResults[i].getTotal());
        }
        for (int i = 0; i < examResults.length; i++) {
            wrongAnswer += Integer.parseInt(examResults[i].getWrong());
        }
        for (int i = 0; i < examResults.length; i++) {
            correctAnswer += Integer.parseInt(examResults[i].getCorrect());
        }
        for (int i = 0; i < examResults.length; i++) {
            notAnswred += Integer.parseInt(examResults[i].getNotAnswred());
        }

        setAnimation(holder.date.getContext(),holder.itemView,position);

        Intent intent1 = new Intent(ACTION_TOTAL_QUESTIONS_CHANGED);
        intent1.putExtra("totalQuestions", totalQuestions);
        intent1.putExtra("wrongAnswer", wrongAnswer);
        intent1.putExtra("correctAnswer", correctAnswer);
        intent1.putExtra("notAnswred", notAnswred);


        holder.date.getContext().sendBroadcast(intent1);


        String date = examResults[position].getDate();
        String total = examResults[position].getTotal();
        String correct = examResults[position].getCorrect();
        String wrong = examResults[position].getWrong();
        String marks = examResults[position].getMark();

        String totalIA = examResults[position].getTotalIA();
        String correctIA = examResults[position].getCorrectIA();
        String wrongIA = examResults[position].getWrongIA();
        String marksIA = examResults[position].getMarksIA();

        String totalBA = examResults[position].getTotalBA();
        String correctBA = examResults[position].getCorrectBA();
        String wrongBA = examResults[position].getWrongBA();
        String marksBA = examResults[position].getMarksBA();

        String totalB = examResults[position].getTotalB();
        String correctB = examResults[position].getCorrectB();
        String wrongB = examResults[position].getWrongB();
        String marksB = examResults[position].getMarksB();

        String totalMAV = examResults[position].getTotalMAV();
        String correctMAV = examResults[position].getCorrectMAV();
        String wrongMAV = examResults[position].getWrongMAV();
        String marksMAV = examResults[position].getMarksMAV();

        String totalG = examResults[position].getTotalG();
        String correctG = examResults[position].getCorrectG();
        String wrongG = examResults[position].getWrongG();
        String marksG = examResults[position].getMarksG();

        String totalML = examResults[position].getTotalML();
        String correctML = examResults[position].getCorrectML();
        String wrongML = examResults[position].getWrongML();
        String marksML = examResults[position].getMarksML();

        String totalEL = examResults[position].getTotalEL();
        String correctEL = examResults[position].getCorrectEL();
        String wrongEL = examResults[position].getWrongEL();
        String marksEL = examResults[position].getMarksEL();

        String totalMS = examResults[position].getTotalMS();
        String correctMS = examResults[position].getCorrectMS();
        String wrongMS = examResults[position].getWrongMS();
        String marksMS = examResults[position].getMarksMS();

        String totalGS = examResults[position].getTotalGS();
        String correctGS = examResults[position].getCorrectGS();
        String wrongGS = examResults[position].getWrongGS();
        String marksGS = examResults[position].getMarksGS();

        String totalICT = examResults[position].getTotalICT();
        String correctICT = examResults[position].getCorrectICT();
        String wrongICT = examResults[position].getWrongICT();
        String marksICT = examResults[position].getMarksICT();


        int time = Integer.parseInt(examResults[position].getTotal()) / 2;


        holder.examDetails.setText("(Overall " + total + " question and " + String.valueOf(time) + " minutes)");

        holder.marks.setText("Marks:" + marks);
        holder.date.setText("Date:" + date);


        holder.cvResultLayout.setOnClickListener(view -> {


            Intent intent = new Intent(holder.date.getContext(), TestResult.class);
            intent.putExtra("date", date);
            intent.putExtra("total", total);
            intent.putExtra("correct", correct);
            intent.putExtra("wrong", wrong);
            intent.putExtra("marks", marks);

            intent.putExtra("totalIA", totalIA);
            intent.putExtra("correctIA", correctIA);
            intent.putExtra("wrongIA", wrongIA);
            intent.putExtra("marksIA", marksIA);

            intent.putExtra("totalBA", totalBA);
            intent.putExtra("correctBA", correctBA);
            intent.putExtra("wrongBA", wrongBA);
            intent.putExtra("marksBA", marksBA);

            intent.putExtra("totalB", totalB);
            intent.putExtra("correctB", correctB);
            intent.putExtra("wrongB", wrongB);
            intent.putExtra("marksB", marksB);

            intent.putExtra("totalMAV", totalMAV);
            intent.putExtra("correctMAV", correctMAV);
            intent.putExtra("wrongMAV", wrongMAV);
            intent.putExtra("marksMAV", marksMAV);

            intent.putExtra("totalG", totalG);
            intent.putExtra("correctG", correctG);
            intent.putExtra("wrongG", wrongG);
            intent.putExtra("marksG", marksG);

            intent.putExtra("totalML", totalML);
            intent.putExtra("correctML", correctML);
            intent.putExtra("wrongML", wrongML);
            intent.putExtra("marksML", marksML);

            intent.putExtra("totalEL", totalEL);
            intent.putExtra("correctEL", correctEL);
            intent.putExtra("wrongEL", wrongEL);
            intent.putExtra("marksEL", marksEL);

            intent.putExtra("totalMS", totalMS);
            intent.putExtra("correctMS", correctMS);
            intent.putExtra("wrongMS", wrongMS);
            intent.putExtra("marksMS", marksMS);

            intent.putExtra("totalGS", totalGS);
            intent.putExtra("correctGS", correctGS);
            intent.putExtra("wrongGS", wrongGS);
            intent.putExtra("marksGS", marksGS);

            intent.putExtra("totalICT", totalICT);
            intent.putExtra("correctICT", correctICT);
            intent.putExtra("wrongICT", wrongICT);
            intent.putExtra("marksICT", marksICT);


            view.getContext().startActivity(intent);


        });


    }

    @Override
    public int getItemCount() {
        return examResults.length;
    }


    class myviewholder extends RecyclerView.ViewHolder {
        TextView total, correct, examDetails, marks, date;
        CardView cvResultLayout;


        public myviewholder(@NonNull View itemView) {
            super(itemView);

            cvResultLayout = itemView.findViewById(R.id.resultLayoutCV);

//            total = itemView.findViewById(R.id.totalTv);
//            correct = itemView.findViewById(R.id.correctTv);
//            wrong =itemView.findViewById(R.id.wrongTv);

            date = itemView.findViewById(R.id.tvDate);
            marks = itemView.findViewById(R.id.marksTv);
            examDetails = itemView.findViewById(R.id.examDetails);

        }
    }


    public void shareResult(Context context, String date, String total, String correct) {
        // Create an intent to share text
        Intent intent = new Intent(context, TestResult.class);

        intent.putExtra("MESSAGE", total);

        context.startActivity(intent);

        // Verify that there is an app available to receive the intent

    }

    public void setAnimation(Context ctx, View viewToAnimate, int position) {

            Animation slideIn = AnimationUtils.loadAnimation(ctx, android.R.anim.fade_in);
            viewToAnimate.setAnimation(slideIn);
            lastPosition = position;

    }
}