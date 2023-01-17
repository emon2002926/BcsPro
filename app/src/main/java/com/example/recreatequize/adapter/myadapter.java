package com.example.recreatequize.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recreatequize.R;
import com.example.recreatequize.modelClass.model;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
{
    model data[];

    public myadapter(model[] data) {
        this.data = data;
    }



   static int userSelectedOption = 0;


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mcq_layout,parent,false);

         return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, @SuppressLint("RecyclerView") final int position) {

        holder.questionTv.setText(data[position].getQuestion());
        holder.option1TV.setText(data[position].getOption1());
        holder.option2TV.setText(data[position].getOption2());
        holder.option3TV.setText(data[position].getOption3());
        holder.option4TV.setText(data[position].getOption4());
        holder.explainTv.setText(data[position].getExplanation());

        String ansr = data[position].getAnswer();
        int answer = Integer.parseInt(ansr);


        holder.option1Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                holder.layoutExplain.setVisibility(View.VISIBLE);

                disableOtherOption(holder.option2Layout,holder.option3Layout,holder.option4Layout);

                selectedRightOpti(answer,holder.option1Layout,holder.option2Layout,holder.option3Layout,holder.option4Layout);


                if (answer!=1){

                    selectedWrongOption(holder.option1Layout,holder.img1);

                }


            }
        });

        holder.option2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                holder.layoutExplain.setVisibility(View.VISIBLE);


                disableOtherOption(holder.option1Layout,holder.option3Layout,holder.option4Layout);
                selectedRightOpti(answer,holder.option1Layout,holder.option2Layout,holder.option3Layout,holder.option4Layout);
                if (answer!=2){

                    selectedWrongOption(holder.option2Layout,holder.img2);

                }

            }
        });
        holder.option3Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                holder.layoutExplain.setVisibility(View.VISIBLE);

                disableOtherOption(holder.option2Layout,holder.option1Layout,holder.option4Layout);
                selectedRightOpti(answer,holder.option1Layout,holder.option2Layout,holder.option3Layout,holder.option4Layout);
                if (answer!=3){

                    selectedWrongOption(holder.option3Layout,holder.img3);

                }

            }
        });
        holder.option4Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                disableOtherOption(holder.option2Layout,holder.option3Layout,holder.option1Layout);


                holder.layoutExplain.setVisibility(View.VISIBLE);

                selectedRightOpti(answer,holder.option1Layout,holder.option2Layout,holder.option3Layout,holder.option4Layout);
                if (answer!=4){

                    selectedWrongOption(holder.option4Layout,holder.img4);

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.length;
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img1,img2,img3,img4;

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




    private void selectedWrongOption(RelativeLayout selectedOptionLayout,ImageView selectedOptionIcon){
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_red50_10);
        selectedOptionIcon.setImageResource(R.drawable.cross);

    }

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





}
