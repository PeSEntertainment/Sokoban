package com.pes.sokoban;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.badlogic.gdx.utils.Array;
import com.pes.sokoban.Interfaces.Platform;

import static com.pes.sokoban.Global.Game.FULLVERSION;
import static com.pes.sokoban.Global.Game.actualMIDI;
import static com.pes.sokoban.Global.Game.versionCode;
import static com.pes.sokoban.Global.Game.musicVolume;

public class Android implements Platform, BillingProcessor.IBillingHandler  {
    private MediaPlayer mediaPlayer;
    private Array<Integer> MIDIs;
    private AndroidLauncher launcher;
    private Activity activity;
    private boolean readyToPurchase;
    public BillingProcessor bp;

    public static final String PRODUCT_ID = "com.pes.sokoban10.full";
    public static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz3Ia+D38qPwvqTx0YR8eyVNoDsL1AD6cB5CXYaqPz0nkHo4PueUGPvnT05tQj5C7NjxHRIItGbB2idYf+/J/NQ5JxvEcAie08QTY8w5sEYwgVlcM6+rq6euruUOfzX8Qv+IkJ9Iqv+3SVISSRTF6q0PXpkViCxAVC+WqDMXCrwleYqJcU1W3qK9W0KJiBh4IjWtFuyFb2UTQ7yyAe58Q5qrclX7jkaLE9IyOnE1bvId6fpCb6g6tWpXqIlBJG+rZiqDLzMoj32gc7f2qzlTN65Jl1ngJHnRiuN/gO/iMJE/VG+oj+7Vwzv1nM8QF/TMTIh8etxHqilp2+k5UAk9FAQIDAQAB";
//    public static final String MERCHANT_ID="08118349747632329075";
    public static final String MERCHANT_ID=null;


    Android(AndroidLauncher launcher) {
        this.launcher = launcher;
        mediaPlayer = null;
        MIDIs = new Array<>(10);
        MIDIs.add(R.raw.broken_arm);
        MIDIs.add(R.raw.casual_afternoon);
        MIDIs.add(R.raw.icy_garden);
        MIDIs.add(R.raw.journey_forgotten);
        MIDIs.add(R.raw.no_one);
        MIDIs.add(R.raw.without_time);
        MIDIs.add(R.raw.old_friends);
        MIDIs.add(R.raw.the_road_you_use);
        MIDIs.add(R.raw.we_have_to_do_something);

        // Create connection to Google play billing
        if (!BillingProcessor.isIabServiceAvailable(launcher)) {
            showToast("In-app billing service is unavailable, please upgrade Android Market/Play to version >= 3.9.16");
        }
        bp = new BillingProcessor(launcher.getApplicationContext(), LICENSE_KEY, MERCHANT_ID, this);
        bp.initialize();
    }

    @Override
    public void getPurchased() {
//        SkuDetails skuDetails = bp.getPurchaseListingDetails(PRODUCT_ID);
        if (bp.isPurchased(PRODUCT_ID)) {
            versionCode = 201903;
            FULLVERSION = true;
        }
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
//        showToast("product Purchased: " + productId);
        versionCode = 201903;
        FULLVERSION = true;
    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
//        showToast("onBillingError: " + Integer.toString(errorCode));
    }

    @Override
    public void onBillingInitialized() {
//        showToast("on Billing Initialized");
        readyToPurchase = true;
    }

    @Override
    public void onPurchaseHistoryRestored() {
//        showToast("onPurchaseHistoryRestored");
/*
				for(String sku : bp.listOwnedProducts())
					Log.d(LOG_TAG, "Owned Managed Product: " + sku);
				for(String sku : bp.listOwnedSubscriptions())
					Log.d(LOG_TAG, "Owned Subscription: " + sku);
*/
    }

    public void showToast(String message) {
        Toast.makeText(launcher, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void purchase() {
        bp.purchase(launcher, PRODUCT_ID);
    }


    @Override
    public void setOrientation(String string) {
        if (string.equals("landscape")){
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        else if (string.equals("portrait")){
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        else
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }



    @Override
    public void change() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(launcher, MIDIs.get(actualMIDI));
            mediaPlayer.setLooping(true);
            setVolume(musicVolume);
        }
    }


    @Override
    public void playNext() {
        actualMIDI++;
        if (actualMIDI > 5) actualMIDI = 0;
        change();
    }

   @Override
    public boolean isPlaying() {
        if (mediaPlayer!=null) return mediaPlayer.isPlaying();
        else return false;
    }

    @Override
    public void release() {
        if (mediaPlayer!=null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void setVolume(float volume) {
        if (mediaPlayer!=null) mediaPlayer.setVolume(volume, volume);
    }

    @Override
    public void play() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(launcher, MIDIs.get(actualMIDI));
            mediaPlayer.setLooping(true);
            setVolume(musicVolume);
        }
        if (!mediaPlayer.isPlaying()) mediaPlayer.start();
    }

    @Override
    public void pause() {
        if (mediaPlayer!=null) mediaPlayer.pause();
    }

    @Override
    public void stop() {
        if (mediaPlayer!=null) if (mediaPlayer.isPlaying()) mediaPlayer.stop();
    }



}
