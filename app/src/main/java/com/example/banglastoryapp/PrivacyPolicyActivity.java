package com.example.banglastoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class PrivacyPolicyActivity extends AppCompatActivity {

    TextView tvPrivacy;

    String htmlText = "<h2>What is Android?</h2>" + "<p> lorem ipsum dollar</p>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        tvPrivacy = findViewById(R.id.tvPrivacy);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Privacy Policy");
        }

        tvPrivacy.setText(HtmlCompat.fromHtml(htmlText, 0));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}