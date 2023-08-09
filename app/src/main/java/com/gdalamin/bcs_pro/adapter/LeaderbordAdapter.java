package com.gdalamin.bcs_pro.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.api.PreferencesUserInfo;
import com.gdalamin.bcs_pro.modelClass.LeaderbordModel;

import java.util.List;
public class LeaderbordAdapter extends RecyclerView.Adapter<LeaderbordAdapter.ViewHolder> {
    private static final int VIEW_TYPE_PROFILE = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<LeaderbordModel> userMarksList;
    private Context context;

    public LeaderbordAdapter(List<LeaderbordModel> userMarksList, Context context) {
        this.userMarksList = userMarksList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutRes = (viewType == VIEW_TYPE_PROFILE) ? R.layout.ranking_user_profile : R.layout.leader_bord_test_layout;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_PROFILE) {
            // Bind data for the profile layout at the top

            PreferencesUserInfo preferencesUserInfo = new PreferencesUserInfo(holder.txtLocalUsername.getContext());
            String base64LocalImage = preferencesUserInfo.getString("userImage");
            String userName  = preferencesUserInfo.getString("name").trim();

            holder.txtLocalUsername.setText(userName);

            String userId = preferencesUserInfo.getString("key_phone").trim();

            LeaderbordModel user = findUserById(userId);
            if (user != null) {
                String userRank = user.getUserRank();
                holder.txtLocalUserRank.setText(userRank);
                int userPoint = (int) Math.floor(user.getAverageMark() * 10);
                holder.txtLocalUserPoint.setText(String.valueOf(userPoint));
            } else {
                holder.txtLocalUserRank.setText("00");
                holder.txtLocalUserPoint.setText("00");
            }

            if (!base64LocalImage.isEmpty()){
                Bitmap bitmapLocalImage = convertBase64ToBitmap(base64LocalImage);
                holder.imgLocalUserProfile.setImageBitmap(bitmapLocalImage);
            }else {
                holder.imgLocalUserProfile.setImageResource(R.drawable.test_profile_image);
            }



        } else {
            // Bind data for regular items
            LeaderbordModel leaderbordModel = userMarksList.get(position - 1);  // Subtract 1 to account for the profile layout


            int userPoint = (int) Math.floor(leaderbordModel.getAverageMark() * 10);


            holder.txtUserName.setText(leaderbordModel.getUserName());
            holder.txtCounter.setText(String.valueOf(position)+".");

            String base64ImageString = leaderbordModel.getBase64ImageString();
            if (!base64ImageString.isEmpty()){
                Bitmap bitmap = convertBase64ToBitmap(base64ImageString);
                holder.imgProfile.setImageBitmap(bitmap);
            }else {
                holder.imgProfile.setImageResource(R.drawable.test_profile_image);
            }


            holder.txtPoints.setText(String.valueOf(userPoint));

            holder.leaderBordLayer.setOnClickListener(v -> {

                Dialog dialog = new Dialog(holder.leaderBordLayer.getContext());
                // Set the layout for the dialog
                dialog.setContentView(R.layout.ranking_profile);

                Window window = dialog.getWindow();
                if (window != null) {
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }


                ImageView imgCloseButton = dialog.findViewById(R.id.closeUpdateLayout);
                imgCloseButton.setOnClickListener(v1 -> dialog.dismiss());


                Bitmap bitmapImage = convertBase64ToBitmap(leaderbordModel.getBase64ImageString());
                ImageView imgRankingProfile = dialog.findViewById(R.id.profileImageID1);
                imgRankingProfile.setImageBitmap(bitmapImage);

                TextView txtUserRank = dialog.findViewById(R.id.navRank);
                txtUserRank.setText("Rank  "+String.valueOf(position));

                TextView txtUserNamePopUp = dialog.findViewById(R.id.userNameTv1);
                txtUserNamePopUp.setText(leaderbordModel.getUserName());

                TextView txtUserPosition = dialog.findViewById(R.id.txtPosition);
                txtUserPosition.setText(String.valueOf(position));

                TextView txtUserPoint = dialog.findViewById(R.id.txtPoint);
                txtUserPoint.setText(String.valueOf(userPoint));


                ///statistics_layout ditales

                TextView txtTotalExam = dialog.findViewById(R.id.totalExamTv);
                txtTotalExam.setText(String.valueOf(leaderbordModel.getTotalExamsTaken()));

                int totalQuestions = leaderbordModel.getTotalQuestions();


                float totalPercentageCorrect = ((float)leaderbordModel.getTotalCorrect()/totalQuestions)*100;
                float totalPercentageWrong =((float) leaderbordModel.getTotalWrong()/totalQuestions) * 100;
                float totalPercentageNotAnswered = ((float) leaderbordModel.getTotalNotAnswered()/totalQuestions) *100;

                TextView correctAnswerTextView = dialog.findViewById(R.id.correctAnswerTv);
                correctAnswerTextView.setText(String.valueOf(
                        leaderbordModel.getTotalCorrect()+" (" + String.valueOf(String.format("%.2f", totalPercentageCorrect)) + "%)"));

                TextView wrongAnswerTextView = dialog.findViewById(R.id.wrongAnswerTv);
                wrongAnswerTextView.setText(String.valueOf(
                        leaderbordModel.getTotalWrong()+ " (" + String.valueOf(String.format("%.2f", totalPercentageWrong)) + "%)"));

                TextView notAnswredTextView = dialog.findViewById(R.id.notAnswredTv);
                notAnswredTextView.setText(String.valueOf(
                        leaderbordModel.getTotalNotAnswered()+" (" + String.valueOf(String.format("%.2f", totalPercentageNotAnswered)) + "%)"));

                TextView txtTotalQuestion = dialog.findViewById(R.id.totalQuestionTv);
                txtTotalQuestion.setText(String.valueOf(totalQuestions));


                ProgressBar progressBarCorrect = dialog.findViewById(R.id.percentageProgressBarCorrect);
                ProgressBar progressBarWrong = dialog.findViewById(R.id.percentageProgressBarWrong);
                ProgressBar progressBarNotAnswered = dialog.findViewById(R.id.percentageProgressBarNotAnswred);

                progressBarCorrect.setProgress(Math.round(totalPercentageCorrect));
                progressBarWrong.setProgress(Math.round(totalPercentageWrong));
                progressBarNotAnswered.setProgress(Math.round(totalPercentageNotAnswered));


                dialog.show();
            });
        }
    }

    @Override
    public int getItemCount() {
        return userMarksList.size() + 1; // Add 1 to account for the profile layout
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? VIEW_TYPE_PROFILE : VIEW_TYPE_ITEM;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final private TextView txtUserName,txtLocalUsername,txtLocalUserRank,txtPoints,txtLocalUserPoint;
        final private TextView txtCounter;
        final private ImageView imgProfile;
        final private ImageView imgLocalUserProfile;
        final private LinearLayout leaderBordLayer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.userNameTv);
            txtCounter = itemView.findViewById(R.id.tvCount);
            imgProfile = itemView.findViewById(R.id.profileImageID);
            imgLocalUserProfile = itemView.findViewById(R.id.profileImageID1);
            txtLocalUsername = itemView.findViewById(R.id.userNameTv1);
            leaderBordLayer = itemView.findViewById(R.id.leaderBordLayout);
            txtLocalUserRank = itemView.findViewById(R.id.txtPosition);
            txtPoints  = itemView.findViewById(R.id.points);
            txtLocalUserPoint  = itemView.findViewById(R.id.txtPoint);

        }
    }

    public Bitmap convertBase64ToBitmap(String base64Image) {
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    private LeaderbordModel findUserById(String userId) {
        for (LeaderbordModel userMarks : userMarksList) {
            if (userMarks.getUserId().equals(userId)) {
                return userMarks;
            }
        }
        // Return null if the userId is not found in the list
        return null;
    }
}