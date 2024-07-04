package com.einnovation.aryonlineshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.einnovation.aryonlineshop.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class StoryDetailsActivity extends AppCompatActivity {

    Button btnPrev, btnNext;
    TextView tvQuite, tvCount;

    private String[] quotes;
    private int currentIndex = 0;
    public static int categoryId = 0;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    int adsCount = 0;

    private AdView adView;
    private View adContainerView;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details);

        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        tvQuite = findViewById(R.id.tvQuite);
        tvCount = findViewById(R.id.tvCount);
        adContainerView = findViewById(R.id.adView1);

        new Thread(
                () -> {
                    MobileAds.initialize(this, initializationStatus -> {});
                })
                .start();

        sharedPreferences= getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Story");
        }

        switch (categoryId){
            case 0:
                quotes = QuoteUtils.getQuotes0();
                break;
            case 1:
                quotes = QuoteUtils.getQuotes1();
                break;
            case 2:
                quotes = QuoteUtils.getQuotes2();
                break;
            case 3:
                quotes = QuoteUtils.getQuotes3();
                break;
        }

        // Retrieve and decrypt the adsCount
        String encryptedAdsCount = sharedPreferences.getString("adsCount", "0");
        try {
            String decryptedAdsCount = decryptCount(encryptedAdsCount);
            adsCount = Integer.parseInt(decryptedAdsCount);
        } catch (Exception e) {
            e.printStackTrace();
            adsCount = 0; // Default to 0 if decryption fails
        }

        tvCount.setText(String.valueOf(adsCount));

        // Display the first quote initially
        tvQuite.setText(quotes[currentIndex]);



        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex > 0) {
                    btnNext.setEnabled(true);
                    currentIndex--;
                    tvQuite.setText(quotes[currentIndex]);
                    showIntAds();
                }else{
                    btnPrev.setEnabled(false);
                }
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex < quotes.length - 1) {
                    currentIndex++;
                    tvQuite.setText(quotes[currentIndex]);
                    showIntAds();
                    btnPrev.setEnabled(true);
                }else{
                    btnNext.setEnabled(false);
                }
            }
        });


        loadBanner();
        loadIntAds();
    }

    //========= On create end here ====================
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void saveEncryptedCount(int count) {
        try {
            String encryptedAdsCount = encryptCount(String.valueOf(count));
            editor.putString("adsCount", encryptedAdsCount);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String decryptCount(String encryptedCount) {
        byte[] decodedBytes = Base64.decode(encryptedCount, Base64.DEFAULT);
        return new String(decodedBytes);
    }

    private String encryptCount(String count) {
        return Base64.encodeToString(count.getBytes(), Base64.DEFAULT);
    }


    //======== Ads =======================================
    private AdSize getAdSize() {
        // Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;
        float adWidthPixels = adContainerView.getWidth();

        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    private void loadBanner() {
        adView = new AdView(this);
        adView.setAdUnitId(Const.admobBanId);
        adView.setAdSize(getAdSize());

        ((ViewGroup) adContainerView).addView(adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError);

            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                super.onAdOpened();
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                super.onAdClicked();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }
        });
    }

    private void loadIntAds(){
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,Const.admobIntId, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdClicked() {

                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                mInterstitialAd = null;
                                adsCount++;
                                saveEncryptedCount(adsCount);
                                tvCount.setText(String.valueOf(adsCount));
                                loadIntAds();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
    }

    private void showIntAds(){
        if (mInterstitialAd != null) {
            mInterstitialAd.show(StoryDetailsActivity.this);
        }else{
            loadIntAds();
        }
    }
}