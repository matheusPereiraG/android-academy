package com.itsector.android.popularmovies.viewmodel;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itsector.android.popularmovies.model.MovieCollection;
import com.itsector.android.popularmovies.database.Repository;
import com.itsector.android.popularmovies.utils.NetworkUtils;


public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<MovieCollection> mMovieCol;
    private int mSelectedSortOption;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadPopularMovies() {
        Repository.getInstance().getPopularMovies(mMovieCol);
    }

    public void loadTopRatedMovies() {
        Repository.getInstance().getTopRatedMovies(mMovieCol);
    }

    private void loadFavoriteMovies() {
        Repository.getInstance().getFavorites(getApplication().getApplicationContext(), mMovieCol);
    }

    public MutableLiveData<MovieCollection> getMovieCollection() {
        if (mMovieCol == null) {
            mMovieCol = new MutableLiveData<MovieCollection>();
            loadMovies();
        }
        return mMovieCol;
    }

    public void loadMovies() {
        if(mSelectedSortOption != 2)
            NetworkUtils.checkInternetConnection(getApplication().getApplicationContext());
        if (mSelectedSortOption == 0)
            loadPopularMovies();
        if (mSelectedSortOption == 1)
            loadTopRatedMovies();
        if (mSelectedSortOption == 2)
            loadFavoriteMovies();
    }


    public void setSelectedSortOption(int option) {
        this.mSelectedSortOption = option;
    }

    public int getSelectedSortOption() {
        return this.mSelectedSortOption;
    }

}
