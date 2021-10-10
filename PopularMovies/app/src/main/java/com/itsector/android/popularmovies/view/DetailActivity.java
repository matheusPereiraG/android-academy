package com.itsector.android.popularmovies.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.Snackbar;
import com.itsector.android.popularmovies.R;
import com.itsector.android.popularmovies.adapter.ReviewAdapter;
import com.itsector.android.popularmovies.databinding.ActivityDetailBinding;
import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.model.Trailer;
import com.itsector.android.popularmovies.network.GlideModule;
import com.itsector.android.popularmovies.viewmodel.DetailActivityViewModel;
import com.itsector.android.popularmovies.viewmodel.MainActivityViewModel;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityDetailBinding mDataBinding;
    DetailActivityViewModel mViewModel;
    Movie mMovie;
    ReviewAdapter mReviewAdapter;
    LinearLayoutManager mLayoutManager;
    boolean isFavBtnEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_detail);
        mDataBinding.setLifecycleOwner(this);

        mMovie = getIntent().getExtras().getParcelable("Movie");

        initActionBar();

        initViewModel();

        initViews();

    }

    private void initActionBar() {
        ActionBar ab = getSupportActionBar();
        try {
            ab.setTitle(R.string.detail_activity_label);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(DetailActivityViewModel.class);


        mViewModel.setMovie(mMovie);
        mViewModel.getMovieDetails().observe(this, movie -> {
            mDataBinding.movieDurationTv.setText(movie.getRuntime() + "min");
            mViewModel.setMovie(movie);
        });

        mViewModel.getMovieTrailers().observe(this, trailerCollection -> {
            trailerCollection.getResults().forEach(this::initTrailerFragment);
        });

        mViewModel.getMovieReviews().observe(this, reviewCollection -> {
            mReviewAdapter.setReviewList(reviewCollection.getResults());
            mReviewAdapter.notifyDataSetChanged();
        });

        mViewModel.getIsFav(this).observe(this, this::setupFavoriteButtonView);
    }

    private void setupFavoriteButtonView(Boolean isFav) {
        if(Boolean.TRUE.equals(isFav)){
            mDataBinding.favoriteBtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_on));
            isFavBtnEnabled = true;
        }
    }

    private void initViews() {
        mDataBinding.movieTitleTv.setText(mMovie.getTitle());
        mDataBinding.ratingTv.setText(mMovie.getVoteAverage() + "/10");

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

        //Init recycler view for reviews
        mReviewAdapter = new ReviewAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        mDataBinding.reviewsRv.setAdapter(mReviewAdapter);
        mDataBinding.reviewsRv.setLayoutManager(mLayoutManager);

        mDataBinding.reviewsRv.setNestedScrollingEnabled(false);
        mDataBinding.detailNestedScroll.setOnScrollChangeListener(
                (NestedScrollView.OnScrollChangeListener)
                        (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                            if (v.getChildAt(v.getChildCount() - 1) != null) {
                                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight()
                                        - v.getMeasuredHeight())) &&
                                        scrollY > oldScrollY) {
                                    if (mDataBinding.reviewsRv.getVisibility() == View.VISIBLE) {
                                        mViewModel.loadReviews();
                                    }
                                }
                            }
                        });

        mDataBinding.reviewsBtn.setOnClickListener(this);
        mDataBinding.trailersBtn.setOnClickListener(this);
        mDataBinding.favoriteBtn.setOnClickListener(this);
    }

    private void initTrailerFragment(Trailer trailer) {
        TrailerFragment trailerFrag = new TrailerFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("Trailer", trailer);
        trailerFrag.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.trailers_container, trailerFrag, null)
                .commit();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.reviews_btn) {
            mDataBinding.trailersContainer.setVisibility(View.GONE);
            mDataBinding.reviewsRv.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.trailers_btn) {
            mDataBinding.reviewsRv.setVisibility(View.GONE);
            mDataBinding.trailersContainer.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.favorite_btn) {
            if (isFavBtnEnabled) {
                mDataBinding.favoriteBtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_off));
                mViewModel.removeFavorite(this);
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
            } else {
                mDataBinding.favoriteBtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_on));
                mViewModel.addFavorite(this);
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
            }
            isFavBtnEnabled = !isFavBtnEnabled;
        }
    }
}