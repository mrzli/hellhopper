package com.turbogerm.hellhopper;

import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.init.InitData;

public class MainActivity extends AndroidApplication {
    
//    private static final String ADMOB_PUBLISHER_ID = "a151d9baa31e05a";
//    
//    private static final String[] TEST_DEVICE_IDS;
//    
//    static {
//        TEST_DEVICE_IDS = new String[] {
//                "353167050265271",
//                "G5S6RC9341004500"
//        };
//    }
//    
//    private InterstitialAd mInterstitialAd;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        cfg.useAccelerometer = true;
        cfg.useCompass = false;
        
        InitData initData = new InitData();
        
//        mInterstitialAd = new InterstitialAd(this, ADMOB_PUBLISHER_ID);
//        mInterstitialAd.loadAd(getAdRequest());
//        mInterstitialAd.setAdListener(getInterstitionAdListener());
//        
//        setWindowFeatures();
//        
//        RelativeLayout layout = new RelativeLayout(this);
//        View gameView = initializeForView(new HellHopper(initData), cfg.useGL20);
//        
//        AdView adView = new AdView(this, AdSize.SMART_BANNER, ADMOB_PUBLISHER_ID);
//        adView.loadAd(getAdRequest());
        
        initialize(new HellHopper(initData), cfg);
    }
    
//    private static AdRequest getAdRequest() {
//        AdRequest adRequest = new AdRequest();
//        adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
//        for (String testDeviceId : TEST_DEVICE_IDS) {
//            adRequest.addTestDevice(testDeviceId);
//        }
//        
//        return adRequest;
//    }
//    
//    private void setWindowFeatures() {
//        try {
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
//        } catch (Exception ex) {
//            log("AndroidApplication", "Content already displayed, cannot request FEATURE_NO_TITLE", ex);
//        }
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//    }
//    
//    private AdListener getInterstitionAdListener() {
//        return new AdListener() {
//            
//            @Override
//            public void onReceiveAd(Ad ad) {
//                Logger.debug("Recieved ad");
//                mInterstitialAd.show();
//            }
//            
//            @Override
//            public void onPresentScreen(Ad ad) {
//            }
//            
//            @Override
//            public void onLeaveApplication(Ad ad) {
//            }
//            
//            @Override
//            public void onFailedToReceiveAd(Ad ad, ErrorCode error) {
//                Logger.debug("Failed to recieve ad");
//            }
//            
//            @Override
//            public void onDismissScreen(Ad ad) {
//            }
//        };
//    }
}