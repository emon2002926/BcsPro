package com.gdalamin.bcs_pro.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Constructor;

public class CacheManager {
    static String CACHE_KEY ;

    public CacheManager(String CACHE_KEY){
        this.CACHE_KEY = CACHE_KEY;
    }

    public void showCACHE_KEY(){
        Log.d("CACHE_KEY",CACHE_KEY);
    }

    public  void saveToCache(Context context, String data) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(CACHE_KEY, data).apply();
        Log.d("Cache", "Data before saving: " + data);
    }

    public  String getFromCache(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(CACHE_KEY, null);
    }


}