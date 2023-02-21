package com.gdalamin.bcs_pro.adapter;

import static android.content.Context.MODE_PRIVATE;

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
import android.widget.ProgressBar;
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
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
{
    model data[];

    ProgressBar progressBar;

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




        SharedPreferences sharedPreferences = holder.explainTv.getContext().getSharedPreferences("totalQuestion", MODE_PRIVATE);
        int NUM_OF_QUESTION = sharedPreferences.getInt("examQuestionNum", 0);

        Log.d("examQuestionNum",String.valueOf(NUM_OF_QUESTION));

        holder.option1Layout.setBackgroundResource(0);
        holder.img1.setImageResource(R.drawable.round_back_white50_100);
        holder.option2Layout.setBackgroundResource(0);
        holder.img2.setImageResource(R.drawable.round_back_white50_100);
        holder.option3Layout.setBackgroundResource(0);
        holder.img3.setImageResource(R.drawable.round_back_white50_100);
        holder.option4Layout.setBackgroundResource(0);
        holder.img4.setImageResource(R.drawable.round_back_white50_100);
        holder.option1Layout.setEnabled(true);
        holder.option2Layout.setEnabled(true);
        holder.option3Layout.setEnabled(true);
        holder.option4Layout.setEnabled(true);
        holder.layoutExplain.setVisibility(View.GONE);


            if (NUM_OF_QUESTION >=1){

                ///for All exam
//            int MAX_QUESTION = Integer.parseInt(NUM_OF_QUESTION);


                if (position >=NUM_OF_QUESTION){

                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }
                else
                {

                    // Get the question and options data from the data array at the given position




                    String question = data[position].getQuestion();
                    String option1 = data[position].getOption1();
                    String option2 = data[position].getOption2();
                    String option3 = data[position].getOption3();
                    String option4 = data[position].getOption4();
                    int answer = Integer.parseInt(data[position].getAnswer());



                    // Create a new QuestionList object with the obtained data
                    QuestionList questionList = new QuestionList(question, option1, option2, option3, option4, answer);
                    questionslists.add(questionList);

                    String questionPosition = String.valueOf(position+1);


                    holder.questionTv.setText(questionPosition+") "+question);
                    holder.questionImg.setVisibility(View.GONE);

                    // If the question text is empty, hide the text view and display the question image
                    if (question.trim().isEmpty()) {


                        holder.questionTv.setVisibility(View.GONE);
                        holder.questionImageLayout.setVisibility(View.VISIBLE);
                        Glide.with(holder.questionImg.getContext()).load("http://192.168.0.104/api/images/" + data[position].getImage()).into(holder.questionImg);
                        holder.questionImg.setVisibility(View.VISIBLE);
                    }

                    // Set the text of the options to their respective text views

                    holder.option1TV.setText(option1.trim());
                    holder.option2TV.setText(option2.trim());
                    holder.option3TV.setText(option3.trim());
                    holder.option4TV.setText(option4.trim());

                    // Use LocalBroadcastManager to send the broadcast


                    if (questionslists.get(position).getUserSelecedAnswer() > 0) {
                        // If the question has a selected option, highlight the corresponding option
                        switch (questionslists.get(position).getUserSelecedAnswer()) {
                            case 1:
                                selectedOption(holder.option1Layout, holder.img1);
                                break;
                            case 2:
                                selectedOption(holder.option2Layout, holder.img2);
                                break;
                            case 3:
                                selectedOption(holder.option3Layout, holder.img3);
                                break;
                            case 4:
                                selectedOption(holder.option4Layout, holder.img4);
                                break;
                        }
                    }


                    View.OnClickListener optionClickListener = view -> {
                        int selectedOption = 0;
                        ImageView img = null;
                        // Determine which option was clicked based on the view that was clicked
                        if (view == holder.option1Layout) {
                            selectedOption = 1;
                            img = holder.img1;
                        } else if (view == holder.option2Layout) {
                            selectedOption = 2;
                            img = holder.img2;
                        } else if (view == holder.option3Layout) {
                            selectedOption = 3;
                            img = holder.img3;
                        } else if (view == holder.option4Layout) {
                            selectedOption = 4;
                            img = holder.img4;
                        }


                        questionslists.get(position).setUserSelecedAnswer(selectedOption);
                        selectedOption(view, img);
                        holder.option1Layout.setEnabled(false);
                        holder.option2Layout.setEnabled(false);
                        holder.option3Layout.setEnabled(false);
                        holder.option4Layout.setEnabled(false);


                        Intent intent = new Intent("my_list_action");
                        intent.putExtra("my_list_key", (Serializable) questionslists);
                        intent.putExtra("totalQuestion", NUM_OF_QUESTION);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                    };

                    holder.option1Layout.setOnClickListener(optionClickListener);
                    holder.option2Layout.setOnClickListener(optionClickListener);
                    holder.option3Layout.setOnClickListener(optionClickListener);
                    holder.option4Layout.setOnClickListener(optionClickListener);



                }


            }

            //for important Question or Other Activitys
            else
            {



                // Get the question and options data from the data array at the given position

                String question = data[position].getQuestion();
                String option1 = data[position].getOption1();
                String option2 = data[position].getOption2();
                String option3 = data[position].getOption3();
                String  option4 = data[position].getOption4();
                String explain = data[position].getExplanation();

                String questionPosition = String.valueOf(position+1);
                holder.questionTv.setText(questionPosition+") "+question);
                holder.option1TV.setText(option1.trim());
                holder.option2TV.setText(option2.trim());
                holder.option3TV.setText(option3.trim());
                holder.option4TV.setText(option4.trim());
                holder.explainTv.setText(explain);

                // Check if question is empty, if not, display text. If yes, display image
                if (!question.isEmpty()) {
                    holder.questionImg.setVisibility(View.GONE);
                    holder.questionTv.setVisibility(View.VISIBLE);
                } else {
                    holder.questionTv.setVisibility(View.GONE);
                    holder.questionImageLayout.setVisibility(View.VISIBLE);
                    Glide.with(holder.questionImg.getContext()).load("http://192.168.0.104/api/images/" + data[position].getImage()).into(holder.questionImg);
                    holder.questionImg.setVisibility(View.VISIBLE);
                }

                // OnClickListeners for each option
                View.OnClickListener optionClickListener = view -> {
                    holder.layoutExplain.setVisibility(View.VISIBLE);

                    switch (view.getId()) {
                        case R.id.option1Layout:
                            holder.option1Layout.setBackgroundResource(R.drawable.round_back_selected_option);
                            holder.img1.setImageResource(R.drawable.chack);
                            break;
                        case R.id.option2Layout:
                            holder.option2Layout.setBackgroundResource(R.drawable.round_back_selected_option);
                            holder.img2.setImageResource(R.drawable.chack);
                            break;
                        case R.id.option3Layout:
                            holder.option3Layout.setBackgroundResource(R.drawable.round_back_selected_option);
                            holder.img3.setImageResource(R.drawable.chack);
                            break;
                        case R.id.opton4Layout:
                            holder.option4Layout.setBackgroundResource(R.drawable.round_back_selected_option);
                            holder.img4.setImageResource(R.drawable.chack);
                            break;
                    }

                    holder.option1Layout.setEnabled(false);
                    holder.option2Layout.setEnabled(false);
                    holder.option3Layout.setEnabled(false);
                    holder.option4Layout.setEnabled(false);
                };

                holder.option1Layout.setOnClickListener(optionClickListener);
                holder.option2Layout.setOnClickListener(optionClickListener);
                holder.option3Layout.setOnClickListener(optionClickListener);
                holder.option4Layout.setOnClickListener(optionClickListener);

            }

        //for Exam Activity


    }



    @Override
    public int getItemCount() {
        return data.length;
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img1,img2,img3,img4,questionImg;

        RelativeLayout option1Layout,option2Layout,option3Layout,option4Layout;
        LinearLayout layoutExplain,fullLayout,questionImageLayout;

         TextView option1TV , option2TV , option3TV , option4TV,questionTv,explainTv,textViewPosition;

        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            textViewPosition = itemView.findViewById(R.id.textViewPosition);
            questionImageLayout = itemView.findViewById(R.id.questionIvLayout);

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

            progressBar = itemView.findViewById(R.id.progressBar4);

        }



    }




    private void selectedOption(View selectedOptionLayout , ImageView selectedOptionIcon) {






        selectedOptionIcon.setImageResource(R.drawable.chack);
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_selected_option);

    }
public void intent(){

    Intent intent = new Intent();
    Bundle bundle = new Bundle();
    bundle.putSerializable("qutions",(Serializable) questionslists);
    intent.putExtras(bundle);
}


}
