package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String weatherForTheDay = getIntent().getExtras().get(Intent.EXTRA_TEXT).toString();
        Toast.makeText(this, weatherForTheDay, Toast.LENGTH_LONG).show();
    }
}