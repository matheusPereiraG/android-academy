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
    private MutableLiveData<List<Movie>> mFavorites;
    private int mSelectedSortOption;

    public void loadPopularMovies() {
        Repository.getInstance().getPopularMovies(mMovieCol);
    }

    public void loadTopRatedMovies() {
        Repository.getInstance().getTopRatedMovies(mMovieCol);
    }

    private void loadFavoriteMovies() {
        Repository.getInstance().getFavorites(mContext, mFavorites);
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


    public static class ScrollListener extends RecyclerView.OnScrollListener {
        private boolean loading = true;
        int pastVisiblesItems, visibleItemCount, totalItemCount;
        private GridLayoutManager mLayoutManager;
        private MainActivityViewModel mViewModel;

        public ScrollListener(GridLayoutManager layoutManager, MainActivityViewModel model) {
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
                        Repository.CURRENT_PAGE += 1;
                        mViewModel.loadMovies();
                        loading = true;
                    }
                }
            }
        }
    }
}
