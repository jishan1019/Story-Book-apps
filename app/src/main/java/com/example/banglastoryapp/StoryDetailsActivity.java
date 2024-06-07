package com.example.banglastoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StoryDetailsActivity extends AppCompatActivity {

    Button btnPrev, btnNext;
    TextView tvQuite, tvCount;

    private String[] quotes;
    private int currentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details);

        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        tvQuite = findViewById(R.id.tvQuite);
        tvCount = findViewById(R.id.tvCount);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Story Details");
        }

        quotes = QuoteUtils.getQuotes1();

        // Display the first quote initially
        tvQuite.setText(quotes[currentIndex]);

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex > 0) {
                    currentIndex--;
                    tvQuite.setText(quotes[currentIndex]);
                }
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex < quotes.length - 1) {
                    currentIndex++;
                    tvQuite.setText(quotes[currentIndex]);
                }
            }
        });



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
}