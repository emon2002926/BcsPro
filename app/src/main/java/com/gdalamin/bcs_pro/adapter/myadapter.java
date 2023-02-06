package com.gdalamin.bcs_pro.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.modelClass.QuestionList;
import com.gdalamin.bcs_pro.modelClass.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
{
    model data[];

    public myadapter(model[] data) {
        this.data = data;
    }


    Context context;

   static int userSelectedOption = 0;

    private final List<QuestionList> questionslists = new ArrayList<>();


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mcq_layout,parent,false);

         return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, @SuppressLint("RecyclerView") final int position) {


        SharedPreferences sharedPreferences = holder.explainTv.getContext().getSharedPreferences("totalQuestion", Context.MODE_PRIVATE);
        String valueString = sharedPreferences.getString("examQuestionNum", "");




        if (!valueString.isEmpty()){
            ///for All exam
            int MAX_QUESTION = Integer.parseInt(valueString);
            Log.d("examQuestionNum",valueString);

            if (position >=MAX_QUESTION){

                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }
            else
            {

                //set question to string
                String question = data[position].getQuestion();
                String option1 = data[position].getOption1();
                String option2 = data[position].getOption2();
                String option3 = data[position].getOption3();
                String  option4 = data[position].getOption4();
                String explain = data[position].getExplanation();
                String ansr = data[position].getAnswer();
                int answer = Integer.parseInt(ansr);


                QuestionList questionList = new QuestionList(question,option1,option2,option3,option4,answer);
                questionslists.add(questionList);

                String questiont = questionslists.get(position).getQuestion().trim();
                //checking  question is aviable ,or have to show the Image
                if (!questiont.isEmpty())
                {
                    holder.questionTv.setText(question);
                    holder.questionImg.setVisibility(View.GONE);
                }else {

                    holder.questionTv.setVisibility(View.GONE);
                    Glide.with(holder.questionImg.getContext()).load("http://192.168.0.104/api/images/"+data[position].getImage()).into(holder.questionImg);
                    holder.questionImg.setVisibility(View.VISIBLE);
                }

                holder.option1TV.setText(questionslists.get(position).getOption1().trim());
                holder.option2TV.setText(questionslists.get(position).getOption2().trim());
                holder.option3TV.setText(questionslists.get(position).getOption4().trim());
                holder.option4TV.setText(questionslists.get(position).getOption4().trim());


                holder.explainTv.setText(explain);


                Intent intent = new Intent("my_list_action");

                intent.putExtra("my_list_key", (Serializable) questionslists);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);



                holder.option1Layout.setOnClickListener(view -> {

                    holder.layoutExplain.setVisibility(View.VISIBLE);

                    selectedOption(holder.option1Layout,holder.img1);
                    holder.option2Layout.setEnabled(false);
                    holder.option3Layout.setEnabled(false);
                    holder.option4Layout.setEnabled(false);

                    questionslists.get(position).setUserSelecedAnswer(1);

                });

                holder.option2Layout.setOnClickListener(view -> {

                    holder.layoutExplain.setVisibility(View.VISIBLE);

                    questionslists.get(position).setUserSelecedAnswer(2);

//            holder.layoutExplain.setVisibility(View.VISIBLE);
//            disableOtherOption(holder.option1Layout,holder.option3Layout,holder.option4Layout);
//            selectedRightOpti(answer,holder.option1Layout,holder.option2Layout,holder.option3Layout,holder.option4Layout);
//            if (answer!=2){
//
                    selectedOption(holder.option2Layout,holder.img2);

                    holder.option1Layout.setEnabled(false);
                    holder.option3Layout.setEnabled(false);
                    holder.option4Layout.setEnabled(false);
//
//            }

                });

                holder.option3Layout.setOnClickListener(view -> {

                    holder.layoutExplain.setVisibility(View.VISIBLE);
                    questionslists.get(position).setUserSelecedAnswer(3);



                    selectedOption(holder.option3Layout,holder.img3);
                    holder.option2Layout.setEnabled(false);
                    holder.option1Layout.setEnabled(false);
                    holder.option4Layout.setEnabled(false);


                });
                holder.option4Layout.setOnClickListener(view -> {

                    holder.layoutExplain.setVisibility(View.VISIBLE);

                    questionslists.get(position).setUserSelecedAnswer(2);


                    holder.layoutExplain.setVisibility(View.VISIBLE);
//
//            selectedRightOpti(answer,holder.option1Layout,holder.option2Layout,holder.option3Layout,holder.option4Layout);
//            if (answer!=4){
//
                    selectedOption(holder.option4Layout,holder.img4);
                    holder.option2Layout.setEnabled(false);
                    holder.option3Layout.setEnabled(false);
                    holder.option1Layout.setEnabled(false);
//
//            }
                });


            }


        }
        //for important Question
        else {
            //set question to string
            String question = data[position].getQuestion();
            String option1 = data[position].getOption1();
            String option2 = data[position].getOption2();
            String option3 = data[position].getOption3();
            String  option4 = data[position].getOption4();
            String explain = data[position].getExplanation();
            String ansr = data[position].getAnswer();
            int answer = Integer.parseInt(ansr);


            QuestionList questionList = new QuestionList(question,option1,option2,option3,option4,answer);
            questionslists.add(questionList);

            String questiont = questionslists.get(position).getQuestion().trim();
            //checking  question is aviable ,or have to show the Image
            if (!questiont.isEmpty())
            {
                holder.questionTv.setText(question);
                holder.questionImg.setVisibility(View.GONE);
            }else {

                holder.questionTv.setVisibility(View.GONE);
                Glide.with(holder.questionImg.getContext()).load("http://192.168.0.104/api/images/"+data[position].getImage()).into(holder.questionImg);
                holder.questionImg.setVisibility(View.VISIBLE);
            }

            holder.option1TV.setText(questionslists.get(position).getOption1().trim());
            holder.option2TV.setText(questionslists.get(position).getOption2().trim());
            holder.option3TV.setText(questionslists.get(position).getOption4().trim());
            holder.option4TV.setText(questionslists.get(position).getOption4().trim());


            holder.explainTv.setText(explain);


            Intent intent = new Intent("my_list_action");

            intent.putExtra("my_list_key", (Serializable) questionslists);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);



            holder.option1Layout.setOnClickListener(view -> {

                holder.layoutExplain.setVisibility(View.VISIBLE);

                selectedOption(holder.option1Layout,holder.img1);
                holder.option2Layout.setEnabled(false);
                holder.option3Layout.setEnabled(false);
                holder.option4Layout.setEnabled(false);

                questionslists.get(position).setUserSelecedAnswer(1);

            });

            holder.option2Layout.setOnClickListener(view -> {

                holder.layoutExplain.setVisibility(View.VISIBLE);

                questionslists.get(position).setUserSelecedAnswer(2);

//            holder.layoutExplain.setVisibility(View.VISIBLE);
//            disableOtherOption(holder.option1Layout,holder.option3Layout,holder.option4Layout);
//            selectedRightOpti(answer,holder.option1Layout,holder.option2Layout,holder.option3Layout,holder.option4Layout);
//            if (answer!=2){
//
                selectedOption(holder.option2Layout,holder.img2);

                holder.option1Layout.setEnabled(false);
                holder.option3Layout.setEnabled(false);
                holder.option4Layout.setEnabled(false);
//
//            }

            });

            holder.option3Layout.setOnClickListener(view -> {

                holder.layoutExplain.setVisibility(View.VISIBLE);
                questionslists.get(position).setUserSelecedAnswer(3);

//            disableOtherOption(holder.option2Layout,holder.option1Layout,holder.option4Layout);
//            selectedRightOpti(answer,holder.option1Layout,holder.option2Layout,holder.option3Layout,holder.option4Layout);
//            if (answer!=3){
//
                selectedOption(holder.option3Layout,holder.img3);
                holder.option2Layout.setEnabled(false);
                holder.option1Layout.setEnabled(false);
                holder.option4Layout.setEnabled(false);
//
//            }

            });
            holder.option4Layout.setOnClickListener(view -> {

                holder.layoutExplain.setVisibility(View.VISIBLE);
//            disableOtherOption(holder.option2Layout,holder.option3Layout,holder.option1Layout);
                questionslists.get(position).setUserSelecedAnswer(2);


                holder.layoutExplain.setVisibility(View.VISIBLE);
//
//            selectedRightOpti(answer,holder.option1Layout,holder.option2Layout,holder.option3Layout,holder.option4Layout);
//            if (answer!=4){
//
                selectedOption(holder.option4Layout,holder.img4);
                holder.option2Layout.setEnabled(false);
                holder.option3Layout.setEnabled(false);
                holder.option1Layout.setEnabled(false);
//
//            }
            });

        }



    }



    @Override
    public int getItemCount() {
        return data.length;
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img1,img2,img3,img4,questionImg;

        RelativeLayout option1Layout,option2Layout,option3Layout,option4Layout;
        LinearLayout layoutExplain,fullLayout;

         TextView option1TV , option2TV , option3TV , option4TV,questionTv,explainTv;

        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            questionTv=itemView.findViewById(R.id.questionTv);
            option1TV=itemView.findViewById(R.id.option1Tv);
            option2TV=itemView.findViewById(R.id.option2Tv);
            option3TV=itemView.findViewById(R.id.option3Tv);
            option4TV=itemView.findViewById(R.id.option4Tv);

            explainTv = itemView.findViewById(R.id.tvExplain);

            img1 = itemView.findViewById(R.id.option1Icon);
            img2 = itemView.findViewById(R.id.option2Icon);
            img3 = itemView.findViewById(R.id.option3Icon);
            img4 = itemView.findViewById(R.id.option4Icon);
            questionImg = itemView.findViewById(R.id.questionIv);

            fullLayout = itemView.findViewById(R.id.fullLayout);
            layoutExplain = itemView.findViewById(R.id.layoutExplain);

            option1Layout = itemView.findViewById(R.id.option1Layout);
            option2Layout = itemView.findViewById(R.id.option2Layout);
            option3Layout = itemView.findViewById(R.id.option3Layout);
            option4Layout = itemView.findViewById(R.id.opton4Layout);

        }



    }

    private void disableOtherOption(RelativeLayout selectedOptionLayout ,RelativeLayout selected2OptionLayout
            ,RelativeLayout selected3OptionLayout ) {
        selectedOptionLayout.setEnabled(false);
        selected2OptionLayout.setEnabled(false);
        selected3OptionLayout.setEnabled(false);
    }


