package com.gdalamin.bcs_pro.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gdalamin.bcs_pro.Activity.ActivityExam;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.api.SharedPreferencesManagerAppLogic;
import com.gdalamin.bcs_pro.modelClass.QuestionList;
import com.gdalamin.bcs_pro.modelClass.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
{
    model data[];

    ProgressBar progressBar;
    private boolean mBooleanValue;


    public myadapter(model[] data,boolean mBooleanValue) {
        this.data = data;
        this.mBooleanValue = mBooleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        mBooleanValue = booleanValue;
        notifyDataSetChanged();
    }


    Context context;


    private final List<QuestionList> questionslists = new ArrayList<>();

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mcq_layout,parent,false);

        return new myviewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, @SuppressLint("RecyclerView") final int position) {

        String BASE_URL = ApiKeys.API_URL+"api/";
        Context ctx = holder.fullLayout.getContext();

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

        holder.option1TV.setTextColor(ContextCompat.getColor(ctx, R.color.black));
        holder.option2TV.setTextColor(ContextCompat.getColor(ctx, R.color.black));
        holder.option3TV.setTextColor(ContextCompat.getColor(ctx, R.color.black));
        holder.option4TV.setTextColor(ContextCompat.getColor(ctx, R.color.black));
        holder.explainTv.setVisibility(View.GONE);

        RelativeLayout option1Layout = holder.option1Layout;
        RelativeLayout option2Layout = holder.option2Layout;
        RelativeLayout option3Layout = holder.option3Layout;
        RelativeLayout option4Layout = holder.option4Layout;


        String questionImageString = data[position].getImage().trim();
        String option1ImageString = data[position].getOption1Image().trim();
        String option2ImageString = data[position].getOption2Image().trim();
        String option3ImageString = data[position].getOption3Image().trim();
        String option4ImageString = data[position].getOption4Image().trim();
        String explainImageString = data[position].getExplanationImage();


        String question = data[position].getQuestion().trim();
        String option1 = data[position].getOption1().trim();
        String option2 = data[position].getOption2().trim();
        String option3 = data[position].getOption3().trim();
        String option4 = data[position].getOption4().trim();
        int answer = Integer.parseInt(data[position].getAnswer());



        SharedPreferencesManagerAppLogic preferencesManager = new SharedPreferencesManagerAppLogic(holder.explainTv.getContext());
        int NUM_OF_QUESTION = preferencesManager.getInt("examQuestionNum");
        int LOGIC_FOR_ALL_SUBJECT_EXAM = preferencesManager.getInt("LogicForExam");


        if (LOGIC_FOR_ALL_SUBJECT_EXAM >=1){

            if (position >=NUM_OF_QUESTION){

                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }
            else
            {

                // Get the question and options data from the data array at the given position


                // Create a new QuestionList object with the obtained data
                QuestionList questionList = new QuestionList(question, option1, option2, option3, option4,questionImageString,option1ImageString,option2ImageString
                        ,option3ImageString,option4ImageString, answer);
                questionslists.add(questionList);

                // Set the text of the options to their respective text views
                holder.textViewPosition2.setText(position+") ");
                holder.questionTv.setText(convertToUTF8(question));
                holder.option1TV.setText(convertToUTF8(option1));
                holder.option2TV.setText(convertToUTF8(option2));
                holder.option3TV.setText(convertToUTF8(option3));
                holder.option4TV.setText(convertToUTF8(option4));

                // If the question text is empty, hide the text view and display the question image
                if (!question.isEmpty()) {
                    holder.questionImg.setVisibility(View.GONE);
                    holder.questionTv.setVisibility(View.VISIBLE);
                    holder.textViewPosition.setVisibility(View.GONE);
                    holder.textViewPosition2.setVisibility(View.VISIBLE);
                } else {
                    holder.textViewPosition2.setVisibility(View.GONE);
                    holder.questionTv.setVisibility(View.GONE);
                    holder.questionImageLayout.setVisibility(View.VISIBLE);
                    Bitmap bitmap = convertBase64ToBitmap(questionImageString);
                    holder.questionImg.setImageBitmap(bitmap);
                    holder.questionImg.setVisibility(View.VISIBLE);
                    holder.textViewPosition.setVisibility(View.VISIBLE);
                    holder.textViewPosition.setText(position+")");
                }

                //Showing Image or text with there respective logic
                showTextViewOrImageView(option1,holder.option1TV,holder.option1Image,option1ImageString);
                showTextViewOrImageView(option2,holder.option2TV,holder.option2Image,option2ImageString);
                showTextViewOrImageView(option3,holder.option3TV,holder.option3Image,option3ImageString);
                showTextViewOrImageView(option4,holder.option4TV,holder.option4Image,option4ImageString);

                if (questionslists.get(position).getUserSelecedAnswer() > 0) {
//                        holder.fullLayout.setEnabled(false);
                    int userSelectedAnswer = questionslists.get(position).getUserSelecedAnswer();
                    if (userSelectedAnswer == 1) {
                        holder.option2Layout.setEnabled(false);
                        holder.option3Layout.setEnabled(false);
                        holder.option4Layout.setEnabled(false);

                        highLightClickedOption(holder.option1Layout,holder.img1);

                        makeGrayTextView(ctx,holder.option2TV,holder.option3TV,holder.option4TV);
                        makeGrayBackground(holder.iconBackground2,holder.iconBackground3,holder.iconBackground4);
                    }
                    else if (userSelectedAnswer ==2) {
                        holder.option1Layout.setEnabled(false);
                        holder.option3Layout.setEnabled(false);
                        holder.option4Layout.setEnabled(false);
                        highLightClickedOption(holder.option2Layout,holder.img2);

                        makeGrayTextView(ctx,holder.option1TV,holder.option3TV,holder.option4TV);
                        makeGrayBackground(holder.iconBackground1,holder.iconBackground3,holder.iconBackground4);
                    }
                    else if (userSelectedAnswer == 3) {
                        holder.option1Layout.setEnabled(false);
                        holder.option2Layout.setEnabled(false);
                        holder.option4Layout.setEnabled(false);

                        highLightClickedOption(holder.option3Layout,holder.img3);

                        makeGrayTextView(ctx,holder.option1TV,holder.option2TV,holder.option4TV);
                        makeGrayBackground(holder.iconBackground1,holder.iconBackground2,holder.iconBackground4);

                    }
                    else if (userSelectedAnswer == 4) {
                        holder.option1Layout.setEnabled(false);
                        holder.option3Layout.setEnabled(false);
                        holder.option2Layout.setEnabled(false);

                        highLightClickedOption(holder.option4Layout,holder.img4);

                        makeGrayTextView(ctx,holder.option1TV,holder.option2TV,holder.option3TV);
                        makeGrayBackground(holder.iconBackground1,holder.iconBackground2,holder.iconBackground3);

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


                    // Change text color of all options to default
                    holder.option1TV.setTextColor(ContextCompat.getColor(ctx, R.color.GreyText));
                    makeGrayTextView(ctx,holder.option2TV,holder.option3TV,holder.option4TV);

                    holder.iconBackground1.setBackgroundResource(R.drawable.gray_baground);

                    makeGrayBackground(holder.iconBackground2,holder.iconBackground3,holder.iconBackground4);


                    // Change text color of selected option
                    switch (selectedOption) {
                        case 1:
                            holder.option1TV.setTextColor(ContextCompat.getColor(ctx, R.color.black));
                            break;
                        case 2:
                            holder.option2TV.setTextColor(ContextCompat.getColor(ctx, R.color.black));
                            break;
                        case 3:
                            holder.option3TV.setTextColor(ContextCompat.getColor(ctx, R.color.black));
                            break;
                        case 4:
                            holder.option4TV.setTextColor(ContextCompat.getColor(ctx, R.color.black));
                            break;
                    }

                    questionslists.get(position).setUserSelecedAnswer(selectedOption);
                    highLightClickedOption(view, img);
                    holder.option1Layout.setEnabled(false);
                    holder.option2Layout.setEnabled(false);
                    holder.option3Layout.setEnabled(false);
                    holder.option4Layout.setEnabled(false);




                    Intent intent = new Intent(ctx, ActivityExam.class);
                    intent.setAction("xy@4gfk@9*2cxlds&0k@#hLAnsx!");
                    intent.putExtra("xy@4gfk@9*2cxlds&0k@#hLAnsx!", (Serializable) questionslists);
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

            String explain = data[position].getExplanation().trim();
            String question2 = data[position].getQuestion().trim();


            String questionPosition = String.valueOf(position+1);


            holder.option1TV.setTextColor(ContextCompat.getColor(ctx, R.color.black));
            holder.option2TV.setTextColor(ContextCompat.getColor(ctx, R.color.black));
            holder.option3TV.setTextColor(ContextCompat.getColor(ctx, R.color.black));
            holder.option4TV.setTextColor(ContextCompat.getColor(ctx, R.color.black));


            holder.rightOrWrongImg1.setImageResource(0);
            holder.rightOrWrongImg2.setImageResource(0);
            holder.rightOrWrongImg3.setImageResource(0);
            holder.rightOrWrongImg4.setImageResource(0);

            holder.iconBackground1.setBackgroundResource(R.drawable.round_back_white50_100);
            holder.iconBackground2.setBackgroundResource(R.drawable.round_back_white50_100);
            holder.iconBackground3.setBackgroundResource(R.drawable.round_back_white50_100);
            holder.iconBackground4.setBackgroundResource(R.drawable.round_back_white50_100);


            holder.textViewPosition2.setText(questionPosition+") ");
            holder.questionTv.setText(convertToUTF8(question));


            holder.option1TV.setText(convertToUTF8(option1));
            holder.option2TV.setText(convertToUTF8(option2));
            holder.option3TV.setText(convertToUTF8(option3));
            holder.option4TV.setText(convertToUTF8(option4));
            holder.explainTv.setText(convertToUTF8(explain));

            // Check if question is empty, if not, display text. If yes, display image
            if (!question.isEmpty()) {
                holder.questionImg.setVisibility(View.GONE);
                holder.questionTv.setVisibility(View.VISIBLE);
                holder.questionTv.setText(convertToUTF8(question));
                holder.textViewPosition.setVisibility(View.GONE);
                holder.textViewPosition2.setVisibility(View.VISIBLE);
            }
            else {
                holder.textViewPosition2.setVisibility(View.GONE);
                holder.questionTv.setVisibility(View.GONE);
                holder.questionImageLayout.setVisibility(View.VISIBLE);
                Bitmap bitmap = convertBase64ToBitmap(questionImageString);
                holder.questionImg.setImageBitmap(bitmap);
                holder.questionImg.setVisibility(View.VISIBLE);
                holder.textViewPosition.setVisibility(View.VISIBLE);
                holder.textViewPosition.setText(questionPosition+")");
            }



            //Todo  work from hare

            //Showing Image or text with there respective logic
            showTextViewOrImageView(option1,holder.option1TV,holder.option1Image,option1ImageString);
            showTextViewOrImageView(option2,holder.option2TV,holder.option2Image,option2ImageString);
            showTextViewOrImageView(option3,holder.option3TV,holder.option3Image,option3ImageString);
            showTextViewOrImageView(option4,holder.option4TV,holder.option4Image,option4ImageString);

            QuestionList questionList = new QuestionList(question, option1, option2, option3, option4,questionImageString,option1ImageString
                    ,option2ImageString,option3ImageString,option4ImageString,answer);
            questionslists.add(questionList);




            // When hide or  unHide button was clicked this if else function will activate

            if (mBooleanValue == true){

                if (answer == 1){
                    highLightClickedOption2(holder.option1Layout,holder.option2Layout,holder.option3Layout
                            ,holder.option4Layout,holder.option1TV);

                    makeGrayTextView(ctx,holder.option2TV,holder.option3TV,holder.option4TV);
                    makeGrayBackground(holder.iconBackground2,holder.iconBackground3,holder.iconBackground4);
                    holder.iconBackground1.setBackgroundResource(R.drawable.gray_baground);
                }
                else if (answer ==2) {

                    highLightClickedOption2(holder.option2Layout,holder.option1Layout
                            ,holder.option3Layout,holder.option4Layout,holder.option2TV);
                    makeGrayTextView(ctx,holder.option1TV,holder.option3TV,holder.option4TV);
                    makeGrayBackground(holder.iconBackground1,holder.iconBackground3,holder.iconBackground4);
                    holder.iconBackground2.setBackgroundResource(R.drawable.gray_baground);
                }
                else if (answer == 3) {

                    highLightClickedOption2(holder.option3Layout,holder.option1Layout
                            ,holder.option2Layout,holder.option4Layout,holder.option3TV);
                    makeGrayTextView(ctx,holder.option2TV,holder.option1TV,holder.option4TV);
                    makeGrayBackground(holder.iconBackground1,holder.iconBackground2,holder.iconBackground4);
                    holder.iconBackground3.setBackgroundResource(R.drawable.gray_baground);
                }

                else if (answer == 4) {
                    highLightClickedOption2(holder.option4Layout,holder.option1Layout
                            ,holder.option3Layout,holder.option2Layout,holder.option4TV);

                    makeGrayTextView(ctx,holder.option2TV,holder.option1TV,holder.option3TV);
                    makeGrayBackground(holder.iconBackground1,holder.iconBackground2,holder.iconBackground3);
                    holder.iconBackground4.setBackgroundResource(R.drawable.gray_baground);
                }

                showTextViewOrImageView(explain,holder.explainTv,holder.explainImage,explainImageString);




                int userSelecedAnswer = questionList.getUserSelecedAnswer();
                switch (questionslists.get(position).getUserSelecedAnswer()) {
                    case 1:
                        if (userSelecedAnswer == answer){
                            highLightClickedOption(holder.option1Layout, holder.img1);
                        }
                        break;
                    case 2:
                        if (userSelecedAnswer == answer){
                            highLightClickedOption(holder.option2Layout, holder.img2);
                        }
                        break;
                    case 3:
                        if (userSelecedAnswer == answer){
                            highLightClickedOption(holder.option3Layout, holder.img3);
                        }
                        break;
                    case 4:

                        if (userSelecedAnswer == answer){
                            highLightClickedOption(holder.option4Layout, holder.img4);
                        }
                        break;
                }

            }


            if (questionslists.get(position).getUserSelecedAnswer() > 0) {
                // If the question has a selected option, highlight the corresponding option
                showTextViewOrImageView(explain,holder.explainTv,holder.explainImage,explainImageString);

                if (answer == 1)
                {
                    holder.iconBackground1.setBackgroundResource(R.drawable.gray_baground);
                    holder.rightOrWrongImg1.setImageResource(R.drawable.baseline_check_24);
                    holder.rightOrWrongImg1.setVisibility(View.VISIBLE);

                    highLightClickedOption2(option1Layout,option2Layout,option3Layout,option4Layout,holder.option1TV);
                    makeGrayTextView(ctx,holder.option2TV,holder.option3TV,holder.option4TV);
                    makeGrayBackground(holder.iconBackground2,holder.iconBackground3,holder.iconBackground4);

                } else if (answer ==2)
                {

                    holder.iconBackground2.setBackgroundResource(R.drawable.gray_baground);
                    holder.rightOrWrongImg2.setImageResource(R.drawable.baseline_check_24);
                    holder.rightOrWrongImg2.setVisibility(View.VISIBLE);

                    highLightClickedOption2(option2Layout,option1Layout,option3Layout,option4Layout,holder.option2TV);
                    makeGrayTextView(ctx,holder.option1TV,holder.option3TV,holder.option4TV);
                    makeGrayBackground(holder.iconBackground1,holder.iconBackground3,holder.iconBackground4);

                } else if (answer == 3)
                {
                    holder.iconBackground3.setBackgroundResource(R.drawable.gray_baground);
                    holder.rightOrWrongImg3.setImageResource(R.drawable.baseline_check_24);
                    holder.rightOrWrongImg3.setVisibility(View.VISIBLE);
                    highLightClickedOption2(option3Layout,option2Layout,option1Layout,option4Layout,holder.option3TV);
                    makeGrayTextView(ctx,holder.option1TV,holder.option2TV,holder.option4TV);
                    makeGrayBackground(holder.iconBackground1,holder.iconBackground2,holder.iconBackground4);
                }
                else if (answer == 4) {

                    holder.iconBackground4.setBackgroundResource(R.drawable.gray_baground);
                    holder.rightOrWrongImg4.setImageResource(R.drawable.baseline_check_24);
                    holder.rightOrWrongImg4.setVisibility(View.VISIBLE);
                    highLightClickedOption2(option4Layout,option2Layout,option3Layout,option1Layout,holder.option4TV);
                    makeGrayTextView(ctx,holder.option1TV,holder.option2TV,holder.option3TV);
                    makeGrayBackground(holder.iconBackground1,holder.iconBackground2,holder.iconBackground3);
                }

                int userSelectedAnswer = questionslists.get(position).getUserSelecedAnswer();
                if (userSelectedAnswer == 1){
                    if (answer ==1 ){
//                        holder.option1Layout.setBackgroundResource(R.drawable.round_back_selected_option);
                        holder.iconBackground1.setBackgroundResource(R.drawable.green_background);
                        highLightClickedOption2(option1Layout,option2Layout,option3Layout,option4Layout,holder.option1TV);
                        holder.img1.setImageResource(R.drawable.green_dot);
                    }else {
                        holder.img1.setImageResource(R.drawable.red_dot);
                        selectedOption2(holder.option1Layout,holder.iconBackground1, holder.rightOrWrongImg1,holder.option1TV,ctx);

                    }

                } else if (userSelectedAnswer == 2) {

                    if (answer == 2){
//                        holder.option2Layout.setBackgroundResource(R.drawable.round_back_selected_option);
                        holder.iconBackground2.setBackgroundResource(R.drawable.green_background);
                        highLightClickedOption2(option2Layout,option1Layout,option3Layout,option4Layout,holder.option2TV);
                        holder.img2.setImageResource(R.drawable.green_dot);
                    }else {

                        holder.img2.setImageResource(R.drawable.red_dot);
                        selectedOption2(holder.option2Layout,holder.iconBackground2, holder.rightOrWrongImg2,holder.option2TV,ctx);
                    }
                } else if (userSelectedAnswer == 3) {
                    if (answer==3){
                        holder.iconBackground3.setBackgroundResource(R.drawable.green_background);
                        highLightClickedOption2(option3Layout,option2Layout,option1Layout,option4Layout,holder.option3TV);
                        holder.img3.setImageResource(R.drawable.green_dot);
                    }else {
                        holder.img3.setImageResource(R.drawable.red_dot);
                        selectedOption2(holder.option3Layout,holder.iconBackground3, holder.rightOrWrongImg3,holder.option3TV,ctx);
                    }
                } else if (userSelectedAnswer == 4) {
                    if (answer==4){
                        holder.iconBackground4.setBackgroundResource(R.drawable.green_background);

                        highLightClickedOption2(option4Layout,option2Layout,option1Layout,option3Layout,holder.option4TV);
                        holder.img4.setImageResource(R.drawable.green_dot);
                    }else {
                        holder.img4.setImageResource(R.drawable.red_dot);
                        selectedOption2(holder.option4Layout,holder.iconBackground4, holder.rightOrWrongImg4,holder.option4TV,ctx);
                    }

                }


            }


            /// it activative  when user clicked
            View.OnClickListener optionClickListener = view -> {

                showTextViewOrImageView(explain,holder.explainTv,holder.explainImage,explainImageString);

                if (answer == 1){
                    holder.option1TV.setTextColor(ContextCompat.getColor(ctx, R.color.green));
                    holder.iconBackground1.setBackgroundResource(R.drawable.gray_baground);
                    holder.rightOrWrongImg1.setImageResource(R.drawable.baseline_check_24);
                    holder.rightOrWrongImg1.setVisibility(View.VISIBLE);
                    holder.option1Layout.setBackgroundResource(R.drawable.round_back_selected_option);
                    makeGrayTextView(ctx,holder.option2TV,holder.option3TV,holder.option4TV);
                    makeGrayBackground(holder.iconBackground2,holder.iconBackground3,holder.iconBackground4);


                } else if (answer ==2) {

                    holder.option2TV.setTextColor(ContextCompat.getColor(ctx, R.color.green));
                    holder.iconBackground2.setBackgroundResource(R.drawable.gray_baground);
                    holder.rightOrWrongImg2.setImageResource(R.drawable.baseline_check_24);
                    holder.rightOrWrongImg2.setVisibility(View.VISIBLE);

                    holder.option2Layout.setBackgroundResource(R.drawable.round_back_selected_option);
                    makeGrayTextView(ctx,holder.option1TV,holder.option3TV,holder.option4TV);
                    makeGrayBackground(holder.iconBackground1,holder.iconBackground3,holder.iconBackground4);


                } else if (answer == 3) {
                    holder.option3TV.setTextColor(ContextCompat.getColor(ctx, R.color.green));
                    holder.iconBackground3.setBackgroundResource(R.drawable.gray_baground);
                    holder.rightOrWrongImg3.setImageResource(R.drawable.baseline_check_24);
                    holder.rightOrWrongImg3.setVisibility(View.VISIBLE);
                    holder.option3Layout.setBackgroundResource(R.drawable.round_back_selected_option);
                    makeGrayTextView(ctx,holder.option1TV,holder.option2TV,holder.option4TV);
                    makeGrayBackground(holder.iconBackground1,holder.iconBackground2,holder.iconBackground4);


                } else if (answer == 4) {
                    holder.option4TV.setTextColor(ContextCompat.getColor(ctx, R.color.green));
                    holder.iconBackground4.setBackgroundResource(R.drawable.gray_baground);
                    holder.rightOrWrongImg4.setImageResource(R.drawable.baseline_check_24);
                    holder.rightOrWrongImg4.setVisibility(View.VISIBLE);
                    holder.option4Layout.setBackgroundResource(R.drawable.round_back_selected_option);
                    makeGrayTextView(ctx,holder.option1TV,holder.option2TV,holder.option3TV);
                    makeGrayBackground(holder.iconBackground1,holder.iconBackground2,holder.iconBackground3);

                }

            // Determine which option was clicked based on the view that was clicked
                int selectedOption = 0;
                ImageView img = null;
                ImageView imgWrong = null;
                TextView  option = null;
                LinearLayout iconBackground= null;
                if (view == holder.option1Layout) {
                    selectedOption = 1;
                    img = holder.img1;
                    imgWrong = holder.rightOrWrongImg1;
                    option = holder.option1TV;
                    iconBackground = holder.iconBackground1;
                } else if (view == holder.option2Layout) {
                    selectedOption = 2;
                    img = holder.img2;
                    imgWrong = holder.rightOrWrongImg2;
                    option = holder.option2TV;
                    iconBackground = holder.iconBackground2;
                } else if (view == holder.option3Layout) {
                    selectedOption = 3;
                    img = holder.img3;
                    imgWrong = holder.rightOrWrongImg3;
                    option = holder.option3TV;
                    iconBackground = holder.iconBackground3;
                } else if (view == holder.option4Layout) {
                    selectedOption = 4;
                    img = holder.img4;
                    imgWrong = holder.rightOrWrongImg4;
                    option = holder.option4TV;
                    iconBackground = holder.iconBackground4;
                }
                questionslists.get(position).setUserSelecedAnswer(selectedOption);
                // if answer id correct selected option will be green if its wrong it will be read
                if (selectedOption == answer){

                    iconBackground.setBackgroundResource(R.drawable.green_background);
                    option.setTextColor(ContextCompat.getColor(ctx, R.color.green));
                    view.setBackgroundResource(R.drawable.round_back_selected_option);
                    img.setImageResource(R.drawable.green_dot);
                    imgWrong.setImageResource(R.drawable.baseline_check_24);
                    imgWrong.setVisibility(View.VISIBLE);
                }else {
                    img.setImageResource(R.drawable.red_dot);
                    selectedOption2(view,iconBackground,imgWrong,option,ctx);
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
        ImageView img1,img2,img3,img4,questionImg,option3Image,option1Image,option2Image,option4Image,explainImage,
                rightOrWrongImg1,rightOrWrongImg2,rightOrWrongImg3,rightOrWrongImg4;

        RelativeLayout option1Layout,option2Layout,option3Layout,option4Layout;
        LinearLayout fullLayout,questionImageLayout,iconBackground1,iconBackground2,iconBackground3,iconBackground4;

        TextView option1TV , option2TV , option3TV , option4TV,questionTv,explainTv,textViewPosition,textViewPosition2;

        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            textViewPosition = itemView.findViewById(R.id.textViewPosition);
            textViewPosition2 = itemView.findViewById(R.id.textViewPosition2);
            questionImageLayout = itemView.findViewById(R.id.questionIvLayout);

            iconBackground1 = itemView.findViewById(R.id.iconBackground1);
            iconBackground2 = itemView.findViewById(R.id.iconBackground2);
            iconBackground3 = itemView.findViewById(R.id.iconBackground3);
            iconBackground4 = itemView.findViewById(R.id.iconBackground4);

            questionTv=itemView.findViewById(R.id.questionTv);
            option1TV=itemView.findViewById(R.id.option1Tv);
            option2TV=itemView.findViewById(R.id.option2Tv);
            option3TV=itemView.findViewById(R.id.option3Tv);
            option4TV=itemView.findViewById(R.id.option4Tv);
            explainTv = itemView.findViewById(R.id.tvExplain);

            questionImg = itemView.findViewById(R.id.questionIv);
            option1Image = itemView.findViewById(R.id.option1IV);
            option2Image = itemView.findViewById(R.id.option2IV);
            option3Image = itemView.findViewById(R.id.option3IV);
            option4Image = itemView.findViewById(R.id.option4IV);
            explainImage = itemView.findViewById(R.id.explainIV);



            img1 = itemView.findViewById(R.id.option1Icon);
            img2 = itemView.findViewById(R.id.option2Icon);
            img3 = itemView.findViewById(R.id.option3Icon);
            img4 = itemView.findViewById(R.id.option4Icon);


            rightOrWrongImg1 = itemView.findViewById(R.id.optionIcon1);
            rightOrWrongImg2 = itemView.findViewById(R.id.optionIcon2);
            rightOrWrongImg3 = itemView.findViewById(R.id.optionIcon3);
            rightOrWrongImg4 = itemView.findViewById(R.id.optionIcon4);


            fullLayout = itemView.findViewById(R.id.fullLayout);

            option1Layout = itemView.findViewById(R.id.option1Layout);
            option2Layout = itemView.findViewById(R.id.option2Layout);
            option3Layout = itemView.findViewById(R.id.option3Layout);
            option4Layout = itemView.findViewById(R.id.opton4Layout);

            progressBar = itemView.findViewById(R.id.progressBar4);

        }

    }



    public  void showTextViewOrImageView(String questionOrAnyOption,TextView TvQuestionOrAnyOption,ImageView IvQuestionOrAnyOption,
                                         String ImageURL){

        if (!questionOrAnyOption.isEmpty()){
            //When questionOrAnyOption has data
            TvQuestionOrAnyOption.setVisibility(View.VISIBLE);
            IvQuestionOrAnyOption.setVisibility(View.GONE);
        }else {
            //When questionOrAnyOption is Empty

            TvQuestionOrAnyOption.setVisibility(View.GONE);
            IvQuestionOrAnyOption.setVisibility(View.VISIBLE);
            Bitmap bitmap = convertBase64ToBitmap(ImageURL);
            IvQuestionOrAnyOption.setImageBitmap(bitmap);

//            Glide.with(IvQuestionOrAnyOption.getContext())
//                    .load(ImageURL)
//                    .into(IvQuestionOrAnyOption);
        }
    }

    private String convertToUTF8(String inputString) {
        try {
            return new String(inputString.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }



    private void highLightClickedOption(View selectedOptionLayout , ImageView selectedOptionIcon) {

        selectedOptionIcon.setImageResource(R.drawable.black_dot);
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_selected_option);
        selectedOptionLayout.setEnabled(false);

    }


    private  void makeGrayTextView(Context ctx,TextView grayText1
            ,TextView grayText2, TextView grayText3 ) {

        grayText1.setTextColor(ContextCompat.getColor(ctx, R.color.t2));
        grayText2.setTextColor(ContextCompat.getColor(ctx, R.color.t2));
        grayText3.setTextColor(ContextCompat.getColor(ctx, R.color.t2));

    }

    private void  makeGrayBackground(LinearLayout grayIconBackground1,LinearLayout grayIconBackground2,LinearLayout grayIconBackground3){

        grayIconBackground1.setBackgroundResource(R.drawable.gray_baground);
        grayIconBackground2.setBackgroundResource(R.drawable.gray_baground);
        grayIconBackground3.setBackgroundResource(R.drawable.gray_baground);

    }



    private void highLightClickedOption2( View highlightedOptionLayout ,View disableOptionLayout1,
                                          View disableOptionLayout2,View disableOptionLayout3,TextView rightTextView) {


        highlightedOptionLayout.setBackgroundResource(R.drawable.round_back_selected_option);
        rightTextView.setTextColor(ContextCompat.getColor(rightTextView.getContext(), R.color.green));
        highlightedOptionLayout.setEnabled(false);
        disableOptionLayout1.setEnabled(false);
        disableOptionLayout2.setEnabled(false);
        disableOptionLayout3.setEnabled(false);


    }
    private void selectedOption2(View selectedOptionLayout ,View OptionIconBackground, ImageView selectedOptionIcon, TextView wrongAnswer,Context ctx) {


        OptionIconBackground.setBackgroundResource(R.drawable.red_background);
        selectedOptionIcon.setVisibility(View.VISIBLE);
        selectedOptionIcon.setImageResource(R.drawable.baseline_close_24);
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_red50_10);
        wrongAnswer.setTextColor(ContextCompat.getColor(ctx, R.color.liteRed));

    }

    public void intent(){

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("qutions",(Serializable) questionslists);
        intent.putExtras(bundle);
    }

    public Bitmap convertBase64ToBitmap(String base64Image) {
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }


}
