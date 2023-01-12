package com.example.recreatequize.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recreatequize.R;
import com.example.recreatequize.modelClass.model;

public class myadapter2 extends RecyclerView.Adapter<myadapter2.myviewholder>
{
    model data[];

    public myadapter2(model[] data2) {
        this.data = data2;
    }

    private Context mContext;






    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_slide,parent,false);

         return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, final int position) {

        holder.t1.setText(data[position].getText());



//      holder.t2.setText(data[position].getOption1());
//      holder.t3.setText(data[position].getOption2());
//        holder.t4.setText(data[position].getOption3());

//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"1",Toast.LENGTH_SHORT).show();
//
//                selectedOption(holder.relativeLayout,holder.img);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }





    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView t1,t2,t3,t4;

        RelativeLayout relativeLayout;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            t1=itemView.findViewById(R.id.exam);
            t2=itemView.findViewById(R.id.q1);




        }
    }

    private void selectedOption(RelativeLayout selectedOptionLayout , ImageView selectedOptionIcon) {


        selectedOptionIcon.setImageResource(R.drawable.chack);
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_selected_option);

    }




}
