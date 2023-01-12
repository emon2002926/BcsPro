package com.example.recreatequize.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recreatequize.QuestionListActivity;
import com.example.recreatequize.R;
import com.example.recreatequize.modelClass.model;

public class myadapterForAllbcs extends RecyclerView.Adapter<myadapterForAllbcs.myviewholder>
{
    model data[];

    public myadapterForAllbcs(model[] data) {
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

        holder.t1.setText(data[position].getText());



//      holder.t2.setText(data[position].getOption1());
//      holder.t3.setText(data[position].getOption2());
//        holder.t4.setText(data[position].getOption3());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"1",Toast.LENGTH_SHORT).show();

              Intent intent = new Intent(view.getContext(), QuestionListActivity.class);
              intent.putExtra("image","http://192.168.0.103/api2/Qbatch/"+data[position].getBatchNum());
              view.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }





    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView t1,t2,t3,t4;


        LinearLayout linearLayout;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            t1=itemView.findViewById(R.id.questionBatch);
            t2=itemView.findViewById(R.id.numOFQuestion);

            linearLayout = itemView.findViewById(R.id.bcsQuestionLayout);



        }
    }




}
