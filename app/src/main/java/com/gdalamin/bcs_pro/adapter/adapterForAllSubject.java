            package com.gdalamin.bcs_pro.adapter;


            import android.annotation.SuppressLint;
            import android.content.Context;
            import android.content.Intent;
            import android.util.Log;
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

            import java.nio.charset.StandardCharsets;

            public class adapterForAllSubject extends RecyclerView.Adapter<adapterForAllSubject.myviewholder> {
                ModelForLectureAndAllQuestion[] data;
                private int lastPosition = -1;
                Context context;

                public adapterForAllSubject(ModelForLectureAndAllQuestion[] data) {
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

                    setAnimation(holder.tvSubject.getContext(),holder.itemView,position);

                    String subjectName = convertToUTF8(data[position].getSubjects());
                    holder.tvPosition.setText(position + 1 +".");
                    holder.tvSubject.setText(subjectName);
                    holder.cardView1.setVisibility(View.GONE);
                    holder.subjectLayout.setVisibility(View.VISIBLE);
                    holder.subjectLayout.setOnClickListener(view -> {
                        preferencesManager.saveString("subjectCode",convertToUTF8(data[position].getSubjectCode()));
                        String ACTION = "subject_Specific_Question";
                        preferencesManager.saveString("Type_Of_Question_To_Load",ACTION);
                        Intent intent = new Intent(view.getContext(), QuestionListActivity.class);
                        intent.putExtra("titleText",subjectName);
                        view.getContext().startActivity(intent);
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
                        tvPosition = itemView.findViewById(R.id.tvPosition);
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
