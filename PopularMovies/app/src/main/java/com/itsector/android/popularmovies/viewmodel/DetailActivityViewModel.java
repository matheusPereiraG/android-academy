package com.itsector.android.popularmovies.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.model.ReviewCollection;
import com.itsector.android.popularmovies.model.TrailerCollection;
import com.itsector.android.popularmovies.network.MovieClient;

public class DetailActivityViewModel extends ViewModel {
    private MutableLiveData<Movie> mMovieDetails;
    private MutableLiveData<TrailerCollection> mMovieTrailers;
    private MutableLiveData<ReviewCollection> mMovieReviews;
    private Movie mMovie;

    public MutableLiveData<Movie> getMovieDetails() {
        if (mMovieDetails == null) {
            mMovieDetails = new MutableLiveData<Movie>();
            loadDetails();
        }
        return mMovieDetails;
    }

    public MutableLiveData<TrailerCollection> getMovieTrailers() {
        if (mMovieTrailers == null) {
            mMovieTrailers = new MutableLiveData<TrailerCollection>();
            loadTrailers();
        }
        return mMovieTrailers;
    }

    public MutableLiveData<ReviewCollection> getMovieReviews() {
        if (mMovieReviews == null) {
            mMovieReviews = new MutableLiveData<ReviewCollection>();
            loadReviews();
        }
        return mMovieReviews;
    }

    private void loadTrailers() {
        MovieClient.getInstance().getMovieTrailers(mMovieTrailers, mMovie.getId());
    }

    private void loadReviews() {
        MovieClient.getInstance().getMovieReviews(mMovieTrailers, mMovie.getId());
    }

    private void loadDetails(){
        MovieClient.getInstance().getMovieDetails(mMovieDetails, mMovie.getId());
    }

    public void setMovie(Movie movie){
        mMovie = movie;
    }


    public static class ScrollListener extends RecyclerView.OnScrollListener {
        private boolean loading = true;
        int pastVisiblesItems, visibleItemCount, totalItemCount;
        private LinearLayoutManager mLayoutManager;
        private DetailActivityViewModel mViewModel;

        public ScrollListener(LinearLayoutManager layoutManager, DetailActivityViewModel model) {
            super();
            this.mLayoutManager = layoutManager;
            this.mViewModel = model;
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

            if (dy > 0) { //check for scroll down
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        Log.v("Scrolled down", "Scrolled down");
                        mViewModel.loadReviews();
                        loading = true;
                    }
                }
            }
        }
    }


}
