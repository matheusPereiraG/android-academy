package com.itsector.android.popularmovies.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.model.MovieCollection;
import com.itsector.android.popularmovies.database.Repository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    //TODO: Get data to show on the view, invoke MovieClient

    private Context mContext;
    private MutableLiveData<MovieCollection> mMovieCol;
    private int mSelectedSortOption;

    public void loadPopularMovies() {
        Repository.getInstance().getPopularMovies(mMovieCol);
    }

    public void loadTopRatedMovies() {
        Repository.getInstance().getTopRatedMovies(mMovieCol);
    }

    private void loadFavoriteMovies() {
        Repository.getInstance().getFavorites(mContext, mMovieCol);
    }

    public MutableLiveData<MovieCollection> getMovieCollection() {
        if (mMovieCol == null) {
            mMovieCol = new MutableLiveData<MovieCollection>();
            //TODO: Get settings
            loadMovies();
        }
        return mMovieCol;
    }

    public void loadMovies() {
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

    public void setContext(Context c) {
        this.mContext = c;
    }
}
