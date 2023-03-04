package com.gdalamin.bcs_pro.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gdalamin.bcs_pro.Activity.QuestionListActivity;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;
import com.gdalamin.bcs_pro.modelClass.model;

public class myadapterForAllbcs extends RecyclerView.Adapter<myadapterForAllbcs.myviewholder>
{
    ModelForLectureAndAllQuestion data[];

    public myadapterForAllbcs(ModelForLectureAndAllQuestion[] data) {
        this.data = data;
    }

    private Context mContext;




    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bcs_question_bank,parent,false);

         return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, final int position) {

        SharedPreferences sharedPreferences = holder.t1.getContext().getSharedPreferences("totalQuestion", MODE_PRIVATE);
        int NUM_OF_QUESTION = sharedPreferences.getInt("logic", 0);



        if (NUM_OF_QUESTION==1){

            holder.t1.setText(data[position].getText());
            holder.cardView2.setVisibility(View.GONE);
            holder.cardView1.setVisibility(View.VISIBLE);
            holder.cardView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"1",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(view.getContext(), QuestionListActivity.class);
                    view.getContext().startActivity(intent);

                }
            });
        } else if (NUM_OF_QUESTION==2) {

            holder.tvSubject.setText(data[position].getSubjects());
            holder.cardView2.setVisibility(View.VISIBLE);
            holder.cardView1.setVisibility(View.GONE);

            holder.cardView2.setOnClickListener(view -> {
                view.getContext().startActivity(new Intent(view.getContext(),QuestionListActivity.class));
            });

        }


    }

    @Override
    public int getItemCount() {
        return data.length;
    }





    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView t1,t2,tvSubject;


        CardView cardView1,cardView2;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            t1=itemView.findViewById(R.id.questionBatch);
            t2=itemView.findViewById(R.id.numOFQuestion);
            tvSubject = itemView.findViewById(R.id.tvSubject);

            cardView1 = itemView.findViewById(R.id.layout1);
            cardView2= itemView.findViewById(R.id.layout2);



        }
    }




}
