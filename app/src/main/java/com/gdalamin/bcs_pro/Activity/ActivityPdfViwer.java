package com.gdalamin.bcs_pro.Activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gdalamin.bcs_pro.R;

public class ActivityPdfViwer extends AppCompatActivity {
    WebView webView;
    TextView textView;

    ImageView imageBackButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viwer);

        String url = getIntent().getExtras().getString("pdfLink");
        String topBarText = getIntent().getExtras().getString("topBarText");

        textView = findViewById(R.id.tvTopBar);
        textView.setText(topBarText);
        imageBackButton = findViewById(R.id.backButton);
        imageBackButton.setOnClickListener(view -> {
            onBackPressed();
        });


        String url2 = "http://www.emon.pixatone.com/PDF/Makkar-IELTS-Speaking-May-Aug-2022-Final-Version.pdf";
//        String url2 = "https://demo.codeseasy.com/downloads/CodesEasy.pdf";
        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url2);


    }


}