//    private void selectedWrongOption(RelativeLayout selectedOptionLayout,ImageView selectedOptionIcon){
//        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_red50_10);
//        selectedOptionIcon.setImageResource(R.drawable.cross);
//
//    }

    private void selectedRightOpti(int answer ,RelativeLayout selectedOptionLayout,RelativeLayout selected2OptionLayout
            ,RelativeLayout selected3OptionLayout,RelativeLayout selected4OptionLayout){


        if (answer==1){
            selectedOptionLayout.setBackgroundResource(R.drawable.round_back_green50_10);
        }
        if (answer==2){
            selected2OptionLayout.setBackgroundResource(R.drawable.round_back_green50_10);
        }if (answer ==3){
            selected3OptionLayout.setBackgroundResource(R.drawable.round_back_green50_10);
        }if (answer ==4){
            selected4OptionLayout.setBackgroundResource(R.drawable.round_back_green50_10);
        }


    }


    private void selectedOption(RelativeLayout selectedOptionLayout , ImageView selectedOptionIcon) {

        selectedOptionIcon.setImageResource(R.drawable.chack);
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_selected_option);

    }
public void intent(){

    Intent intent = new Intent();
    Bundle bundle = new Bundle();
    bundle.putSerializable("qutions",(Serializable) questionslists);
    intent.putExtras(bundle);
}

    public interface RecyclerViewDataPass {
        public void pass(ArrayList questionList);
    }


}
