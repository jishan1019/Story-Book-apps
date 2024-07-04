package com.einnovation.aryonlineshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.einnovation.aryonlineshop.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap <String, String> hashMap;
    Button privacyBtn;
    private AdView adView;
    private View adContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        privacyBtn = findViewById(R.id.privacyBtn);

        adContainerView = findViewById(R.id.adView);

        new Thread(
                () -> {
                    MobileAds.initialize(this, initializationStatus -> {});
                })
                .start();


//        hashMap = new HashMap<>();
//        hashMap.put("categoryTitle", "Golpo 1");
//        hashMap.put("categoryName", "Vuter Golpo");
//        hashMap.put("categoryImg", String.valueOf(R.drawable.icon));
//        arrayList.add(hashMap);


        hashMap = new HashMap<>();
        hashMap.put("categoryImg", String.valueOf(R.drawable.cat1));
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("categoryImg", String.valueOf(R.drawable.cat2));
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("categoryImg", String.valueOf(R.drawable.cat3));
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("categoryImg", String.valueOf(R.drawable.cat4));
        arrayList.add(hashMap);


        MyAdaptar myAdaptar = new MyAdaptar();
        gridView.setAdapter(myAdaptar);

        privacyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
            }
        });

        loadBanner();

    }

    private class MyAdaptar extends BaseAdapter{
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.story_card, parent, false);

            CardView storyBtn = view.findViewById(R.id.storyBtn);
//            TextView tvTitle = view.findViewById(R.id.tvTitle);
//            TextView tvName = view.findViewById(R.id.tvName);
            ImageView tvImg = view.findViewById(R.id.tvImg);



            HashMap<String, String> hashMap = arrayList.get(position);

//            String categoryTitle = hashMap.get("categoryTitle");
//            String categoryName = hashMap.get("categoryName");
            int categoryImage = Integer.parseInt(hashMap.get("categoryImg"));

//            tvTitle.setText(categoryTitle);
//            tvName.setText(categoryName);
            tvImg.setImageResource(categoryImage);

            storyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StoryDetailsActivity.categoryId = position;
                    startActivity(new Intent(MainActivity.this, StoryDetailsActivity.class));
                }
            });

            return view;
        }
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


}