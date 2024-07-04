package com.einnovation.aryonlineshop;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

public class MyContent extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(
                () -> {
                    MobileAds.initialize(this, initializationStatus -> {});
                })
                .start();
    }


}
