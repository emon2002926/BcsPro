package com.gdalamin.bcs_pro.downloader;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class ImageUploader {
    private Context context;
    private RequestQueue requestQueue;
    private String uploadUrl;
    private boolean isImageSelected = false;
    private Bitmap selectedImage;
    private String selectedImageName;

    public static final int REQUEST_IMAGE_PICK = 1;

    public ImageUploader(Context context, String uploadUrl) {
        this.context = context;
        this.uploadUrl = uploadUrl;
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void selectAndUploadImage(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    public void handleImageSelectionResult(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                selectedImageName = getImageNameFromUri(imageUri);
                isImageSelected = true;
            } catch (IOException e) {
                e.printStackTrace();
                isImageSelected = false; // Set isImageSelected to false if an error occurs during image selection
            }
        } else {
            isImageSelected = false; // Set isImageSelected to false if image selection was canceled or unsuccessful
        }
    }

    private String getImageNameFromUri(Uri uri) {
        String imageName = null;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            cursor.moveToFirst();
            imageName = cursor.getString(nameIndex);
            cursor.close();
        }
        return imageName;
    }

    public void uploadImage() {
        if (isImageSelected) {
            // Rest of the upload code...
        } else {
            Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isImageSelected() {
        return isImageSelected;
    }

    public Bitmap getSelectedImage() {
        return selectedImage;
    }

    public String getSelectedImageName() {
        return selectedImageName;
    }
}