package com.itsector.android.popularmovies.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itsector.android.popularmovies.model.MovieCollection;
import com.itsector.android.popularmovies.network.MovieClient;

public class MainActivityViewModel extends ViewModel {

    //TODO: Get data to show on the view, invoke MovieClient

    private MutableLiveData<MovieCollection> mMovieCol;

    public void loadPopularMovies() {
        MovieClient.getInstance().getPopularMovies(mMovieCol);
    }

    public MutableLiveData<MovieCollection> getMovieCollection(){
        if(mMovieCol == null){
            mMovieCol = new MutableLiveData<MovieCollection>();
            //TODO: Get settings
            loadPopularMovies();
        }
        return mMovieCol;
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
                        MovieClient.CURRENT_PAGE += 1;
                        mViewModel.loadPopularMovies();
                        loading = true;
                    }
                }
            }
        }
    }
}
