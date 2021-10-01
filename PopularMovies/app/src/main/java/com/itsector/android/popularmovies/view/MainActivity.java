package com.itsector.android.popularmovies.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.util.FixedPreloadSizeProvider;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.itsector.android.popularmovies.R;
import com.itsector.android.popularmovies.adapter.MovieAdapter;
import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private MovieAdapter mMovieAdapter;
    private MainActivityViewModel mainActivityViewModel;
    private RecyclerView mRv;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRv = findViewById(R.id.rv_movies);
        mMovieAdapter = new MovieAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        mRv.setAdapter(mMovieAdapter);
        mRv.setLayoutManager(layoutManager);
        mRv.addOnScrollListener(new MainActivityViewModel.ScrollListener(layoutManager));

        mProgressBar = findViewById(R.id.progress_bar);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getMovieList().observe(this, movies -> {
            mMovieAdapter.setMovieList(movies);
            mMovieAdapter.notifyDataSetChanged();
            mProgressBar.setVisibility(View.GONE);
        });


    }
}