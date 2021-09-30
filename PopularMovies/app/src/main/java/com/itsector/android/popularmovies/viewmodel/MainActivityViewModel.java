package com.itsector.android.popularmovies.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.network.MovieClient;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    //TODO: Get data to show on the view, invoke MovieClient

    private MutableLiveData<List<Movie>> mMovieList;

    public MutableLiveData<List<Movie>> getMovieList(){
        if (mMovieList == null) {
            mMovieList = new MutableLiveData<>();
            loadTopRatedMovies();
        }
        return mMovieList;
    }

    private void loadTopRatedMovies() {
        mMovieList = MovieClient.getInstance().getTopRatedMovies();
    }
}
