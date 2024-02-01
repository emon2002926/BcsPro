package com.gdalamin.bcs_pro.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import com.gdalamin.bcs_pro.api.SharedPreferencesManagerAppLogic;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;

import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class AdapterForShowAllBcsYearList extends RecyclerView.Adapter<AdapterForShowAllBcsYearList.myviewholder> {
    ModelForLectureAndAllQuestion[] data;

    private int lastPosition = -1;

    public AdapterForShowAllBcsYearList(ModelForLectureAndAllQuestion[] data) {
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

        Resources resources = holder.tvSubject.getContext().getResources();
        String amount = resources.getText(R.string.amountOfQuestion) + " : " + convertToBengaliString(data[position].getTotalQuestion());
        holder.numOfQuestion.setText(amount);

        setAnimation(holder.tvSubject.getContext(), holder.itemView, position);


        String subjectName = convertToUTF8(data[position].getSubjects());
        String postion2 = convertToBengaliString(position + 1 + ")");

        holder.tvPosition.setText(convertToBengaliString(postion2));

        holder.tvSubject.setText(subjectName);
        holder.cardView1.setVisibility(View.GONE);
        holder.subjectLayout.setVisibility(View.VISIBLE);

        holder.bcsYearName.setText(data[position].getText());

        holder.cardView1.setVisibility(View.VISIBLE);
        holder.subjectLayout.setVisibility(View.GONE);
        holder.cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String BCS_Question_Year_Name = data[position].getText();
                String oldBcs = "Older_Bcs_Question";
                preferencesManager.saveString("oldBcs", oldBcs);

                preferencesManager.saveString("bcsYearName", BCS_Question_Year_Name);
                preferencesManager.saveInt("subCode", 4);

                Intent intent = new Intent(view.getContext(), QuestionListActivity.class);
                intent.putExtra("titleText", BCS_Question_Year_Name);
                view.getContext().startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return data.length;
    }
    static class myviewholder extends RecyclerView.ViewHolder {
        TextView bcsYearName, numOfQuestion, tvSubject, tvPosition;
        LinearLayout cardView1, subjectLayout;

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


    private String convertToUTF8(String inputString) {
        return new String(inputString.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    public String convertToBengaliString(String numberStr) {
        try {
            double number = Double.parseDouble(numberStr);
            Locale bengaliLocale = new Locale("bn", "BD");
            NumberFormat bengaliNumberFormat = NumberFormat.getNumberInstance(bengaliLocale);
            return bengaliNumberFormat.format(number);
        } catch (NumberFormatException e) {
            e.printStackTrace(); // You can log or handle the error here
            return numberStr; // Return the original string as-is
        }
    }

    public void addItems(List<ModelForLectureAndAllQuestion> newData) {
        synchronized (this) {
            List<ModelForLectureAndAllQuestion> currentData = new ArrayList<>(Arrays.asList(data));
            List<ModelForLectureAndAllQuestion> combinedData = new ArrayList<>(currentData);
            combinedData.addAll(newData);
            this.data = combinedData.toArray(new ModelForLectureAndAllQuestion[0]);
        }
        notifyItemRangeInserted(data.length - newData.size(), newData.size());
    }

    public ModelForLectureAndAllQuestion[] getData() {
        return data;
    }

    public void setData(List<ModelForLectureAndAllQuestion> newData) {
        synchronized (this) {
            this.data = newData.toArray(new ModelForLectureAndAllQuestion[0]);
        }
        notifyDataSetChanged();
    }

    public void setAnimation(Context ctx, View viewToAnimate, int position) {
        Animation slideIn = AnimationUtils.loadAnimation(ctx, android.R.anim.fade_in);
        viewToAnimate.setAnimation(slideIn);
        lastPosition = position;
    }

}
