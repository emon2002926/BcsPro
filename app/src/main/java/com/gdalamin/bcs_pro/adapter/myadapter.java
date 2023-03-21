package com.gdalamin.bcs_pro.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.api.SharedPreferencesManager;
import com.gdalamin.bcs_pro.modelClass.QuestionList;
import com.gdalamin.bcs_pro.modelClass.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
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



        String BASE_URL = ApiKeys.API_URL_BASE;




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



        SharedPreferencesManager preferencesManager = new SharedPreferencesManager(holder.explainTv.getContext());
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

                    String question = data[position].getQuestion().trim();
                    String option1 = data[position].getOption1().trim();
                    String option2 = data[position].getOption2().trim();
                    String option3 = data[position].getOption3().trim();
                    String option4 = data[position].getOption4().trim();
                    int answer = Integer.parseInt(data[position].getAnswer());

                    String option1ImageURL = BASE_URL+"image/option1/"+data[position].getOption1Image().trim();
                    String option2ImageURL = BASE_URL+"image/option2/"+data[position].getOption2Image().trim();
                    String option3ImageURL = BASE_URL+"image/option3/"+data[position].getOption3Image().trim();
                    String option4ImageURL = BASE_URL+"image/option4/"+data[position].getOption4Image().trim();


                    // Create a new QuestionList object with the obtained data
                    QuestionList questionList = new QuestionList(question, option1, option2, option3, option4, answer);
                    questionslists.add(questionList);

                    String questionPosition = String.valueOf(position+1);




                    // Set the text of the options to their respective text views
                    holder.questionTv.setText(convertToUTF8(questionPosition+") "+question));
                    holder.option1TV.setText(convertToUTF8(option1));
                    holder.option2TV.setText(convertToUTF8(option2));
                    holder.option3TV.setText(convertToUTF8(option3));
                    holder.option4TV.setText(convertToUTF8(option4));


                    // If the question text is empty, hide the text view and display the question image
                    if (!question.isEmpty()) {
                        holder.questionImg.setVisibility(View.GONE);
                        holder.questionTv.setVisibility(View.VISIBLE);
                        holder.textViewPosition.setVisibility(View.GONE);
                    } else {
                        holder.questionTv.setVisibility(View.GONE);
                        holder.questionImageLayout.setVisibility(View.VISIBLE);
                        Glide.with(holder.questionImg.getContext())
                                .load(BASE_URL+"image/" + data[position].getImage()).into(holder.questionImg);
                        holder.questionImg.setVisibility(View.VISIBLE);
                        holder.textViewPosition.setVisibility(View.VISIBLE);
                        holder.textViewPosition.setText(questionPosition+")");
                    }


                    //Showing Image or text with there respective logic
                    showTextViewOrImageView(option1,holder.option1TV,holder.option1Image,option1ImageURL);
                    showTextViewOrImageView(option2,holder.option2TV,holder.option2Image,option2ImageURL);
                    showTextViewOrImageView(option3,holder.option3TV,holder.option3Image,option3ImageURL);
                    showTextViewOrImageView(option4,holder.option4TV,holder.option4Image,option4ImageURL);



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

                String question = data[position].getQuestion().trim();
                String option1 = data[position].getOption1().trim();
                String option2 = data[position].getOption2().trim();
                String option3 = data[position].getOption3().trim();
                String  option4 = data[position].getOption4().trim();
                String explain = data[position].getExplanation().trim();
                String questionPosition = String.valueOf(position+1);

                String option1ImageURL = BASE_URL+"image/option1/"+data[position].getOption1Image().trim();
                String option2ImageURL = BASE_URL+"image/option2/"+data[position].getOption2Image().trim();
                String option3ImageURL = BASE_URL+"image/option3/"+data[position].getOption3Image().trim();
                String option4ImageURL = BASE_URL+"image/option4/"+data[position].getOption4Image().trim();



                holder.questionTv.setText(convertToUTF8(questionPosition+") "+question));

                holder.option1TV.setText(convertToUTF8(option1));
                holder.option2TV.setText(convertToUTF8(option2));
                holder.option3TV.setText(convertToUTF8(option3));
                holder.option4TV.setText(convertToUTF8(option4));
                holder.explainTv.setText(convertToUTF8(explain));

                // Check if question is empty, if not, display text. If yes, display image
                if (!question.isEmpty()) {
                    holder.questionImg.setVisibility(View.GONE);
                    holder.questionTv.setVisibility(View.VISIBLE);
                    holder.textViewPosition.setVisibility(View.GONE);
                } else {
                    holder.questionTv.setVisibility(View.GONE);
                    holder.questionImageLayout.setVisibility(View.VISIBLE);
                    Glide.with(holder.questionImg.getContext())
                            .load(BASE_URL+"image/" + data[position].getImage()).into(holder.questionImg);
                    holder.questionImg.setVisibility(View.VISIBLE);
                    holder.textViewPosition.setVisibility(View.VISIBLE);
                    holder.textViewPosition.setText(questionPosition+")");
                }


                //Showing Image or text with there respective logic
                showTextViewOrImageView(option1,holder.option1TV,holder.option1Image,option1ImageURL);
                showTextViewOrImageView(option2,holder.option2TV,holder.option2Image,option2ImageURL);
                showTextViewOrImageView(option3,holder.option3TV,holder.option3Image,option3ImageURL);
                showTextViewOrImageView(option4,holder.option4TV,holder.option4Image,option4ImageURL);



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
        ImageView img1,img2,img3,img4,questionImg,option3Image,option1Image,option2Image,option4Image;

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

            option1Image = itemView.findViewById(R.id.option1IV);
            option2Image = itemView.findViewById(R.id.option2IV);
            option3Image = itemView.findViewById(R.id.option3IV);
            option4Image = itemView.findViewById(R.id.option4IV);



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
            Glide.with(IvQuestionOrAnyOption.getContext())
                    .load(ImageURL)
                    .into(IvQuestionOrAnyOption);
        }
    }





    private String convertToUTF8(String inputString) {
        try {
            return new String(inputString.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
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
