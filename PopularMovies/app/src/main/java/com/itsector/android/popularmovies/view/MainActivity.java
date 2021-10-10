package com.itsector.android.popularmovies.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.itsector.android.popularmovies.R;
import com.itsector.android.popularmovies.adapter.MovieAdapter;
import com.itsector.android.popularmovies.model.MovieCollection;
import com.itsector.android.popularmovies.utils.EndlessRecyclerViewScrollListener;
import com.itsector.android.popularmovies.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private MovieAdapter mMovieAdapter;
    private MainActivityViewModel mainActivityViewModel;
    private RecyclerView mRv;
    private GridLayoutManager mLayoutManager;
    private Menu mMenu;
    private LinearProgressIndicator mProgressBar;
    private EndlessRecyclerViewScrollListener scrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewModel();

        initRecyclerView();

        mProgressBar = findViewById(R.id.progress_horizontal);
    }

    private void initViewModel() {

        int sortOption = getSavedPreference();

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.setSelectedSortOption(sortOption);
        mainActivityViewModel.getMovieCollection().observe(this, this::updateMovieList);
    }

    private void initRecyclerView() {
        mRv = findViewById(R.id.rv_movies);
        mMovieAdapter = new MovieAdapter(this);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLayoutManager = new GridLayoutManager(this, 3);
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new GridLayoutManager(this, 2);
        }

        mRv.setAdapter(mMovieAdapter);
        mRv.setLayoutManager(mLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(mainActivityViewModel.getSelectedSortOption() != 2){
                    mProgressBar.setVisibility(View.VISIBLE);
                    mainActivityViewModel.loadMovies();
                }
            }
        };

        mRv.addOnScrollListener(scrollListener);
    }

    private void updateMovieList(MovieCollection collection) {
        mMovieAdapter.setMoviesList(collection.getResults());
        if(collection.getCollectionType().equals("favorites")){
            mMovieAdapter.notifyDataSetChanged();
        }
        else {
            mMovieAdapter.notifyItemRangeChanged(collection.getNewMoviesStartIndex()
                    , collection.getItemsSize());
        }
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sort_by_popular) {
            if (mainActivityViewModel.getSelectedSortOption() != 0) {
                enableMenuItemCheckIcon(0);
                disableMenuItemCheckIcon(0);

                mRv.smoothScrollToPosition(0);
                mLayoutManager.scrollToPositionWithOffset(0, 0);

                mainActivityViewModel.setSelectedSortOption(0);
                mProgressBar.setVisibility(View.VISIBLE);
                mainActivityViewModel.loadMovies();

                savePreference(0);
            }

        }
        if (item.getItemId() == R.id.sort_by_top) {
            if (mainActivityViewModel.getSelectedSortOption() != 1) {
                enableMenuItemCheckIcon(1);
                disableMenuItemCheckIcon(1);

                mRv.smoothScrollToPosition(0);
                mLayoutManager.scrollToPositionWithOffset(0, 0);

                mainActivityViewModel.setSelectedSortOption(1);
                mProgressBar.setVisibility(View.VISIBLE);
                mainActivityViewModel.loadMovies();

                savePreference(1);
            }
        }
        if (item.getItemId() == R.id.sort_by_favorites) {
            if (mainActivityViewModel.getSelectedSortOption() != 2) {
                enableMenuItemCheckIcon(2);
                disableMenuItemCheckIcon(2);

                mRv.smoothScrollToPosition(0);
                mLayoutManager.scrollToPositionWithOffset(0, 0);


                mainActivityViewModel.setSelectedSortOption(2);
                mProgressBar.setVisibility(View.GONE);
                mainActivityViewModel.loadMovies();

                savePreference(2);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Function that saves preferable sorting option
     *
     * @param preferenceValue (0 for popular movies, 1 for top rated and 2 for favorites)
     */
    private void savePreference(int preferenceValue) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.preference_sort_key), preferenceValue);
        editor.apply();
    }

    private int getSavedPreference() {
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        int defaultValue = getResources().getInteger(R.integer.sort_by_default_key);
        return pref.getInt(getString(R.string.preference_sort_key), defaultValue);
    }

    private void disableMenuItemCheckIcon(int selectedOption) {

        if (selectedOption == 0) { //Popular
            MenuItem topOption = (MenuItem) mMenu.findItem(R.id.sort_by_top);
            MenuItem favOption = (MenuItem) mMenu.findItem(R.id.sort_by_favorites);
            topOption.setIcon(0);
            favOption.setIcon(0);
        }
        if (selectedOption == 1) { //Top rated
            MenuItem popularOption = (MenuItem) mMenu.findItem(R.id.sort_by_popular);
            MenuItem favOption = (MenuItem) mMenu.findItem(R.id.sort_by_favorites);
            popularOption.setIcon(0);
            favOption.setIcon(0);
        }
        if (selectedOption == 2) { //Favorites
            MenuItem popularOption = (MenuItem) mMenu.findItem(R.id.sort_by_popular);
            MenuItem topOption = (MenuItem) mMenu.findItem(R.id.sort_by_top);
            popularOption.setIcon(0);
            topOption.setIcon(0);
        }
    }

    private void enableMenuItemCheckIcon(int selectedOption){
        if (selectedOption == 0) {
            MenuItem item = mMenu.findItem(R.id.sort_by_popular);
            item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_sort_by_check));
        }
        if (selectedOption == 1) {
            MenuItem item = mMenu.findItem(R.id.sort_by_top);
            item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_sort_by_check));
        }
        if (selectedOption == 2) {
            MenuItem item = mMenu.findItem(R.id.sort_by_favorites);
            item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_sort_by_check));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        int prefOption = getSavedPreference();
        mMenu = menu;
        enableMenuItemCheckIcon(prefOption);

        return true;
    }
}