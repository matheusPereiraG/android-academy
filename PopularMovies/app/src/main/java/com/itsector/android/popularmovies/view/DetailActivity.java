package com.itsector.android.popularmovies.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.itsector.android.popularmovies.R;
import com.itsector.android.popularmovies.databinding.ActivityDetailBinding;
import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.network.GlideModule;
import com.itsector.android.popularmovies.viewmodel.DetailActivityViewModel;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

        initActionBar();

        initViewModel();

        initViews();

    }

    private void initActionBar() {
        ActionBar ab = getSupportActionBar();
        try {
            ab.setTitle(R.string.detail_activity_label);
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }

        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(DetailActivityViewModel.class);
        mViewModel.setMovie(mMovie);
        mViewModel.getMovieDetails().observe(this, movie -> {
            mDataBinding.movieDurationTv.setText(movie.getRuntime()+"min");
        });
    }

    private void initViews() {
        mDataBinding.movieTitleTv.setText(mMovie.getTitle());
        mDataBinding.ratingTv.setText(mMovie.getVoteAverage()+"/10");

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(mMovie.getReleaseDate());
        int year = calendar.get(Calendar.YEAR);
        mDataBinding.releaseYearTv.setText(String.valueOf(year));

        mDataBinding.synopsisTv.setText(mMovie.getOverview());
        String url = GlideModule.buildUri(mMovie.getPosterPath());
        Glide.with(this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target,
                                                boolean isFirstResource) {
                        Log.e("TAG", "Error loading image", e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .fitCenter()
                .into(mDataBinding.moviePosterIv);
    }
}