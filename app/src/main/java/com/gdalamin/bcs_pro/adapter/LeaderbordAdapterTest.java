package com.gdalamin.bcs_pro.adapter;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;
public class LeaderbordAdapterTest extends RecyclerView.Adapter<LeaderbordAdapterTest.ViewHolder> {
    private static final int VIEW_TYPE_PROFILE = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<LeaderbordModel> userMarksList;
    private Context context;

    public LeaderbordAdapterTest(List<LeaderbordModel> userMarksList, Context context) {
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
            // Example: holder.profileImageView.setImageResource(R.drawable.profile_image);
            // You can access the views in your profile_layout using the 'holder' object

            SharedPreferences sharedPreferences1 = holder.txtLocalUsername.getContext().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
            String base64LocalImage = sharedPreferences1.getString("profileImage","");
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(holder.txtLocalUsername.getContext());
            if (account !=null){
               String userName = account.getDisplayName();
                holder.txtLocalUsername.setText(userName);
            }else {
              String  userName = sharedPreferences1.getString("name", "");
                holder.txtLocalUsername.setText(userName);
            }

            Bitmap bitmapLocalImage = convertBase64ToBitmap(base64LocalImage);
            holder.imgLocalUserProfile.setImageBitmap(bitmapLocalImage);

        } else {
            // Bind data for regular items
            LeaderbordModel leaderbordModel = userMarksList.get(position - 1);  // Subtract 1 to account for the profile layout
            String count = String.valueOf(position);

            holder.txtUserName.setText(leaderbordModel.getUserName());
            holder.txtCounter.setText(count+".");

            String base64ImageString = leaderbordModel.getBase64ImageString();
            Bitmap bitmap = convertBase64ToBitmap(base64ImageString);
            holder.imgProfile.setImageBitmap(bitmap);
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
        private TextView txtUserName,txtLocalUsername;
        private TextView txtCounter;
        private ImageView imgProfile;
        private ImageView imgLocalUserProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.userNameTv);
            txtCounter = itemView.findViewById(R.id.tvCount);
            imgProfile = itemView.findViewById(R.id.profileImageID);
            imgLocalUserProfile = itemView.findViewById(R.id.profileImageID1);
            txtLocalUsername = itemView.findViewById(R.id.userNameTv1);

        }
    }

    public Bitmap convertBase64ToBitmap(String base64Image) {
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}