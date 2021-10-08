package com.itsector.android.popularmovies.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.model.ReviewCollection;
import com.itsector.android.popularmovies.model.TrailerCollection;
import com.itsector.android.popularmovies.database.Repository;

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
        Repository.getInstance().getMovieTrailers(mMovieTrailers, mMovie.getId());
    }

    public void loadReviews() {
        Repository.getInstance().getMovieReviews(mMovieReviews, mMovie.getId());
    }

    private void loadDetails(){
        Repository.getInstance().getMovieDetails(mMovieDetails, mMovie.getId());
    }

    public void setMovie(Movie movie){
        mMovie = movie;
    }


}
