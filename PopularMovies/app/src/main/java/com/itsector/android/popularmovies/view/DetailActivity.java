package com.itsector.android.popularmovies.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.itsector.android.popularmovies.R;
import com.itsector.android.popularmovies.databinding.ActivityDetailBinding;
import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.viewmodel.DetailActivityViewModel;
import com.itsector.android.popularmovies.viewmodel.MainActivityViewModel;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding mDataBinding;
    DetailActivityViewModel mViewModel;
    Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_detail);

        mMovie = getIntent().getExtras().getParcelable("Movie");

        initViewModel();

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(DetailActivityViewModel.class);
        mViewModel.setMovie(mMovie);
        mViewModel.getMovieDetails().observe(this, movie -> {
            mDataBinding.movieDurationTv.setText(movie.getRuntime()+"");
            mDataBinding.movieTitleTv.setText(movie.getTitle());
            mDataBinding.ratingTv.setText(movie.getVoteAverage()+"/10");
            mDataBinding.releaseYearTv.setText(movie.getReleaseDate());
            mDataBinding.synopsisTv.setText(movie.getOverview());
        });

    }

    private void initViews() {
        mDataBinding.movieTitleTv.setText(mMovie.getTitle());
        //mDataBinding.movieDurationTv.setText(mMovie.get);
    }
}