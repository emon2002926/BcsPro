package com.gdalamin.bcs_pro.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gdalamin.bcs_pro.Activity.ActivityPdfViwer;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.modelClass.modelForLecture;

public class LectureAndNotesAdapter extends RecyclerView.Adapter<LectureAndNotesAdapter.myviewholder>
{

    modelForLecture data[];


    private Context mContext;



    public LectureAndNotesAdapter(modelForLecture[] data) {
        this.data = data;
    }



    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_lecture_notes,parent,false);


         return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, final int position) {

        holder.t1.setText(data[position].getLectureDetails());

        holder.t2.setText("Last Update   "+data[position].getPdfDate());



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Intent intent = new Intent(view.getContext(), ActivityPdfViwer.class);
              intent.putExtra("pdfLink",data[position].getPdfLink());
              intent.putExtra("topBarText",data[position].getLectureDetails());
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
        TextView t1,t2;


        CardView cardView;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            t1=itemView.findViewById(R.id.tvLectureDitals);
            t2=itemView.findViewById(R.id.tvDate);

            cardView = itemView.findViewById(R.id.layoutLecture);



        }
    }




}
