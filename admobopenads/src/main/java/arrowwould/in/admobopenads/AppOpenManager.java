package arrowwould.in.admobopenads;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

public class AppOpenManager implements LifecycleObserver, Application.ActivityLifecycleCallbacks {

    private static final String LOG_TAG = "AppOpenManager";
    private static String AD_UNIT_ID;
    private AppOpenAd appOpenAd = null;
    private static boolean isShowingAds = false;

    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private final Application myApplication;
    private Activity currentActivity;

    public AppOpenManager(Application myApplication, String adId) {
        AD_UNIT_ID = adId;
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        showAdIfAvailable();
    }

    /**
     * Request an ad
     */
    public void fetchAd() {
        if (isAdAvailable()) {
            return;
        }

        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                AppOpenManager.this.appOpenAd = appOpenAd;
                Log.d(LOG_TAG, "Ad was loaded.");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.d(LOG_TAG, "Ad failed to load: " + loadAdError.getMessage());
            }
        };

        AdRequest adRequest = getAdRequest();
        AppOpenAd.load(myApplication, AD_UNIT_ID, adRequest, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }

    public void showAdIfAvailable() {
        if (!isShowingAds && isAdAvailable()) {
            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    isShowingAds = false;
                    Log.d(LOG_TAG, "Ad failed to show: " + adError.getMessage());
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    isShowingAds = true;
                    Log.d(LOG_TAG, "Ad showed fullscreen content.");
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    AppOpenManager.this.appOpenAd = null;
                    isShowingAds = false;
                    Log.d(LOG_TAG, "Ad was dismissed.");
                    fetchAd();
                }

                @Override
                public void onAdImpression() {
                    Log.d(LOG_TAG, "Ad impression logged.");
                }
            };

            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(currentActivity);
        } else {
            fetchAd();
        }
    }

    /**
     * Creates and returns ad request.
     */
    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    /**
     * Utility method that checks if ad exists and can be shown.
     */
    public boolean isAdAvailable() {
        return appOpenAd != null;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {}

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {}

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        if (currentActivity == activity) {
            currentActivity = null;
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {}

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (currentActivity == activity) {
            currentActivity = null;
        }
    }
}


//package arrowwould.in.admobopenads;
//
//import android.app.Activity;
//import android.app.Application;
//import android.os.Bundle;
//import android.util.Log;
//
//import com.google.android.gms.ads.AdError;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.FullScreenContentCallback;
//import com.google.android.gms.ads.LoadAdError;
//import com.google.android.gms.ads.appopen.AppOpenAd;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.lifecycle.Lifecycle;
//import androidx.lifecycle.LifecycleObserver;
//import androidx.lifecycle.OnLifecycleEvent;
//import androidx.lifecycle.ProcessLifecycleOwner;
//
//public class AppOpenManager  implements LifecycleObserver, Application.ActivityLifecycleCallbacks {
//
//    private static final String LOG_TAG = "AppOpenManager";
//    private static String AD_UNIT_ID;
//    private AppOpenAd appOpenAd = null;
//    private static boolean isShowingAds = false;
//
//    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
//
//    private Application myApplication;
//
//    private Activity currentActivity;
//
//    public AppOpenManager(Application myApplication, String adId) {
//        AD_UNIT_ID = adId;
//        this.myApplication = myApplication;
//        this.myApplication.registerActivityLifecycleCallbacks(this);
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    public void onStart() {
//        showAdIfAvailable();
//    }
//
//    /**
//     * Request an ad
//     */
//    public void fetchAd() {
//
//        if (isAdAvailable()) {
//            return;
//        }
//        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
//                super.onAdLoaded(appOpenAd);
//                AppOpenManager.this.appOpenAd = appOpenAd;
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//                Log.d("POENAD", "onAdFailedToLoad: " + loadAdError.getMessage());
//            }
//        };
//        AdRequest adRequest = getAdRequest();
//        AppOpenAd.load(myApplication,
//                AD_UNIT_ID, adRequest,
//                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
//
//    }
//
//    public void showAdIfAvailable() {
//        if (!isShowingAds && isAdAvailable()) {
//            FullScreenContentCallback fullScreenContentCallback =
//                    new FullScreenContentCallback() {
//                        @Override
//                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
//                            super.onAdFailedToShowFullScreenContent(adError);
//                        }
//
//                        @Override
//                        public void onAdShowedFullScreenContent() {
//                            super.onAdShowedFullScreenContent();
//                            isShowingAds = true;
//                        }
//
//                        @Override
//                        public void onAdDismissedFullScreenContent() {
//                            super.onAdDismissedFullScreenContent();
//                            AppOpenManager.this.appOpenAd = null;
//                            isShowingAds = false;
//                            fetchAd();
//                        }
//
//                        @Override
//                        public void onAdImpression() {
//                            super.onAdImpression();
//                        }
//                    };
//
//            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
//            appOpenAd.show(currentActivity);
//        }
//        else
//            fetchAd();
//    }
//
//    /**
//     * Creates and returns ad request.
//     */
//    private AdRequest getAdRequest() {
//        return new AdRequest.Builder().build();
//    }
//
//    /**
//     * Utility method that checks if ad exists and can be shown.
//     */
//    public boolean isAdAvailable() {
//        return appOpenAd != null;
//    }
//
//    @Override
//    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
//
//    }
//
//    @Override
//    public void onActivityStarted(@NonNull Activity activity) {
//        currentActivity = activity;
//    }
//
//    @Override
//    public void onActivityResumed(@NonNull Activity activity) {
//        currentActivity = activity;
//
//    }
//
//    @Override
//    public void onActivityPaused(@NonNull Activity activity) {
//
//    }
//
//    @Override
//    public void onActivityStopped(@NonNull Activity activity) {
//
//    }
//
//    @Override
//    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
//
//    }
//
//    @Override
//    public void onActivityDestroyed(@NonNull Activity activity) {
//        currentActivity = null;
//
//    }
//}
//
