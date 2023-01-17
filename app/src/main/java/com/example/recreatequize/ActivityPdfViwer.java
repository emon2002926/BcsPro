package com.example.recreatequize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.io.InputStream;

public class ActivityPdfViwer extends AppCompatActivity {
    WebView webView;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viwer);

        String url = getIntent().getExtras().getString("pdfLink");
        String topBarText = getIntent().getExtras().getString("topBarText");

        textView = findViewById(R.id.tvTopBar);
        textView.setText(topBarText);


//        String url = "https://drive.google.com/file/d/1DieRNQNzW5w0TmF3WN6NexSz4B7A7ZQK/view";
//        String url2 = "https://demo.codeseasy.com/downloads/CodesEasy.pdf";
        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }


}