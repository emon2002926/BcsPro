package com.gdalamin.bcs_pro.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.modelClass.LeaderbordModel;
import java.util.List;
public class LeaderbordAdapter extends RecyclerView.Adapter<LeaderbordAdapter.ViewHolder> {
    private List<LeaderbordModel> userMarksList;
    private Context context;

    public LeaderbordAdapter(List<LeaderbordModel> userMarksList, Context context) {
        this.userMarksList = userMarksList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leader_bord_test_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaderbordModel leaderbordModel = userMarksList.get(position);
        String count = String.valueOf(position+1);

//        holder.txtUserId.setText(leaderbordModel.getUserId());
        holder.txtUserName.setText(leaderbordModel.getUserName());
        holder.txtCounter.setText(count);
//        holder.txtAverageMark.setText(String.valueOf(leaderbordModel.getAverageMark()));
//        holder.txtTotalCorrect.setText(String.valueOf(leaderbordModel.getTotalCorrect()));
//        holder.txtTotalWrong.setText(String.valueOf(leaderbordModel.getTotalWrong()));
//        holder.txtTotalNotAnswered.setText(String.valueOf(leaderbordModel.getTotalNotAnswered()));
//        holder.txtTotalExamsTaken.setText(String.valueOf(leaderbordModel.getTotalExamsTaken()));



        String base64ImageString = leaderbordModel.getBase64ImageString();
        Bitmap bitmap = convertBase64ToBitmap(base64ImageString);
        holder.imgProfile.setImageBitmap(bitmap);


        Log.d("lkhsfttr7",base64ImageString);

    }

    @Override
    public int getItemCount() {
        return userMarksList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //        private TextView txtUserId;
        private TextView txtUserName;
        //        private TextView txtAverageMark;
//        private TextView txtTotalCorrect;
//        private TextView txtTotalWrong;
//        private TextView txtTotalNotAnswered;
//        private TextView txtTotalExamsTaken;
        private TextView txtCounter;
        private ImageView imgProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            txtUserId = itemView.findViewById(R.id.txtUserId);
            txtUserName = itemView.findViewById(R.id.userNameTv);
            txtCounter = itemView.findViewById(R.id.tvCount);
//            txtAverageMark = itemView.findViewById(R.id.txtAverageMark);
//            txtTotalCorrect = itemView.findViewById(R.id.txtTotalCorrect);
//            txtTotalWrong = itemView.findViewById(R.id.txtTotalWrong);
//            txtTotalNotAnswered = itemView.findViewById(R.id.txtTotalNotAnswered);
//            txtTotalExamsTaken = itemView.findViewById(R.id.txtTotalExamsTaken);
            imgProfile = itemView.findViewById(R.id.profileImageID);

        }
    }
    public Bitmap convertBase64ToBitmap(String base64Image) {
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}