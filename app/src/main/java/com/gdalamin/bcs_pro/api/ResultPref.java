package com.gdalamin.bcs_pro.api;

import android.content.Context;
import android.content.SharedPreferences;

public class ResultPref {

    private final SharedPreferences preferences;

    public ResultPref(Context context) {
        preferences = context.getSharedPreferences("ResultPref", Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public void saveInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }
    public double getDouble(String key){
        return Double.longBitsToDouble(preferences.getLong(key, Double.doubleToLongBits(0.0)));
    }
    public void saveDouble(String key, double value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, Double.doubleToRawLongBits(value));
        editor.apply();
    }

    public void putBoolean(String key,boolean value){

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,value);
        editor.apply();

    }
    public boolean getBoolean(String key){
        return preferences.getBoolean(key,false);
    }
    public void remove(String key){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }
    public void clear(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}