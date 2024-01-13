package com.gdalamin.bcs_pro;

import android.app.Application;

import com.onesignal.OneSignal;



public class ApplicationClass extends Application {

    private static final String ONESIGNAL_APP_ID = "224d739c-e76a-4b14-a1fa-abda6972ccc6";

    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);


    }
}
