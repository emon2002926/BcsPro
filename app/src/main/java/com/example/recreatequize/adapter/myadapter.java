package com.example.recreatequize.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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





    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mcq_layout,parent,false);

         return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, @SuppressLint("RecyclerView") final int position) {

        holder.t1.setText(data[position].getQuestion());
        holder.t2.setText(data[position].getOption1());
        holder.t3.setText(data[position].getOption2());
        holder.t4.setText(data[position].getOption3());

        //                final String answer = data[position].getAnswer();

        holder.option1Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int option1 = 1;
                String ansr = data[position].getAnswer();
                int answer = Integer.parseInt(ansr);


                holder.t5.setText(data[position].getExplanation());

                holder.layoutExplain.setVisibility(View.VISIBLE);

                if (option1 == answer){

                    selectedOption(holder.option1Layout,holder.img);


                    disableOption(holder.option2Layout,holder.img);
                    disableOption(holder.option3Layout,holder.img);
                    disableOption(holder.option4Layout,holder.img);

                }else {
                    disableOption(holder.option1Layout,holder.img);
                }

//                disableOption(holder.option2Layout,holder.img);


            }
        });

        holder.option2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int option1 = 1;
                selectedOption(holder.option1Layout,holder.img);

                holder.t5.setText(data[position].getExplanation());

                holder.layoutExplain.setVisibility(View.VISIBLE);

            }
        });
        holder.option3Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int option1 = 1;
                selectedOption(holder.option1Layout,holder.img);

                holder.t5.setText(data[position].getExplanation());

                holder.layoutExplain.setVisibility(View.VISIBLE);


            }
        });
        holder.option4Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int option1 = 1;
                selectedOption(holder.option1Layout,holder.img);

                holder.t5.setText(data[position].getExplanation());

                holder.layoutExplain.setVisibility(View.VISIBLE);

            }
        });


//      Glide.with(holder.t1.getContext()).load("http://192.168.0.103/api2/images/"+data[position].getImage()).into(holder.img);


//        final String name = data[position].getName();

//      holder.itemView.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View view) {
//              Toast.makeText(view.getContext(),name,Toast.LENGTH_SHORT).show();
//              Intent intent = new Intent(view.getContext(),MainActivity2.class);
//              intent.putExtra("name",name);
////              intent.putExtra("image",data[position].getImage());
//              intent.putExtra("image","http://192.168.0.103/api2/images/"+data[position].getImage());
//
//              view.getContext().startActivity(intent);
//          }
//      });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }





    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView t1,t2,t3,t4,t5;

        RelativeLayout option1Layout,option2Layout,option3Layout,option4Layout;
        LinearLayout layoutExplain;

        private TextView option1TV , option2TV , option3TV , option4TV ;
        private ImageView option1Icon , option2Icon , option3Icon , option4Icon ;

        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            t1=itemView.findViewById(R.id.questionTv);
            t2=itemView.findViewById(R.id.option1Tv);
            t3=itemView.findViewById(R.id.option2Tv);
            t4=itemView.findViewById(R.id.option3Tv);
            t5 = itemView.findViewById(R.id.tvExplain);

            img = itemView.findViewById(R.id.option1Icon);

            layoutExplain = itemView.findViewById(R.id.layoutExplain);

            option1Layout = itemView.findViewById(R.id.option1Layout);
            option2Layout = itemView.findViewById(R.id.option2Layout);
            option3Layout = itemView.findViewById(R.id.option3Layout);
            option4Layout = itemView.findViewById(R.id.opton4Layout);

        }

    }

    private void selectedOption(RelativeLayout selectedOptionLayout , ImageView selectedOptionIcon) {


        selectedOptionIcon.setImageResource(R.drawable.chack);
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_selected_option);

    }

    private void disableOption(RelativeLayout selectedOptionLayout , ImageView selectedOptionIcon) {


        selectedOptionIcon.setImageResource(R.drawable.cross);
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_red50_10);
//        selectedOptionLayout.setEnabled(false);

    }





}
