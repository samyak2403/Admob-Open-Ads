package arrowwould.in.admobopenads;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

import arrowwould.in.admobopenads.AppOpenManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this);

        new AppOpenManager(this, "ca-app-pub-3940256099942544/3419835294");

    }

}
