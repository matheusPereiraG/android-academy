package com.itsector.android.popularmovies.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.model.TrailerCollection;
import com.itsector.android.popularmovies.network.MovieClient;

public class DetailActivityViewModel extends ViewModel {
    private MutableLiveData<Movie> mMovieDetails;
    private MutableLiveData<TrailerCollection> mMovieTrailers;
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

    private void loadTrailers() {
        MovieClient.getInstance().getMovieTrailers(mMovieTrailers, mMovie.getId());
    }

    private void loadDetails(){
        MovieClient.getInstance().getMovieDetails(mMovieDetails, mMovie.getId());
    }

    public void setMovie(Movie movie){
        mMovie = movie;
    }
}
