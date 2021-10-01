package com.itsector.android.popularmovies.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.network.MovieClient;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    //TODO: Get data to show on the view, invoke MovieClient

    public MutableLiveData<List<Movie>> mMovieList;

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


    public static class ScrollListener extends RecyclerView.OnScrollListener {
        private boolean loading = true;
        int pastVisiblesItems, visibleItemCount, totalItemCount;
        private GridLayoutManager mLayoutManager;

        public ScrollListener(GridLayoutManager layoutManager) {
            super();
            this.mLayoutManager = layoutManager;
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
                        Log.v("...", "Last Item Wow !");
                        // Do pagination.. i.e. fetch new data

                        loading = true;
                    }
                }
            }
        }
    }
}
