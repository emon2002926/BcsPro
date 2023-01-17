package com.example.recreatequize.downloader;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class PdfDownloader extends AsyncTask<String,Void,Void> {
    Context ctx;
    @Override
    protected Void doInBackground(String... strings) {

        String fileUrl= strings[0];
        String fileName  = strings[1];



        String externalDiractory = Environment.getExternalStorageDirectory().toString();

        File folder = new File(externalDiractory,"PDF DOWNLOAD");
        folder.mkdir();

        File pdfFile = new File(folder,fileName);

        try {
            pdfFile.createNewFile();
        }catch (IOException o){
            o.printStackTrace();
        }

        FileDownloader.downloadFile(fileUrl, pdfFile);

        return null;
    }
}
