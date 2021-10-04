package com.itsector.android.popularmovies.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.itsector.android.popularmovies.R;
import com.itsector.android.popularmovies.model.Movie;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Movie m = getIntent().getExtras().getParcelable("Movie");
        Log.v(this.getClass().getSimpleName(), m.getOriginalTitle());
    }
}